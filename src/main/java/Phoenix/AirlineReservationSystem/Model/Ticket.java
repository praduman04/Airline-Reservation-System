package Phoenix.AirlineReservationSystem.Model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Ticket {
    @Id
    private String ticketId;
    @ManyToOne
    @JoinColumn(name = "flight_id")
    private Flight flight;
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
    private String arrival;
    private  String departure;

}
