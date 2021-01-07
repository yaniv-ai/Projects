package vo.frame.header;

public enum MP3Synchronizer {

	Synchronizer("11111111111");

	private final String sinchronizer;

	MP3Synchronizer(String sinchronizer) {
		this.sinchronizer = sinchronizer;
	}

	public String getSinchronizer() {
		return sinchronizer;
	}
}
