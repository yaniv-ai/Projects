package vo.frame.header;

public enum MP3ModeExtension {
	OffOff("00"), OffOn("01"), OnOff("10"), OnOn("11");

	private final String modeExtension;

	MP3ModeExtension(String modeExtension) {
		this.modeExtension = modeExtension;
	}

	public String getModeExtension() {
		return modeExtension;
	}
}
