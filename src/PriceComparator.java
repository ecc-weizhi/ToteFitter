import java.util.Comparator;


public class PriceComparator implements Comparator<Product> {
	@Override
	public int compare(Product first, Product second) {
		return second.price - first.price;
	}
}
