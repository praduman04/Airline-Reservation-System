package Phoenix.AirlineReservationSystem.Model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Flight {
    @Id
    private String flightId;
    @Column(nullable = false)
    private String arrival;
    @Column(nullable = false)
    private String departure;
    @Column(nullable = false)
    private String duration;
    @Column(nullable = false)
    private int seats;
    @Column(nullable = false)
    private double fare;
    @Column(nullable = false)
    private Date date;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "flight_id")
    List<Ticket>tickets=new ArrayList<>();
}
