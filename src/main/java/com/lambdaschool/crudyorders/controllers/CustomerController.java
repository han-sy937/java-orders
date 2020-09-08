package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.services.CustomerService;
import com.lambdaschool.crudyorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {
    @Autowired
    private CustomerService customerService;

    //http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> countOrders() {
        List<OrderCounts> ordList = customerService.countOrderByCustomer();
        return new ResponseEntity<>(ordList, HttpStatus.OK);
    }

    //http://localhost:2019/customers/customer/7
    @GetMapping(value = "/customer/{custcode}", produces = "application/json")
    public ResponseEntity<?> findCustomerById(@PathVariable long custcode) {
        Customer myCustomer = customerService.findCustomerById(custcode);
        return new ResponseEntity<>(myCustomer, HttpStatus.OK);
    }

    //http://localhost:2019/customers/customer/77

    //http://localhost:2019/customers/namelike/mes
    @GetMapping(value = "/namelike/{subname}", produces = "application/json")
    public ResponseEntity<?> findCustomerByNameLike(@PathVariable String subname) {
        List<Customer> rtnList = customerService.findCustomerByNameLike(subname);
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    //http://localhost:2019/customers/namelike/cin

}
