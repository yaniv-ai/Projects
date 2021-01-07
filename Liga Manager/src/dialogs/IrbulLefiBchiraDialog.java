package dialogs;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;

import javax.swing.JDialog;

import ui.IrbulLefiBchiraPanel;

public class IrbulLefiBchiraDialog {

	private JDialog d;

	public IrbulLefiBchiraDialog(Component component,
			Dimension screenDimension, Integer actionManagerId) {
		d = new JDialog();
		d.setModal(true);
		d.setLayout(new GridBagLayout());
		Dimension dialogDimension = new Dimension(
				(int) (screenDimension.width * 0.9),
				(int) (screenDimension.height * 1.0));
		IrbulLefiBchiraPanel irbulLefiBchiraPanel = new IrbulLefiBchiraPanel(
				dialogDimension, actionManagerId);
		irbulLefiBchiraPanel.showPanel();
		d.add(irbulLefiBchiraPanel);
		d.setTitle("ערבול משחקים לפי בחירה");
		d.pack();
		d.setResizable(false);

		// GraphicsEnvironment env = GraphicsEnvironment
		// .getLocalGraphicsEnvironment();
		// Rectangle rect = env.getMaximumWindowBounds();
		// TODO
		// int x = rect.x;
		// int y = rect.y;
		d.setLocation(20, 20);
		d.setVisible(true);
	}
}
