package com.lambdaschool.crudyorders.services;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.models.Order;
import com.lambdaschool.crudyorders.models.Payment;
import com.lambdaschool.crudyorders.repositories.AgentsRepository;
import com.lambdaschool.crudyorders.repositories.CustomersRepository;
import com.lambdaschool.crudyorders.repositories.OrdersRepository;
import com.lambdaschool.crudyorders.repositories.PaymentRepository;
import com.lambdaschool.crudyorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional
@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    CustomersRepository custrepos;

    @Autowired
    OrdersRepository ordrepos;

    @Autowired
    AgentsRepository agentrepos;

    @Autowired
    PaymentRepository paymentrepos;

    @Transactional
    @Override
    public Customer save(Customer customer) {
        Customer newCustomer = new Customer();

        if (customer.getCustcode() != 0) {
            findCustomerById(customer.getCustcode());
            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        newCustomer.setAgent(customer.getAgent());

        newCustomer.getOrders().clear();
        for (Order o : customer.getOrders()) {
            Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(), newCustomer ,o.getOrderdescription());

            for (Payment p : o.getPayments()) {
                Payment newPayment = paymentrepos.findById(p.getPaymentid())
                        .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
                        newOrder.getPayments().add(newPayment);
            }
            newCustomer.getOrders().add(newOrder);
        }

        return custrepos.save(newCustomer);
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

    @Transactional
    @Override
    public void deleteAllCustomers() {

        custrepos.deleteAll();
    }

    @Transactional
    @Override
    public void delete(long custcode) {

        if (custrepos.findById(custcode).isPresent()) {
            custrepos.deleteById(custcode);
        } else {
            throw new EntityNotFoundException("Customer " + custcode + " Not Found!");
        }
    }

    @Transactional
    @Override
    public Customer update(Customer customer, long restid) {
        Customer updateCustomer = findCustomerById(restid);

        if (customer.getCustname() != null) {
            updateCustomer.setCustname(customer.getCustname());
        }

        if (customer.getCustcity() != null) {
            updateCustomer.setCustcity(customer.getCustcity());
        }

        if (customer.getWorkingarea() != null) {
            updateCustomer.setWorkingarea(customer.getWorkingarea());
        }

        if (customer.getCustcountry() != null) {
            updateCustomer.setCustcountry(customer.getCustcountry());
        }

        if (customer.getGrade() != null) {
            updateCustomer.setGrade(customer.getGrade());
        }

        if (customer.hasvalueforopeningamt) {
            updateCustomer.setOpeningamt(customer.getOpeningamt());
        }

        if (customer.hasvalueforreceiveamt) {
            updateCustomer.setReceiveamt(customer.getReceiveamt());
        }

        if (customer.hasvalueforpaymentamt) {
            updateCustomer.setPaymentamt(customer.getPaymentamt());
        }

        if (customer.hasvalueforoutstandingamt) {
            updateCustomer.setOutstandingamt(customer.getOutstandingamt());
        }

        if (customer.getPhone() != null) {
            updateCustomer.setPhone(customer.getPhone());
        }

        if (customer.getAgent() != null) {
            updateCustomer.setAgent(customer.getAgent());
        }

        if (customer.getOrders().size() > 0) {
            updateCustomer.getOrders().clear();
            for (Order o : customer.getOrders()) {
                Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(), updateCustomer, o.getOrderdescription());

                for (Payment p : o.getPayments()) {
                    Payment newPayment = paymentrepos.findById(p.getPaymentid())
                            .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " Not Found"));
                    newOrder.getPayments().add(newPayment);
                }
                updateCustomer.getOrders().add(newOrder);
            }
        }

        return custrepos.save(updateCustomer);
    }
}
