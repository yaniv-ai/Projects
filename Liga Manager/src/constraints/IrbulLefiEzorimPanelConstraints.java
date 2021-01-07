package constraints;

import java.awt.GridBagConstraints;
import java.awt.Insets;

public class IrbulLefiEzorimPanelConstraints {

	public final GridBagConstraints ezorTextConstraints;
	public final GridBagConstraints baitTextConstraints;
	public final GridBagConstraints machzorTextConstraints;
	public final GridBagConstraints misparMifgashimConstraints;
	private final GridBagConstraints cholelfButtonConstraints;
	public final GridBagConstraints groupTableConstraints;

	public IrbulLefiEzorimPanelConstraints() {
		ezorTextConstraints = new GridBagConstraints();
		ezorTextConstraints.gridx = 0;
		ezorTextConstraints.gridy = 0;
		ezorTextConstraints.fill = GridBagConstraints.HORIZONTAL;
		ezorTextConstraints.anchor = GridBagConstraints.CENTER;
		ezorTextConstraints.insets = new Insets(0, 10, 0, 10);

		baitTextConstraints = new GridBagConstraints();
		baitTextConstraints.gridx = 0;
		baitTextConstraints.gridy = 1;
		baitTextConstraints.fill = GridBagConstraints.HORIZONTAL;
		baitTextConstraints.anchor = GridBagConstraints.CENTER;
		baitTextConstraints.insets = new Insets(0, 10, 0, 10);

		machzorTextConstraints = new GridBagConstraints();
		machzorTextConstraints.gridx = 0;
		machzorTextConstraints.gridy = 2;
		machzorTextConstraints.fill = GridBagConstraints.NONE;
		machzorTextConstraints.anchor = GridBagConstraints.CENTER;
		machzorTextConstraints.insets = new Insets(0, 10, 0, 10);

		misparMifgashimConstraints = new GridBagConstraints();
		misparMifgashimConstraints.gridx = 0;
		misparMifgashimConstraints.gridy = 3;
		misparMifgashimConstraints.fill = GridBagConstraints.NONE;
		misparMifgashimConstraints.anchor = GridBagConstraints.CENTER;
		misparMifgashimConstraints.insets = new Insets(0, 10, 0, 10);

		groupTableConstraints = new GridBagConstraints();
		groupTableConstraints.gridx = 1;
		groupTableConstraints.gridy = 0;
		groupTableConstraints.gridheight = 5;
		// playerTableConstraints.weightx = 0.5;
		// playerTableConstraints.weighty = 0.5;
		groupTableConstraints.fill = GridBagConstraints.NONE;
		groupTableConstraints.anchor = GridBagConstraints.CENTER;

		cholelfButtonConstraints = new GridBagConstraints();
		cholelfButtonConstraints.gridx = 0;
		cholelfButtonConstraints.gridy = 4;
		cholelfButtonConstraints.fill = GridBagConstraints.NONE;
		cholelfButtonConstraints.anchor = GridBagConstraints.CENTER;
		cholelfButtonConstraints.insets = new Insets(0, 10, 0, 10);

	}

	public GridBagConstraints getEzorTextConstraints() {
		return ezorTextConstraints;
	}

	public GridBagConstraints getBaitTextConstraints() {
		return baitTextConstraints;
	}

	public GridBagConstraints getMachzorTextConstraints() {
		return machzorTextConstraints;
	}

	public GridBagConstraints getMisparMifgashimConstraints() {
		return misparMifgashimConstraints;
	}

	public GridBagConstraints getCholelfButtonConstraints() {
		return cholelfButtonConstraints;
	}

	public GridBagConstraints getGroupTableConstraints() {
		return groupTableConstraints;
	}

}
