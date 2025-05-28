package Phoenix.AirlineReservationSystem.Controller;

import Phoenix.AirlineReservationSystem.Dto.Request.TicketRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.TicketResponse;
import Phoenix.AirlineReservationSystem.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping("/bookTicket")
    public ResponseEntity<TicketResponse> bookTicket(@RequestBody TicketRequest ticketRequest){
        TicketResponse ticketResponse=ticketService.bookTicket(ticketRequest);
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);

    }
    @GetMapping("/get")
    public ResponseEntity<TicketResponse>getTicket(@RequestParam String ticketId){
        TicketResponse ticketResponse=ticketService.getTicketById(ticketId);
        return new ResponseEntity<>(ticketResponse,HttpStatus.OK);
    }
    @DeleteMapping("/cancel")
    public ResponseEntity<TicketResponse>cancelTicket(@RequestParam String ticketId){
        TicketResponse ticketResponse=ticketService.cancelTicket(ticketId);
        return new ResponseEntity<>(ticketResponse,HttpStatus.OK);
    }
}
