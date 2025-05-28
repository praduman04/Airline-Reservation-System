package Phoenix.AirlineReservationSystem.Dto.Request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AdminRequest {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
