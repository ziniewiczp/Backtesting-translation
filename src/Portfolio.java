import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Queue;

public class Portfolio {
	
	private DatabaseDataHandler data_handler;
	private Queue<Event> events;
	private String start_date;
	private BigDecimal initial_capital;
	private String[] symbol_list;
	
	private ArrayList<Position> all_positions;
	private ArrayList<Holding> all_holdings;
	private HashMap<String, Position> current_positions;
	private HashMap<String, Holding> current_holdings;

	public Portfolio(DatabaseDataHandler data_handler, Queue<Event> events, String start_date, BigDecimal initial_capital, String[] symbol_list) {
		
		this.data_handler = data_handler;
		this.events = events;
		this.start_date = start_date;
		this.initial_capital = initial_capital;
		this.symbol_list = symbol_list;
		
		this.all_positions = construct_all_positions();
		this.all_holdings = construct_all_holdings();
		this.current_positions = construct_current_positions();
		this.current_holdings = construct_current_holdings();
	}
	
	private ArrayList<Position> construct_all_positions() {
		
		ArrayList<Position> all_positions = new ArrayList<Position>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = Calendar.getInstance().getTime();
		String formattedCurrentTime = dateFormat.format(currentTime);
		
		for( String symbol : this.symbol_list) {
			
			all_positions.add(new Position(symbol, BigDecimal.ZERO, formattedCurrentTime));
		}
		
		return all_positions;
	}
	
	private ArrayList<Holding> construct_all_holdings() {
		
		ArrayList<Holding> all_holdings = new ArrayList<Holding>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = Calendar.getInstance().getTime();
		String formattedCurrentTime = dateFormat.format(currentTime);
		
		for( String symbol : this.symbol_list) {
			
			all_holdings.add(new Holding(symbol, BigDecimal.ZERO, formattedCurrentTime, this.initial_capital, BigDecimal.ZERO, this.initial_capital));
		}
		
		return all_holdings;
	}
	
	private HashMap<String, Position> construct_current_positions() {
		
		HashMap<String, Position> current_positions = new HashMap<String, Position>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = Calendar.getInstance().getTime();
		String formattedCurrentTime = dateFormat.format(currentTime);
		
		for( String symbol : this.symbol_list) {
			
			current_positions.put(symbol, new Position(symbol, BigDecimal.ZERO, formattedCurrentTime));
		}
		
		return current_positions;
	}
	
	private HashMap<String, Holding> construct_current_holdings() {
		
		HashMap<String, Holding> current_holdings = new HashMap<String, Holding>();
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date currentTime = Calendar.getInstance().getTime();
		String formattedCurrentTime = dateFormat.format(currentTime);
		
		for( String symbol : this.symbol_list) {
			
			current_holdings.put(symbol, new Holding(symbol, BigDecimal.ZERO, formattedCurrentTime, this.initial_capital, BigDecimal.ZERO, this.initial_capital));
		}
		
		return current_holdings;
	}
	
	public void update_timeindex(Event event) {
		
		String latest_datetime = data_handler.get_latest_bar_datetime(this.symbol_list[0]);
		
		// update positions
		for( String symbol : this.symbol_list ) {
			
			BigDecimal currentQuantity = this.current_positions.get(symbol).getQuantity();
			
			this.all_positions.add(new Position(symbol, currentQuantity, latest_datetime));
		}
		
		// update holdings
		for( String symbol : this.symbol_list ) {
			
			BigDecimal currentCash = this.current_holdings.get(symbol).getCash();
			BigDecimal currentCommissions = this.current_holdings.get(symbol).getCommissions();
			BigDecimal currentTotal = this.current_holdings.get(symbol).getTotal();
			
			// Approximation to the real value
			BigDecimal market_value = this.current_positions.get(symbol).getQuantity().multiply(
					this.data_handler.get_latest_bar_value(symbol, "adj_close_price"));
			
			BigDecimal currentQuantity = market_value;
			currentTotal.add(market_value);
			
			this.all_holdings.add(new Holding(symbol, currentQuantity, latest_datetime, currentCash, currentCommissions, currentTotal));
		}
	}
	
	private void update_positions_from_fill(FillEvent fillEvent) {
		int fill_dir = 0;
		
		if(fillEvent.getDirection().equals("BUY")) {
			fill_dir = 1;
		} else if(fillEvent.getDirection().equals("SELL")) {
			fill_dir = -1;
		}
		
		BigDecimal newQuantity = fillEvent.getQuantity().multiply(new BigDecimal(fill_dir));
		BigDecimal currentQuantity = this.current_positions.get(fillEvent.getSymbol()).getQuantity();
		
		this.current_positions.get(fillEvent.getSymbol()).setQuantity(currentQuantity.add(newQuantity));
	}
	
	private void update_holdings_from_fill(FillEvent fillEvent) {
		int fill_dir = 0;
		
		if(fillEvent.getDirection().equals("BUY")) {
			fill_dir = 1;
		} else if(fillEvent.getDirection().equals("SELL")) {
			fill_dir = -1;
		}
		
		BigDecimal currentQuantity = this.current_holdings.get(fillEvent.getSymbol()).getQuantity();
		BigDecimal currentCommissions = this.current_holdings.get(fillEvent.getSymbol()).getCommissions();
		BigDecimal currentCash = this.current_holdings.get(fillEvent.getSymbol()).getCash();
		BigDecimal currentTotal = this.current_holdings.get(fillEvent.getSymbol()).getTotal();
		
		BigDecimal fill_cost = data_handler.get_latest_bar_value(fillEvent.getSymbol(), "adj_close");
		BigDecimal cost = fill_cost.multiply(new BigDecimal(fill_dir).multiply(fillEvent.getQuantity()));
		
	    this.current_holdings.get(fillEvent.getSymbol()).setQuantity(currentQuantity.add(cost));
	    this.current_holdings.get(fillEvent.getSymbol()).setCommissions(currentCommissions.add(fillEvent.getCommission()));
	    this.current_holdings.get(fillEvent.getSymbol()).setCash(currentCash.subtract(cost.add(fillEvent.getCommission())));
	    this.current_holdings.get(fillEvent.getSymbol()).setTotal(currentTotal.subtract(cost.add(fillEvent.getCommission())));
	}
	
	public void update_fill(FillEvent fillEvent) {
		if( fillEvent.getOfType().equals("FILL")) {
			update_positions_from_fill(fillEvent);
			update_holdings_from_fill(fillEvent);
		}
	}
	
	private OrderEvent generate_naive_order(SignalEvent signalEvent) {
		
		OrderEvent orderEvent = null;
		
		BigDecimal mkt_quantity = new BigDecimal(100);
		BigDecimal cur_quantity = this.current_positions.get(signalEvent.getSymbol()).getQuantity();
		String order_type = "MKT";
		
		if(signalEvent.getSignal_type().equals("LONG") && cur_quantity.equals(0)) {
			orderEvent = new OrderEvent(signalEvent.getSymbol(), order_type, mkt_quantity, "BUY");
		}
		
		if(signalEvent.getSignal_type().equals("SHORT") && cur_quantity.equals(0)) {
			orderEvent = new OrderEvent(signalEvent.getSymbol(), order_type, mkt_quantity, "SELL");
		}
		
		if(signalEvent.getSignal_type().equals("EXIT") && cur_quantity.compareTo(BigDecimal.ZERO) == 1) {
			orderEvent = new OrderEvent(signalEvent.getSymbol(), order_type, cur_quantity.abs(), "SELL");
		}
		
		if(signalEvent.getSignal_type().equals("EXIT") && cur_quantity.compareTo(BigDecimal.ZERO) == -1) {
			orderEvent = new OrderEvent(signalEvent.getSymbol(), order_type, cur_quantity.abs(), "BUY");
		}
		
		return orderEvent;
	}
	
	public void update_signal(SignalEvent signalEvent) {
		
		if(signalEvent.getOfType().equals("SIGNAL")) {
			this.events.add(generate_naive_order(signalEvent));
		}
	}
	
	public void output_summary_stats() {
		System.out.println(all_holdings);
	}
}
