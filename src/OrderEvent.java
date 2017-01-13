import java.math.BigDecimal;

public class OrderEvent implements Event {
	
	String oftype;
	String symbol;
	String order_type;
	BigDecimal quantity;
	String direction;
	
	public OrderEvent(String symbol, String order_type, BigDecimal quantity, String direction) {

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
	
	@Override
	public String getOfType() {
		return this.oftype;
	}
}
