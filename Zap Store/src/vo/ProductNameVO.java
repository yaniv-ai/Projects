package vo;

public class ProductNameVO {

	private String productName;
	private String productProducer;

	public ProductNameVO(String productName, String productProducer) {
		super();
		this.productName = productName;
		this.productProducer = productProducer;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductProducer() {
		return productProducer;
	}

	public void setProductProducer(String productProducer) {
		this.productProducer = productProducer;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(ProductNameVO.class)) {
			return false;
		}

		ProductNameVO p = (ProductNameVO) obj;
		if (getProductName() == null || getProductProducer() == null
				|| p.getProductName() == null || p.getProductProducer() == null) {
			return false;
		}
		if (getProductName().equals(p.getProductName())
				&& getProductProducer().equals(p.getProductProducer())) {
			return true;
		}
		return false;
	}

}
