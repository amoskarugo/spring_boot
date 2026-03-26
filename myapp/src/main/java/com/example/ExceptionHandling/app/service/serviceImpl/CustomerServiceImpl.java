package com.example.ExceptionHandling.app.service.serviceImpl;


import com.example.ExceptionHandling.app.Exceptions.CustomerAlreadyExistsException;
import com.example.ExceptionHandling.app.Exceptions.NoSuchCustomerExistsException;
import com.example.ExceptionHandling.app.Repo.CustomerRepository;
import com.example.ExceptionHandling.app.model.Customer;
import com.example.ExceptionHandling.app.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer getCustomer(Long id) {
        return customerRepository.findById(id).orElseThrow(
                () -> new NoSuchElementException("No CUSTOMER PRESENT WITH ID = " + id)
        );
    }

    @Override
    public String addCustomer(Customer customer) {

        Optional<Customer> existingCustomer = customerRepository.findById(customer.getId());

        if (existingCustomer.isEmpty()) {
            customerRepository.save(customer);
            return "Customer added successfully";
        }else {
            throw new CustomerAlreadyExistsException("customer already exists!!");
        }

    }

    @Override
    public String updateCustomer(Customer customer) {
        Optional<Customer> existingCustomer = customerRepository.findById(customer.getId());

        if(!existingCustomer.isPresent())
            throw new NoSuchCustomerExistsException("No such Customer exists!!");

        else {
            existingCustomer.get().setName(customer.getName());
            existingCustomer.get().setAddress(customer.getAddress());
            customerRepository.save(existingCustomer.get());

            return "Record updated successfully";
        }

    }
}
