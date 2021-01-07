package constraints;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class IrbulLefiBchiraPanelConstraints {

	private final GridBagConstraints kamutKvutzotBeBaitConstraints;
	private final GridBagConstraints machzorTextConstraints;
	private final GridBagConstraints cholelBatimButtonConstraints;

	private final GridBagConstraints misparMifgashimConstraints;
	private final GridBagConstraints cholelMischakimButtonConstraints;

	private final GridBagConstraints mechakMachzorButtonConstraints;
	private final GridBagConstraints simunHakolButtonConstraints;

	private final GridBagConstraints batimTableConstraints;
	private final GridBagConstraints groupTableConstraints;

	private final GridBagConstraints buttonsSideConstraints;
	private final GridBagConstraints tablesSideConstraints;

	public IrbulLefiBchiraPanelConstraints() {
		kamutKvutzotBeBaitConstraints = new GridBagConstraints();
		kamutKvutzotBeBaitConstraints.gridx = 0;
		kamutKvutzotBeBaitConstraints.gridy = 0;
		kamutKvutzotBeBaitConstraints.fill = GridBagConstraints.HORIZONTAL;
		kamutKvutzotBeBaitConstraints.anchor = GridBagConstraints.CENTER;
		kamutKvutzotBeBaitConstraints.insets = new Insets(0, 10, 0, 10);

		machzorTextConstraints = new GridBagConstraints();
		machzorTextConstraints.gridx = 0;
		machzorTextConstraints.gridy = 1;
		machzorTextConstraints.fill = GridBagConstraints.NONE;
		machzorTextConstraints.anchor = GridBagConstraints.CENTER;
		machzorTextConstraints.insets = new Insets(0, 10, 0, 10);

		cholelBatimButtonConstraints = new GridBagConstraints();
		cholelBatimButtonConstraints.gridx = 0;
		cholelBatimButtonConstraints.gridy = 2;
		cholelBatimButtonConstraints.fill = GridBagConstraints.NONE;
		cholelBatimButtonConstraints.anchor = GridBagConstraints.CENTER;
		cholelBatimButtonConstraints.insets = new Insets(0, 10, 0, 10);

		misparMifgashimConstraints = new GridBagConstraints();
		misparMifgashimConstraints.gridx = 0;
		misparMifgashimConstraints.gridy = 3;
		misparMifgashimConstraints.fill = GridBagConstraints.NONE;
		misparMifgashimConstraints.anchor = GridBagConstraints.CENTER;
		misparMifgashimConstraints.insets = new Insets(0, 10, 0, 10);

		cholelMischakimButtonConstraints = new GridBagConstraints();
		cholelMischakimButtonConstraints.gridx = 0;
		cholelMischakimButtonConstraints.gridy = 4;
		cholelMischakimButtonConstraints.fill = GridBagConstraints.NONE;
		cholelMischakimButtonConstraints.anchor = GridBagConstraints.CENTER;
		cholelMischakimButtonConstraints.insets = new Insets(0, 10, 0, 10);

		mechakMachzorButtonConstraints = new GridBagConstraints();
		mechakMachzorButtonConstraints.gridx = 0;
		mechakMachzorButtonConstraints.gridy = 5;
		mechakMachzorButtonConstraints.fill = GridBagConstraints.NONE;
		mechakMachzorButtonConstraints.anchor = GridBagConstraints.CENTER;
		mechakMachzorButtonConstraints.insets = new Insets(0, 10, 0, 10);

		simunHakolButtonConstraints = new GridBagConstraints();
		simunHakolButtonConstraints.gridx = 0;
		simunHakolButtonConstraints.gridy = 6;
		simunHakolButtonConstraints.fill = GridBagConstraints.NONE;
		simunHakolButtonConstraints.anchor = GridBagConstraints.CENTER;
		simunHakolButtonConstraints.insets = new Insets(0, 10, 0, 10);

		batimTableConstraints = new GridBagConstraints();
		batimTableConstraints.gridx = 0;
		batimTableConstraints.gridy = 0;
		batimTableConstraints.weightx = 0.5;
		batimTableConstraints.weighty = 0.5;
		batimTableConstraints.fill = GridBagConstraints.NONE;
		batimTableConstraints.anchor = GridBagConstraints.CENTER;

		groupTableConstraints = new GridBagConstraints();
		groupTableConstraints.gridx = 0;
		groupTableConstraints.gridy = 1;
		groupTableConstraints.weightx = 0.5;
		groupTableConstraints.weighty = 0.5;
		groupTableConstraints.fill = GridBagConstraints.NONE;
		groupTableConstraints.anchor = GridBagConstraints.CENTER;

		buttonsSideConstraints = new GridBagConstraints();
		buttonsSideConstraints.gridx = 0;
		buttonsSideConstraints.gridy = 0;
		buttonsSideConstraints.fill = GridBagConstraints.NONE;
		buttonsSideConstraints.anchor = GridBagConstraints.CENTER;

		tablesSideConstraints = new GridBagConstraints();
		tablesSideConstraints.gridx = 1;
		tablesSideConstraints.gridy = 0;
		// tablesSideConstraints.gridheight = 6;
		tablesSideConstraints.fill = GridBagConstraints.NONE;
		tablesSideConstraints.anchor = GridBagConstraints.CENTER;

	}

	public GridBagConstraints getKamutKvutzotBeBaitConstraints() {
		return kamutKvutzotBeBaitConstraints;
	}

	public GridBagConstraints getMachzorTextConstraints() {
		return machzorTextConstraints;
	}

	public GridBagConstraints getCholelBatimButtonConstraints() {
		return cholelBatimButtonConstraints;
	}

	public GridBagConstraints getMisparMifgashimConstraints() {
		return misparMifgashimConstraints;
	}

	public GridBagConstraints getCholelMischakimButtonConstraints() {
		return cholelMischakimButtonConstraints;
	}

	public GridBagConstraints getMechakMachzorButtonConstraints() {
		return mechakMachzorButtonConstraints;
	}

	public GridBagConstraints getBatimTableConstraints() {
		return batimTableConstraints;
	}

	public GridBagConstraints getGroupTableConstraints() {
		return groupTableConstraints;
	}

	public GridBagConstraints getButtonsSideConstraints() {
		return buttonsSideConstraints;
	}

	public GridBagConstraints getTablesSideConstraints() {
		return tablesSideConstraints;
	}

	public GridBagConstraints getSimunHakolButtonConstraints() {
		return simunHakolButtonConstraints;
	}

}
