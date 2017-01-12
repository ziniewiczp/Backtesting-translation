import java.math.BigDecimal;

public class FillEvent extends Event {
	
	String timeindex;
	String symbol;
	String exchange;
	BigDecimal quantity;
	String direction;
	BigDecimal fill_cost;
	BigDecimal commission;
	
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
