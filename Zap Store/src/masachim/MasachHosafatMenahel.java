package masachim;

import helpers.ComponentTableCellRenderer;
import helpers.FinalStringEditor;
import helpers.JButtonEditor;
import helpers.JSensitiveTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import main.MainWindow;

import databaseManager.DBQueries;
import enums.Options;
import enums.Ranks;
import templates.MyPanel;
import vo.UserVO;

public class MasachHosafatMenahel extends MyPanel implements TableModelListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JButton back = null;
	private Object[][] data;
	private JTable table;
	private JScrollPane scrollpane;

	private JSensitiveTextField newUser;
	private JSensitiveTextField newPassword;
	private JButton addUser;

	// יישות ששולפת נתונים מהטבלאות
	private DBQueries dbq;

	public MasachHosafatMenahel() throws Exception {
		dbq = new DBQueries();
		JPanel gridBagPanel = new JPanel(new GridBagLayout());

		// מילוי טבלת לקוחות רשומים
		fillTable();

		// הכנת פאנל הוספת לקוח
		JPanel addLakoach = createAddLakoach();

		// הגדרת כפתור חזרה אחורה
		back = new JButton(Options.tafrit.getDescription());
		back.setActionCommand(Options.tafrit.name());

		setLayout(new BorderLayout());

		// מיקום הטבלה במרכז המסך
		scrollpane = new JScrollPane(table);
		add(scrollpane, BorderLayout.CENTER);

		// מיקום פאנל הוספת לקוח רשום
		add(addLakoach, BorderLayout.EAST);

		// מיקום כפתור החזרה בתחתית המסך
		gridBagPanel.add(back);
		add(gridBagPanel, BorderLayout.SOUTH);
	}

	private void fillTable() throws Exception {
		Vector<UserVO> users = dbq.getUsers();
		if (users != null && users.size() > 0) {
			data = new Object[users.size()][5];
			for (int i = 0; i < data.length; i++) {
				JRadioButton admin = new JRadioButton();
				JRadioButton manager = new JRadioButton();
				JRadioButton user = new JRadioButton();

				admin.setActionCommand(Ranks.admin.name());
				manager.setActionCommand(Ranks.manager.name());
				user.setActionCommand(Ranks.user.name());

				ButtonGroup ranksGroup = new ButtonGroup();
				ranksGroup.add(admin);
				ranksGroup.add(manager);
				ranksGroup.add(user);

				data[i][0] = admin;
				data[i][1] = manager;
				data[i][2] = user;

				Ranks rank = users.elementAt(i).getRank();
				if (rank == null) {
					rank = Ranks.user;
				}
				switch (rank) {
				case admin:
					admin.setSelected(true);
					break;
				case manager:
					manager.setSelected(true);
					break;
				case user:
					user.setSelected(true);
					break;
				default:
					user.setSelected(true);
					break;
				}

				data[i][3] = users.elementAt(i).getUsername();

				JCheckBox delete = new JCheckBox();
				delete.setActionCommand("delete");
				data[i][4] = delete;
			}
		} else {
			data = new Object[0][];
		}
		DefaultTableModel dm = new DefaultTableModel(data, new Object[] {
				Ranks.admin.getDescription(), Ranks.manager.getDescription(),
				Ranks.user.getDescription(), "שם", "מחיקה" });
		dm.addTableModelListener(this);
		table = new JTable(dm);

		JButtonEditor cellEditor = new JButtonEditor();
		ComponentTableCellRenderer cellRenderer = new ComponentTableCellRenderer();
		cellRenderer.setHorizontalAlignment(JLabel.CENTER);
		cellRenderer.setVerticalAlignment(JLabel.CENTER);
		table.getColumn(Ranks.admin.getDescription()).setCellRenderer(
				cellRenderer);
		table.getColumn(Ranks.manager.getDescription()).setCellRenderer(
				cellRenderer);
		table.getColumn(Ranks.user.getDescription()).setCellRenderer(
				cellRenderer);
		table.getColumn("מחיקה").setCellRenderer(cellRenderer);

		table.getColumn(Ranks.admin.getDescription()).setCellEditor(cellEditor);
		table.getColumn(Ranks.manager.getDescription()).setCellEditor(
				cellEditor);
		table.getColumn(Ranks.user.getDescription()).setCellEditor(cellEditor);
		table.getColumn("שם").setCellEditor(new FinalStringEditor());
		table.getColumn("מחיקה").setCellEditor(cellEditor);

		table.getTableHeader().setFont(MainWindow.FONT);
		table.setRowHeight(30);
		table.getTableHeader().setReorderingAllowed(false);
		table.setFont(MainWindow.FONT);
	}

	private JPanel createAddLakoach() {
		// הוספת לקוח
		JLabel newUserLabel = new JLabel("שם משתמש: ", SwingConstants.CENTER);
		JLabel newPasswordLabel = new JLabel("סיסמא: ", SwingConstants.CENTER);
		newUser = new JSensitiveTextField();
		newUser.setHorizontalAlignment(JTextField.CENTER);
		newPassword = new JSensitiveTextField();
		newPassword.setHorizontalAlignment(JTextField.CENTER);
		addUser = new JButton("הוסף");
		newUserLabel.setFont(MainWindow.FONT);
		newPasswordLabel.setFont(MainWindow.FONT);
		newUser.setFont(MainWindow.FONT);
		newPassword.setFont(MainWindow.FONT);
		addUser.setFont(MainWindow.FONT);
		addUser.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				addLakoach(newUser.getText(), newPassword.getText());
			}
		});

		JPanel gridBagPanel;

		JPanel userGrid = new JPanel(new GridLayout(2, 2, 20, 20));
		userGrid.add(newUser);
		userGrid.add(newUserLabel);
		userGrid.add(newPassword);
		userGrid.add(newPasswordLabel);
		JPanel userPanel = new JPanel(new BorderLayout());
		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(userGrid);
		userPanel.add(gridBagPanel, BorderLayout.NORTH);
		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(addUser);
		userPanel.add(gridBagPanel, BorderLayout.SOUTH);
		userPanel.add(new JLabel("   "), BorderLayout.EAST);
		userPanel.add(new JLabel("   "), BorderLayout.WEST);

		userPanel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.lightGray, 2, true),
				" הוספת לקוח ", TitledBorder.CENTER, TitledBorder.CENTER,
				MainWindow.FONT));

		JPanel returnedPanel = new JPanel(new BorderLayout());
		gridBagPanel = new JPanel(new GridBagLayout());
		gridBagPanel.add(userPanel);
		returnedPanel.add(userPanel);
		returnedPanel.add(new JLabel("        "), BorderLayout.WEST);
		returnedPanel.add(new JLabel("        "), BorderLayout.EAST);
		return returnedPanel;
	}

	private void addLakoach(String username, String password) {
		String message = null;
		if (username == null || username.length() <= 0) {
			message = "עליך להקליד שם משתמש על מנת לבצע הוספה";
		}
		if (message == null && (password == null || password.length() <= 0)) {
			message = "עליך להקליד סיסמה יצרן על מנת לבצע הוספה";
		}
		if (message == null) {
			for (int i = 0; i < table.getRowCount(); i++) {
				if (((String) table.getValueAt(i, 3)).equals(username)) {
					message = "לקוח כבר קיים, נא לבחור שם אחר";
				}
			}
		}
		if (message == null) {
			try {
				Object[] dataRow = new Object[5];
				JRadioButton admin = new JRadioButton();
				JRadioButton manager = new JRadioButton();
				JRadioButton user = new JRadioButton();

				admin.setActionCommand(Ranks.admin.name());
				manager.setActionCommand(Ranks.manager.name());
				user.setActionCommand(Ranks.user.name());

				ButtonGroup ranksGroup = new ButtonGroup();
				ranksGroup.add(admin);
				ranksGroup.add(manager);
				ranksGroup.add(user);

				dataRow[0] = admin;
				dataRow[1] = manager;
				dataRow[2] = user;

				user.setSelected(true);

				dataRow[3] = username;

				JCheckBox delete = new JCheckBox();
				delete.setActionCommand("delete");
				dataRow[4] = delete;
				if (dbq.addNewUser(username, password, Ranks.user)) {
					((DefaultTableModel) table.getModel())
							.insertRow(0, dataRow);
					newUser.setText(null);
					newPassword.setText(null);
					JOptionPane.showMessageDialog(this, "לקוח הוסף בהצלחה",
							"Info", JOptionPane.INFORMATION_MESSAGE);
				}
			} catch (Exception e) {
				message = "תקלה בעת נסיון הוספת מוצר";
			}
			if (message != null) {
				JOptionPane.showMessageDialog(this, message, "Error",
						JOptionPane.ERROR_MESSAGE);
			}
		}

		if (message != null) {
			JOptionPane.showMessageDialog(this, message, "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	@Override
	public synchronized void addActionListener(ActionListener l) {
		super.addActionListener(l);

		if (back != null) {
			back.addActionListener(l);
		}
	}

	@Override
	public void tableChanged(TableModelEvent e) {
		if (e.getType() == TableModelEvent.UPDATE) {
			int row = e.getFirstRow();
			int column = e.getColumn();
			Object comp = table.getModel().getValueAt(row, column);
			if (comp.getClass().equals(JRadioButton.class)
					|| comp.getClass().equals(JCheckBox.class)) {
				String name = (String) table.getValueAt(row, 3);
				String action = ((AbstractButton) comp).getActionCommand();
				String message = null;
				if (action.equals(Ranks.admin.name())) {
					try {
						dbq.updateRank(name, Ranks.admin);
						message = "משתמש שונה לבעלים";
					} catch (Exception e1) {
						message = "תקלה בעת נסיון לשנות דרגה";
					}
				} else if (action.equals(Ranks.manager.name())) {
					try {
						dbq.updateRank(name, Ranks.manager);
						message = "משתמש שונה למנהל";
					} catch (Exception e1) {
						message = "תקלה בעת נסיון לשנות דרגה";
					}
				} else if (action.equals(Ranks.user.name())) {
					try {
						dbq.updateRank(name, Ranks.user);
						message = "משתמש שונה למשתמש";
					} catch (Exception e1) {
						message = "תקלה בעת נסיון לשנות דרגה";
					}
				} else if (action.equals("delete")) {
					try {
						dbq.deleteUser(name);
						((DefaultTableModel) table.getModel()).removeRow(row);
						message = "משתמש נמחק";
					} catch (Exception e1) {
						message = "תקלה בעת נסיון מחיקת משתמש";
					}
				}
				if (message != null) {
					JOptionPane
							.showMessageDialog(this, message,
									"הודעת מסך מנהלים",
									JOptionPane.INFORMATION_MESSAGE);
				}

			}
			table.repaint();
		}
	}
}
