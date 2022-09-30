package vttp2022.assessment.csf.orderbackend.controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObjectBuilder;
import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.services.OrderService;

@RestController
@RequestMapping(path="/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderRestController {

    @Autowired
    private OrderService orderSvc;

    @PostMapping(path="/order", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createOrder(@RequestBody String json) throws IOException {
        Order order = Order.createOrderFromJson(json);

        orderSvc.createOrder(order);

        JsonObjectBuilder object = Json.createObjectBuilder();
        object.add("result", order.getOrderId());

        return ResponseEntity.ok().body(object.build().toString());

    }

    @GetMapping(path="/order/{email}/all")
    public ResponseEntity<String> getOrders(@PathVariable String email) {
        List<OrderSummary> orderList = new LinkedList<>();
        orderList = orderSvc.getOrdersByEmail(email);
        System.out.println(">>> Order list: " + orderList);

        if (orderList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
        }

        JsonObjectBuilder object = Json.createObjectBuilder();
        JsonArrayBuilder array = Json.createArrayBuilder();
        for (int i = 0; i < orderList.size(); i++) {
            JsonObjectBuilder innerObject = Json.createObjectBuilder();
            innerObject.add("name", orderList.get(i).getName());
            innerObject.add("email", orderList.get(i).getEmail());
            innerObject.add("orderId", orderList.get(i).getOrderId());
            innerObject.add("amount", orderList.get(i).getAmount());
            array.add(innerObject);
        }
        object.add("list", array);

        return ResponseEntity.ok().body(object.build().toString());
    }

}
