package vttp2022.assessment.csf.orderbackend.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

// IMPORTANT: You can add to this class, but you cannot delete its original content

public class Order {

	private Integer orderId;
	private String name;
	private String email;
	private Integer size;
	private String sauce;
	private Boolean thickCrust;
	private List<String> toppings = new LinkedList<>();
	private String comments;

	public void setOrderId(Integer orderId) { this.orderId = orderId; }
	public Integer getOrderId() { return this.orderId; }

	public void setName(String name) { this.name = name; }
	public String getName() { return this.name; }

	public void setEmail(String email) { this.email = email; }
	public String getEmail() { return this.email; }

	public void setSize(Integer size) { this.size = size; }
	public Integer getSize() { return this.size; }

	public void setSauce(String sauce) { this.sauce = sauce; }
	public String getSauce() { return this.sauce; }

	public void setThickCrust(Boolean thickCrust) { this.thickCrust = thickCrust; }
	public Boolean isThickCrust() { return this.thickCrust; }

	public void setToppings(List<String> toppings) { this.toppings = toppings; }
	public List<String> getToppings() { return this.toppings; }
	public void addTopping(String topping) { this.toppings.add(topping); }

	public void setComments(String comments) { this.comments = comments; }
	public String getComments() { return this.comments; }

	public static Order createOrderFromJson(String json) throws IOException {
		Order order = new Order();
		try (InputStream is = new ByteArrayInputStream(json.getBytes())) {
			JsonReader reader = Json.createReader(is);
			JsonObject jsonObject = reader.readObject();
			order.setName(jsonObject.getString("name"));
			order.setEmail(jsonObject.getString("email"));
			order.setSauce(jsonObject.getString("sauce"));
			order.setSize(jsonObject.getInt("pizzaSize"));
			order.setComments(jsonObject.getString("comments"));

			if (jsonObject.getString("base") == "thick") {
				order.setThickCrust(true);
			} else {
				order.setThickCrust(false);
			}

			String orderId = UUID.randomUUID().toString().substring(0,8);
			// orderId.replaceAll("^\\d+\\.", "");;
			order.setOrderId(Integer.parseInt(orderId.replaceAll("[^\\d.]", "")));

			List<String> toppingsList = new LinkedList<>();
			JsonArray array = jsonObject.getJsonArray("toppings");

			for (int i = 0; i< array.size(); i++) {
				JsonObject innerObject = array.getJsonObject(i); // toppings object
				if (innerObject.getBoolean("chicken") == true) {
					toppingsList.add("chicken");
				} else if (innerObject.getBoolean("seafood") == true) {
					toppingsList.add("seafood");
				} else if (innerObject.getBoolean("beef") == true) {
					toppingsList.add("beef");
				} else if (innerObject.getBoolean("vegetables") == true) {
					toppingsList.add("vegetables");
				} else if (innerObject.getBoolean("cheese") == true) {
					toppingsList.add("cheese");
				} else if (innerObject.getBoolean("arugula") == true) {
					toppingsList.add("arugula");
				} else if (innerObject.getBoolean("pineapple") == true) {
					toppingsList.add("pineapple");
				}
				order.setToppings(toppingsList);
			} 
			
		}
		return order;
	}
}
