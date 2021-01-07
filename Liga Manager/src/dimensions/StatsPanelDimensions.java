package dimensions;

import java.awt.Dimension;

import main.MasachRashi;

public class StatsPanelDimensions {

	private final Dimension koteretDimension;

	private final Dimension tableDimension;

	private final Dimension screenDimension;

	public StatsPanelDimensions(Dimension screenDimension) {
		this.screenDimension = screenDimension;
		int tableHeight = screenDimension.height
				- MasachRashi.BUTTON_DIMENSION.height
				- MasachRashi.HEIGHT_INSET;
		int buttonsWidth = screenDimension.width / 5;

		koteretDimension = new Dimension(buttonsWidth,
				MasachRashi.BUTTON_DIMENSION.height);

		tableDimension = new Dimension(screenDimension.width, tableHeight);

	}

	public Dimension getKoteretDimension() {
		return koteretDimension;
	}

	public Dimension getTableDimension() {
		return tableDimension;
	}

	public Dimension getScreenDimension() {
		return screenDimension;
	}

}
