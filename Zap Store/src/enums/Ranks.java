package enums;

public enum Ranks {
	admin("בעלים", 1), manager("מנהל", 2), user("משתמש", 3);
	private final String description;
	private final int index;

	Ranks(String description, int index) {
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