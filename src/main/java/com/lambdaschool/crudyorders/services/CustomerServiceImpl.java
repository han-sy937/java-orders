package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import com.lambdaschool.crudyorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomersRepository custrepos;

    @Override
    public Customer save(Customer customer) {
        return custrepos.save(customer);
    }

    @Override
    public Customer findCustomerById(long id) {
        return custrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " Not Found!"));
    }

    @Override
    public List<Customer> findCustomerByNameLike(String subname) {
        List<Customer> list = custrepos.findByCustnameContainingIgnoringCase(subname);
        return list;
    }

    @Override
    public List<OrderCounts> countOrderByCustomer() {
        List<OrderCounts> list = custrepos.findOrderCounts();
        return list;
    }
}
