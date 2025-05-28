package Phoenix.AirlineReservationSystem.Services;

import Phoenix.AirlineReservationSystem.Dto.Request.CustomerRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.CustomerResponse;
import Phoenix.AirlineReservationSystem.Exceptions.ResourceNotFoundException;
import Phoenix.AirlineReservationSystem.Model.Customer;
import Phoenix.AirlineReservationSystem.Repository.CustomerRepo;
import Phoenix.AirlineReservationSystem.Transformer.CustomerTransformer;
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
    public CustomerResponse getByCustomerId(String id){
        Optional<Customer>customer=customerRepo.findByCustomerId(id);
        if(customer.isEmpty()){
            throw  new ResourceNotFoundException("Customer Not Found.");
        }
        CustomerResponse customerResponse=CustomerTransformer.customerToCustomerResponse(customer.get());
        return customerResponse ;
    }
    public Page<CustomerResponse> getAllCustomer(int page,int size){
        Pageable pageable= PageRequest.of(page,size);
        Page<Customer>list=customerRepo.findAll(pageable);
        return list.map(CustomerTransformer::customerToCustomerResponse);

    }
    public CustomerResponse updateCustomer(CustomerRequest customerRequest,String customerId){
        Optional<Customer> customer=customerRepo.findByCustomerId(customerId);
        if(customer.isEmpty())throw new ResourceNotFoundException("Customer Not Found");
        Customer existingCustomer=customer.get();

        Customer updatedCustomer=customerRepo.save(customer.get());
        return CustomerTransformer.customerToCustomerResponse(updatedCustomer);

    }
    @Transactional
    public  CustomerResponse deleteCustomer(String customerId){
        Optional<Customer> customer=customerRepo.findByCustomerId(customerId);
        if(customer.isEmpty())throw new ResourceNotFoundException("Customer Not Found");
        customerRepo.deleteByCustomerId(customerId);
        return CustomerTransformer.customerToCustomerResponse(customer.get());

    }
    @Transactional
    public CustomerResponse updateCustomer(String customerId,CustomerRequest customerRequest){
        Optional<Customer> customerOp=customerRepo.findByCustomerId(customerId);
        if(customerOp.isEmpty())throw new ResourceNotFoundException("Customer Not Found");
        Customer customer=customerOp.get();
        CustomerTransformer.updateCustomerFromRequest(customer,customerRequest);
        Customer updateCustomer= customerRepo.save(customer);

        return CustomerTransformer.customerToCustomerResponse(updateCustomer);
    }
}
