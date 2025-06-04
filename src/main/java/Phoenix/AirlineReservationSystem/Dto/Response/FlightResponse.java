package Phoenix.AirlineReservationSystem.Dto.Response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlightResponse {
    private String flightId;
    private String arrival;
    private String departure;
    private String duration;
    private int seats;
    private int availableSeats;
    private double fare;
    private Date date;
}
