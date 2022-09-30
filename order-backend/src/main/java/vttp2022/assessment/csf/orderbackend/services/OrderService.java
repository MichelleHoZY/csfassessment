package vttp2022.assessment.csf.orderbackend.services;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vttp2022.assessment.csf.orderbackend.models.Order;
import vttp2022.assessment.csf.orderbackend.models.OrderSummary;
import vttp2022.assessment.csf.orderbackend.repositories.OrderRepository;

@Service
public class OrderService {

	@Autowired
	private OrderRepository orderRepo;

	@Autowired
	private PricingService priceSvc;

	// POST /api/order
	// Create a new order by inserting into orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public void createOrder(Order order) {
		int count = orderRepo.uploadOrder(order);
		System.out.println(">>> Order row added count: " + count);
	}

	// GET /api/order/<email>/all
	// Get a list of orders for email from orders table in pizzafactory database
	// IMPORTANT: Do not change the method's signature
	public List<OrderSummary> getOrdersByEmail(String email) {
		// Use priceSvc to calculate the total cost of an order

		List<Order> orderList = orderRepo.getOrders(email);
		List<OrderSummary> orderSummaryList = new LinkedList<>();
		for (int i=0; i<orderList.size(); i++) {
			Float amount = getAmount(orderList.get(i));

			OrderSummary orderSummary = new OrderSummary();
			orderSummary.setName(orderList.get(i).getName());
			orderSummary.setEmail(orderList.get(i).getEmail());
			orderSummary.setOrderId(orderList.get(i).getOrderId());
			orderSummary.setAmount(amount);

			orderSummaryList.add(orderSummary);
		}

		return orderSummaryList;
	}

	public Float getAmount(Order order) {
		Float total = 0f;

			String sauce = order.getSauce();
			Integer size = order.getSize();
			Boolean thickCrust = order.isThickCrust();
			Float crustPrice = 0f;
			if (thickCrust == true) {
				crustPrice = priceSvc.thickCrust();
			} else {
				crustPrice = priceSvc.thinCrust();
			}
			// for (int t = 0; t<orderList.get(i).getToppings().size(); t++) {
			// 	if (priceSvc.topping(t))
			// }
			total = total + priceSvc.sauce(sauce) + priceSvc.size(size) + crustPrice ;
		
		return total;
	}
}
