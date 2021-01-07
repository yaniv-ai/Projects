/**
 * 
 */
package enums;

public enum KodShem {
	bait("���", 1), ezor("����", 2), kvutza("�����", 3), liga("����", 4);
	private final String description;
	private final int index;

	KodShem(String description, int index) {
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