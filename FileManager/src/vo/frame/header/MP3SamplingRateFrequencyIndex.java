package vo.frame.header;

public enum MP3SamplingRateFrequencyIndex {
	Hz44100("00", 44100), Hz48000("01", 48000), Hz32000("10", 32000);
	// , Reserved("11", 0);

	private final String samplingRateFrequencyIndex;
	private final int samplingRateFrequencyIndexAsInt;

	MP3SamplingRateFrequencyIndex(String samplingRateFrequencyIndex,
			int samplingRateFrequencyIndexAsInt) {
		this.samplingRateFrequencyIndex = samplingRateFrequencyIndex;
		this.samplingRateFrequencyIndexAsInt = samplingRateFrequencyIndexAsInt;
	}

	public String getSamplingRateFrequencyIndex() {
		return samplingRateFrequencyIndex;
	}

	public int getSamplingRateFrequencyIndexAsInt() {
		return samplingRateFrequencyIndexAsInt;
	}

}
