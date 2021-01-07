package enums;

public enum Options {
	knisatMenahel("כניסת מנהל", 0), knisatMishtamesh("כניסת משתמש", 1), hashvaatMechirim(
			"השוואת מחירים", 10), hosafatMutzar("הוספת מוצר", 11), hosafatManager(
			"הוספת מנהל", 12), knisa("מסך כניסה", 50), tafrit("תפריט", 100), noOption(
			"כלום", 999);
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