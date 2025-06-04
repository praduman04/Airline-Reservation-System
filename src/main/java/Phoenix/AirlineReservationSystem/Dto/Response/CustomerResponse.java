package Phoenix.AirlineReservationSystem.Dto.Response;

import Phoenix.AirlineReservationSystem.Enums.Gender;
import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomerResponse {
    private String username;
    private String password;
    private String customerId;
    private String firstName;
    private String lastName;
    private String passport;
    private String nationalId;
    private String address;
    private String role;

    private int contact;
    private Gender gender;
    private Date dob;
}
