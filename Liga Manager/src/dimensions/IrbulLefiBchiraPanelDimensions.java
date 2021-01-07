package dimensions;

import java.awt.Dimension;

import main.MasachRashi;

public class IrbulLefiBchiraPanelDimensions {
	private final Dimension kamutKvutzotBeBaitDimension;
	private final Dimension machzorTextDimension;
	private final Dimension cholelBatimButtonDimension;

	private final Dimension misparMifgashimDimension;
	private final Dimension cholelMischakimButtonDimension;

	private final Dimension mechakMachzorButtonDimension;
	private final Dimension simunHakolButtonDimension;

	private final Dimension batimTableDimension;
	private final Dimension groupTableDimension;

	private final Dimension buttonsSideDimension;
	private final Dimension tablesSideDimension;

	public IrbulLefiBchiraPanelDimensions(Dimension screenDimension) {
		int rightPanelWidth = MasachRashi.TEXT_FIELD_DIMENSION.width * 2;
		int tablePanelWidth = screenDimension.width - rightPanelWidth;

		int tableHeight = (screenDimension.height - MasachRashi.HEIGHT_INSET) / 2;
		batimTableDimension = new Dimension(tablePanelWidth, tableHeight);
		groupTableDimension = new Dimension(tablePanelWidth, tableHeight);
		tablesSideDimension = new Dimension(tablePanelWidth,
				screenDimension.height - MasachRashi.HEIGHT_INSET);
		buttonsSideDimension = new Dimension(rightPanelWidth,
				screenDimension.height - MasachRashi.HEIGHT_INSET);

		kamutKvutzotBeBaitDimension = new Dimension(rightPanelWidth
				- MasachRashi.WIDTH_INSET,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		machzorTextDimension = new Dimension(rightPanelWidth
				- MasachRashi.WIDTH_INSET,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		cholelBatimButtonDimension = new Dimension(rightPanelWidth
				- MasachRashi.WIDTH_INSET,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		misparMifgashimDimension = new Dimension(rightPanelWidth
				- MasachRashi.WIDTH_INSET,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		cholelMischakimButtonDimension = new Dimension(rightPanelWidth
				- MasachRashi.WIDTH_INSET,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		mechakMachzorButtonDimension = new Dimension(rightPanelWidth
				- MasachRashi.WIDTH_INSET,
				MasachRashi.TEXT_FIELD_DIMENSION.height);
		simunHakolButtonDimension = new Dimension(rightPanelWidth
				- MasachRashi.WIDTH_INSET,
				MasachRashi.TEXT_FIELD_DIMENSION.height);

	}

	public Dimension getKamutKvutzotBeBaitDimension() {
		return kamutKvutzotBeBaitDimension;
	}

	public Dimension getMachzorTextDimension() {
		return machzorTextDimension;
	}

	public Dimension getCholelBatimButtonDimension() {
		return cholelBatimButtonDimension;
	}

	public Dimension getMisparMifgashimDimension() {
		return misparMifgashimDimension;
	}

	public Dimension getCholelMischakimButtonDimension() {
		return cholelMischakimButtonDimension;
	}

	public Dimension getMechakMachzorButtonDimension() {
		return mechakMachzorButtonDimension;
	}

	public Dimension getBatimTableDimension() {
		return batimTableDimension;
	}

	public Dimension getGroupTableDimension() {
		return groupTableDimension;
	}

	public Dimension getButtonsSideDimension() {
		return buttonsSideDimension;
	}

	public Dimension getTablesSideDimension() {
		return tablesSideDimension;
	}

	public Dimension getSimunHakolButtonDimension() {
		return simunHakolButtonDimension;
	}

}
