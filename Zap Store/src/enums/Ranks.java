package enums;

public enum Ranks {
	admin("�����", 1), manager("����", 2), user("�����", 3);
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