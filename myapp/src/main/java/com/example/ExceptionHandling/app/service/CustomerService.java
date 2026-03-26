package com.example.ExceptionHandling.app.service;

import com.example.ExceptionHandling.app.model.Customer;

public interface CustomerService {

    Customer getCustomer(Long id);

    String addCustomer(Customer customer);

    String updateCustomer(Customer customer);



}
