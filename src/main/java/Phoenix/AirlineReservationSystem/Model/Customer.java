package Phoenix.AirlineReservationSystem.Model;

import Phoenix.AirlineReservationSystem.Enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
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
public class Customer {

   @Id
    private String customerId;
    @Column(nullable = false)
    private String firstName;
    private String lastName;
    @Column(unique = true,nullable = false)
    private String passport;
    @Column(unique = true,nullable = false)
    private String nationalId;
    private String address;
    @Column(unique = true,nullable = false)
    private int contact;
    @Enumerated(value = EnumType.STRING)
    private Gender gender;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dob;
 @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
 @JoinColumn(name = "customer_id")
    List<Ticket> tickets=new ArrayList<>();
}
