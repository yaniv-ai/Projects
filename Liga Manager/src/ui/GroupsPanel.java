package ui;

import helpers.EqualSet;
import helpers.JSensitiveTextField;
import helpers.JSiduriShemComboBox;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import constraints.GroupPanelConstraints;
import dimensions.GroupPanelDimensions;

import liga.Liga;
import main.MasachRashi;

import enums.KodShinuyDB;

import tableHelpers.StringTableCellRenderer;
import tableHelpers.StringTableFieldEditor;
import templates.MyPanel;
import vo.GroupVO;
import vo.SiduriShemVO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * TableRenderDemo is just like TableDemo, except that it explicitly initializes
 * column sizes and it uses a combo box as an editor for the Sport column.
 */
public class GroupsPanel extends MyPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JTable ligaGroupsTable, restGroupsTable;
	private MyTableModel ligaGroupsTableModel, restGroupsTableModel;
	private JButton deleteButton = null, haaverButton = null,
			simunHakolButton = null;
	private final String GroupName_COL = "קבוצה";
	private final String Ezor_COL = "איזור בליגה";
	private final String Bait_COL = "בית בליגה";
	private final String Check_COL = "סימון";
	private Vector<GroupVO> ligaGroupVector = null, restGroupVector = null,
			totalVector = null;
	private JScrollPane ligaGroupsScrollPane, restGroupsScrollPane;
	private GroupAdder groupAdder;
	private EqualSet<GroupVO> groupSet;

	private boolean simunHakol = false;

	private GroupPanelConstraints constraints;
	private GroupPanelDimensions dimensions;
	private final Integer actionManagerId;

	public GroupsPanel(Dimension screenDimension, Integer actionManagerId) {
		super();
		this.actionManagerId = actionManagerId;
		constraints = new GroupPanelConstraints();
		dimensions = new GroupPanelDimensions(screenDimension);
	}

	public void showPanel() {
		this.removeAll();
		String message = null;

		deleteButton = new JButton("מחק קבוצות מסומנות");
		deleteButton.addActionListener(this);
		deleteButton.setFont(MasachRashi.FONT);

		haaverButton = new JButton("העבר קבוצות מסומנות");
		haaverButton.addActionListener(this);
		haaverButton.setFont(MasachRashi.FONT);

		simunHakolButton = new JButton("סימון כל הקבוצות בליגה");
		simunHakolButton.addActionListener(this);
		simunHakolButton.setFont(MasachRashi.FONT);

		try {
			fillTable();
			ligaGroupsScrollPane = new JScrollPane(ligaGroupsTable,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			ligaGroupsScrollPane.setViewportView(ligaGroupsTable);

			ligaGroupsScrollPane.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.MAGENTA, 3, true),
					"    קבוצות רשומות בליגה    ", TitledBorder.RIGHT,
					TitledBorder.CENTER, MasachRashi.BIGFONT));

			restGroupsScrollPane = new JScrollPane(restGroupsTable,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			restGroupsScrollPane.setViewportView(restGroupsTable);

			restGroupsScrollPane.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.RED, 3, true),
					"   קבוצות שאינן רשומות בליגה   ", TitledBorder.RIGHT,
					TitledBorder.CENTER, MasachRashi.BIGFONT));

			// setLayout(new GridBagLayout());
			// setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

			groupAdder = new GroupAdder();

			groupAdder.getKoteret().setPreferredSize(
					dimensions.getKoteretDimension());
			groupAdder.getGroupName().setPreferredSize(
					dimensions.getGroupNameTextDimension());
			groupAdder.getEzorComboBox().setPreferredSize(
					dimensions.getEzorComboBoxDimension());
			groupAdder.getBaitComboBox().setPreferredSize(
					dimensions.getBaitComboBoxDimension());
			groupAdder.getHosefKvutzaButton().setPreferredSize(
					dimensions.getHosefKvutzaButtonDimension());

			groupAdder.getEzorChadash().setPreferredSize(
					dimensions.getEzorChadashDimension());
			groupAdder.getHosefEzorButton().setPreferredSize(
					dimensions.getHosefEzorButtonDimension());
			groupAdder.getBaitChadash().setPreferredSize(
					dimensions.getBaitChadashDimension());
			groupAdder.getHosefBaitButton().setPreferredSize(
					dimensions.getHosefBaitButtonDimension());

			deleteButton
					.setPreferredSize(dimensions.getMechakButtonDimension());
			haaverButton
					.setPreferredSize(dimensions.getHaaverButtonDimension());
			simunHakolButton.setPreferredSize(dimensions
					.getSimunHakolButtonDimension());

			ligaGroupsScrollPane.setPreferredSize(dimensions
					.getLigaGroupTableDimension());

			restGroupsScrollPane.setPreferredSize(dimensions
					.getRestGroupTableDimension());

			JPanel buttonsSidePanel = new JPanel();
			buttonsSidePanel.setPreferredSize(dimensions
					.getButtonsSideDimension());
			buttonsSidePanel.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					"", TitledBorder.RIGHT, TitledBorder.CENTER,
					MasachRashi.FONT));
			JPanel tablesSidePanel = new JPanel();
			tablesSidePanel.setPreferredSize(dimensions
					.getTablesSideDimension());
			tablesSidePanel.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					"", TitledBorder.RIGHT, TitledBorder.CENTER,
					MasachRashi.FONT));

			buttonsSidePanel.add(groupAdder.getKoteret(),
					constraints.getKoteretConstraints());
			buttonsSidePanel.add(groupAdder.getGroupName(),
					constraints.getGroupNameTextConstraints());
			buttonsSidePanel.add(groupAdder.getEzorComboBox(),
					constraints.getEzorComboBoxConstraints());
			buttonsSidePanel.add(groupAdder.getBaitComboBox(),
					constraints.getBaitComboBoxConstraints());
			buttonsSidePanel.add(groupAdder.getHosefKvutzaButton(),
					constraints.getHosefKvutzaButtonConstraints());

			buttonsSidePanel.add(groupAdder.getEzorChadash(),
					constraints.getEzorChadashConstraints());
			buttonsSidePanel.add(groupAdder.getHosefEzorButton(),
					constraints.getHosefEzorButtonConstraints());
			buttonsSidePanel.add(groupAdder.getBaitChadash(),
					constraints.getBaitChadashConstraints());
			buttonsSidePanel.add(groupAdder.getHosefBaitButton(),
					constraints.getHosefBaitButtonConstraints());

			tablesSidePanel.add(deleteButton,
					constraints.getMechakButtonConstraints());
			tablesSidePanel.add(haaverButton,
					constraints.getHaaverButtonConstraints());
			tablesSidePanel.add(simunHakolButton,
					constraints.getSimunHakolButtonConstraints());
			tablesSidePanel.add(ligaGroupsScrollPane,
					constraints.getLigaGroupTableConstraints());
			tablesSidePanel.add(restGroupsScrollPane,
					constraints.getRestGroupTableConstraints());

			setLayout(new BorderLayout());
			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

			add(buttonsSidePanel, BorderLayout.EAST);
			add(tablesSidePanel, BorderLayout.CENTER);
		} catch (Exception e) {
			message = e.getMessage();
		}

		if (message != null) {
			JOptionPane.showMessageDialog(GroupsPanel.this, message,
					"Error in making Groups table", JOptionPane.ERROR_MESSAGE);
		}

	}

	public void fillTable() throws Exception {

		StringTableCellRenderer stringCellRenderer = new StringTableCellRenderer();
		stringCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		stringCellRenderer.setVerticalAlignment(JLabel.CENTER);
		StringTableFieldEditor stringCellEditor = new StringTableFieldEditor();

		ligaGroupVector = Liga.getActionManager(actionManagerId)
				.getReshimatKvutzot(true);
		restGroupVector = Liga.getActionManager(actionManagerId)
				.getReshimatKvutzot(false);

		totalVector = new Vector<GroupVO>();
		totalVector.addAll(ligaGroupVector);
		totalVector.addAll(restGroupVector);
		groupSet = new EqualSet<GroupVO>(totalVector);

		// קבוצות בליגה
		ligaGroupsTableModel = new MyTableModel(ligaGroupVector);
		ligaGroupsTable = new JTable(ligaGroupsTableModel);
		ligaGroupsTable.getTableHeader().setFont(MasachRashi.FONT);
		ligaGroupsTable
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		ligaGroupsTable.setFillsViewportHeight(true);
		ligaGroupsTable.setAutoCreateRowSorter(true);
		ligaGroupsTable.setFont(MasachRashi.FONT);
		ligaGroupsTable.getTableHeader().setReorderingAllowed(false);
		ligaGroupsTable.setRowHeight(30);

		ligaGroupsTable.getColumnModel().getColumn(0).setMinWidth(80);
		ligaGroupsTable.getColumnModel().getColumn(0)
				.setCellRenderer(stringCellRenderer);

		ligaGroupsTable.getColumnModel().getColumn(1).setMinWidth(80);
		ligaGroupsTable.getColumnModel().getColumn(1)
				.setCellRenderer(stringCellRenderer);

		ligaGroupsTable.getColumnModel().getColumn(2).setMinWidth(300);
		ligaGroupsTable.getColumnModel().getColumn(2)
				.setCellRenderer(stringCellRenderer);
		ligaGroupsTable.getColumn(ligaGroupsTable.getColumnName(2))
				.setCellEditor(stringCellEditor);

		ligaGroupsTable.getColumnModel().getColumn(3).setMinWidth(80);

		// קבוצות לא בליגה
		restGroupsTableModel = new MyTableModel(restGroupVector);
		restGroupsTable = new JTable(restGroupsTableModel);
		restGroupsTable.getTableHeader().setFont(MasachRashi.FONT);
		restGroupsTable
				.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		restGroupsTable.setFillsViewportHeight(true);
		restGroupsTable.setAutoCreateRowSorter(true);
		restGroupsTable.setFont(MasachRashi.FONT);
		restGroupsTable.getTableHeader().setReorderingAllowed(false);
		restGroupsTable.setRowHeight(30);

		restGroupsTable.getColumnModel().getColumn(0).setMinWidth(80);
		restGroupsTable.getColumnModel().getColumn(0)
				.setCellRenderer(stringCellRenderer);

		restGroupsTable.getColumnModel().getColumn(1).setMinWidth(80);
		restGroupsTable.getColumnModel().getColumn(1)
				.setCellRenderer(stringCellRenderer);

		restGroupsTable.getColumnModel().getColumn(2).setMinWidth(300);
		restGroupsTable.getColumnModel().getColumn(2)
				.setCellRenderer(stringCellRenderer);
		restGroupsTable.getColumn(restGroupsTable.getColumnName(2))
				.setCellEditor(stringCellEditor);

		restGroupsTable.getColumnModel().getColumn(3).setMinWidth(80);

	}

	public boolean addUpdateGroup(Vector<GroupVO> groups,
			KodShinuyDB kodShinuyDB) {
		String message = null;
		if (groups == null) {
			message = "קבוצות לא אותחלו";
		}
		if (message == null && groups.size() == 0) {
			message = "לא נבחרה קבוצה להוספה או לעדכון";
		}
		try {
			if (message == null) {
				GroupVO group = null;
				for (int i = 0; i < groups.size(); i++) {
					group = groups.elementAt(i);

					if (group == null) {
						throw new Exception("קבוצה לא אותחלה");
					}
					if (message == null
							&& (group.getGroupName() == null || group
									.getGroupName().length() == 0)) {
						throw new Exception("עליך למלא את שדה שם הקבוצה");
					}

					if (groupSet.contains(group)) {
						if (kodShinuyDB.equals(KodShinuyDB.idkun)) {
							GroupVO g = totalVector.elementAt(totalVector
									.indexOf(group));
							if (g.getGroupID().intValue() != group.getGroupID()
									.intValue()) {
								throw new Exception("כבר קיימת קבוצה בשם זה");
							}
						} else if (kodShinuyDB.equals(KodShinuyDB.hosafa)) {
							throw new Exception("כבר קיימת קבוצה בשם זה");
						}
					}

					if (kodShinuyDB.equals(KodShinuyDB.idkun)) {
						Liga.getActionManager(actionManagerId).adkenKvutza(
								group);
						MasachRashi.publishMessage("קבוצה "
								+ group.getGroupName() + ", עודכנה בהצלחה");
					} else {
						Liga.getActionManager(actionManagerId).hosefKvutza(
								group);
						MasachRashi.publishMessage("קבוצה "
								+ group.getGroupName() + ", הוספה בהצלחה");
					}
				}

				updatePanel();
				checkTableForResult(groups.elementAt(0).getGroupName());
				groupAdder.resetFields();
				updateUI();
			}
		} catch (Exception e) {
			message = e.getMessage();
		}

		if (message == null) {
			return true;
		} else {
			JOptionPane.showMessageDialog(GroupsPanel.this, message,
					"Addition failed", JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	private void updatePanel() throws Exception {
		this.remove(ligaGroupsTable);
		this.remove(restGroupsTable);
		fillTable();
		ligaGroupsScrollPane.setViewportView(ligaGroupsTable);
		restGroupsScrollPane.setViewportView(restGroupsTable);
	}

	class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] COLUMN_NAMES = { Bait_COL, Ezor_COL, GroupName_COL,
				Check_COL };
		private final Class<?>[] COLUMN_TYPES = new Class<?>[] { String.class,
				String.class, String.class, Boolean.class };
		private Vector<GroupVO> groups;
		private Vector<Boolean> checkList = null;

		public MyTableModel(Vector<GroupVO> groups) {
			this.groups = groups;
			checkList = new Vector<Boolean>();
			if (groups != null) {
				for (int i = 0; i < groups.size(); i++) {
					checkList.add(new Boolean(false));
				}
			}
		}

		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		public int getRowCount() {
			if (groups == null) {
				return 0;
			} else {
				return groups.size();
			}
		}

		public String getColumnName(int col) {
			return COLUMN_NAMES[col];
		}

		public Object getValueAt(final int rowIndex, final int columnIndex) {
			switch (columnIndex) {

			case 0:
				return groups.elementAt(rowIndex).getBaitName();
			case 1:
				return groups.elementAt(rowIndex).getEzorName();
			case 2:
				return groups.elementAt(rowIndex).getGroupName();
			case 3:
				return checkList.elementAt(rowIndex);
			default:
				return "Error";
			}
		}

		public Class<?> getColumnClass(int columnIndex) {
			return COLUMN_TYPES[columnIndex];
		}

		/*
		 * Don't need to implement this method unless your table's editable.
		 */
		public boolean isCellEditable(int row, int col) {
			// Note that the data/cell address is constant,
			// no matter where the cell appears onscreen.
			if (col == 2 || col == 3) {
				return true;
			} else {
				return false;
			}
		}

		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		public void setValueAt(Object value, int row, int col) {
			if (col == 3) {
				checkList.remove(row);
				checkList.add(row, (Boolean) value);
			} else if (col == 2) {
				boolean changed = false;
				GroupVO group = new GroupVO(groups.elementAt(row));

				String groupName = (String) value;
				if (!Liga.stringFieldAreEqual(group.getGroupName(), groupName)) {
					group.setGroupName(groupName);
					changed = true;
				}
				if (changed) {
					Vector<GroupVO> groups = new Vector<GroupVO>();
					groups.add(group);
					addUpdateGroup(groups, KodShinuyDB.idkun);
				}
			}
			fireTableCellUpdated(row, col);
		}

		private Vector<Boolean> getCheckList() {
			return checkList;
		}

		private void setCheckList(Vector<Boolean> chekList) {
			this.checkList = chekList;
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = null;
		Object source = e.getSource();
		try {
			if (source == deleteButton) {
				Vector<Integer> groupsList = new Vector<Integer>();
				for (int i = 0; i < ligaGroupsTableModel.getCheckList().size(); i++) {
					if (ligaGroupsTableModel.getCheckList().elementAt(i)) {
						groupsList.add(ligaGroupVector.elementAt(i)
								.getGroupID());
					}
				}
				if (groupsList.size() > 0) {
					int confirm = JOptionPane
							.showConfirmDialog(GroupsPanel.this,
									"הנך עומד/ת לבצע מחיקת קבוצות, האם להמשיך את הפעולה");
					if (confirm == 0) {
						Liga.getActionManager(actionManagerId).mechakKvutzot(
								groupsList);
						updatePanel();
						updateUI();
						message = "קבוצות נמחקו בהצלחה";
					} else {
						message = "פעולת מחיקת קבוצות התבטלה";
					}
				}
			} else if (source == haaverButton) {
				Vector<GroupVO> groupsList = new Vector<GroupVO>();
				for (int i = 0; i < ligaGroupsTableModel.getCheckList().size(); i++) {
					if (ligaGroupsTableModel.getCheckList().elementAt(i)) {
						groupsList
								.add(new GroupVO(ligaGroupVector.elementAt(i)));
					}
				}
				for (int i = 0; i < restGroupsTableModel.getCheckList().size(); i++) {
					if (restGroupsTableModel.getCheckList().elementAt(i)) {
						groupsList
								.add(new GroupVO(restGroupVector.elementAt(i)));
					}
				}
				if (groupsList.size() > 0) {
					int confirm = JOptionPane
							.showConfirmDialog(GroupsPanel.this,
									"הנך עומד/ת לבצע עדכון בתים ואזורים קבוצות, האם להמשיך את הפעולה");
					if (confirm == 0) {
						Integer ezorID = groupAdder.getEzorComboBox()
								.getSelectedSiduri();
						String ezorShem = groupAdder.getEzorComboBox()
								.getSelectedShem();
						Integer baitID = groupAdder.getBaitComboBox()
								.getSelectedSiduri();
						String baitShem = groupAdder.getBaitComboBox()
								.getSelectedShem();

						for (int j = 0; j < groupsList.size(); j++) {
							groupsList.elementAt(j).setEzor(ezorID);
							groupsList.elementAt(j).setEzorName(ezorShem);
							groupsList.elementAt(j).setBait(baitID);
							groupsList.elementAt(j).setBaitName(baitShem);
						}
						addUpdateGroup(groupsList, KodShinuyDB.idkun);
					} else {
						message = "פעולת עדכון קבוצות התבטלה";
					}
				} else {
					message = "לא נבחרו קבוצות להעברה";
				}
			} else if (source == simunHakolButton) {
				if (simunHakol == false) {
					simunHakol = true;
				} else {
					simunHakol = false;
				}
				int kamut = ligaGroupsTableModel.getCheckList().size();
				Vector<Boolean> checkList = new Vector<Boolean>();
				for (int i = 0; i < kamut; i++) {
					checkList.add(new Boolean(simunHakol));
				}
				ligaGroupsTableModel.setCheckList(checkList);
				updateUI();
			}
		} catch (Exception e1) {
			message = e1.getMessage();
		}
		if (message != null) {
			JOptionPane.showMessageDialog(GroupsPanel.this, message, "action",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private class GroupAdder implements ActionListener {

		private JLabel koteret;
		private JSensitiveTextField groupName;
		Vector<SiduriShemVO> ezorim, batim;
		private JSiduriShemComboBox ezorComboBox, baitComboBox;
		private JTextField ezorChadash, baitChadash;
		private JButton hosefKvutzaButton, hosefEzorButton, hosefBaitButton;
		private final String teurBechar = "בחר איזור",
				teurLoHugderu = "לא הוגדרו איזורים",
				teurEinNetunim = "אין נתונים להצגה";

		public GroupAdder() {
			koteret = new JLabel("פאנל הוספת קבוצה", SwingConstants.CENTER);
			koteret.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					"", TitledBorder.CENTER, TitledBorder.CENTER,
					MasachRashi.FONT));

			groupName = new JSensitiveTextField(20);
			groupName.addActionListener(this);
			groupName.setHorizontalAlignment(JTextField.RIGHT);
			groupName.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" שם קבוצה: ", TitledBorder.RIGHT, TitledBorder.CENTER,
					MasachRashi.FONT));

			ezorChadash = new JTextField(20);
			ezorChadash.setHorizontalAlignment(JTextField.RIGHT);
			ezorChadash.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" שם איזור להוספה: ", TitledBorder.RIGHT,
					TitledBorder.CENTER, MasachRashi.FONT));

			baitChadash = new JTextField(20);
			baitChadash.setHorizontalAlignment(JTextField.RIGHT);
			baitChadash.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" שם בית להוספה: ", TitledBorder.RIGHT,
					TitledBorder.CENTER, MasachRashi.FONT));

			hosefKvutzaButton = new JButton("הוסף קבוצה");
			hosefKvutzaButton.addActionListener(this);
			hosefKvutzaButton.setFont(MasachRashi.FONT);

			hosefEzorButton = new JButton("הוסף איזור");
			hosefEzorButton.addActionListener(this);
			hosefEzorButton.setFont(MasachRashi.FONT);

			hosefBaitButton = new JButton("הוסף בית");
			hosefBaitButton.addActionListener(this);
			hosefBaitButton.setFont(MasachRashi.FONT);

			ezorim = Liga.getActionManager(actionManagerId).getEzorim();
			ezorComboBox = new JSiduriShemComboBox(ezorim, teurBechar,
					teurLoHugderu, teurEinNetunim);
			ezorComboBox.addActionListener(this);
			ezorComboBox.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" איזור: ", TitledBorder.RIGHT, TitledBorder.CENTER,
					MasachRashi.FONT));

			batim = Liga.getActionManager(actionManagerId).getBatim();
			baitComboBox = new JSiduriShemComboBox(batim, teurBechar,
					teurLoHugderu, teurEinNetunim);
			baitComboBox.addActionListener(this);
			baitComboBox.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" בית: ", TitledBorder.RIGHT, TitledBorder.CENTER,
					MasachRashi.FONT));
			if (ezorim != null && ezorim.size() > 0) {
				ezorComboBox.setSelectedIndex(0);
			}
			if (batim != null && batim.size() > 0) {
				baitComboBox.setSelectedIndex(0);
			}

		}

		public JLabel getKoteret() {
			return koteret;
		}

		public JSensitiveTextField getGroupName() {
			return groupName;
		}

		public JSiduriShemComboBox getEzorComboBox() {
			return ezorComboBox;
		}

		public JSiduriShemComboBox getBaitComboBox() {
			return baitComboBox;
		}

		public JTextField getEzorChadash() {
			return ezorChadash;
		}

		public JTextField getBaitChadash() {
			return baitChadash;
		}

		public JButton getHosefKvutzaButton() {
			return hosefKvutzaButton;
		}

		public JButton getHosefEzorButton() {
			return hosefEzorButton;
		}

		public JButton getHosefBaitButton() {
			return hosefBaitButton;
		}

		private void resetFields() {
			groupName.setText(null);
			ezorChadash.setText(null);
			baitChadash.setText(null);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			Object source = e.getSource();
			if (source == groupName) {
				checkTableForResult(groupName.getText());
			}
			if (source == hosefKvutzaButton) {
				Vector<GroupVO> groups = new Vector<GroupVO>();
				groups.add(new GroupVO(null, groupName.getText(), ezorComboBox
						.getSelectedSiduri(), ezorComboBox.getSelectedShem(),
						baitComboBox.getSelectedSiduri(), baitComboBox
								.getSelectedShem()));
				addUpdateGroup(groups, KodShinuyDB.hosafa);
			}
			if (source == hosefEzorButton) {
				String message = null;
				try {
					String newEzor = ezorChadash.getText();

					if (newEzor == null || newEzor.length() == 0) {
						message = "צריך לכתוב שם אזור";
					} else {
						if (alreadyExistInSiduriShemVector(ezorim, newEzor)) {
							message = "כבר קיים שם כזה במערכת";
						} else {
							Liga.getActionManager(actionManagerId).hosefEzor(
									newEzor);
							ezorim = Liga.getActionManager(actionManagerId)
									.getEzorim();
							ezorComboBox.setAllData(ezorim);
							ezorChadash.setText(null);
							updateUI();
						}
					}
				} catch (Exception e1) {
					message = e1.getMessage();
					if (message == null) {
						message = "תקלה לא ידועה ארעה";
					}
				}
				if (message != null) {
					JOptionPane.showMessageDialog(GroupsPanel.this, message,
							"action", JOptionPane.INFORMATION_MESSAGE);
				}
			}
			if (source == hosefBaitButton) {
				String message = null;
				try {
					String newBait = baitChadash.getText();

					if (newBait == null || newBait.length() == 0) {
						message = "צריך לכתוב שם בית";
					} else {
						if (alreadyExistInSiduriShemVector(batim, newBait)) {
							message = "כבר קיים שם כזה במערכת";
						} else {
							Liga.getActionManager(actionManagerId).hosefBait(
									newBait);
							batim = Liga.getActionManager(actionManagerId)
									.getBatim();
							baitComboBox.setAllData(batim);
							baitChadash.setText(null);
							updateUI();
						}
					}
				} catch (Exception e1) {
					message = e1.getMessage();
					if (message == null) {
						message = "תקלה לא ידועה ארעה";
					}
				}
				if (message != null) {
					JOptionPane.showMessageDialog(GroupsPanel.this, message,
							"action", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}

		private boolean alreadyExistInSiduriShemVector(
				Vector<SiduriShemVO> vector, String shem) {
			if (vector == null || vector.size() == 0 || shem == null
					|| shem.length() == 0) {
				return false;
			}
			for (int i = 0; i < vector.size(); i++) {
				if (vector.elementAt(i).getShem().equalsIgnoreCase(shem)) {
					return true;
				}
			}
			return false;
		}
	}

	private void checkTableForResult(String name) {
		changeSelection(ligaGroupsTable, name);
		JViewport viewport = (JViewport) ligaGroupsTable.getParent();
		Rectangle rect = ligaGroupsTable.getCellRect(
				ligaGroupsTable.getSelectedRow(), 0, true);
		Rectangle r2 = viewport.getVisibleRect();
		ligaGroupsTable.scrollRectToVisible(new Rectangle(rect.x, rect.y,
				(int) r2.getWidth(), (int) r2.getHeight()));
	}

	private void changeSelection(JTable table, String rowKey) {
		final int GROUPNAME_COL = 2;
		int col = table.convertColumnIndexToView(GROUPNAME_COL);
		for (int row = table.getRowCount(); --row >= 0;) {
			String b = (String) table.getValueAt(row, col);
			if (b.contains(rowKey)) {
				table.changeSelection(row, col, false, false);
				return;
			}
		}
		table.clearSelection();
	}
}
