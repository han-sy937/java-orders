package com.lambdaschool.crudyorders.controllers;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;
    //http://localhost:2019/orders/order/7
    @GetMapping(value = "/order/{ordnum}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long ordnum) {
        Order myOrder = orderService.findOrderById(ordnum);
        return new ResponseEntity<>(myOrder, HttpStatus.OK);
    }

    //DELETE
    //http://localhost:2019/orders/order/58
    @DeleteMapping(value = "/order/{ordnum}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long ordnum) {
        orderService.delete(ordnum);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    //POST
    //http://localhost:2019/orders/order
    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewOrder(@Valid
                                         @RequestBody Order newOrder) {
        newOrder.setOrdnum(0);
        newOrder = orderService.save(newOrder);
        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/ordnum")
                .buildAndExpand(newOrder.getOrdnum())
                .toUri();
        return new ResponseEntity<>(newOrder, responseHeaders, HttpStatus.CREATED);
    }

    //PUT
    //http://localhost:2019/orders/order/63
    @PutMapping(value = "/order/{restid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateFullOrder(@PathVariable long restid, @Valid @RequestBody Order updateOrder) {
        updateOrder = orderService.save(updateOrder);
        return new ResponseEntity<>(updateOrder, HttpStatus.OK);
    }

}
