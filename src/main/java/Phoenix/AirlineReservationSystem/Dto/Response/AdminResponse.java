package Phoenix.AirlineReservationSystem.Dto.Response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdminResponse {
    private String firstName;
    private String lastName;
    private String username;
   // private String password;
}
