package enums;

public enum Options {
	knisatMenahel("����� ����", 0), knisatMishtamesh("����� �����", 1), hashvaatMechirim(
			"������ ������", 10), hosafatMutzar("����� ����", 11), hosafatManager(
			"����� ����", 12), knisa("��� �����", 50), tafrit("�����", 100), noOption(
			"����", 999);
	private final String description;
	private final int index;

	Options(String description, int index) {
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