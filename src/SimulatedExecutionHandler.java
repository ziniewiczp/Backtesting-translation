import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Queue;

public class SimulatedExecutionHandler {
	
	private Queue<Event> events;
	
	public SimulatedExecutionHandler(Queue<Event> events) {
		
		this.events = events;
	}
	
	public void execute_order(OrderEvent event) {
		
		if(event.getOfType() == "ORDER") {
			Date currentDate = GregorianCalendar.getInstance().getTime();
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			String timeindex = df.format(currentDate); 
			
			FillEvent fill_event = new FillEvent(timeindex, event.symbol, "ARCA", event.quantity, event.direction, new BigDecimal(0));
			
			events.add(fill_event);
		}		
	}
}
