/**
 * 
 */
package enums;

public enum LigaActions {
	welcome("ברוכים הבאים", 1), login("כניסה למערכת", 2), logoff(
			"יציאה מהמערכת", 3), next("למסך הבא", 4), back("למסך הקודם", 5), menu(
			"תפריט", 6), stats("סטטיסטיקות", 7), kvutzot("ניהול פרטי קבוצות", 8), sachkanim(
			"ניהול פרטי שחקנים", 9), nekudot("הצגת גולים לשחקן", 10), mischakim(
			"ניהול והצגת משחקים", 11), admin("מסך ניהול כללי", 12), liga(
			"בחירת ליגה", 13), noAction("פעולה לא מוכרת", 999);
	private final String description;
	private final int index;

	LigaActions(String description, int index) {
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