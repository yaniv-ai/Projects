package dimensions;

import java.awt.Dimension;

import main.MasachRashi;

public class RandomizeGamesPanelDimensions {

	private final Dimension leagueRadioDimension;
	private final Dimension alifutRadioDimension;
	private final Dimension regionComboBoxDimension;
	private final Dimension baitComboBoxDimension;

	private final Dimension machzorDimension;
	private final Dimension kamutDimension;
	private final Dimension ishurButtonDimension;
	private final Dimension siyumButtonDimension;

	public RandomizeGamesPanelDimensions() {
		leagueRadioDimension = new Dimension(MasachRashi.BUTTON_DIMENSION);
		alifutRadioDimension = new Dimension(MasachRashi.BUTTON_DIMENSION);
		regionComboBoxDimension = new Dimension(
				MasachRashi.TEXT_FIELD_DIMENSION);
		baitComboBoxDimension = new Dimension(MasachRashi.TEXT_FIELD_DIMENSION);
		machzorDimension = new Dimension(MasachRashi.TEXT_FIELD_DIMENSION);
		kamutDimension = new Dimension(MasachRashi.TEXT_FIELD_DIMENSION);
		ishurButtonDimension = new Dimension(MasachRashi.BUTTON_DIMENSION);
		siyumButtonDimension = new Dimension(MasachRashi.BUTTON_DIMENSION);

	}

	public Dimension getLeagueRadioDimension() {
		return leagueRadioDimension;
	}

	public Dimension getAlifutRadioDimension() {
		return alifutRadioDimension;
	}

	public Dimension getRegionComboBoxDimension() {
		return regionComboBoxDimension;
	}

	public Dimension getBaitComboBoxDimension() {
		return baitComboBoxDimension;
	}

	public Dimension getMachzorDimension() {
		return machzorDimension;
	}

	public Dimension getKamutDimension() {
		return kamutDimension;
	}

	public Dimension getIshurButtonDimension() {
		return ishurButtonDimension;
	}

	public Dimension getSiyumButtonDimension() {
		return siyumButtonDimension;
	}

}
