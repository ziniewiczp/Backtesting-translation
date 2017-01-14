import java.math.BigDecimal;

public class Holding {

	private String symbol;
	private BigDecimal quantity;
	private String timeindex;
	private BigDecimal cash;
	private BigDecimal commissions;
	private BigDecimal total;
	
	public Holding(String symbol, BigDecimal quantity, String timeindex, BigDecimal cash, BigDecimal commissions, BigDecimal total) {
		
		this.symbol = symbol;
		this.quantity = quantity;
		this.timeindex = timeindex;
		this.cash = cash;
		this.commissions = commissions;
		this.total = total;
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
	public BigDecimal getCash() {
		return cash;
	}
	public void setCash(BigDecimal cash) {
		this.cash = cash;
	}
	public BigDecimal getCommissions() {
		return commissions;
	}
	public void setCommissions(BigDecimal commissions) {
		this.commissions = commissions;
	}
	public BigDecimal getTotal() {
		return total;
	}
	public void setTotal(BigDecimal total) {
		this.total = total;
	}
	
	public String toString(){
		
		return symbol + "  " + quantity + "  " + timeindex + "  " + cash + "  " + commissions + "  " + total;
	}
	
}
