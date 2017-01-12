import java.math.BigDecimal;
import java.util.ArrayList;
import java.sql.*;


public class DatabaseDataHandler{

	public int currentRow;
	public ArrayList<DataRow> dataset;
	
	public void open_convert_database(){
		
		Connection dbConnection = null;
		Statement dbStatement = null;
		ResultSet resultSet = null;
		dataset = new ArrayList<DataRow>();
		currentRow = 0;
		
		try {
			// registering JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			// creating DB connection
			dbConnection = DriverManager
					.getConnection("jdbc:mysql://localhost:3306/securities_master","root", "hieno07miau");

			dbStatement = dbConnection.createStatement();
			resultSet = dbStatement.executeQuery("SELECT dp.price_date, dp.open_price, dp.high_price, dp.low_price, dp.close_price, dp.volume, dp.adj_close_price FROM symbol AS sym INNER JOIN daily_price AS dp ON dp.symbol_id = sym.id WHERE sym.ticker = \"AAPL\" ORDER BY dp.price_date ASC;");
			while (resultSet.next()) {
				DataRow newRow = new DataRow();
				newRow.adj_close_price = resultSet.getBigDecimal("adj_close_price");
				newRow.open_price = resultSet.getBigDecimal("open_price");
				newRow.price_date = resultSet.getString("price_date");
				newRow.close_price = resultSet.getBigDecimal("close_price");
				newRow.high_price = resultSet.getBigDecimal("high_price");
				newRow.low_price = resultSet.getBigDecimal("low_price");
				newRow.volume = resultSet.getBigDecimal("volume");
				dataset.add(newRow);
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
	
	public DataRow get_new_bar(){
		DataRow row = dataset.get(currentRow);
		currentRow++;
		return row;
	}
	
	public DataRow get_latest_bar(){
		DataRow latest_row = dataset.get(currentRow-1);
		return latest_row;
	}
	
	public ArrayList<DataRow> get_latest_bars(int startIndex) {
		ArrayList<DataRow> latestBars = new ArrayList<DataRow>();
		for(int i = startIndex; i <= currentRow; i++) {
			latestBars.add(dataset.get(i));
		}
		
		return latestBars;
	}
	
	public String get_latest_bar_datetime(){
		String latest_bar_datetime = dataset.get(currentRow).price_date;
		return latest_bar_datetime;
	}
	
	public BigDecimal get_latest_bar_value(String val_type){
		DataRow currentDataRow = dataset.get(currentRow);
		BigDecimal latest_bar_value = currentDataRow;
		
	}
}
