package dimensions;

import java.awt.Dimension;

import main.MasachRashi;

public class BchiratLigaPanelDimensions {

	private final Dimension koteretDimension;
	public Dimension getKoteretDimension() {
		return koteretDimension;
	}

	private final Dimension mechakButtonDimension;
	private final Dimension ligotTableDimension;

	public BchiratLigaPanelDimensions(Dimension screenDimension) {

		koteretDimension = new Dimension(MasachRashi.TEXT_FIELD_DIMENSION);
		mechakButtonDimension = new Dimension(MasachRashi.BUTTON_DIMENSION);

		ligotTableDimension = new Dimension(screenDimension.width
				- MasachRashi.WIDTH_INSET * 2, screenDimension.height
				- mechakButtonDimension.height - koteretDimension.height
				- MasachRashi.HEIGHT_INSET * 2);
	}

	public Dimension getMechakButtonDimension() {
		return mechakButtonDimension;
	}

	public Dimension getLigotTableDimension() {
		return ligotTableDimension;
	}

}
