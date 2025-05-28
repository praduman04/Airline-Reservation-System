package Phoenix.AirlineReservationSystem.Repository;

import Phoenix.AirlineReservationSystem.Model.Flight;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface FlightRepo extends JpaRepository<Flight,String> {
    Flight findTopByOrderByFlightIdDesc();

    void deleteByFlightId(String id);

    Optional<Flight> findByFlightId(String s);

    @Query(value = "SELECT * FROM flight f " +
            "WHERE (:departure IS NULL OR f.departure = :departure) " +
            "AND (:arrival IS NULL OR f.arrival = :arrival) " +
            "AND (:date IS NULL OR f.date = :date)",
            nativeQuery = true)
    Page<Flight> searchFlights(
            @Param("departure") String departure,
            @Param("arrival") String arrival,
            @Param("date") Date date,
            Pageable pageable);
}
