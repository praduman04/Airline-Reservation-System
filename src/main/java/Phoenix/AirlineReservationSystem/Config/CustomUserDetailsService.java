package Phoenix.AirlineReservationSystem.Config;

import javax.management.RuntimeErrorException;

import Phoenix.AirlineReservationSystem.Model.Admin;
import Phoenix.AirlineReservationSystem.Model.Customer;
import Phoenix.AirlineReservationSystem.Repository.AdminRepo;
import Phoenix.AirlineReservationSystem.Repository.CustomerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    AdminRepo adminRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Auto-generated method stub
        Customer user=customerRepo.findByUsername(username);
        if(user!=null) return new CustomUserDetails(user);
        Admin admin=adminRepo.findByUsername(username);
        if(admin!=null)return new CustomUserDetails(admin);

        throw new RuntimeErrorException(null, "Invalid Username");


    }

}
