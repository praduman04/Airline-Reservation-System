package Phoenix.AirlineReservationSystem.Controller;

import Phoenix.AirlineReservationSystem.Dto.Request.CustomerRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.CustomerResponse;
import Phoenix.AirlineReservationSystem.Model.Customer;
import Phoenix.AirlineReservationSystem.Services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @PostMapping("/signUp")
    public ResponseEntity<CustomerResponse> addCustomer(@RequestBody CustomerRequest customerRequest){
        CustomerResponse customer=customerService.addCustomer(customerRequest);
        return new ResponseEntity<>(customer, HttpStatus.CREATED);
    }
   
    @GetMapping("/get")
    public ResponseEntity<CustomerResponse>getByCustomerId(HttpServletRequest request){
        CustomerResponse customerResponse=customerService.getByCustomerId(request);
        return new ResponseEntity<>(customerResponse,HttpStatus.OK);
    }
    @GetMapping("/getAllCustomer")
    public ResponseEntity<Page<CustomerResponse>>getAllCustomer(@RequestParam(value = "page",defaultValue = "0") int page,@RequestParam(value = "Size",defaultValue = "5") int size){
        Page<CustomerResponse>list=customerService.getAllCustomer(page,size);
        return new ResponseEntity<>(list,HttpStatus.OK);
    }
    @DeleteMapping("/delete")
    public ResponseEntity<CustomerResponse>deleteCustomer(HttpServletRequest request){
        CustomerResponse customerResponse=customerService.deleteCustomer(request);
        return new ResponseEntity<>(customerResponse,HttpStatus.OK);
    }
    @PutMapping("/update")
    public ResponseEntity<CustomerResponse>updateCustomer(HttpServletRequest request,@RequestBody CustomerRequest customerRequest){
        CustomerResponse customerResponse=customerService.updateCustomer(request,customerRequest);
        return new ResponseEntity<>(customerResponse,HttpStatus.ACCEPTED);
    }
}
