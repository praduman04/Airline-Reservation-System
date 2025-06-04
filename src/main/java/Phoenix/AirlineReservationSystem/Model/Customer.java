package Phoenix.AirlineReservationSystem.Model;

import Phoenix.AirlineReservationSystem.Enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
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
   @Column(nullable = false,unique = true)
   private String username;
   @Column(nullable = false)
   private String password;
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
    private String role;
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dob;
 @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
 @JoinColumn(name = "customer_id")
 @JsonManagedReference
    List<Ticket> tickets=new ArrayList<>();
}
