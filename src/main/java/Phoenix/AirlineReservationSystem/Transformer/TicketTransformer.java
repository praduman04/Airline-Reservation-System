package Phoenix.AirlineReservationSystem.Transformer;

import Phoenix.AirlineReservationSystem.Dto.Request.TicketRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.TicketResponse;
import Phoenix.AirlineReservationSystem.Model.Ticket;

public class TicketTransformer {
    public static Ticket ticketRequestToTicket(TicketRequest ticketRequest){
        return Ticket.builder()
                .arrival(ticketRequest.getArrival())
                .departure(ticketRequest.getDeparture())
                .build();
    }
    public static TicketResponse ticketToTicketResponse(Ticket ticket){
        return TicketResponse.builder()
                .ticketId(ticket.getTicketId())
                .flightId(ticket.getFlight().getFlightId())
                .customerId(ticket.getCustomer().getCustomerId())
                .date(ticket.getFlight().getDate())
                .arrival(ticket.getArrival())
                .departure(ticket.getDeparture())
                .customerName(ticket.getCustomer().getFirstName()+" "+ticket.getCustomer().getLastName())
                .build();
    }
}
