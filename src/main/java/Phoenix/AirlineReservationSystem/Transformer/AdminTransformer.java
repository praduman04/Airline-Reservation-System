package Phoenix.AirlineReservationSystem.Transformer;

import Phoenix.AirlineReservationSystem.Dto.Request.AdminRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.AdminResponse;
import Phoenix.AirlineReservationSystem.Model.Admin;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class AdminTransformer {
    private static final PasswordEncoder passwordEncoder=new BCryptPasswordEncoder();
    public static Admin adminRequestToAdmin(AdminRequest adminRequest){
        return Admin.builder()
                .firstName(adminRequest.getFirstName())
                .lastName(adminRequest.getLastName())
                .username(adminRequest.getUsername())
                .password(passwordEncoder.encode(adminRequest.getPassword()))
                .role("admin")
                .build();
    }
    public static AdminResponse adminToAdminResponse(Admin admin){
        return AdminResponse.builder()
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .username(admin.getUsername())
                .password(admin.getPassword())
                .role(admin.getRole())
                .build();
    }
    public static void updateAdminFromRequest(Admin admin, AdminRequest request) {
        if (request.getFirstName() != null) {
            admin.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            admin.setLastName(request.getLastName());
        }
        if (request.getUsername() != null) {
            admin.setUsername(request.getUsername());
        }
        if (request.getPassword() != null) {
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
        }
    }
}
