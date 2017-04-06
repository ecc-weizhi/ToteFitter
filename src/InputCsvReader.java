import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;


public class InputCsvReader {
	public static final String DELIMITER = ",";
	
	public static List<Product> read(File inputCsv){
		List<Product> productList = new LinkedList<>();
		
		try (BufferedReader br = new BufferedReader(new FileReader(inputCsv))) {
			String line;
		    while ((line = br.readLine()) != null) {
		    	String[] splittedString = line.split(DELIMITER);
		    	if(splittedString.length == 6){
		    		Product product = new Product(splittedString[0].trim(),
		    				Integer.parseInt(splittedString[1].trim()),
		    				Integer.parseInt(splittedString[2].trim()),
		    				Integer.parseInt(splittedString[3].trim()),
		    				Integer.parseInt(splittedString[4].trim()),
		    				Integer.parseInt(splittedString[5].trim()));
		    		productList.add(product);
		    	}
		    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return productList;
	}

}
