package vo;

public class ProductVO extends ProductNameVO {

	private Integer productID;
	private Integer productCategory;

	public ProductVO(Integer productID, String productName,
			String productProducer, Integer productCategory) {
		super(productName, productProducer);
		this.productID = productID;
		this.productCategory = productCategory;
	}

	public Integer getProductID() {
		return productID;
	}

	public void setProductID(Integer productID) {
		this.productID = productID;
	}

	public Integer getProductCategory() {
		return productCategory;
	}

	public void setProductCategory(Integer productCategory) {
		this.productCategory = productCategory;
	}

}
