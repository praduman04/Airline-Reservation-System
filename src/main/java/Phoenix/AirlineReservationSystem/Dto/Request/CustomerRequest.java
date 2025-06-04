package Phoenix.AirlineReservationSystem.Dto.Request;

import Phoenix.AirlineReservationSystem.Enums.Gender;
import lombok.*;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerRequest {
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private String passport;
    private String nationalId;
    private String address;
    private int contact;
    private Gender gender;
    private Date dob;
}
