package Phoenix.AirlineReservationSystem.Transformer;

import Phoenix.AirlineReservationSystem.Dto.Request.CustomerRequest;
import Phoenix.AirlineReservationSystem.Dto.Response.CustomerResponse;
import Phoenix.AirlineReservationSystem.Model.Customer;

public class CustomerTransformer {
    public static Customer customerRequestToCustomer(CustomerRequest customerRequest){
        return Customer.builder()
                .firstName(customerRequest.getFirstName())
                .lastName(customerRequest.getLastName())
                .passport(customerRequest.getPassport())
                .nationalId(customerRequest.getNationalId())
                .address(customerRequest.getAddress())
                .contact(customerRequest.getContact())
                .gender(customerRequest.getGender())
                .dob(customerRequest.getDob())
                .build();
    }
    public static CustomerResponse customerToCustomerResponse(Customer customer){
        return CustomerResponse.builder()
                .customerId(customer.getCustomerId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .passport(customer.getPassport())
                .nationalId(customer.getNationalId())
                .address(customer.getAddress())
                .contact(customer.getContact())
                .gender(customer.getGender())
                .dob(customer.getDob())
                .build();
    }
    public static void updateCustomerFromRequest(Customer customer, CustomerRequest request) {
        if (request.getFirstName() != null) {
            customer.setFirstName(request.getFirstName());
        }
        if (request.getLastName() != null) {
            customer.setLastName(request.getLastName());
        }
        if (request.getPassport() != null) {
            customer.setPassport(request.getPassport());
        }
        if (request.getNationalId() != null) {
            customer.setNationalId(request.getNationalId());
        }
        if (request.getAddress() != null) {
            customer.setAddress(request.getAddress());
        }
        if (request.getContact() != 0) { // Caution: 0 might be a valid input. Prefer Integer.
            customer.setContact(request.getContact());
        }
        if (request.getGender() != null) {
            customer.setGender(request.getGender());
        }
        if (request.getDob() != null) {
            customer.setDob(request.getDob());
        }
    }
}
