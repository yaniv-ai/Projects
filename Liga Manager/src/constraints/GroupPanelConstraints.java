package constraints;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class GroupPanelConstraints {

	public final GridBagConstraints koteretConstraints;
	public final GridBagConstraints groupNameTextConstraints;
	public final GridBagConstraints ezorComboBoxConstraints;
	public final GridBagConstraints baitComboBoxConstraints;
	public final GridBagConstraints ezorChadashConstraints;
	public final GridBagConstraints baitChadashConstraints;
	public final GridBagConstraints hosefKvutzaButtonConstraints;
	public final GridBagConstraints hosefEzorButtonConstraints;
	public final GridBagConstraints hosefBaitButtonConstraints;

	public final GridBagConstraints mechakButtonConstraints;
	public final GridBagConstraints haaverButtonConstraints;
	private final GridBagConstraints simunHakolButtonConstraints;

	public final GridBagConstraints ligaGroupTableConstraints;
	public final GridBagConstraints restGroupTableConstraints;

	public GroupPanelConstraints() {
		koteretConstraints = new GridBagConstraints();
		koteretConstraints.gridx = 0;
		koteretConstraints.gridy = 0;
		koteretConstraints.fill = GridBagConstraints.HORIZONTAL;
		koteretConstraints.anchor = GridBagConstraints.CENTER;
		koteretConstraints.insets = new Insets(0, 10, 0, 10);

		groupNameTextConstraints = new GridBagConstraints();
		groupNameTextConstraints.gridx = 0;
		groupNameTextConstraints.gridy = 1;
		groupNameTextConstraints.fill = GridBagConstraints.HORIZONTAL;
		groupNameTextConstraints.anchor = GridBagConstraints.CENTER;
		groupNameTextConstraints.insets = new Insets(0, 10, 0, 10);

		ezorComboBoxConstraints = new GridBagConstraints();
		ezorComboBoxConstraints.gridx = 0;
		ezorComboBoxConstraints.gridy = 2;
		ezorComboBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
		ezorComboBoxConstraints.anchor = GridBagConstraints.CENTER;
		ezorComboBoxConstraints.insets = new Insets(0, 10, 0, 10);

		baitComboBoxConstraints = new GridBagConstraints();
		baitComboBoxConstraints.gridx = 0;
		baitComboBoxConstraints.gridy = 3;
		baitComboBoxConstraints.fill = GridBagConstraints.HORIZONTAL;
		baitComboBoxConstraints.anchor = GridBagConstraints.CENTER;
		baitComboBoxConstraints.insets = new Insets(0, 10, 0, 10);

		hosefKvutzaButtonConstraints = new GridBagConstraints();
		hosefKvutzaButtonConstraints.gridx = 0;
		hosefKvutzaButtonConstraints.gridy = 4;
		hosefKvutzaButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		hosefKvutzaButtonConstraints.anchor = GridBagConstraints.CENTER;
		hosefKvutzaButtonConstraints.insets = new Insets(0, 10, 0, 10);

		ezorChadashConstraints = new GridBagConstraints();
		ezorChadashConstraints.gridx = 0;
		ezorChadashConstraints.gridy = 5;
		ezorChadashConstraints.fill = GridBagConstraints.HORIZONTAL;
		ezorChadashConstraints.anchor = GridBagConstraints.CENTER;
		ezorChadashConstraints.insets = new Insets(0, 10, 0, 10);

		hosefEzorButtonConstraints = new GridBagConstraints();
		hosefEzorButtonConstraints.gridx = 0;
		hosefEzorButtonConstraints.gridy = 6;
		hosefEzorButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		hosefEzorButtonConstraints.anchor = GridBagConstraints.CENTER;
		hosefEzorButtonConstraints.insets = new Insets(0, 10, 0, 10);

		baitChadashConstraints = new GridBagConstraints();
		baitChadashConstraints.gridx = 0;
		baitChadashConstraints.gridy = 5;
		baitChadashConstraints.fill = GridBagConstraints.HORIZONTAL;
		baitChadashConstraints.anchor = GridBagConstraints.CENTER;
		baitChadashConstraints.insets = new Insets(0, 10, 0, 10);

		hosefBaitButtonConstraints = new GridBagConstraints();
		hosefBaitButtonConstraints.gridx = 0;
		hosefBaitButtonConstraints.gridy = 6;
		hosefBaitButtonConstraints.fill = GridBagConstraints.HORIZONTAL;
		hosefBaitButtonConstraints.anchor = GridBagConstraints.CENTER;
		hosefBaitButtonConstraints.insets = new Insets(0, 10, 0, 10);

		haaverButtonConstraints = new GridBagConstraints();
		haaverButtonConstraints.gridx = 0;
		haaverButtonConstraints.gridy = 0;
		haaverButtonConstraints.fill = GridBagConstraints.NONE;
		haaverButtonConstraints.anchor = GridBagConstraints.WEST;

		mechakButtonConstraints = new GridBagConstraints();
		mechakButtonConstraints.gridx = 1;
		mechakButtonConstraints.gridy = 0;
		mechakButtonConstraints.fill = GridBagConstraints.NONE;
		mechakButtonConstraints.anchor = GridBagConstraints.EAST;

		simunHakolButtonConstraints = new GridBagConstraints();
		simunHakolButtonConstraints.gridx = 2;
		simunHakolButtonConstraints.gridy = 0;
		simunHakolButtonConstraints.fill = GridBagConstraints.NONE;
		simunHakolButtonConstraints.anchor = GridBagConstraints.CENTER;

		ligaGroupTableConstraints = new GridBagConstraints();
		ligaGroupTableConstraints.gridx = 0;
		ligaGroupTableConstraints.gridy = 1;
		ligaGroupTableConstraints.gridwidth = 3;
		// groupTableConstraints.gridheight = 6;
		ligaGroupTableConstraints.fill = GridBagConstraints.NONE;
		ligaGroupTableConstraints.anchor = GridBagConstraints.CENTER;

		restGroupTableConstraints = new GridBagConstraints();
		restGroupTableConstraints.gridx = 0;
		restGroupTableConstraints.gridy = 2;
		restGroupTableConstraints.gridwidth = 3;
		// restGroupTableConstraints.gridheight = 6;
		restGroupTableConstraints.fill = GridBagConstraints.NONE;
		restGroupTableConstraints.anchor = GridBagConstraints.CENTER;
	}

	public GridBagConstraints getKoteretConstraints() {
		return koteretConstraints;
	}

	public GridBagConstraints getGroupNameTextConstraints() {
		return groupNameTextConstraints;
	}

	public GridBagConstraints getEzorComboBoxConstraints() {
		return ezorComboBoxConstraints;
	}

	public GridBagConstraints getBaitComboBoxConstraints() {
		return baitComboBoxConstraints;
	}

	public GridBagConstraints getEzorChadashConstraints() {
		return ezorChadashConstraints;
	}

	public GridBagConstraints getBaitChadashConstraints() {
		return baitChadashConstraints;
	}

	public GridBagConstraints getHosefKvutzaButtonConstraints() {
		return hosefKvutzaButtonConstraints;
	}

	public GridBagConstraints getHosefEzorButtonConstraints() {
		return hosefEzorButtonConstraints;
	}

	public GridBagConstraints getHosefBaitButtonConstraints() {
		return hosefBaitButtonConstraints;
	}

	public GridBagConstraints getMechakButtonConstraints() {
		return mechakButtonConstraints;
	}

	public GridBagConstraints getHaaverButtonConstraints() {
		return haaverButtonConstraints;
	}

	public GridBagConstraints getSimunHakolButtonConstraints() {
		return simunHakolButtonConstraints;
	}

	public GridBagConstraints getLigaGroupTableConstraints() {
		return ligaGroupTableConstraints;
	}

	public GridBagConstraints getRestGroupTableConstraints() {
		return restGroupTableConstraints;
	}

}
