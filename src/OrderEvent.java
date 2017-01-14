import java.math.BigDecimal;

public class OrderEvent implements Event {
	
	private String oftype;
	private String symbol;
	private String order_type;
	private BigDecimal quantity;
	private String direction;
	
	@Override
	public String getOfType() {
		return .oftype;
	}

	public void setOftype(String oftype) {
		this.oftype = oftype;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getOrder_type() {
		return order_type;
	}

	public void setOrder_type(String order_type) {
		this.order_type = order_type;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

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
}
