package vo.frame.header;

public enum MP3Padding {
	Not_Padded("0", 0), Padded("1", 1);

	private final String padding;
	private final int paddingAsInt;

	MP3Padding(String padding, int paddingAsInt) {
		this.padding = padding;
		this.paddingAsInt = paddingAsInt;
	}

	public String getPadding() {
		return padding;
	}

	public int getPaddingAsInt() {
		return paddingAsInt;
	}
}
