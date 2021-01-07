/**
 * 
 */
package enums;

public enum LigaActions {
	welcome("������ �����", 1), login("����� ������", 2), logoff(
			"����� �������", 3), next("���� ���", 4), back("���� �����", 5), menu(
			"�����", 6), stats("����������", 7), kvutzot("����� ���� ������", 8), sachkanim(
			"����� ���� ������", 9), nekudot("���� ����� �����", 10), mischakim(
			"����� ����� ������", 11), admin("��� ����� ����", 12), liga(
			"����� ����", 13), noAction("����� �� �����", 999);
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