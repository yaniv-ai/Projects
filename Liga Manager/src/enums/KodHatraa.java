/**
 * 
 */
package enums;

public enum KodHatraa {
	meida("���� ����", 1), sheela("����", 2), shgia("�����", 3);
	private final String description;
	private final int index;

	KodHatraa(String description, int index) {
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