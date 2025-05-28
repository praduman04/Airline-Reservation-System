package Phoenix.AirlineReservationSystem.Controller;

import Phoenix.AirlineReservationSystem.Dto.Request.CustomerRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.CustomerResponse;
import Phoenix.AirlineReservationSystem.Model.Customer;
import Phoenix.AirlineReservationSystem.Services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @PostMapping("/addCustomer")
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customer=customerService.addCustomer(customerRequest);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
    @GetMapping("/getById")
    public ResponseEntity<CustomerResponse>getByCustomerId(@RequestParam("customerId") String customerId){
        CustomerResponse customerResponse=customerService.getByCustomerId(customerId);
        return new ResponseEntity<>(customerResponse,HttpStatus.OK);
    }
    @GetMapping("/getAllCustomer")
    public ResponseEntity<Page<CustomerResponse>>getAllCustomer(@RequestParam(value = "page",defaultValue = "0") int page,@RequestParam(value = "Size",defaultValue = "5") int size){
        Page<CustomerResponse>list=customerService.getAllCustomer(page,size);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<CustomerResponse>deleteCustomer(@RequestParam("customerId") String customerId){
        CustomerResponse customerResponse=customerService.deleteCustomer(customerId);
        return new ResponseEntity<>(customerResponse,HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<CustomerResponse>updateCustomer(@RequestParam String customerId,@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse=customerService.updateCustomer(customerId,customerRequest);
        return new ResponseEntity<>(customerResponse,HttpStatus.ACCEPTED);
    }
}
