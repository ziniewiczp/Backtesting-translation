import java.math.BigDecimal;
import java.util.ArrayList;

public abstract class DataHandler {

	abstract DataRow get_latest_bar(String symbol);
	
	abstract ArrayList<DataRow> get_latest_bars(String symbol, int N);
	
	abstract String get_latest_bar_datetime(String symbol);
	
	abstract BigDecimal get_latest_bar_value(String symbol, String val_type);
	
	abstract ArrayList<BigDecimal> get_latest_bar_values(String symbol, String val_type, int N);
	
	abstract void update_bars();
}
