
public class OrderEvent extends Event {
	
	String symbol;
	String order_type;
	int quantity;
	String direction;
	
	public OrderEvent(String symbol, String order_type, int quantity, String direction) {

		this.oftype = "ORDER";
		this.symbol = symbol;
		this.order_type = order_type;
		this.quantity = quantity;
		this.direction = direction;
		
	}
	
	public void print_order() {
		System.out.println("Order: Symbol=" + symbol + ", Type=" + order_type +
				", Quantity=" + quantity + ", Direction=" + direction);
	}
}
