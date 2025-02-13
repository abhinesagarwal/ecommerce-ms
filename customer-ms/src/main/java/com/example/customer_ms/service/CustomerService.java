
package com.example.customer_ms.service;

import com.example.customer_ms.dto.CustomerDTO;
import com.example.customer_ms.entity.CustomerEntity;
import com.example.customer_ms.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static com.ibm.java.diagnostics.utils.Context.logger;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerEntity> getAllCustomers() {
        List<CustomerEntity> customers = customerRepository.findAll();
        if (customers.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No customers found");
        }
        return customers;
    }

    public CustomerEntity getCustomerById(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found with ID: " + id));
    }

    public CustomerEntity addCustomer(CustomerEntity customer) {
        if (customer.getName() == null || customer.getName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer name is required");
        }
        if (customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Customer email is required");
        }
        return customerRepository.save(customer);
    }



    public CustomerEntity updateCustomer(String id, CustomerDTO customer) {
        CustomerEntity existingCustomer = getCustomerById(id);
        if (customer.getName() != null && !customer.getName().isEmpty()) {
            existingCustomer.setName(customer.getName());
        }
        if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
            existingCustomer.setEmail(customer.getEmail());
        }
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(String id) {
        CustomerEntity customer = getCustomerById(id);
        customerRepository.delete(customer);
        logger.info("Customer deleted successfully: " + id);
    }
}