/**
 * 
 */
package actions;

public enum AdminActions {
	resetGames(1, "����� ���� ������"), loadGroups(6, "����� ������ �����"), irbulLefiEzorim(
			8, "����� ������ ��� ������"), irbulLefiBchira(9,
			"����� ������ ��� �����"), resetGoals(2, "����� ���� �����"), loadPlayers(
			7, "����� ������ �����"), resetGroups(3, "����� ���� ������"), resetPlayers(
			4, "����� ���� ������"), resetAllTables(5, "����� �� �������"), resetLogTables(
			10, "����� ������ ������"), toExcel(11, "����� ����� ����"), changePasswords(
			12, "����� ������� ��������"), noAction(999, "����� �� �����");
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