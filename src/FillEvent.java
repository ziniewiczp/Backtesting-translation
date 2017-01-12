
public class FillEvent extends Event {
	
	FillEvent(){
		String oftype = "FILL";
		String timeindex;
		String symbol;
		String exchange;
		String quantity;
		String direction;
		String fill_cost;
		Double commission = null;
		
		if(commission == null){
			commission = calculate_ib_commission();
		}
		else{
			commission = commission;
		}
	}
	
	public double calculate_ib_commission(){
		if(quantity<=500){
			full_cost = Math.max(1.3, 0.013 * quantity);
		}
		else{
			full_cost = Math.max(1.3, 0,008 * quantity);
		}
		return full_cost;
	}
}
