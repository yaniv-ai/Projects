/**
 * 
 */
package enums;

public enum KodShem {
	bait("בית", 1), ezor("אזור", 2), kvutza("קבוצה", 3), liga("ליגה", 4);
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