package Phoenix.AirlineReservationSystem.Services;

import Phoenix.AirlineReservationSystem.Dto.Request.CustomerRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.CustomerResponse;
import Phoenix.AirlineReservationSystem.Exceptions.ResourceNotFoundException;
import Phoenix.AirlineReservationSystem.Model.Customer;
import Phoenix.AirlineReservationSystem.Repository.CustomerRepo;
import Phoenix.AirlineReservationSystem.Transformer.CustomerTransformer;
import Phoenix.AirlineReservationSystem.Utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    CustomerRepo customerRepo;
    @Autowired
    JwtUtil jwtUtil;
    private String generateCustomerId() {
        Customer lastCustomer = customerRepo.findTopByOrderByCustomerIdDesc();

        int nextId = 1;
        if (lastCustomer != null) {
            String lastId = lastCustomer.getCustomerId().substring(2);
            nextId = Integer.parseInt(lastId) + 1;
        }

        return String.format("CS%03d", nextId); // CS001, CS002, etc.
    }
    public CustomerResponse addCustomer(CustomerRequest customerRequest){
        Customer customer= CustomerTransformer.customerRequestToCustomer(customerRequest);
        customer.setCustomerId(generateCustomerId());
        Customer addedCustomer=customerRepo.save(customer);
        return CustomerTransformer.customerToCustomerResponse(addedCustomer);
    }
    public CustomerResponse getByCustomerId(HttpServletRequest request){
        String authHeader= request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header.");
        }
        String token=authHeader.substring(7);
        String username= jwtUtil.extractUsername(token);
        Customer customer=customerRepo.findByUsername(username);
        if(customer==null)throw new ResourceNotFoundException("Customer Not Found");
        CustomerResponse customerResponse=CustomerTransformer.customerToCustomerResponse(customer);
        return customerResponse ;
    }
    public Page<CustomerResponse> getAllCustomer(int page,int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Customer>list=customerRepo.findAll(pageable);
        return list.map(CustomerTransformer::customerToCustomerResponse);

    }

    @Transactional
    public  CustomerResponse deleteCustomer(HttpServletRequest request){
        String authHeader= request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header.");
        }
        String token=authHeader.substring(7);
        String username= jwtUtil.extractUsername(token);
        Customer customer=customerRepo.findByUsername(username);
        if(customer==null)throw new ResourceNotFoundException("Customer Not Found");
        customerRepo.deleteByUsername(username);
        return CustomerTransformer.customerToCustomerResponse(customer);

    }
    @Transactional
    public CustomerResponse updateCustomer(HttpServletRequest request,CustomerRequest customerRequest){
        String authHeader= request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header.");
        }
        String token=authHeader.substring(7);
        String username= jwtUtil.extractUsername(token);
        Customer customer=customerRepo.findByUsername(username);
        if(customer==null)throw new ResourceNotFoundException("Customer Not Found");
        CustomerTransformer.updateCustomerFromRequest(customer,customerRequest);
        Customer updateCustomer= customerRepo.save(customer);

        return CustomerTransformer.customerToCustomerResponse(updateCustomer);
    }
}
