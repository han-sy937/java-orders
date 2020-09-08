package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value = "customerService")
public class CustomerServiceImpl implements CutomerService{
    @Autowired
    CustomersRepository custrepos;

    @Override
    public Customer save(Customer customer) {
        return custrepos.save(customer);
    }
}
