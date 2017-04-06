import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;


public class Solver {
	private final int mMaxLength;
	private final int mMaxWidth;
	private final int mMaxHeight;
	private final int mTotalVolume;
	
	private final int mSmallestProductVolume;
	
	private long mStartTime;
	
	//private Map<Integer, List<Product>> mVolumeToProductListMap = new HashMap<>();
	private List<Product> mProductList = new ArrayList<>();
	private Map<Integer, Solution> mVolumeToSolutionMap = new HashMap<>();
	
	public Solver(int maxLength, int maxWidth, int maxHeight, List<Product> productList){
		mMaxLength = maxLength;
		mMaxWidth = maxWidth;
		mMaxHeight = maxHeight;
		mTotalVolume = maxLength * maxWidth * maxHeight;
		
		// remove products which are too large. O(N).
		productList = removeTooLargeProduct(productList);
		System.out.println(String.format(Locale.US, "After removing too large products left: %d", 
				productList.size()));
		
		// sort product list. O(N log(N)).
		Collections.sort(productList, new ProductComparator());
		mSmallestProductVolume = productList.get(productList.size()-1).volume;
		System.out.println("Product List sorted. Smallest volume is " + mSmallestProductVolume);
		
		// partition product list by volume. O(N).
		Map<Integer, List<Product>> volumeToProductListMap = new HashMap<>();
		volumeToProductListMap = partitionProductListByVolume(productList, volumeToProductListMap);
		System.out.println("Product list partitioned");
		
		// join back the partitions and sort it by price. O(N Log(N)).
		mProductList = joinPartition(volumeToProductListMap);
		System.out.println("Product list joined back and sorted by price: " + mProductList.size());
	}
	
	public Solution solve(){
		mStartTime = System.currentTimeMillis();
		System.out.println("\nBegin solving...");
		return solve(mTotalVolume);
	}
	
	private boolean isTooLarge(Product product){
		return (product.length > mMaxLength) ||
				(product.width > mMaxWidth) ||
				(product.height > mMaxHeight) || 
				(product.volume > mTotalVolume);
	}
	
	/**
	 * O(N) method to remove products that are too large.
	 * 
	 * @param productList list of products to remove too large products from.
	 * @return product list with too large products removed.
	 */
	private List<Product> removeTooLargeProduct(List<Product> productList){
		Iterator<Product> it = productList.iterator();
		while (it.hasNext()) {
		    Product product = it.next();
		    
		    if ( isTooLarge(product) ) {
		        it.remove();
		    }
		}
		
		return productList;
	}
	
	/**
	 * O(N) method to partition list of products by volume and put into a map.
	 *  
	 * @param productList to be partition.
	 * @return map of volume to product list.
	 */
	private Map<Integer, List<Product>> partitionProductListByVolume(List<Product> productList, 
			Map<Integer, List<Product>> volumeToProductListMap){
		Iterator<Product> it = productList.iterator();
		
		if(volumeToProductListMap == null){
			volumeToProductListMap = new HashMap<>();
		}
		else{
			volumeToProductListMap.clear();
		}
		
		List<Product> productListByVolume = new ArrayList<>();
		int listVolume = -1;
		int maxProductInList = 0;
		int productCount = 0;
		while (it.hasNext()) {
		    Product product = it.next();
		    
		    if(product.volume != listVolume){
		    	productListByVolume = new ArrayList<>();
		    	listVolume = product.volume;
		    	maxProductInList = mTotalVolume / listVolume;
		    	volumeToProductListMap.put(listVolume, productListByVolume);
		    }
		    
		    if(productListByVolume.size() < maxProductInList){
		    	productListByVolume.add(product);
		    	productCount++;
		    }
		}
		
		System.out.println("AFter partition left: " + productCount);
		return volumeToProductListMap;
	}
	
	/**
	 * O(N Log(N)) method to join the partitioned list back and sort it.
	 * 
	 * @param volumeToProductListMap the partitioned map
	 * @return a list of product sorted by price.
	 */
	private List<Product> joinPartition(Map<Integer, List<Product>> volumeToProductListMap){
		List<Product> productList = new ArrayList<>();
		
		// O(N) since we are adding each product to array list.
		Set<Integer> keys = volumeToProductListMap.keySet();
		for(int key : keys){
			productList.addAll(volumeToProductListMap.get(key));
		}
		
		// O(N Log(N)) sorting.
		Collections.sort(productList, new PriceComparator());
		return productList;
	}
	
	/**
	 * Solution for each volume will be solved exactly once and save it to a map so we can
	 * reuse the result in the future. Solving each volume takes O(N) assuming solution for
	 * all the previous solutions are known. We need O(VN) time in total. 
	 * 
	 * @param volume
	 * @return
	 */
	private Solution solve(int volume){
		Solution bestSolution = null;
		
		if(mVolumeToSolutionMap.containsKey(volume)){
			bestSolution = mVolumeToSolutionMap.get(volume);
			return bestSolution;
		}
		
		if(volume < mSmallestProductVolume){
			bestSolution = new Solution(0, 0, new HashSet<String>());
			mVolumeToSolutionMap.put(volume, bestSolution);
			return bestSolution;
		}
		
		// This is the actual solving for a specific V = volume. We iterate through every solution
		// for each V less than the current volume. For each solution we iterate through we look for
		// the product with the highest price that can be added the current solution.
		// O(VN);
		for(int i=0; i<volume; i++){
			Solution solution = solve(i);
			
			// go through all product with volume less than volumeLeft to find
			// the product with highest price.
			int volumeLeft = volume - i;
			Product highestPriceProduct = null;
			for(Product product : mProductList){
				if(product.volume <= volumeLeft && !solution.contains(product.id)){
					highestPriceProduct = product;
					break;
				}
			}
			
			// Construct a possible new solution for given volume
			Solution possibleSolution;
			if(highestPriceProduct == null){
				possibleSolution = solution;
			}
			else{
				Set<String> configuration = new HashSet<>(solution.getConfiguration());
				configuration.add(highestPriceProduct.id);
				possibleSolution = new Solution(solution.totalVolume + highestPriceProduct.volume, 
						solution.totalPrice + highestPriceProduct.price, 
						configuration);
			}
			
			if(bestSolution == null){
				bestSolution = possibleSolution;
			}
			else{
				if(possibleSolution.totalPrice > bestSolution.totalPrice){
					bestSolution = possibleSolution;
				}
			}
		}
		
		if(volume % 1000 == 0){
			long timeElapsed = System.currentTimeMillis() - mStartTime;
			System.out.println(String.format(Locale.US, "Found optimal solution for volume %d: $%d, timeElapsed(ms): %d", 
					volume, bestSolution.totalPrice, timeElapsed));	
		}
		mVolumeToSolutionMap.put(volume, bestSolution);
		return bestSolution;
	}
}
