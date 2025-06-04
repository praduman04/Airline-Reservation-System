package Phoenix.AirlineReservationSystem.Dto.Request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder

public class TicketRequest {
    //private String customerId;
    private String flightId;
    private String arrival;
    private String departure;
}
