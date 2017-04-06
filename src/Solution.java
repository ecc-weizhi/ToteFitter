import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;


public class Solution {
	public final int totalVolume;
	public final int totalPrice;
	private final HashSet<String> configuration;

	public Solution(int totalVolume, int totalPrice, Set<String> configuration){
		this.totalVolume = totalVolume;
		this.totalPrice = totalPrice;
		this.configuration = new HashSet<String>(configuration);
	}
	
	public boolean contains(String productId){
		return configuration.contains(productId);
	}
	
	public Set<String> getConfiguration(){
		return configuration;
	}
	
	public String toPrettyString(){
		final String comma = ", ";
		StringBuffer sb = new StringBuffer();
		sb.append(String.format(Locale.US, "Total price: %d\nTotal volume: %d\n", totalPrice, totalVolume));
		if(configuration.size() == 0){
			sb.append("Product: None");
		}
		else {
			Iterator<String> it = configuration.iterator();
			sb.append("Product: ");
			sb.append(it.next());
			
			while(it.hasNext()) {
				sb.append(comma);
				sb.append(it.next());
		    }
		}
		
		return sb.toString();
	}
}
