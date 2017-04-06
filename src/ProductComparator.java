import java.util.Comparator;

/**
 * Comparator to sort product by volume (high to low) first, price (high to low) second and weight (low to high) last. 
 * 
 * @author Lin Weizhi (ecc.weizhi@gmail.com)
 *
 */
public class ProductComparator implements Comparator<Product> {
	@Override
	public int compare(Product first, Product second) {
		int volumeDiff = second.volume - first.volume;
		if(volumeDiff == 0){
			int priceDiff = second.price - first.price;
			if(priceDiff == 0){
				return first.weight - second.weight;
			}
			else{
				return priceDiff;
			}
		}
		else{
			return volumeDiff;
		}
	}
}
