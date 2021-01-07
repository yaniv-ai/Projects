package vo.frame.header;

public enum MP3BitrateIndex {
	// Free("0000", 0),
	Kbps32("0001", 32), Kpbs40("0010", 40), Kpbs48("0011", 48), Kpbs56("0100",
			56), Kpbs64("0101", 64), Kpbs80("0110", 80), Kpbs96("0111", 96), Kpbs112(
			"1000", 112), Kpbs128("1001", 128), Kpbs160("1010", 160), Kpbs192(
			"1011", 192), Kpbs224("1100", 224), Kpbs256("1101", 256), Kpbs320(
			"1110", 320);
	// , BAD("1111", 0);

	private final String bitRate;
	private final int bitRateAsInt;

	MP3BitrateIndex(String bitRate, int bitRateAsInt) {
		this.bitRate = bitRate;
		this.bitRateAsInt = bitRateAsInt;
	}

	public String getBitRate() {
		return bitRate;
	}

	public int getBitRateAsInt() {
		return bitRateAsInt;
	}

}
