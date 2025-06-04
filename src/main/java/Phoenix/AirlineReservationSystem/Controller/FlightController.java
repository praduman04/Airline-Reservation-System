package Phoenix.AirlineReservationSystem.Controller;

import Phoenix.AirlineReservationSystem.Dto.Request.FlightRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.FlightResponse;
import Phoenix.AirlineReservationSystem.Model.Flight;
import Phoenix.AirlineReservationSystem.Services.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("api/v1/flight")
public class FlightController {
    @Autowired
    FlightService flightService;
    @PostMapping("/add")
    public ResponseEntity<FlightResponse>addFlight(@RequestBody FlightRequest flightRequest){
        FlightResponse flightResponse=flightService.addFlight(flightRequest);
        return new ResponseEntity<>(flightResponse, HttpStatus.CREATED);
    }
    @GetMapping("/get")
    public ResponseEntity<FlightResponse> getFlightById(@RequestParam String flightId){
        FlightResponse flightResponse=flightService.getFlightById(flightId);
        return new ResponseEntity<>(flightResponse,HttpStatus.OK);
    }
    @GetMapping("/listAll")
    public ResponseEntity<Page<FlightResponse>> getAllFlight(@RequestParam int page,@RequestParam int size){
        Page<FlightResponse>list=flightService.listAllFight(page,size);
        return new ResponseEntity<>(list,HttpStatus.OK);

    }
    @DeleteMapping("/delete")
    public ResponseEntity<FlightResponse> removeFlight(@RequestParam String flightId){
        FlightResponse flightResponse=flightService.removeFlight(flightId);
        return new ResponseEntity<>(flightResponse,HttpStatus.OK);

    }
    @GetMapping("/search")
    public ResponseEntity<Page<FlightResponse>> searchFlight(
            @RequestParam(required = false) String departure,
            @RequestParam(required = false) String arrival,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date date,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Page<FlightResponse> list = flightService.searchFlight(departure, arrival, date, page, size);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
