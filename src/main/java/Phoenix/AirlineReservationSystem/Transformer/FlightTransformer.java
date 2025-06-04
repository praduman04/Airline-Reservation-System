package Phoenix.AirlineReservationSystem.Transformer;

import Phoenix.AirlineReservationSystem.Dto.Request.FlightRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.FlightResponse;
import Phoenix.AirlineReservationSystem.Model.Flight;

public class FlightTransformer {
    public static Flight flightRequestToFlight(FlightRequest flightRequest){
        return Flight.builder()
                .arrival(flightRequest.getArrival())
                .departure(flightRequest.getDeparture())
                .duration(flightRequest.getDuration())
                .seats(flightRequest.getSeats())
                .fare(flightRequest.getFare())
                .date(flightRequest.getDate())
                .build();
    }

    public static FlightResponse flightToFlightResponse(Flight flight){
        return FlightResponse.builder()
                .flightId(flight.getFlightId())
                .arrival(flight.getArrival())
                .departure(flight.getDeparture())
                .duration(flight.getDuration())
                .seats(flight.getSeats())
                .availableSeats(flight.getSeats()-flight.getTickets().size())
                .fare(flight.getFare())
                .date(flight.getDate())
                .build();
    }
    public static void updateFlightFromRequest(Flight flight, FlightRequest request) {
        if (request.getArrival() != null) {
            flight.setArrival(request.getArrival());
        }
        if (request.getDeparture() != null) {
            flight.setDeparture(request.getDeparture());
        }
        if (request.getDuration() != null) {
            flight.setDuration(request.getDuration());
        }
        if (request.getSeats() != 0) {
            flight.setSeats(request.getSeats());
        }
        if (request.getFare() != 0.0) {
            flight.setFare(request.getFare());
        }
        if (request.getDate() != null) {
            flight.setDate(request.getDate());
        }
    }
}
