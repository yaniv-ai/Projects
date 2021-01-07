package ui;

import helpers.EqualMapSet;
import helpers.JSensitiveIntField;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import constraints.IrbulLefiBchiraPanelConstraints;
import dimensions.IrbulLefiBchiraPanelDimensions;

import liga.Liga;
import main.MasachRashi;

import tableHelpers.IntegerTableCellRenderer;
import tableHelpers.StringTableCellRenderer;
import tableHelpers.TextAreaTableCellRenderer;
import templates.MyPanel;
import vo.GroupVO;
import vo.SiduriShemVO;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Vector;

public class IrbulLefiBchiraPanel extends MyPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private static final int MAX_BATIM = 99;
	private JSensitiveIntField kamutKvutzotBeBaitField, machzorField,
			misparMifgashimField;
	private JButton cholelBatimButton, cholelMischakimButton,
			mechakMachzorButton, simunHakolButton;
	private JTable groupsTable = new JTable(), batimTable = new JTable();
	private GroupsTableModel groupsTableModel;
	private BatimTableModel batimTableModel;
	final String check_COL = "סימון";
	final String groupName_COL = "שם קבוצה";
	final String groupNames_COL = "קבוצות בבית";
	final String ezorName_COL = "איזור";
	final String baitName_COL = "בית";
	private Vector<GroupVO> groupVector = null;
	private Vector<Boolean> checkList = null;
	private boolean metegSimunHakol = false;
	private EqualMapSet<SiduriShemVO, Vector<GroupVO>> batimVeKvutzot = null;
	private Integer machzorNochechi = null;
	private JScrollPane groupsScrollPane, batimScrollPane;
	boolean reloadGroupsTable = false;
	boolean reloadBatimTable = false;

	private IrbulLefiBchiraPanelConstraints constraints;
	private IrbulLefiBchiraPanelDimensions dimensions;
	private final Integer actionManagerId;

	public IrbulLefiBchiraPanel(Dimension screenDimension,
			Integer actionManagerId) {
		super();
		this.actionManagerId = actionManagerId;
		constraints = new IrbulLefiBchiraPanelConstraints();
		dimensions = new IrbulLefiBchiraPanelDimensions(screenDimension);
	}

	public void showPanel() {
		this.removeAll();
		String message = null;
		try {
			fillTable(0);
			fillBatimTable(0);
			updateUI();

			kamutKvutzotBeBaitField = new JSensitiveIntField();
			// machzor.addActionListener(this);
			kamutKvutzotBeBaitField.setLengthLimit(9);
			kamutKvutzotBeBaitField.setHorizontalAlignment(JTextField.RIGHT);
			kamutKvutzotBeBaitField.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" כמות קבוצות בבית: ", TitledBorder.RIGHT,
					TitledBorder.CENTER, MasachRashi.FONT));

			machzorField = new JSensitiveIntField();
			machzorField.addActionListener(this);
			machzorField.setLengthLimit(9);
			machzorField.setHorizontalAlignment(JTextField.RIGHT);
			machzorField.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" מחזור: ", TitledBorder.RIGHT, TitledBorder.CENTER,
					MasachRashi.FONT));

			cholelBatimButton = new JButton("חולל בתים");
			cholelBatimButton.addActionListener(this);
			cholelBatimButton.setFont(MasachRashi.FONT);

			misparMifgashimField = new JSensitiveIntField();
			// misparMifgashim.addActionListener(this);
			misparMifgashimField.setLengthLimit(9);
			misparMifgashimField.setHorizontalAlignment(JTextField.RIGHT);
			misparMifgashimField.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" מספר מפגשים: ", TitledBorder.RIGHT, TitledBorder.CENTER,
					MasachRashi.FONT));

			cholelMischakimButton = new JButton("חולל משחקים");
			cholelMischakimButton.addActionListener(this);
			cholelMischakimButton.setFont(MasachRashi.FONT);

			mechakMachzorButton = new JButton("מחק מחזור");
			mechakMachzorButton.addActionListener(this);
			mechakMachzorButton.setFont(MasachRashi.FONT);

			simunHakolButton = new JButton("סימון כל הקבוצות");
			simunHakolButton.addActionListener(this);
			simunHakolButton.setFont(MasachRashi.FONT);

			groupsScrollPane = new JScrollPane(groupsTable,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			groupsScrollPane.setViewportView(groupsTable);

			batimScrollPane = new JScrollPane(batimTable,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			batimScrollPane.setViewportView(batimTable);

			kamutKvutzotBeBaitField.setPreferredSize(dimensions
					.getKamutKvutzotBeBaitDimension());
			machzorField.setPreferredSize(dimensions.getMachzorTextDimension());
			cholelBatimButton.setPreferredSize(dimensions
					.getCholelBatimButtonDimension());

			misparMifgashimField.setPreferredSize(dimensions
					.getMisparMifgashimDimension());
			cholelMischakimButton.setPreferredSize(dimensions
					.getCholelMischakimButtonDimension());

			mechakMachzorButton.setPreferredSize(dimensions
					.getMechakMachzorButtonDimension());

			simunHakolButton.setPreferredSize(dimensions
					.getSimunHakolButtonDimension());

			groupsScrollPane.setPreferredSize(dimensions
					.getGroupTableDimension());
			batimScrollPane.setPreferredSize(dimensions
					.getBatimTableDimension());

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

			buttonsSidePanel.add(kamutKvutzotBeBaitField,
					constraints.getKamutKvutzotBeBaitConstraints());
			buttonsSidePanel.add(machzorField,
					constraints.getMachzorTextConstraints());
			buttonsSidePanel.add(cholelBatimButton,
					constraints.getCholelBatimButtonConstraints());
			buttonsSidePanel.add(misparMifgashimField,
					constraints.getMisparMifgashimConstraints());
			buttonsSidePanel.add(cholelMischakimButton,
					constraints.getCholelMischakimButtonConstraints());
			buttonsSidePanel.add(mechakMachzorButton,
					constraints.getMechakMachzorButtonConstraints());
			buttonsSidePanel.add(simunHakolButton,
					constraints.getSimunHakolButtonConstraints());

			tablesSidePanel.add(batimScrollPane,
					constraints.getBatimTableConstraints());
			tablesSidePanel.add(groupsScrollPane,
					constraints.getGroupTableConstraints());

			setLayout(new BorderLayout());
			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

			add(buttonsSidePanel, BorderLayout.EAST);
			add(tablesSidePanel, BorderLayout.CENTER);
			// add(buttonsSidePanel, constraints.getButtonsSideConstraints());
			// add(tablesSidePanel, constraints.getTablesSideConstraints());
		} catch (Exception e) {
			message = e.getMessage();
		}

		if (message != null) {
			JOptionPane.showMessageDialog(IrbulLefiBchiraPanel.this, message,
					"Error in making Players table", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void fillTable(int machzor) {
		try {
			groupVector = Liga.getActionManager(actionManagerId)
					.getReshimatKvutzot(true);
		} catch (Exception e) {
			groupVector = new Vector<GroupVO>();
		}
		Vector<GroupVO> lastPlayedGroupVector;
		try {
			lastPlayedGroupVector = Liga.getActionManager(actionManagerId)
					.getReshimatKvutzot(machzor - 1);
		} catch (Exception e) {
			lastPlayedGroupVector = new Vector<GroupVO>();
		}
		checkList = new Vector<Boolean>();
		if (groupVector != null) {
			for (int i = 0; i < groupVector.size(); i++) {
				if (lastPlayedGroupVector.contains(groupVector.elementAt(i))) {
					checkList.add(new Boolean(true));
				} else {
					checkList.add(new Boolean(false));
				}
			}
		}
		groupsTableModel = new GroupsTableModel();
		groupsTable.setModel(groupsTableModel);

		groupsTable.getTableHeader().setFont(MasachRashi.FONT);
		groupsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		groupsTable.setFillsViewportHeight(true);
		groupsTable.setAutoCreateRowSorter(true);
		groupsTable.setFont(MasachRashi.FONT);
		groupsTable.getTableHeader().setReorderingAllowed(false);
		groupsTable.setRowHeight(30);

		IntegerTableCellRenderer integerCellRenderer = new IntegerTableCellRenderer();
		integerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		integerCellRenderer.setVerticalAlignment(JLabel.CENTER);

		StringTableCellRenderer stringCellRenderer = new StringTableCellRenderer();
		stringCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		stringCellRenderer.setVerticalAlignment(JLabel.CENTER);

		groupsTable.getColumnModel().getColumn(0).setMinWidth(120);
		groupsTable.getColumnModel().getColumn(0)
				.setCellRenderer(stringCellRenderer);
		groupsTable.getColumnModel().getColumn(1).setMinWidth(120);
		groupsTable.getColumnModel().getColumn(1)
				.setCellRenderer(stringCellRenderer);
		groupsTable.getColumnModel().getColumn(2).setMinWidth(120);
		groupsTable.getColumnModel().getColumn(2)
				.setCellRenderer(stringCellRenderer);

		groupsTable.getColumnModel().getColumn(0).setMinWidth(80);
	}

	class GroupsTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] COLUMN_NAMES = { baitName_COL, ezorName_COL,
				groupName_COL, check_COL };
		private final Class<?>[] COLUMN_TYPES = new Class<?>[] { String.class,
				String.class, String.class, Boolean.class };

		public GroupsTableModel() {
		}

		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		public int getRowCount() {
			if (groupVector == null) {
				return 0;
			} else {
				return groupVector.size();
			}
		}

		public String getColumnName(int col) {
			return COLUMN_NAMES[col];
		}

		public Object getValueAt(final int rowIndex, final int columnIndex) {
			switch (columnIndex) {

			case 0:
				return groupVector.elementAt(rowIndex).getBaitName();
			case 1:
				return groupVector.elementAt(rowIndex).getEzorName();
			case 2:
				return groupVector.elementAt(rowIndex).getGroupName();
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
			if (col == 3) {
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
			}
			fireTableCellUpdated(row, col);
		}
	}

	public void fillBatimTable(int machzor) {
		if (batimVeKvutzot == null) {
			batimVeKvutzot = new EqualMapSet<SiduriShemVO, Vector<GroupVO>>();
		}

		batimTableModel = new BatimTableModel();
		batimTable.setModel(batimTableModel);

		batimTable.getTableHeader().setFont(MasachRashi.FONT);
		batimTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		batimTable.setFillsViewportHeight(true);
		batimTable.setAutoCreateRowSorter(true);
		batimTable.setFont(MasachRashi.FONT);
		batimTable.getTableHeader().setReorderingAllowed(false);
		batimTable.setRowHeight(30);

		StringTableCellRenderer stringCellRenderer = new StringTableCellRenderer();
		stringCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		stringCellRenderer.setVerticalAlignment(JLabel.CENTER);

		TextAreaTableCellRenderer textAreaTableCellRenderer = new TextAreaTableCellRenderer();
		// ScrollPaneTableCellRenderer scrollPaneTableCellRenderer = new
		// ScrollPaneTableCellRenderer();

		batimTable.getColumnModel().getColumn(0).setMinWidth(120);
		batimTable.getColumnModel().getColumn(0)
				.setCellRenderer(textAreaTableCellRenderer);

		batimTable.getColumnModel().getColumn(1).setMinWidth(120);
		batimTable.getColumnModel().getColumn(1)
				.setCellRenderer(stringCellRenderer);
	}

	class BatimTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] COLUMN_NAMES = { groupNames_COL, baitName_COL };
		private final Class<?>[] COLUMN_TYPES = new Class<?>[] { Vector.class,
				String.class };

		public BatimTableModel() {
		}

		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		public int getRowCount() {
			if (batimVeKvutzot == null) {
				return 0;
			} else {
				return batimVeKvutzot.size();
			}
		}

		public String getColumnName(int col) {
			return COLUMN_NAMES[col];
		}

		public Object getValueAt(final int rowIndex, final int columnIndex) {
			SiduriShemVO b = batimVeKvutzot.elementAt(rowIndex);
			Vector<GroupVO> v = batimVeKvutzot.get(b);
			switch (columnIndex) {
			case 0:
				String s = "";
				for (int i = 0; i < v.size(); i++) {
					s = s + v.elementAt(i).getGroupName() + "\n";
				}
				return s;
			case 1:
				return b.getShem();
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
			return false;
		}

		/*
		 * Don't need to implement this method unless your table's data can
		 * change.
		 */
		public void setValueAt(Object value, int row, int col) {
		}
	}

	private void cholelBatim() throws Exception {
		String message = null;
		Vector<GroupVO> groupsChosen = new Vector<GroupVO>();
		if (kamutKvutzotBeBaitField.toInt() == null
				|| kamutKvutzotBeBaitField.toInt() < 2) {
			message = "מספר קבוצות בבית חייב להיות לפחות 2";
		}
		if (message == null
				&& (machzorField.toInt() == null || machzorField.toInt() <= 0)) {
			message = "חובה לציין מספר מחזור";
		}

		if (message == null) {
			// נבדוק כמה קבוצות נבחרו
			for (int i = 0; i < groupVector.size(); i++) {
				if (checkList.elementAt(i)) {
					groupsChosen.add(new GroupVO(groupVector.elementAt(i)));
				}
			}
			if (groupsChosen.size() < 2) {
				message = "חובה לסמן לפחות שתי קבוצות";
			}
		}
		if (message == null) {
			int kamutKvutzotBeBait = kamutKvutzotBeBaitField.toInt().intValue();
			int misparBatim = groupsChosen.size() / kamutKvutzotBeBait;
			if (groupsChosen.size() % kamutKvutzotBeBait != 0) {
				misparBatim++;
			}

			Integer[] indexArray = new Integer[groupsChosen.size()];
			for (int i = 0; i < indexArray.length; i++) {
				indexArray[i] = i;
			}
			ArrayList<Integer> arrayToShuffle = new ArrayList<Integer>(
					Arrays.asList(indexArray));
			Collections.shuffle(arrayToShuffle);

			// EqualMapSet<SiduriShemVO, Vector<GroupVO>> batimVeKvutzotNew
			// =
			// new EqualMapSet<SiduriShemVO, Vector<GroupVO>>();
			GroupVO[][] groupsToBatimArray = new GroupVO[kamutKvutzotBeBait][misparBatim];

			Vector<SiduriShemVO> batim = Liga.getActionManager(actionManagerId)
					.getBatim();

			if (misparBatim > batim.size() || misparBatim > MAX_BATIM) {
				message = "אין אפשרות ליצור כמות זו של בתים נא לבחור יותר קבוצות בבית";
			}

			if (message == null) {
				for (int i = 0; i < kamutKvutzotBeBait; i++) {
					for (int j = 0; j < misparBatim; j++) {
						int n = misparBatim * i + j;
						if (n < groupsChosen.size()) {
							groupsToBatimArray[i][j] = groupsChosen
									.elementAt(arrayToShuffle.get(n));
						} else {
							groupsToBatimArray[i][j] = null;
						}
					}
				}

				batimVeKvutzot = new EqualMapSet<SiduriShemVO, Vector<GroupVO>>();

				for (int i = 0; i < misparBatim; i++) {
					Vector<GroupVO> groupsInBait = new Vector<GroupVO>();
					for (int j = 0; j < kamutKvutzotBeBait; j++) {
						if (groupsToBatimArray[j][i] != null) {
							groupsInBait.add(groupsToBatimArray[j][i]);
						}
					}
					batimVeKvutzot.put(batim.elementAt(i), groupsInBait);
				}
			}
		}
		if (message != null) {
			JOptionPane.showMessageDialog(IrbulLefiBchiraPanel.this, message,
					"action", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void cholelMischakim() throws Exception {
		String message = null;
		if (batimVeKvutzot == null || batimVeKvutzot.size() == 0) {
			message = "אין בתים לערבול";
		}

		if (message == null
				&& (machzorNochechi == null || machzorNochechi <= 0)) {
			message = "חובה לציין מספר מחזור";
		}
		if (message == null
				&& (misparMifgashimField.toInt() == null || misparMifgashimField
						.toInt() <= 0)) {
			message = "חובה לציין מספר גבוה מ 0";
		}

		if (message == null) {
			for (int i = 0; i < batimVeKvutzot.size(); i++) {
				SiduriShemVO bait = batimVeKvutzot.elementAt(i);
				Integer ezor = Liga.EZOR_LIGA;
				Vector<GroupVO> groups = batimVeKvutzot.get(bait);
				Liga.getActionManager(actionManagerId).randomizeGames(groups,
						ezor, bait.getSiduri(), machzorNochechi.intValue(),
						misparMifgashimField.toInt().intValue());
			}
			message = "משחקים הוספו בהצלחה";
		}
		if (message != null) {
			JOptionPane.showMessageDialog(IrbulLefiBchiraPanel.this, message,
					"action", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void mechakMachzorButton() throws Exception {
		String message = null;
		if (machzorNochechi == null || machzorNochechi <= 0) {
			message = "חובה לציין מספר מחזור";
		}
		if (message == null) {
			int confirm = JOptionPane.showConfirmDialog(
					IrbulLefiBchiraPanel.this,
					"הנך עומד/ת לבצע מחיקת מחזור שלם, האם להמשיך את הפעולה");
			if (confirm == 0) {
				Vector<Integer> gameIDs = Liga
						.getActionManager(actionManagerId).getGamesIDs(
								machzorNochechi);

				Liga.getActionManager(actionManagerId).deleteGames(gameIDs);
				message = "מחזור נמחק";
			} else {
				message = "פעולת מחיקת מחזור שלם התבטלה";
			}
		}
		if (message != null) {
			JOptionPane.showMessageDialog(IrbulLefiBchiraPanel.this, message,
					"action", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = null;
		try {
			// טיפול בשינוי במחזור
			if (e.getSource() == machzorField) {
				Integer machzorChadash = machzorField.toInt();
				if (machzorChadash == null
						&& machzorNochechi != null
						|| machzorChadash != null
						&& machzorNochechi == null
						|| machzorChadash != null
						&& machzorNochechi != null
						&& machzorChadash.intValue() != machzorNochechi
								.intValue()) {
					machzorNochechi = machzorChadash;
					reloadGroupsTable = true;
					reloadBatimTable = true;
					EqualMapSet<SiduriShemVO, Vector<GroupVO>> oldBatimVeKvutzot = null;
					if (machzorNochechi != null) {
						oldBatimVeKvutzot = Liga.getActionManager(
								actionManagerId).getBatimVeKvutzotBeMachzor(
								machzorNochechi.intValue());
					}
					if (oldBatimVeKvutzot != null
							&& oldBatimVeKvutzot.size() > 0) {
						batimVeKvutzot = oldBatimVeKvutzot;
						cholelBatimButton.setEnabled(false);
					} else {
						batimVeKvutzot = null;
						cholelBatimButton.setEnabled(true);
					}
				}
			} else if (e.getSource() == simunHakolButton) {
				if (metegSimunHakol == false) {
					metegSimunHakol = true;
				} else {
					metegSimunHakol = false;
				}
				int kamut = checkList.size();
				checkList = new Vector<Boolean>();
				for (int i = 0; i < kamut; i++) {
					checkList.add(new Boolean(metegSimunHakol));
				}
				updateUI();
			} else if (e.getSource() == cholelBatimButton) {
				cholelBatim();
				reloadBatimTable = true;
			} else if (e.getSource() == cholelMischakimButton) {
				cholelMischakim();
			} else if (e.getSource() == mechakMachzorButton) {
				mechakMachzorButton();
				batimVeKvutzot = null;
				cholelBatimButton.setEnabled(true);
				reloadBatimTable = true;
			}

			int machzor = 0;
			if (machzorNochechi != null) {
				machzor = machzorNochechi.intValue();
			}
			if (reloadGroupsTable) {
				reloadGroupsTable = false;
				fillTable(machzor);
				updateUI();
			}

			if (reloadBatimTable) {
				reloadBatimTable = false;
				fillBatimTable(machzor);
				updateUI();
			}
		} catch (Exception e1) {
			message = e1.getMessage();
		}
		if (message != null) {
			JOptionPane.showMessageDialog(IrbulLefiBchiraPanel.this, message,
					"action", JOptionPane.INFORMATION_MESSAGE);
		}
	}

}
