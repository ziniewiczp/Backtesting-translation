import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Queue;
import java.sql.*;


public class DatabaseDataHandler extends DataHandler {

	private int currentRow;
	private String[] symbolList;
	private ArrayList<DataRow> AAPLDataset;
	private ArrayList<DataRow> GOOGDataset;
	private ArrayList<DataRow> PEPDataset;
	private Queue<Event> events;
	private boolean continueBacktest;
	
	public DatabaseDataHandler(Queue<Event> events, String[] symbolList) {
		
		this.symbolList = symbolList;
		this.events = events;
		this.continueBacktest = true;
		
		open_convert_database();
	}
	
	public boolean getContinueBacktest() {
		return this.continueBacktest;
	}
	
	public String[] getSymbolList() {
		return this.symbolList;
	}

	/*
	 * Initialization. Downloads data from
	 * database and propagates arrays with it.
	 */
	private void open_convert_database() {
		
		Connection dbConnection = null;
		Statement dbStatement = null;
		ResultSet resultSet = null;
		AAPLDataset = new ArrayList<DataRow>();
		GOOGDataset = new ArrayList<DataRow>();
		PEPDataset = new ArrayList<DataRow>();
		currentRow = 0;
		
		try {
			// registering JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// creating DB connection
			dbConnection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/securities_master","root", "hieno07miau");

			dbStatement = dbConnection.createStatement();
			
			for( String symbol : symbolList ) {
				// getting data from database
				resultSet = dbStatement.executeQuery("SELECT dp.price_date, dp.open_price," + 
													 "dp.high_price, dp.low_price, dp.close_price," + 
													 "dp.volume, dp.adj_close_price FROM symbol AS sym " + 
													 "INNER JOIN daily_price AS dp ON dp.symbol_id = sym.id " + 
													 "WHERE sym.ticker = \"" + symbol + "\" ORDER BY dp.price_date ASC;");
				
				// filling dataset ArrayList with data taken from database
				while (resultSet.next()) {
					DataRow newRow = new DataRow();
					newRow.setAdj_close_price(resultSet.getBigDecimal("adj_close_price"));
					newRow.setOpen_price(resultSet.getBigDecimal("open_price"));
					newRow.setPrice_date(resultSet.getString("price_date"));
					newRow.setClose_price(resultSet.getBigDecimal("close_price"));
					newRow.setHigh_price(resultSet.getBigDecimal("high_price"));
					newRow.setLow_price(resultSet.getBigDecimal("low_price"));
					newRow.setVolume(resultSet.getBigDecimal("volume"));
					
					switch( symbol ) {
						case "AAPL":
							AAPLDataset.add(newRow);
							break;
							
						case "GOOG":
							GOOGDataset.add(newRow);
							break;
						
						case "PEP":
							PEPDataset.add(newRow);
							break;
					}
				}
			}
			
			resultSet.close();
			dbStatement.close();
			
		} catch (SQLException e) {
			e.printStackTrace();

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (dbStatement != null)
					dbStatement.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}

			try {
				if (dbConnection != null)
					dbConnection.close();

			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * Returns the next row from database for 
	 * specified symbol. Used by update_rows().
	 */
	private DataRow get_new_bar(String symbol) {
		DataRow row = null;
		
		switch( symbol ) {
			case "AAPL":
				row = AAPLDataset.get(currentRow);
				break;
				
			case "GOOG":
				row = GOOGDataset.get(currentRow);
				break;
			
			case "PEP":
				row = PEPDataset.get(currentRow);
				break;
		}
		
		return row;
	}
	
	public DataRow get_latest_bar(String symbol) {
		DataRow latestRow = null;
		
		switch( symbol ) {
			case "AAPL":
				latestRow = AAPLDataset.get(currentRow - 1);
				break;
				
			case "GOOG":
				latestRow = GOOGDataset.get(currentRow - 1);
				break;
			
			case "PEP":
				latestRow = PEPDataset.get(currentRow - 1);
				break;
		}
		
		return latestRow;
	}

	public ArrayList<DataRow> get_latest_bars(String symbol, int N) {
		ArrayList<DataRow> latestBars = new ArrayList<DataRow>();
		
		switch( symbol ) {
			case "AAPL":
				for(int i = N; i > 0; i--) {
					latestBars.add(AAPLDataset.get(currentRow - (i-1)));
				}
				break;
				
			case "GOOG":
				for(int i = N; i > 0; i--) {
					latestBars.add(GOOGDataset.get(currentRow - (i-1)));
				}
				break;
			
			case "PEP":
				for(int i = N; i > 0; i--) {
					latestBars.add(PEPDataset.get(currentRow - (i-1)));
				}
				break;
		}
		
		return latestBars;
	}

	public String get_latest_bar_datetime(String symbol) {
		String latestBarDatetime = null;
		
		switch( symbol ) {
			case "AAPL":
				latestBarDatetime = AAPLDataset.get(currentRow).getPrice_date();
				break;
				
			case "GOOG":
				latestBarDatetime = GOOGDataset.get(currentRow).getPrice_date();
				break;
			
			case "PEP":
				latestBarDatetime = PEPDataset.get(currentRow).getPrice_date();
				break;
		}
		
		return latestBarDatetime;
	}
	
	public BigDecimal get_latest_bar_value(String symbol, String val_type) {
		DataRow currentDataRow = get_latest_bar(symbol);
		
		switch( val_type ) {
			case "adj_close_price":
				return currentDataRow.getAdj_close_price();
			
			case "open price":
				return currentDataRow.getOpen_price();
			
			case "close price":
				return currentDataRow.getClose_price();
			
			case "high_price":
				return currentDataRow.getHigh_price();
			
			case "low_price":
				return currentDataRow.getLow_price();
			
			case "volume":
				return currentDataRow.getVolume();
				
			default:
				return null;
		
		}
	}
	
	public ArrayList<BigDecimal> get_latest_bar_values(String symbol, String val_type, int N) {
		ArrayList<DataRow> latestRows = get_latest_bars(symbol, N);
		ArrayList<BigDecimal> latestRowsValues = new ArrayList<BigDecimal>();
		
		switch( val_type ) {
			case "adj_close_price":
				for( DataRow row : latestRows ) {
					latestRowsValues.add(row.getAdj_close_price());
				}
				break;
			
			case "open price":
				for( DataRow row : latestRows ) {
					latestRowsValues.add(row.getOpen_price());
				}
				break;
			
			case "close price":
				for( DataRow row : latestRows ) {
					latestRowsValues.add(row.getClose_price());
				}
				break;
			
			case "high_price":
				for( DataRow row : latestRows ) {
					latestRowsValues.add(row.getHigh_price());
				}
				break;
			
			case "low_price":
				for( DataRow row : latestRows ) {
					latestRowsValues.add(row.getLow_price());
				}
				break;
			
			case "volume":
				for( DataRow row : latestRows ) {
					latestRowsValues.add(row.getVolume());
				}
				break;
				
			default:
				return null;		
		}
		
		return latestRowsValues;
	}
	
	public void update_bars() {
		for( String symbol : symbolList ) {
			get_new_bar(symbol);
		}
		
		this.events.add(new MarketEvent());
		
		if(this.currentRow >= this.AAPLDataset.size() - 1 || this.currentRow >= this.GOOGDataset.size() - 1
				|| this.currentRow >= this.PEPDataset.size() - 1) {
			
			this.continueBacktest = false;
			
		} else {
			this.currentRow++;
		}
	}
}
