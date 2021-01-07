/**
 * 
 */
package actions;

public enum AdminActions {
	resetGames(1, "איפוס טבלת משחקים"), loadGroups(6, "טעינת קבוצות מאקסל"), irbulLefiEzorim(
			8, "בניית משחקים לפי אזורים"), irbulLefiBchira(9,
			"בניית משחקים לפי בחירה"), resetGoals(2, "איפוס טבלת גולים"), loadPlayers(
			7, "טעינת שחקנים מאקסל"), resetGroups(3, "איפוס טבלת קבוצות"), resetPlayers(
			4, "איפוס טבלת שחקנים"), resetAllTables(5, "איפוס כל הטבלאות"), resetLogTables(
			10, "איפוס טבלאות הגיבוי"), toExcel(11, "שמירת לקובץ אקסל"), changePasswords(
			12, "ניהול משתמשים וסיסמאות"), noAction(999, "פעולה לא מוכרת");
	private final String description;
	private final int index;

	AdminActions(int index, String description) {
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