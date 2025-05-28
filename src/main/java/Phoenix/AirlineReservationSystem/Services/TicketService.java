package Phoenix.AirlineReservationSystem.Services;

import Phoenix.AirlineReservationSystem.Dto.Request.TicketRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.TicketResponse;
import Phoenix.AirlineReservationSystem.Exceptions.ResourceNotFoundException;
import Phoenix.AirlineReservationSystem.Model.Customer;
import Phoenix.AirlineReservationSystem.Model.Flight;
import Phoenix.AirlineReservationSystem.Model.Ticket;
import Phoenix.AirlineReservationSystem.Repository.CustomerRepo;
import Phoenix.AirlineReservationSystem.Repository.FlightRepo;
import Phoenix.AirlineReservationSystem.Repository.TicketRepo;
import Phoenix.AirlineReservationSystem.Transformer.TicketTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    FlightRepo flightRepo;
    private String generateTicketId() {
        Ticket lastTicket = ticketRepo.findTopByOrderByTicketIdDesc();

        int nextId = 1;
        if (lastTicket != null) {
            String lastId = lastTicket.getTicketId().substring(2);
            nextId = Integer.parseInt(lastId) + 1;
        }

        return String.format("TK%03d", nextId); // CS001, CS002, etc.
    }
    public TicketResponse bookTicket(TicketRequest ticketRequest){

        Optional<Flight>flight=flightRepo.findByFlightId(ticketRequest.getFlightId());
        if(flight.isEmpty()){
            throw new ResourceNotFoundException("Flight Not Found");
        }
        Optional<Customer> customer=customerRepo.findByCustomerId(ticketRequest.getCustomerId());
        if(customer.isEmpty()){
            throw  new ResourceNotFoundException("Customer Not Found");
        }

        Ticket ticket= TicketTransformer.ticketRequestToTicket(ticketRequest);
        ticket.setTicketId(generateTicketId());
        ticket.setCustomer(customer.get());
        ticket.setFlight(flight.get());
        int seats=flight.get().getSeats();
        flight.get().setSeats(seats-1);
        flightRepo.save(flight.get());
        ticketRepo.save(ticket);
        return TicketTransformer.ticketToTicketResponse(ticket,flight.get(),customer.get());
    }
    public TicketResponse getTicketById(String id){
        Optional<Ticket> ticket=ticketRepo.findById(id);
        if(ticket.isEmpty()){
            throw new ResourceNotFoundException("Ticket Not Found");
        }
        return TicketTransformer.ticketToTicketResponse(ticket.get(),ticket.get().getFlight(),ticket.get().getCustomer());

    }
    @Transactional
    public TicketResponse cancelTicket(String Id){
        Optional<Ticket> ticket=ticketRepo.findById(Id);
        if(ticket.isEmpty()){
            throw new ResourceNotFoundException("Ticket Not Found");
        }
        ticketRepo.delete(ticket.get());
        Flight flight=ticket.get().getFlight();
        int seats=flight.getSeats();
        flight.setSeats(seats+1);
        flightRepo.save(flight);
        return TicketTransformer.ticketToTicketResponse(ticket.get(),ticket.get().getFlight(),ticket.get().getCustomer());


    }
}
