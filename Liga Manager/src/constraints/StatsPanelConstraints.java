package constraints;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class StatsPanelConstraints {

	public final GridBagConstraints koteretConstraints;

	public final GridBagConstraints tableConstraints;

	public StatsPanelConstraints() {

		koteretConstraints = new GridBagConstraints();
		koteretConstraints.gridx = 0;
		koteretConstraints.gridy = 0;
		koteretConstraints.fill = GridBagConstraints.HORIZONTAL;
		koteretConstraints.anchor = GridBagConstraints.CENTER;
		koteretConstraints.insets = new Insets(0, 10, 0, 10);

		tableConstraints = new GridBagConstraints();
		tableConstraints.gridx = 0;
		tableConstraints.gridy = 1;
		tableConstraints.fill = GridBagConstraints.CENTER;
		tableConstraints.anchor = GridBagConstraints.CENTER;

	}

	public GridBagConstraints getKoteretConstraints() {
		return koteretConstraints;
	}

	public GridBagConstraints getTableConstraints() {
		return tableConstraints;
	}

}
