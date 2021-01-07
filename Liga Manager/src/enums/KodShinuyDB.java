/**
 * 
 */
package enums;

public enum KodShinuyDB {
	hosafa("הוספה", 1), idkun("עדכון", 2);
	private final String description;
	private final int index;

	KodShinuyDB(String description, int index) {
		this.description = description;
		this.index = index;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	public int getIndex() {
		return index;
	}

}