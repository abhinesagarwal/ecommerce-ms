package com.example.customer_ms.service;

import com.example.customer_ms.dto.CustomerDTO;
import com.example.customer_ms.entity.CustomerEntity;
import com.example.customer_ms.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @KafkaListener(topics = "orders")
    public void receiveOrderNotification(String message) {
        System.out.println("Received order notification: " + message);
    }

    public List<CustomerDTO> getAllCustomers() {
        List<CustomerEntity> customers = customerRepository.findAll();
        List<CustomerDTO> customerDTOs = new ArrayList<>();
        for (CustomerEntity customer : customers) {
            CustomerDTO customerDTO = new CustomerDTO();
            customerDTO.setCustomerId(customer.getId());
            customerDTO.setName(customer.getName());
            customerDTO.setEmail(customer.getEmail());
            customerDTOs.add(customerDTO);
        }
        return customerDTOs;
    }

    public CustomerDTO getCustomerById(String id) {
        CustomerEntity customer = customerRepository.findById(id).orElseThrow();
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setCustomerId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    }

    public CustomerEntity addCustomer(CustomerEntity customer) {
        return customerRepository.save(customer);
    }

    public CustomerEntity getCustomerByCustomerId(String id) {
        return customerRepository.findById(id).orElseThrow();
    }

    public CustomerEntity updateCustomer(String id, CustomerEntity customer) {
        CustomerEntity existingCustomer = getCustomerByCustomerId(id);
        existingCustomer.setName(customer.getName());
        existingCustomer.setEmail(customer.getEmail());
        return customerRepository.save(existingCustomer);
    }

    public void deleteCustomer(String id) {
        customerRepository.deleteById(id);
    }
}