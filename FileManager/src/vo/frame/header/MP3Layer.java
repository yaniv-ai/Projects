package vo.frame.header;

public enum MP3Layer {
	Reserved("00"), Layer_III("01"), Layer_II("10"), Layer_I("11");

	private final String layer;

	MP3Layer(String layer) {
		this.layer = layer;
	}

	public String getLayer() {
		return layer;
	}
}
