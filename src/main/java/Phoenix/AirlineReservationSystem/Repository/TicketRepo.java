package Phoenix.AirlineReservationSystem.Repository;

import Phoenix.AirlineReservationSystem.Model.Customer;
import Phoenix.AirlineReservationSystem.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepo extends JpaRepository<Ticket,String> {
    Ticket findTopByOrderByTicketIdDesc();

}
