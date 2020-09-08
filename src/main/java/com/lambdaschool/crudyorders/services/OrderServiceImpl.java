package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersRepository ordersrepos;

    @Override
    public Order save(Order order) {
        return ordersrepos.save(order);
    }

    @Override
    public Order findOrderById(long id) {
        return ordersrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found!"));
    }
}
