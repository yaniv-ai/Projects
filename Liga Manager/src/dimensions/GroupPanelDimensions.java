package dimensions;

import java.awt.Dimension;

import main.MasachRashi;

public class GroupPanelDimensions {

	private final Dimension koteretDimension;
	private final Dimension groupNameTextDimension;
	private final Dimension ezorComboBoxDimension;
	private final Dimension baitComboBoxDimension;
	private final Dimension ezorChadashDimension;
	private final Dimension baitChadashDimension;
	private final Dimension hosefKvutzaButtonDimension;
	private final Dimension hosefEzorButtonDimension;
	private final Dimension hosefBaitButtonDimension;

	private final Dimension mechakButtonDimension;
	private final Dimension haaverButtonDimension;
	private final Dimension simunHakolButtonDimension;

	private final Dimension ligaGroupTableDimension;
	private final Dimension restGroupTableDimension;

	private final Dimension buttonsSideDimension;
	private final Dimension tablesSideDimension;

	public GroupPanelDimensions(Dimension screenDimension) {
		int rightPanelWidth = MasachRashi.TEXT_FIELD_DIMENSION.width * 2
				- MasachRashi.WIDTH_INSET;
		int tablePanelWidth = screenDimension.width - rightPanelWidth;

		int tableHeight = (screenDimension.height - MasachRashi.BUTTON_DIMENSION.height)
				/ 2 - MasachRashi.HEIGHT_INSET;

		tablesSideDimension = new Dimension(tablePanelWidth,
				screenDimension.height - MasachRashi.HEIGHT_INSET);
		buttonsSideDimension = new Dimension(rightPanelWidth,
				screenDimension.height - MasachRashi.HEIGHT_INSET);

		int tablePanelButtonsWidth = tablePanelWidth / 4;

		koteretDimension = new Dimension(rightPanelWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		groupNameTextDimension = new Dimension(rightPanelWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		ezorComboBoxDimension = new Dimension(rightPanelWidth,
				MasachRashi.COMBO_BOX_DIMENSION.height);
		baitComboBoxDimension = new Dimension(rightPanelWidth,
				MasachRashi.COMBO_BOX_DIMENSION.height);
		ezorChadashDimension = new Dimension(rightPanelWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		baitChadashDimension = new Dimension(rightPanelWidth,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		hosefKvutzaButtonDimension = new Dimension(new Dimension(
				MasachRashi.BUTTON_DIMENSION));
		hosefEzorButtonDimension = new Dimension(new Dimension(
				MasachRashi.BUTTON_DIMENSION));
		hosefBaitButtonDimension = new Dimension(new Dimension(
				MasachRashi.BUTTON_DIMENSION));

		mechakButtonDimension = new Dimension(tablePanelButtonsWidth,
				MasachRashi.BUTTON_DIMENSION.height);
		haaverButtonDimension = new Dimension(tablePanelButtonsWidth,
				MasachRashi.BUTTON_DIMENSION.height);
		simunHakolButtonDimension = new Dimension(tablePanelButtonsWidth,
				MasachRashi.BUTTON_DIMENSION.height);

		ligaGroupTableDimension = new Dimension(tablePanelWidth
				- MasachRashi.WIDTH_INSET * 2, tableHeight);
		restGroupTableDimension = new Dimension(tablePanelWidth
				- MasachRashi.WIDTH_INSET * 2, tableHeight);

	}

	public Dimension getKoteretDimension() {
		return koteretDimension;
	}

	public Dimension getGroupNameTextDimension() {
		return groupNameTextDimension;
	}

	public Dimension getEzorComboBoxDimension() {
		return ezorComboBoxDimension;
	}

	public Dimension getBaitComboBoxDimension() {
		return baitComboBoxDimension;
	}

	public Dimension getEzorChadashDimension() {
		return ezorChadashDimension;
	}

	public Dimension getBaitChadashDimension() {
		return baitChadashDimension;
	}

	public Dimension getHosefKvutzaButtonDimension() {
		return hosefKvutzaButtonDimension;
	}

	public Dimension getHosefEzorButtonDimension() {
		return hosefEzorButtonDimension;
	}

	public Dimension getHosefBaitButtonDimension() {
		return hosefBaitButtonDimension;
	}

	public Dimension getMechakButtonDimension() {
		return mechakButtonDimension;
	}

	public Dimension getHaaverButtonDimension() {
		return haaverButtonDimension;
	}

	public Dimension getSimunHakolButtonDimension() {
		return simunHakolButtonDimension;
	}

	public Dimension getLigaGroupTableDimension() {
		return ligaGroupTableDimension;
	}

	public Dimension getRestGroupTableDimension() {
		return restGroupTableDimension;
	}

	public Dimension getButtonsSideDimension() {
		return buttonsSideDimension;
	}

	public Dimension getTablesSideDimension() {
		return tablesSideDimension;
	}

}
