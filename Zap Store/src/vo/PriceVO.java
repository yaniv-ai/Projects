package vo;

public class PriceVO {
	private Integer productID;
	private Integer storeID;
	private Double price;

	public PriceVO(Integer productID, Integer storeID, Double price) {
		super();
		this.productID = productID;
		this.storeID = storeID;
		this.price = price;
	}

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public Integer getStoreID() {
		return storeID;
	}

	public void setStoreID(Integer storeID) {
		this.storeID = storeID;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
