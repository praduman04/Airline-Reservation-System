package Phoenix.AirlineReservationSystem.Dto.Response;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TicketResponse {
    private String ticketId;
    private String customerId;
    private String customerName;
    private String flightId;
    private Date date;
    private String arrival;
    private String departure;
}
