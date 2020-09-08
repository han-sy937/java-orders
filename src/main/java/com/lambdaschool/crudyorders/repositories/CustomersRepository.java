package com.lambdaschool.crudyorders.repositories;

import com.lambdaschool.crudyorders.models.Customer;
import com.lambdaschool.crudyorders.views.OrderCounts;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface  CustomersRepository extends CrudRepository <Customer, Long> {

    List<Customer> findByCustnameContainingIgnoringCase(String subname);

    @Query(value = "SELECT c.custname name, COUNT (o.ordnum) countorder " +
            "FROM customers c LEFT JOIN orders o " +
            "ON c.custcode=o.custcode " +
            "GROUP BY c.custname ", nativeQuery = true)
    List<OrderCounts> findOrderCounts();

}
