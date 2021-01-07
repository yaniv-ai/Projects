package vo;

public class GameRandomizerVO {
	private int groupA;
	private int groupB;

	public GameRandomizerVO(int groupA, int groupB) {
		super();
		this.groupA = groupA;
		this.groupB = groupB;
	}

	public void setGroupA(int groupA) {
		this.groupA = groupA;
	}

	public void setGroupB(int groupB) {
		this.groupB = groupB;
	}

	public int getGroupA() {
		return groupA;
	}

	public int getGroupB() {
		return groupB;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null || !obj.getClass().equals(GameRandomizerVO.class)) {
			return false;
		}

		GameRandomizerVO p = (GameRandomizerVO) obj;

		if (groupA == p.getGroupA() && groupB == p.getGroupB()) {
			return true;
		}
		if (groupB == p.getGroupA() && groupA == p.getGroupB()) {
			return true;
		}
		return false;
	}
}
