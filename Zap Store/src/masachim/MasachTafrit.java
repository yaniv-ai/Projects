package masachim;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import main.MainWindow;
import enums.Options;
import enums.Ranks;

import templates.MyPanel;

public class MasachTafrit extends MyPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton hashvaa = null;
	private JButton mutzar = null;
	private JButton menahel = null;
	private JButton back = null;

	public MasachTafrit(Ranks rank) {
		if (rank == null) {
			throw new NullPointerException("Rank should not be null");
		}

		JPanel panel = new JPanel();
		switch (rank) {
		case admin:
			panel.setLayout(new GridLayout(5, 1));
			hashvaa = new JButton(Options.hashvaatMechirim.getDescription());
			mutzar = new JButton(Options.hosafatMutzar.getDescription());
			menahel = new JButton(Options.hosafatManager.getDescription());
			back = new JButton(Options.knisa.getDescription());

			hashvaa.setActionCommand(Options.hashvaatMechirim.name());
			mutzar.setActionCommand(Options.hosafatMutzar.name());
			menahel.setActionCommand(Options.hosafatManager.name());
			back.setActionCommand(Options.knisa.name());

			hashvaa.setFont(MainWindow.FONT);
			mutzar.setFont(MainWindow.FONT);
			menahel.setFont(MainWindow.FONT);
			back.setFont(MainWindow.FONT);

			panel.add(hashvaa);
			panel.add(mutzar);
			panel.add(menahel);
			panel.add(new JLabel());
			panel.add(back);

			break;
		case manager:
			panel.setLayout(new GridLayout(4, 1));
			hashvaa = new JButton(Options.hashvaatMechirim.getDescription());
			mutzar = new JButton(Options.hosafatMutzar.getDescription());
			back = new JButton(Options.knisa.getDescription());

			hashvaa.setActionCommand(Options.hashvaatMechirim.name());
			mutzar.setActionCommand(Options.hosafatMutzar.name());
			back.setActionCommand(Options.knisa.name());

			hashvaa.setFont(MainWindow.FONT);
			mutzar.setFont(MainWindow.FONT);
			back.setFont(MainWindow.FONT);

			panel.add(hashvaa);
			panel.add(mutzar);
			panel.add(new JLabel());
			panel.add(back);

			break;
		case user:
			panel.setLayout(new GridLayout(3, 1));
			hashvaa = new JButton(Options.hashvaatMechirim.getDescription());
			back = new JButton(Options.knisa.getDescription());

			hashvaa.setActionCommand(Options.hashvaatMechirim.name());
			back.setActionCommand(Options.knisa.name());

			hashvaa.setFont(MainWindow.FONT);
			back.setFont(MainWindow.FONT);

			panel.add(hashvaa);
			panel.add(new JLabel());
			panel.add(back);

			break;
		default:
			break;
		}
		setLayout(new BorderLayout());

		// הוספת הטבלה, כם שתהיה באמצע ולא תתרחב עם המסך יוגדל
		JPanel gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(panel);
		add(gridBagPanel, BorderLayout.CENTER);
		setOpaque(true);
	}

	@Override
	public synchronized void addActionListener(ActionListener l) {
		super.addActionListener(l);

		if (hashvaa != null) {
			hashvaa.addActionListener(l);
		}
		if (mutzar != null) {
			mutzar.addActionListener(l);
		}
		if (menahel != null) {
			menahel.addActionListener(l);
		}
		if (back != null) {
			back.addActionListener(l);
		}
	}
}
