package Phoenix.AirlineReservationSystem.Repository;

import Phoenix.AirlineReservationSystem.Model.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepo extends JpaRepository<Admin,Integer> {

    Optional<Admin> findByAdminId(Integer id);
    void deleteByAdminId(int id);
    Admin findByUsername(String username);
}
