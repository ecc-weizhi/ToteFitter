
public class Product {
	public final String id;
	public final int price;
	public final int length;
	public final int width;
	public final int height;
	public final int volume;
	public final int weight;
	
	public Product(String id, int price, int length, int width, int height, int weight){
		this.id = id;
		this.price = price;
		this.length = length;
		this.width = width;
		this.height = height;
		this.volume = length * width * height;
		this.weight = weight;
	}
}
