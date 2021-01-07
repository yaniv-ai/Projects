package constraints;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class BchiratLigaPanelConstraints {

	public final GridBagConstraints koteretConstraints;
	public final GridBagConstraints mechakButtonConstraints;
	public final GridBagConstraints ligotTableConstraints;

	public BchiratLigaPanelConstraints() {
		koteretConstraints = new GridBagConstraints();
		koteretConstraints.gridx = 0;
		koteretConstraints.gridy = 0;
		koteretConstraints.fill = GridBagConstraints.HORIZONTAL;
		koteretConstraints.anchor = GridBagConstraints.CENTER;
		koteretConstraints.insets = new Insets(0, 10, 0, 10);

		mechakButtonConstraints = new GridBagConstraints();
		mechakButtonConstraints.gridx = 0;
		mechakButtonConstraints.gridy = 1;
		mechakButtonConstraints.fill = GridBagConstraints.NONE;
		mechakButtonConstraints.anchor = GridBagConstraints.LINE_START;

		ligotTableConstraints = new GridBagConstraints();
		ligotTableConstraints.gridx = 0;
		ligotTableConstraints.gridy = 2;
		ligotTableConstraints.weightx = 0.5;
		ligotTableConstraints.weighty = 0.5;
		ligotTableConstraints.fill = GridBagConstraints.NONE;
		ligotTableConstraints.anchor = GridBagConstraints.CENTER;
	}

	public GridBagConstraints getKoteretConstraints() {
		return koteretConstraints;
	}

	public GridBagConstraints getMechakButtonConstraints() {
		return mechakButtonConstraints;
	}

	public GridBagConstraints getLigotTableConstraints() {
		return ligotTableConstraints;
	}

}
