package vo.frame.header;

public enum MP3PrivateBit {
	Zero("0"), One("1");

	private final String privateBit;

	MP3PrivateBit(String privateBit) {
		this.privateBit = privateBit;
	}

	public String getPrivateBit() {
		return privateBit;
	}
}
