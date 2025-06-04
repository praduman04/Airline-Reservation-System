package Phoenix.AirlineReservationSystem.Repository;

import Phoenix.AirlineReservationSystem.Model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepo extends JpaRepository<Customer,String> {
    Customer findTopByOrderByCustomerIdDesc();
    Optional<Customer> findByCustomerId(String id);

    void deleteByUsername(String username);
    Customer findByUsername(String username);

}
