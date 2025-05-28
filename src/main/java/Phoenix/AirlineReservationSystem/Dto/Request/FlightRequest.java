package Phoenix.AirlineReservationSystem.Dto.Request;


import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class FlightRequest {
    private String arrival;
    private String departure;
    private String duration;
    private int seats;
    private double fare;
    private Date date;
}
