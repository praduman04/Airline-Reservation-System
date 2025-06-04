package Phoenix.AirlineReservationSystem.Config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Phoenix.AirlineReservationSystem.Model.Admin;
import Phoenix.AirlineReservationSystem.Model.Customer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public class CustomUserDetails implements UserDetails {
    private String username;
    private String password;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(Customer user) {
        this.username = user.getUsername();
        this.password = user.getPassword();

        // Fix: use comma if roles are comma-separated, or "\\." if period-separated
        String[] roles = user.getRole().split(","); // or "\\." based on actual format

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        this.authorities = grantedAuthorities;
    }
    public CustomUserDetails(Admin user) {
        this.username = user.getUsername();
        this.password = user.getPassword();

        // Fix: use comma if roles are comma-separated, or "\\." if period-separated
        String[] roles = user.getRole().split(","); // or "\\." based on actual format

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        this.authorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
