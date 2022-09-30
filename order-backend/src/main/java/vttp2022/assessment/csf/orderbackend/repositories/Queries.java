package vttp2022.assessment.csf.orderbackend.repositories;

public class Queries {

    public static final String STRING_SQL_UPLOAD_NEW_ORDER = "insert into orders(order_id, name, email, pizza_size, thick_crust, sauce, toppings, comments) values (?, ?, ?, ?, ?, ?, ?, ?)";

    public static final String STRING_SQL_GET_ORDERS_FROM_EMAIL = "select * from orders where email = ?";
    
}
