import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;

public class Backtest {

	private int signals;
    private int orders;
	private int fills;
	
	private String[] symbolList;
	private BigDecimal initialCapital;
	private long heartbeat;
	private String startDate;
	private DatabaseDataHandler dataHandler;
	private SimulatedExecutionHandler executionHandler;
	private Portfolio portfolio;
	private Strategy strategy;
	private Queue<Event> events;
	
	public Backtest(String[] symbolList, BigDecimal initialCapital, long heartbeat, String startDate) {
		
		this.symbolList = symbolList;
		this.initialCapital = initialCapital;
		this.heartbeat = heartbeat;
		this.startDate = startDate;
		
		this.signals = 0;
		this.orders = 0;
		this.fills = 0;
		
		this.events = new LinkedList<Event>();
		
		generate_trading_instances();
	}
	
	public void generate_trading_instances() {
		System.out.println("Creating DataHandler, Strategy, Portfolio and ExecutionHandler...");
		this.dataHandler = new DatabaseDataHandler(this.events, this.symbolList);
		this.strategy = new Strategy();
		this.portfolio = new Portfolio(this.dataHandler, this.events, this.startDate, this.initialCapital, this.symbolList);
		this.executionHandler = new SimulatedExecutionHandler(this.events);
	}
	
	public void run_backtest() {
		int i = 0;
		Event event;
		
		while(true) {
			i++;
			System.out.println(i);
			if(dataHandler.getContinueBacktest() == true) {
				dataHandler.update_bars();
			}
			else {
				break;
			}
			
			//Handle the events.
			while(true) {
				if( !events.isEmpty() ) {
					
					event = events.remove();

					if(event.getOfType().equals("MARKET")) {
						strategy.calculate_signals(event);
						portfolio.update_timeindex(event);
						
					} else if(event.getOfType().equals("SIGNAL")) {
							signals++;
							portfolio.update_signal((SignalEvent) event);
							
					} else if(event.getOfType().equals("ORDER")) {
							orders++;
							executionHandler.execute_order((OrderEvent) event);
					} else if(event.getOfType().equals("FILL")) {
							fills++;
							portfolio.update_fill((FillEvent) event);
					}
				}
			}
				
			Thread.sleep(heartbeat);
		}
	}
	
	public void output_performance() {
		
		/*portfolio.create_equity_curve_dataframe();
		System.out.println("Creating summary stats...");
		stats = portfolio.output_summary_stats();
		
		System.out.println("Creating equity curve...");
		System.out.println(portfolio.equity_curve.tail(10));
		System.out.println(stats);
		System.out.println("Signals: " + signals);
		System.out.println("Orders: " + orders);
		System.out.println("Fills: " + fills);*/
		
		portfolio.output_summary_stats();
	}
	
	public void simulate_trading(){
		run_backtest();
		output_performance();
	}
}
