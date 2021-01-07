package constraints;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class AdminPanelConstraints {

	public final GridBagConstraints screenConstraints;

	public AdminPanelConstraints() {
		screenConstraints = new GridBagConstraints();
		screenConstraints.gridx = 0;
		screenConstraints.gridy = 0;
		screenConstraints.fill = GridBagConstraints.BOTH;
		screenConstraints.anchor = GridBagConstraints.CENTER;
		screenConstraints.insets = new Insets(0, 10, 0, 10);

	}

	public GridBagConstraints getScreenConstraints() {
		return screenConstraints;
	}

}
