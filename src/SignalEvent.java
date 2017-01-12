
public class SignalEvent extends Event {
	
	String strategy_id;
	String symbol;
	String datetime;
	String signal_type;
	String strength;
	
	public SignalEvent(String strategy_id, String symbol, String datetime, String signal_type, String strength) {

		this.oftype = "SIGNAL";
		this.strategy_id = strategy_id;
		this.symbol = symbol;
		this.datetime = datetime;
		this.signal_type = signal_type;
		this.strength = strength;
	}
}
