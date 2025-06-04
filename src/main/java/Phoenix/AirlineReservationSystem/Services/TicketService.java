package Phoenix.AirlineReservationSystem.Services;

import Phoenix.AirlineReservationSystem.Dto.Request.TicketRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.TicketResponse;
import Phoenix.AirlineReservationSystem.Exceptions.AccessDeniedException;
import Phoenix.AirlineReservationSystem.Exceptions.ResourceNotFoundException;
import Phoenix.AirlineReservationSystem.Model.Customer;
import Phoenix.AirlineReservationSystem.Model.Flight;
import Phoenix.AirlineReservationSystem.Model.Ticket;
import Phoenix.AirlineReservationSystem.Repository.CustomerRepo;
import Phoenix.AirlineReservationSystem.Repository.FlightRepo;
import Phoenix.AirlineReservationSystem.Repository.TicketRepo;
import Phoenix.AirlineReservationSystem.Transformer.TicketTransformer;
import Phoenix.AirlineReservationSystem.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Service
public class TicketService {
    @Autowired
    TicketRepo ticketRepo;
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    FlightRepo flightRepo;
    @Autowired
    JwtUtil jwtUtil;
    private String generateTicketId() {
        Ticket lastTicket = ticketRepo.findTopByOrderByTicketIdDesc();

        int nextId = 1;
        if (lastTicket != null) {
            String lastId = lastTicket.getTicketId().substring(2);
            nextId = Integer.parseInt(lastId) + 1;
        }

        return String.format("TK%03d", nextId); // CS001, CS002, etc.
    }
    public TicketResponse bookTicket(TicketRequest ticketRequest,HttpServletRequest request){
        String authHeader= request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header.");
        }
        String token=authHeader.substring(7);
        String username=jwtUtil.extractUsername(token);

        Customer customer=customerRepo.findByUsername(username);
        if(customer==null){
            throw  new ResourceNotFoundException("Customer Not Found");
        }
        Optional<Flight>flight=flightRepo.findByFlightId(ticketRequest.getFlightId());
        if(flight.isEmpty()){
            throw new ResourceNotFoundException("Flight Not Found");
        }
        Ticket ticket= TicketTransformer.ticketRequestToTicket(ticketRequest);
        ticket.setTicketId(generateTicketId());
        ticket.setCustomer(customer);
        ticket.setFlight(flight.get());

       Ticket savedTicket= ticketRepo.save(ticket);
        return TicketTransformer.ticketToTicketResponse(savedTicket);
    }
    public TicketResponse getTicketById(String id, HttpServletRequest request){
        String authHeader= request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header.");
        }
        String token=authHeader.substring(7);
        Optional<Ticket> ticket=ticketRepo.findById(id);

        String role=jwtUtil.extractRoles(token).get(0);

        String username=jwtUtil.extractUsername(token);
        if(!role.equals("admin") && !ticket.get().getCustomer().getUsername().equals(username)){
            throw new AccessDeniedException("You are not authorized to view this ticket.");
        }
        if(ticket.isEmpty()){
            throw new ResourceNotFoundException("Ticket Not Found");
        }

        return TicketTransformer.ticketToTicketResponse(ticket.get());

    }
    @Transactional
    public TicketResponse cancelTicket(String Id,HttpServletRequest request){
        String authHeader= request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header.");
        }
        String token=authHeader.substring(7);
        Optional<Ticket> ticket=ticketRepo.findById(Id);
        String role=jwtUtil.extractRoles(token).get(0);

        String username=jwtUtil.extractUsername(token);
        if(!role.equals("admin") && !ticket.get().getCustomer().getUsername().equals(username)){
            throw new AccessDeniedException("You are not authorized to view this ticket.");
        }
        if(ticket.isEmpty()){
            throw new ResourceNotFoundException("Ticket Not Found");
        }
        ticketRepo.delete(ticket.get());
        return TicketTransformer.ticketToTicketResponse(ticket.get());
    }
    public Page<TicketResponse> listAllTicketByFlightId(int page, int size, String flightId) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Ticket> list = ticketRepo.findByFlight_FlightId(flightId, pageable);
        return list.map(TicketTransformer::ticketToTicketResponse);
    }
    public Page<TicketResponse>listAllTicketByCustomer(int page,int size,HttpServletRequest request){
         String authHeader= request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header.");
        }
        String token=authHeader.substring(7);
        String username=jwtUtil.extractUsername(token);
        Pageable pageable=PageRequest.of(page,size);
        Page<Ticket>list=ticketRepo.findByCustomer_Username(username, pageable);
        return list.map(TicketTransformer::ticketToTicketResponse);
    }
}
