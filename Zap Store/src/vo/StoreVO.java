package vo;

public class StoreVO {

	private Integer storeID;
	private String storeName;
	private String storeURL;

	public StoreVO(Integer storeID, String storeName, String storeURL) {
		super();
		this.storeID = storeID;
		this.storeName = storeName;
		this.storeURL = storeURL;
	}

	public Integer getStoreID() {
		return storeID;
	}

	public void setStoreID(Integer storeID) {
		this.storeID = storeID;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreURL() {
		return storeURL;
	}

	public void setStoreURL(String storeURL) {
		this.storeURL = storeURL;
	}

}
