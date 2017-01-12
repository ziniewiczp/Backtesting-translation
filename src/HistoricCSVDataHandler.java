import java.util.HashMap;
import joinery.DataFrame;


public class HistoricCSVDataHandler extends DataHandler {

	Boolean continue_backtest = true;
	
	public void _open_convert_csv_files(String[] symbol_list){
		
		int L = symbol_list.length;
//		DataFrame<Object>[] symbol_data = new DataFrame<Object>[L];
//		ArrayList<Object> symbol_data
		HashMap<String, DataFrame<Object>> symbol_data = new HashMap<String, DataFrame<Object>>();
		for(int i=0; i<L; i++){
			//Load csv file
			DataFrame<Object> temp_symbol_data = new DataFrame<Object>().readCsv("AAPL.csv");
			symbol_data.put(symbol_list[i], temp_symbol_data);
			System.out.print(symbol_data.get("AAPL"));	
			//***********************
		
/*			if (comb_index = null){
				comb_index = symbol_data[i]
			}
			else{
				comb_index.union(symbol_data[s].index)
			}*/
		}
	}
}
