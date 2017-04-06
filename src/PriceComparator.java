import java.util.Comparator;


public class PriceComparator implements Comparator<Product> {
	@Override
	public int compare(Product first, Product second) {
		int priceDiff = second.price - first.price;
		if(priceDiff == 0){
			return first.weight - second.weight;
		}
		else{
			return priceDiff;
		}
	}
}
