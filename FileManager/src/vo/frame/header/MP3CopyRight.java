package vo.frame.header;

public enum MP3CopyRight {
	Not_Copyrighted("0"), Copyrighted("1");

	private final String copyright;

	MP3CopyRight(String copyright) {
		this.copyright = copyright;
	}

	public String getCopyright() {
		return copyright;
	}
}
