import java.math.BigDecimal;

public class DataRow {

	private String price_date;
	private BigDecimal open_price;
	private BigDecimal high_price;
	private BigDecimal low_price;
	private BigDecimal close_price;
	private BigDecimal volume;
	private BigDecimal adj_close_price;
	
	public String getPrice_date() {
		return price_date;
	}
	public void setPrice_date(String price_date) {
		this.price_date = price_date;
	}
	public BigDecimal getOpen_price() {
		return open_price;
	}
	public void setOpen_price(BigDecimal open_price) {
		this.open_price = open_price;
	}
	public BigDecimal getHigh_price() {
		return high_price;
	}
	public void setHigh_price(BigDecimal high_price) {
		this.high_price = high_price;
	}
	public BigDecimal getLow_price() {
		return low_price;
	}
	public void setLow_price(BigDecimal low_price) {
		this.low_price = low_price;
	}
	public BigDecimal getClose_price() {
		return close_price;
	}
	public void setClose_price(BigDecimal close_price) {
		this.close_price = close_price;
	}
	public BigDecimal getVolume() {
		return volume;
	}
	public void setVolume(BigDecimal volume) {
		this.volume = volume;
	}
	public BigDecimal getAdj_close_price() {
		return adj_close_price;
	}
	public void setAdj_close_price(BigDecimal adj_close_price) {
		this.adj_close_price = adj_close_price;
	}
	
	public String toString(){
		return price_date + open_price + close_price + low_price + high_price + volume + adj_close_price;
	}
	
	
}
