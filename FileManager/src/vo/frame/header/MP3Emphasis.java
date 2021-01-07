package vo.frame.header;

public enum MP3Emphasis {
	None("00"), E50_15("01"), Reserved("10"), CCIT_j_17("11");

	private final String emphasis;

	MP3Emphasis(String emphasis) {
		this.emphasis = emphasis;
	}

	public String getEmphasis() {
		return emphasis;
	}
}
