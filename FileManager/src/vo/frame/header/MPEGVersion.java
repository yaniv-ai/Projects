package vo.frame.header;

public enum MPEGVersion {

	V2_5("00"), Reserved("01"), V2("10"), V1("11");

	private final String version;

	MPEGVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return version;
	}
}
