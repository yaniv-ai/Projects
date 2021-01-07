package vo.frame.header;

public enum MP3CRCProtection {
	Protected("0"), Not_Protected("1");

	private final String crcProtection;

	MP3CRCProtection(String crcProtection) {
		this.crcProtection = crcProtection;
	}

	public String getCRCProtection() {
		return crcProtection;
	}
}
