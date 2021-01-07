package dimensions;

import java.awt.Dimension;

import main.MasachRashi;

public class IrbulLefiEzorimPanelDimensions {

	private final Dimension ezorTextDimension;
	private final Dimension baitTextDimension;
	private final Dimension machzorTextDimension;
	private final Dimension misparMifgashimButtonDimension;
	private final Dimension cholelfButtonConstraints;
	private final Dimension groupTableDimension;

	public IrbulLefiEzorimPanelDimensions(Dimension screenDimension) {
		int rightPanelWidth = MasachRashi.TEXT_FIELD_DIMENSION.width;
		int tablePanelWidth = screenDimension.width - rightPanelWidth;

		int groupTableHeight = screenDimension.height
				- MasachRashi.HEIGHT_INSET;

		ezorTextDimension = new Dimension(rightPanelWidth,
				MasachRashi.COMBO_BOX_DIMENSION.height);
		baitTextDimension = new Dimension(rightPanelWidth,
				MasachRashi.COMBO_BOX_DIMENSION.height);
		machzorTextDimension = new Dimension(rightPanelWidth / 2,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		misparMifgashimButtonDimension = new Dimension(rightPanelWidth / 2,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		cholelfButtonConstraints = new Dimension(rightPanelWidth,
				MasachRashi.BUTTON_DIMENSION.height);

		groupTableDimension = new Dimension(tablePanelWidth
				- MasachRashi.WIDTH_INSET * 2, groupTableHeight);

	}

	public Dimension getEzorTextDimension() {
		return ezorTextDimension;
	}

	public Dimension getBaitTextDimension() {
		return baitTextDimension;
	}

	public Dimension getMachzorTextDimension() {
		return machzorTextDimension;
	}

	public Dimension getMisparMifgashimButtonDimension() {
		return misparMifgashimButtonDimension;
	}

	public Dimension getCholelfButtonConstraints() {
		return cholelfButtonConstraints;
	}

	public Dimension getGroupTableDimension() {
		return groupTableDimension;
	}

}
