import java.math.BigDecimal;

public class FillEvent implements Event {
	
	private String oftype;
	private String timeindex;
	private String symbol;
	private String exchange;
	private BigDecimal quantity;
	private String direction;
	private BigDecimal fill_cost;
	private BigDecimal commission;
	
	@Override
	public String getOfType() {
		return oftype;
	}

	public void setOftype(String oftype) {
		this.oftype = oftype;
	}

	public String getTimeindex() {
		return timeindex;
	}

	public void setTimeindex(String timeindex) {
		this.timeindex = timeindex;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getExchange() {
		return exchange;
	}

	public void setExchange(String exchange) {
		this.exchange = exchange;
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

	public BigDecimal getFill_cost() {
		return fill_cost;
	}

	public void setFill_cost(BigDecimal fill_cost) {
		this.fill_cost = fill_cost;
	}

	public BigDecimal getCommission() {
		return commission;
	}

	public void setCommission(BigDecimal commission) {
		this.commission = commission;
	}

	// commission as an argument
	public FillEvent(String timeindex, String symbol, String exchange, 
			BigDecimal quantity, String direction, BigDecimal fill_cost, BigDecimal commission) {
		
			this.oftype = "FILL";
			this.timeindex = timeindex;
			this.symbol = symbol;
			this.exchange = exchange;
			this.quantity = quantity;
			this.direction = direction;
			this.fill_cost = fill_cost;
			this.commission = commission;
	}
	
	// commission is calculated
	public FillEvent(String timeindex, String symbol, String exchange, 
			BigDecimal quantity, String direction, BigDecimal fill_cost) {
		
			this.oftype = "FILL";
			this.timeindex = timeindex;
			this.symbol = symbol;
			this.exchange = exchange;
			this.quantity = quantity;
			this.direction = direction;
			this.fill_cost = fill_cost;
			this.commission = calculate_ib_commission();
	}
	
	public BigDecimal calculate_ib_commission() {
		BigDecimal full_cost;
		
		// compareTo returns -1, 0, or 1 as this BigDecimal is
		// numerically less than, equal to, or greater than val.
		if(this.quantity.compareTo(new BigDecimal(500)) <= 0) {
			// max(1.3, quantity*0.013)
			full_cost = (new BigDecimal(1.3).max(this.quantity.multiply(new BigDecimal(0.013))));
		}
		else {
			// max(1.3, quantity*0.008)
			full_cost = (new BigDecimal(0.008).max(this.quantity.multiply(new BigDecimal(0.013))));
		}
		
		return full_cost;
	}
}
