package ui;

import helpers.XLSFileBrowse;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dialogs.IrbulLefiBchiraDialog;
import dialogs.IrbulLefiEzorimDialog;

import templates.MyPanel;

import liga.Liga;
import main.MasachRashi;
import actions.AdminActions;

import excelWriters.ExportToExcel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * TableRenderDemo is just like TableDemo, except that it explicitly initializes
 * column sizes and it uses a combo box as an editor for the Sport column.
 */
public class AdminPanel extends MyPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
	private final Integer actionManagerId;

	private JButton[] button = new JButton[15];
	private final Dimension screenDimension;
	private MasachRashi masachRashi;

	public AdminPanel(Dimension screenDimension, Integer actionManagerId,
			MasachRashi masachRashi) {
		this.actionManagerId = actionManagerId;
		this.screenDimension = screenDimension;
		this.masachRashi = masachRashi;
		setLayout(new BorderLayout());

		JPanel p1 = new JPanel(new GridLayout(5, 3, 30, 10));

		ButtonName[] names;
		if (MasachRashi.getLigaId() != null) {
			names = new ButtonName[] { new ButtonName(AdminActions.resetGames),
					new ButtonName(AdminActions.loadGroups),
					new ButtonName(AdminActions.irbulLefiEzorim), null, null,
					new ButtonName(AdminActions.irbulLefiBchira),
					new ButtonName(AdminActions.resetGroups), null, null, null,
					null, null, new ButtonName(AdminActions.resetAllTables),
					new ButtonName(AdminActions.toExcel), null };
		} else {
			names = new ButtonName[] { new ButtonName(AdminActions.resetGames),
					new ButtonName(AdminActions.loadGroups), null, null, null,
					null, new ButtonName(AdminActions.resetGroups), null, null,
					null, null, null,
					new ButtonName(AdminActions.resetAllTables), null, null };
		}
		for (int x = 0; x < button.length; x++) {
			button[x] = new JButton();
			button[x].setFont(MasachRashi.FONT);
			button[x].setFocusPainted(false);
			if (names[x] == null) {
				button[x].setEnabled(false);
				button[x].setBackground(Color.white);
			} else {
				button[x].setActionCommand(names[x].getName());
				button[x].setText(names[x].getDescription());
				button[x].setEnabled(true);
				button[x].addActionListener(this);

				button[x].setForeground(Color.blue);
				button[x].setBackground(Color.pink);
			}

			p1.add(button[x]);
		}

		add(p1, BorderLayout.CENTER);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AdminActions action = null;
		String message = null;
		int confirm = 0;
		try {
			action = AdminActions.valueOf(e.getActionCommand());
		} catch (Exception ee) {
			action = AdminActions.noAction;
		}
		switch (action) {
		case loadGroups:
			confirm = JOptionPane
					.showConfirmDialog(this,
							"הנך עומד/ת לבצע טעינה קבוצות מקובץ חיצוני, האם להמשיך את הפעולה");
			if (confirm == 0) {
				try {
					Liga.getActionManager(actionManagerId)
							.loadGroupsFromExcel();
					message = "רשומות נטענו בהצלחה";
				} catch (Exception e1) {
					message = e1.getMessage();
				}
			} else {
				message = "פעולת טעינת רשומות התבטלה";
			}
			break;
		case irbulLefiEzorim:
			new IrbulLefiEzorimDialog(this, screenDimension, actionManagerId);
			break;
		case irbulLefiBchira:
			new IrbulLefiBchiraDialog(this, screenDimension, actionManagerId);
			break;
		case toExcel:
			String filename = new XLSFileBrowse(this, Liga.getExcelFileName())
					.getFileName();
			if (filename != null && filename.length() > 0) {
				Liga.setExcelFileName(filename);
				new ExportToExcel(this, filename, actionManagerId);
			}
			break;
		case resetAllTables:
			confirm = JOptionPane.showConfirmDialog(this,
					"הנך עומד/ת למחוק את כל הטבלאות, האם להמשיך את הפעולה");
			if (confirm == 0) {
				try {
					Liga.getActionManager(actionManagerId).resetAllTables();
					message = "טבלאות נמחקו בהצלחה";
					masachRashi.setNewLiga(null, null);
				} catch (Exception e1) {
					message = e1.getMessage();
				}
			} else {
				message = "פעולת מחיקת טבלאות התבטלה";
			}
			break;
		case resetGames:
			confirm = JOptionPane.showConfirmDialog(this,
					"הנך עומד/ת למחוק את טבלת המשחקים, האם להמשיך את הפעולה");
			if (confirm == 0) {
				try {
					Liga.getActionManager(actionManagerId).resetGamesTable();
					message = "טבלת המשחקים נמחקה בהצלחה";
				} catch (Exception e1) {
					message = e1.getMessage();
				}
			} else {
				message = "פעולת מחיקת טבלת המשחקים התבטלה";
			}
			break;
		case resetGroups:
			confirm = JOptionPane.showConfirmDialog(this,
					"הנך עומד/ת למחוק את טבלת הקבוצות, האם להמשיך את הפעולה");
			if (confirm == 0) {
				try {
					Liga.getActionManager(actionManagerId).resetGroupsTable();
					message = "טבלת הקבוצות נמחקה בהצלחה";
				} catch (Exception e1) {
					message = e1.getMessage();
				}
			} else {
				message = "פעולת מחיקת טבלת הקבוצות התבטלה";
			}
			break;
		case noAction:
			break;
		default:
			break;
		}
		if (message != null) {
			JOptionPane.showMessageDialog(this, message, "action",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class ButtonName {
		private String name;
		private String description;

		public ButtonName(AdminActions a) {
			super();
			this.name = a.name();
			this.description = a.getDescription();
		}

		public String getName() {
			return name;
		}

		public String getDescription() {
			return description;
		}
	}
}
