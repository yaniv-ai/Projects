package constraints;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class RandomizeGamePanelConstraints {

	public final GridBagConstraints leagueRadioConstraints;
	public final GridBagConstraints alifutRadioConstraints;
	public final GridBagConstraints regionComboBoxConstraints;
	public final GridBagConstraints baitComboBoxConstraints;

	public final GridBagConstraints machzorConstraints;
	public final GridBagConstraints kamutConstraints;

	public final GridBagConstraints ishurButtonConstraints;
	public final GridBagConstraints siyumButtonConstraints;

	public RandomizeGamePanelConstraints() {

		leagueRadioConstraints = new GridBagConstraints();
		leagueRadioConstraints.gridx = 0;
		leagueRadioConstraints.gridy = 0;
		leagueRadioConstraints.fill = GridBagConstraints.HORIZONTAL;
		leagueRadioConstraints.anchor = GridBagConstraints.CENTER;
		leagueRadioConstraints.insets = new Insets(0, 10, 0, 10);

		alifutRadioConstraints = new GridBagConstraints();
		alifutRadioConstraints.gridx = 1;
		alifutRadioConstraints.gridy = 0;
		alifutRadioConstraints.fill = GridBagConstraints.HORIZONTAL;
		alifutRadioConstraints.anchor = GridBagConstraints.CENTER;
		alifutRadioConstraints.insets = new Insets(0, 10, 0, 10);

		regionComboBoxConstraints = new GridBagConstraints();
		regionComboBoxConstraints.gridx = 0;
		regionComboBoxConstraints.gridy = 1;
		regionComboBoxConstraints.gridwidth = 2;
		regionComboBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
		regionComboBoxConstraints.anchor = GridBagConstraints.CENTER;
		regionComboBoxConstraints.insets = new Insets(0, 10, 0, 10);

		baitComboBoxConstraints = new GridBagConstraints();
		baitComboBoxConstraints.gridx = 0;
		baitComboBoxConstraints.gridy = 2;
		baitComboBoxConstraints.gridwidth = 2;
		baitComboBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
		baitComboBoxConstraints.anchor = GridBagConstraints.CENTER;
		baitComboBoxConstraints.insets = new Insets(0, 10, 0, 10);

		machzorConstraints = new GridBagConstraints();
		machzorConstraints.gridx = 0;
		machzorConstraints.gridy = 3;
		machzorConstraints.gridwidth = 2;
		machzorConstraints.fill = GridBagConstraints.HORIZONTAL;
		machzorConstraints.anchor = GridBagConstraints.CENTER;
		machzorConstraints.insets = new Insets(0, 10, 0, 10);

		kamutConstraints = new GridBagConstraints();
		kamutConstraints.gridx = 0;
		kamutConstraints.gridy = 4;
		kamutConstraints.gridwidth = 2;
		kamutConstraints.fill = GridBagConstraints.HORIZONTAL;
		kamutConstraints.anchor = GridBagConstraints.CENTER;
		kamutConstraints.insets = new Insets(0, 10, 0, 10);

		ishurButtonConstraints = new GridBagConstraints();
		ishurButtonConstraints.gridx = 0;
		ishurButtonConstraints.gridy = 5;
		ishurButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		ishurButtonConstraints.anchor = GridBagConstraints.CENTER;
		ishurButtonConstraints.insets = new Insets(0, 10, 0, 10);

		siyumButtonConstraints = new GridBagConstraints();
		siyumButtonConstraints.gridx = 1;
		siyumButtonConstraints.gridy = 5;
		siyumButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		siyumButtonConstraints.anchor = GridBagConstraints.CENTER;
		siyumButtonConstraints.insets = new Insets(0, 10, 0, 10);
	}

	public GridBagConstraints getLeagueRadioConstraints() {
		return leagueRadioConstraints;
	}

	public GridBagConstraints getAlifutRadioConstraints() {
		return alifutRadioConstraints;
	}

	public GridBagConstraints getRegionComboBoxConstraints() {
		return regionComboBoxConstraints;
	}

	public GridBagConstraints getBaitComboBoxConstraints() {
		return baitComboBoxConstraints;
	}

	public GridBagConstraints getMachzorConstraints() {
		return machzorConstraints;
	}

	public GridBagConstraints getKamutConstraints() {
		return kamutConstraints;
	}

	public GridBagConstraints getIshurButtonConstraints() {
		return ishurButtonConstraints;
	}

	public GridBagConstraints getSiyumButtonConstraints() {
		return siyumButtonConstraints;
	}

}
