package dialogs;

import java.awt.Component;
import java.awt.Dimension;
import javax.swing.JDialog;

import ui.IrbulLefiEzorimPanel;

public class IrbulLefiEzorimDialog {

	private JDialog d;

	public IrbulLefiEzorimDialog(Component component,
			Dimension screenDimension, Integer actionManagerId) {
		d = new JDialog();
		d.setModal(true);
		// d.setLayout(new GridBagLayout());
		IrbulLefiEzorimPanel irbulLefiEzorimPanel = new IrbulLefiEzorimPanel(
				screenDimension, actionManagerId);
		irbulLefiEzorimPanel.showPanel();
		d.add(irbulLefiEzorimPanel);
		d.setTitle("ערבול משחקים לפי איזורים");
		d.pack();
		// d.setResizable(false);

		// GraphicsEnvironment env = GraphicsEnvironment
		// .getLocalGraphicsEnvironment();
		// Rectangle rect = env.getMaximumWindowBounds();
		// TODO
		// int x = rect.x;
		// int y = rect.y;
		// d.setLocation(x + 400, y + 200);
		d.setVisible(true);
	}
}
