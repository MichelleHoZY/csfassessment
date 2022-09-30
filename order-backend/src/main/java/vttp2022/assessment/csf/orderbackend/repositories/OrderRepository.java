package vttp2022.assessment.csf.orderbackend.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.assessment.csf.orderbackend.models.Order;

import static vttp2022.assessment.csf.orderbackend.repositories.Queries.*;

import java.util.LinkedList;
import java.util.List;

@Repository
public class OrderRepository {

    @Autowired
    private JdbcTemplate template;

    public int uploadOrder(Order order) {
        String toppingsString = order.getToppings().toString();
        int count = template.update(
            STRING_SQL_UPLOAD_NEW_ORDER, order.getOrderId(), order.getName(), order.getEmail(), order.getSize(), order.isThickCrust(), order.getSauce(), toppingsString, order.getComments()
        );

        return count;
    }

    public List<Order> getOrders(String email) {
        List<Order> orderList = new LinkedList<>();
        SqlRowSet rs = template.queryForRowSet(
            STRING_SQL_GET_ORDERS_FROM_EMAIL, email
        );
        while(rs.next()) {
            Order order = new Order();
            List<String> toppingsList = new LinkedList<>();
            order.setOrderId(rs.getInt("order_id"));
            order.setName(rs.getString("name"));
            order.setEmail(rs.getString("email"));
            order.setSize(rs.getInt("pizza_size"));
            order.setThickCrust(rs.getBoolean("thick_crust"));
            toppingsList.add(rs.getString("toppings"));
            order.setToppings(toppingsList);
            order.setSauce(rs.getString("sauce"));
            orderList.add(order);
        }

        return orderList;
    }
    
}
