package vo.frame.header;

public enum MP3Original {
	Copy("0"), Original("1");

	private final String originalMedia;

	MP3Original(String originalMedia) {
		this.originalMedia = originalMedia;
	}

	public String getOriginalMedia() {
		return originalMedia;
	}
}
