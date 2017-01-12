
public abstract class DataHandler {

	abstract void get_latest_bar(String symbol);
	
	abstract void get_latest_bars(String symbol, int N);
	
	abstract void get_latest_bar_datetime(String symbol);
	
	abstract void get_latest_bar_value(String symbol, String val_type);
	
	abstract void get_latest_bar_values(String symbol, String val_type, int N);
	
	abstract void update_bars();
}
