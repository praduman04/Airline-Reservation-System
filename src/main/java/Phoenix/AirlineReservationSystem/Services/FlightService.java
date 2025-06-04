package Phoenix.AirlineReservationSystem.Services;

import Phoenix.AirlineReservationSystem.Dto.Request.FlightRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.FlightResponse;
import Phoenix.AirlineReservationSystem.Exceptions.ResourceNotFoundException;
import Phoenix.AirlineReservationSystem.Model.Flight;
import Phoenix.AirlineReservationSystem.Repository.FlightRepo;
import Phoenix.AirlineReservationSystem.Transformer.FlightTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Service
public class FlightService {
    @Autowired
    FlightRepo flightRepo;
    private String generateFlightId() {
        Flight lastFlight = flightRepo.findTopByOrderByFlightIdDesc();

        int nextId = 1;
        if (lastFlight != null) {
            String lastId = lastFlight.getFlightId().substring(2);
            nextId = Integer.parseInt(lastId) + 1;
        }

        return String.format("FT%03d", nextId); // CS001, CS002, etc.
    }
    public FlightResponse addFlight(FlightRequest flightRequest){
        Flight flight= FlightTransformer.flightRequestToFlight(flightRequest);
        flight.setFlightId(generateFlightId());
        Flight addedFlight=flightRepo.save(flight);
        return FlightTransformer.flightToFlightResponse(addedFlight);
    }
    public FlightResponse getFlightById(String id){
        Optional<Flight> flight=flightRepo.findByFlightId(id);
        if(flight.isEmpty()){
            throw  new ResourceNotFoundException("Flight Not Found");
        }
        return FlightTransformer.flightToFlightResponse(flight.get());

    }
    @Transactional
    public FlightResponse removeFlight(String id){
        Optional<Flight> flight=flightRepo.findByFlightId(id);
        if(flight.isEmpty()){
            throw  new ResourceNotFoundException("Flight Not Found");
        }
        Flight deletedFlight=flight.get();
        flightRepo.deleteByFlightId(id);
        return FlightTransformer.flightToFlightResponse(deletedFlight);
    }
    public Page<FlightResponse> listAllFight(int page,int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Flight>list=flightRepo.findAll(pageable);
       // return adminPage.map(AdminTransformer::adminToAdminResponse);
        return list.map(FlightTransformer::flightToFlightResponse);
    }
   public Page<FlightResponse> searchFlight(String departure, String arrival, Date date, int page,int size){
        Pageable pageable=PageRequest.of(page,size);
       if (departure != null && departure.trim().isEmpty()) departure = null;
       if (arrival != null && arrival.trim().isEmpty()) arrival = null;
       Page<Flight>list=flightRepo.searchFlights(departure,arrival,date,pageable);
       return list.map(FlightTransformer::flightToFlightResponse);
   }

}
