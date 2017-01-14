import java.math.BigDecimal;

public class Position {
	
	private String symbol;
	private BigDecimal quantity;
	private String timeindex;
	
	public Position(String symbol, BigDecimal quantity, String timeindex) {
		
		this.symbol = symbol;
		this.quantity = quantity;
		this.timeindex = timeindex;
	}
	
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public BigDecimal getQuantity() {
		return quantity;
	}
	public void setQuantity(BigDecimal quantity) {
		this.quantity = quantity;
	}
	public String getTimeindex() {
		return timeindex;
	}
	public void setTimeindex(String timeindex) {
		this.timeindex = timeindex;
	}
	
	public String toString(){
	return symbol + "  "  + quantity + "  " + timeindex;
	}
}
