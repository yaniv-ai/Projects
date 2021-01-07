/**
 * 
 */
package enums;

public enum KodShinuyDB {
	hosafa("�����", 1), idkun("�����", 2);
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