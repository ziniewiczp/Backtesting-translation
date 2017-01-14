
public class SignalEvent implements Event {
	
	private String ofType;
	private String strategy_id;
	private String symbol;
	private String datetime;
	private String signal_type;
	private String strength;
	
	@Override
	public String getOfType() {
		return ofType;
	}

	public void setOftype(String ofType) {
		this.ofType = ofType;
	}

	public String getStrategy_id() {
		return strategy_id;
	}

	public void setStrategy_id(String strategy_id) {
		this.strategy_id = strategy_id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getSignal_type() {
		return signal_type;
	}

	public void setSignal_type(String signal_type) {
		this.signal_type = signal_type;
	}

	public String getStrength() {
		return strength;
	}

	public void setStrength(String strength) {
		this.strength = strength;
	}

	public SignalEvent(String strategy_id, String symbol, String datetime, String signal_type, String strength) {

		this.oftype = "SIGNAL";
		this.strategy_id = strategy_id;
		this.symbol = symbol;
		this.datetime = datetime;
		this.signal_type = signal_type;
		this.strength = strength;
	}
}
