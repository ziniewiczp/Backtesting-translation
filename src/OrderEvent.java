
public class OrderEvent extends Event {
	
	OrderEvent(){
		String oftype = "ORDER";
		String symbol;
		String order_type;
		String quantity;
		String direction;
	}
	
	public void print_order(){
		System.out.println("Order: Symbol=" + symbol + ", Type=" + order_type +
				", Quantity=" + quantity + ", Direction=" + direction);
	}
}
