package Phoenix.AirlineReservationSystem.Repository;
import Phoenix.AirlineReservationSystem.Model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<Ticket,String> {
    Ticket findTopByOrderByTicketIdDesc();
    Page<Ticket>findByFlight_FlightId(String id, Pageable pageable);
    Page<Ticket>findByCustomer_Username(String id,Pageable pageable);
}
