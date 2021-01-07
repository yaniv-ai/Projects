package vo.frame.header;

public enum MP3Channel {
	Stereo("00"), JointS_Stereo("01"), Dual("10"), Mono("11");

	private final String channel;

	MP3Channel(String channel) {
		this.channel = channel;
	}

	public String getChannel() {
		return channel;
	}
}
