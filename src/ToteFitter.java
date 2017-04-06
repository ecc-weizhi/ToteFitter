import java.io.File;
import java.util.List;
import java.util.Locale;


public class ToteFitter {
	private static final String FILE_NAME = "products.csv";
	private static final int LENGTH = 45;
	private static final int WIDTH = 30;
	private static final int HEIGHT = 35;
	
	public static void main(String[] args) {
		List<Product> productList = InputCsvReader.read(new File(FILE_NAME));
		System.out.println(String.format(Locale.US, "Read %d products from csv", productList.size()));
		
		Solver solver = new Solver(LENGTH, WIDTH, HEIGHT, productList);
		Solution solution = solver.solve();
		
		System.out.println(solution.toPrettyString());
	}

}
