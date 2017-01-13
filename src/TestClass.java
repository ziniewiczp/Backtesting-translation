
public class TestClass {
	
	public static void main(String[] args) {
		
		DatabaseDataHandler dataHandler = new DatabaseDataHandler();
		
		dataHandler.update_bars();
		dataHandler.update_bars();
		dataHandler.update_bars();
		
		System.out.println("Get latest bar:");
		System.out.println(dataHandler.get_latest_bar("AAPL"));
		System.out.println(dataHandler.get_latest_bar("GOOG"));
		System.out.println(dataHandler.get_latest_bar("PEP"));
		System.out.println();
		
		System.out.println("Get latest 3 bars:");
		System.out.println(dataHandler.get_latest_bars("AAPL", 3));
		System.out.println(dataHandler.get_latest_bars("GOOG", 3));
		System.out.println(dataHandler.get_latest_bars("PEP", 3));
		System.out.println();
		
		System.out.println("Get latest bar datetime:");
		System.out.println(dataHandler.get_latest_bar_datetime("AAPL"));
		System.out.println(dataHandler.get_latest_bar_datetime("GOOG"));
		System.out.println(dataHandler.get_latest_bar_datetime("PEP"));
		System.out.println();
		
		System.out.println("Get latest bar volume:");
		System.out.println(dataHandler.get_latest_bar_value("AAPL", "volume"));
		System.out.println(dataHandler.get_latest_bar_value("GOOG", "volume"));
		System.out.println(dataHandler.get_latest_bar_value("PEP", "volume"));
		System.out.println();
		
		System.out.println("Get volume of 3 latest bars:");
		System.out.println(dataHandler.get_latest_bar_values("AAPL", "volume", 3));
		System.out.println(dataHandler.get_latest_bar_values("GOOG", "volume", 3));
		System.out.println(dataHandler.get_latest_bar_values("PEP", "volume", 3));
		System.out.println();
	}
}
