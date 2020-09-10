package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Transactional
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    OrdersRepository ordersrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Transactional
    @Override
    public Order save(Order order) {
        Order newOrder = new Order();

        if(order.getOrdnum() != 0) {
            findOrderById(order.getOrdnum());
            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setCustomer(order.getCustomer());
        newOrder.setOrderdescription(order.getOrderdescription());

        newOrder.getPayments().clear();
        for (Payment p : order.getPayments()) {
            Payment newPayment = paymentrepos.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment" + p.getPaymentid() + "Not Found"));
        newOrder.getPayments().add(newPayment);
        }

        return ordersrepos.save(order);
    }

    @Override
    public Order findOrderById(long id) {
        return ordersrepos.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order " + id + " Not Found!"));
    }

    @Transactional
    @Override
    public void deleteAllOrders() {
        ordersrepos.deleteAll();
    }

    @Transactional
    @Override
    public void delete(long id) {
        if (ordersrepos.findById(id).isPresent()) {
            ordersrepos.deleteById(id);
        } else {
            throw new EntityNotFoundException("Order " + id + " Not Found!");
        }
    }
}
