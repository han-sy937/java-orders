package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.services.CustomerService;
import com.lambdaschool.crudyorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
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

    //DELETE
    //http://localhost:2019/customers/customer/54
    @DeleteMapping(value = "/customer/{custcode}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode) {
        customerService.delete(custcode);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    //POST
    //http://localhost:2019/customers/customer
    @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewCustomer(@Valid
                                            @RequestBody Customer newCustomer) {
        newCustomer.setCustcode(0);
        newCustomer = customerService.save(newCustomer);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{custcode}")
                .buildAndExpand(newCustomer.getCustcode())
                .toUri();

        return new ResponseEntity<>(newCustomer, responseHeaders, HttpStatus.CREATED);
    }

    //PUT
    //http://localhost:2019/customers/customer/19
    @PutMapping(value = "/customer/{restid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateFullCustomer(@PathVariable long restid, @Valid @RequestBody Customer updateCustomer) {
        updateCustomer = customerService.save(updateCustomer);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

    //PATCH
    //http://localhost:2019/customers/customer/19
    @PatchMapping(value = "/customer/{restid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updatePartCustomer(@PathVariable long restid, @RequestBody Customer updateCustomer) {
        updateCustomer = customerService.update(updateCustomer, restid);
        return new ResponseEntity<>(updateCustomer, HttpStatus.OK);
    }

}
