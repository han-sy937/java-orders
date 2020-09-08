package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;

public interface OrderService {
    Order findOrderById(long id);
    Order save(Order order);
}
