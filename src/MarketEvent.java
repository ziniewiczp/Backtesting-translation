
public class MarketEvent implements Event {
	
	String oftype;
	
	public MarketEvent() {
		
		this.oftype = "MARKET";
	}
	
	@Override
	public String getOfType() {
		return this.oftype;
	}
}
