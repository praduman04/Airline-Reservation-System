package Phoenix.AirlineReservationSystem.Controller;

import Phoenix.AirlineReservationSystem.Dto.Request.TicketRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.TicketResponse;
import Phoenix.AirlineReservationSystem.Services.TicketService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/ticket")
public class TicketController {
    @Autowired
    TicketService ticketService;
    @PostMapping("/bookTicket")
    public ResponseEntity<TicketResponse> bookTicket(@RequestBody TicketRequest ticketRequest,HttpServletRequest request){
        TicketResponse ticketResponse=ticketService.bookTicket(ticketRequest,request);
        return new ResponseEntity<>(ticketResponse, HttpStatus.CREATED);

    }
    @GetMapping("/get")
    public ResponseEntity<TicketResponse>getTicket(@RequestParam String ticketId, HttpServletRequest request){
        TicketResponse ticketResponse=ticketService.getTicketById(ticketId,request);
        return new ResponseEntity<>(ticketResponse,HttpStatus.OK);
    }
    @DeleteMapping("/cancel")
    public ResponseEntity<TicketResponse>cancelTicket(@RequestParam String ticketId,HttpServletRequest request){
        TicketResponse ticketResponse=ticketService.cancelTicket(ticketId,request);
        return new ResponseEntity<>(ticketResponse,HttpStatus.OK);
    }
    @GetMapping("/get_all")
    public ResponseEntity<Page<TicketResponse>> getAllTicketByFlightId(@RequestParam String flightId, @RequestParam int page, @RequestParam int size){
        Page<TicketResponse>list=ticketService.listAllTicketByFlightId(page,size,flightId);
        return new  ResponseEntity<>(list,HttpStatus.OK);
    }
    @GetMapping("get_all_by_customer")
    public ResponseEntity<Page<TicketResponse>>getAllByCustomerId(HttpServletRequest request,@RequestParam int page,@RequestParam int size){
        Page<TicketResponse>list=ticketService.listAllTicketByCustomer(page, size,request);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
}
