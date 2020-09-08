package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.views.OrderCounts;

import java.util.List;

public interface CustomerService {
    List<Customer> findCustomerByNameLike(String subname);
    List<OrderCounts> countOrderByCustomer();
    Customer findCustomerById(long id);
    Customer save(Customer customer);


}
