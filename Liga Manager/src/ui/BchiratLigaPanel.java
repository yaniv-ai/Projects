package ui;

import helpers.IDButton;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;

import constraints.BchiratLigaPanelConstraints;
import dimensions.BchiratLigaPanelDimensions;

import tableHelpers.IDTableButtonRenderer;
import tableHelpers.JTableButtonMouseListener;
import tableHelpers.StringTableCellRenderer;
import tableHelpers.StringTableFieldEditor;
import templates.MyPanel;
import vo.SiduriShemVO;

import liga.Liga;
import liga.LigaDB;
import main.MasachRashi;

import enums.KodShinuyDB;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * TableRenderDemo is just like TableDemo, except that it explicitly initializes
 * column sizes and it uses a combo box as an editor for the Sport column.
 */
public class BchiratLigaPanel extends MyPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JLabel koteret = null;
	private JTable table;
	private MyTableModel myTableModel;
	private JButton deleteButton = null;
	final String ligaName_COL = "שם ליגה";
	final String ligaId_COL = "מספר ליגה";
	final String sugLiga_COL = "סוג ליגה";
	final String check_COL = "סימון";
	private Vector<SiduriShemVO> ligaVector = null;
	private Vector<Boolean> checkList = null;
	private JScrollPane scrollPane;
	private Vector<Integer> ligotList = null;

	private BchiratLigaPanelConstraints constraints;
	private BchiratLigaPanelDimensions dimensions;
	private final Integer actionManagerId;
	private MasachRashi masachRashi;

	public BchiratLigaPanel(Dimension screenDimension, Integer actionManagerId, MasachRashi masachRashi) {
		super();
		this.actionManagerId = actionManagerId;
		constraints = new BchiratLigaPanelConstraints();
		dimensions = new BchiratLigaPanelDimensions(screenDimension);
		this.masachRashi = masachRashi;
	}

	public void showPanel() {
		this.removeAll();
		String message = null;
		try {
			fillTable();
			koteret = new JLabel("פאנל הוספת ליגה", SwingConstants.CENTER);
			koteret.setBorder(
					BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true), "",
							TitledBorder.CENTER, TitledBorder.CENTER, MasachRashi.FONT));

			// בניית לחצן מחיקת שחקנים מסומנים
			deleteButton = new JButton("מחק ליגות מסומנות");
			deleteButton.addActionListener(this);
			deleteButton.setFont(MasachRashi.FONT);

			scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setViewportView(table);

			setLayout(new GridBagLayout());
			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

			koteret.setPreferredSize(dimensions.getKoteretDimension());
			deleteButton.setPreferredSize(dimensions.getMechakButtonDimension());
			scrollPane.setPreferredSize(dimensions.getLigotTableDimension());

			add(koteret, constraints.getKoteretConstraints());
			add(deleteButton, constraints.getMechakButtonConstraints());
			add(scrollPane, constraints.getLigotTableConstraints());

		} catch (Exception e) {
			message = e.getMessage();
		}

		if (message != null) {
			JOptionPane.showMessageDialog(BchiratLigaPanel.this, message, "Error in making Liga table",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public void fillTable() throws Exception {
		LigaDB.kraLigot();
		ligaVector = Liga.getActionManager(actionManagerId).getLigot();
		ligaVector.add(new SiduriShemVO(null, " "));

		checkList = new Vector<Boolean>();
		if (ligaVector != null) {
			for (int i = 0; i < ligaVector.size(); i++) {
				checkList.add(new Boolean(false));
			}
		}

		myTableModel = new MyTableModel();
		table = new JTable(myTableModel);
		table.getTableHeader().setFont(MasachRashi.FONT);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		table.setFont(MasachRashi.FONT);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(30);

		StringTableCellRenderer stringCellRenderer = new StringTableCellRenderer();
		stringCellRenderer.setHorizontalAlignment(JTextField.RIGHT);
		stringCellRenderer.setVerticalAlignment(JLabel.CENTER);
		StringTableFieldEditor stringCellEditor = new StringTableFieldEditor();

		table.getColumnModel().getColumn(0).setMinWidth(500);
		table.getColumnModel().getColumn(0).setCellRenderer(stringCellRenderer);
		table.getColumn(table.getColumnName(0)).setCellEditor(stringCellEditor);

		table.getColumnModel().getColumn(1).setMinWidth(80);
		table.getColumnModel().getColumn(1).setCellRenderer(new IDTableButtonRenderer());

		table.getColumnModel().getColumn(2).setMinWidth(80);

		table.addMouseListener(new JTableButtonMouseListener(table));
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	public boolean addUpdateLiga(SiduriShemVO liga, KodShinuyDB kodShinuyDB) {
		String message = null;
		if (liga == null) {
			message = "שדה ליגה לא אותחל";
		}
		if (message == null && kodShinuyDB.equals(KodShinuyDB.idkun) && liga.getSiduri() == null) {
			message = "ליגה לא קיימת במערכת, נא לפנות לתמיכה";
		}
		if (message == null
				&& (liga.getShem() == null || liga.getShem().isEmpty() || (liga.getShem().trim()).isEmpty())) {
			message = "עליך למלא שדה שם ליגה";
		}

		if (message == null) {
			try {
				if (kodShinuyDB.equals(KodShinuyDB.idkun)) {
					Liga.getActionManager(actionManagerId).updateLiga(liga);
					MasachRashi.publishMessage("ליגה עודכנה בהצלחה");
				} else {
					Liga.getActionManager(actionManagerId).hosefLiga(liga);
					MasachRashi.publishMessage("ליגה הוספה בהצלחה");
				}
				updatePanel();
				checkTableForResult(liga.getSiduri());
				updateUI();
			} catch (Exception e) {
				message = e.getMessage();
			}
		}

		if (message == null) {
			return true;
		} else {
			JOptionPane.showMessageDialog(BchiratLigaPanel.this, message, "Player addition failed",
					JOptionPane.ERROR_MESSAGE);
		}
		return false;
	}

	private void updatePanel() throws Exception {
		this.remove(table);
		fillTable();
		scrollPane.setViewportView(table);
	}

	class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] COLUMN_NAMES = { ligaName_COL, ligaId_COL, check_COL };
		private final Class<?>[] COLUMN_TYPES = new Class<?>[] { String.class, IDButton.class, Boolean.class };

		public MyTableModel() {
		}

		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		public int getRowCount() {
			if (ligaVector == null) {
				return 0;
			} else {
				return ligaVector.size();
			}
		}

		public String getColumnName(int col) {
			return COLUMN_NAMES[col];
		}

		public Object getValueAt(final int rowIndex, final int columnIndex) {
			SiduriShemVO liga = null;
			switch (columnIndex) {

			case 0:
				return ligaVector.elementAt(rowIndex).getShem();
			case 1:
				String buttonName;
				liga = ligaVector.elementAt(rowIndex);
				if (liga.getSiduri() != null) {
					buttonName = liga.getSiduri().toString();
				} else {
					buttonName = "הקמת ליגה חדשה";
				}
				final IDButton button = new IDButton(buttonName);
				button.setId(liga.getSiduri());
				button.setFont(MasachRashi.FONT);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						SiduriShemVO liga = ligaVector.elementAt(rowIndex);
						if (liga.getSiduri() != null) {
							masachRashi.setNewLiga(liga.getSiduri(), liga.getShem());
						} else {
							if (addUpdateLiga(liga, KodShinuyDB.hosafa)) {
								masachRashi.setNewLiga(liga.getSiduri(), liga.getShem());
							}
						}
					}
				});

				return button;
			case 2:
				liga = ligaVector.elementAt(rowIndex);
				if (liga.getSiduri() != null) {
					return checkList.elementAt(rowIndex);
				} else {
					return null;
				}
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
			if (col == 0 || col == 2) {
				return true;
			} else {
				return false;
			}
		}

		/*
		 * Don't need to implement this method unless your table's data can change.
		 */
		public void setValueAt(Object value, int row, int col) {
			if (col == 2) {
				checkList.remove(row);
				checkList.add(row, (Boolean) value);
			} else {
				boolean changed = false;
				SiduriShemVO liga = new SiduriShemVO(ligaVector.elementAt(row));
				switch (col) {
				case 0:
					String newName = (String) value;
					if (!Liga.stringFieldAreEqual(liga.getShem(), newName)) {
						liga.setShem(newName);
						changed = true;
						if (liga.getSiduri() == null) {
							ligaVector.elementAt(row).setShem(newName);
						}
					}
					break;
				default:
					break;
				}
				if (changed) {
					if (liga.getSiduri() != null) {
						addUpdateLiga(liga, KodShinuyDB.idkun);
					}
				}
			}
			fireTableCellUpdated(row, col);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = null;
		Object source = e.getSource();
		try {
			if (source == deleteButton) {
				ligotList = new Vector<Integer>();
				for (int i = 0; i < checkList.size(); i++) {
					if (checkList.elementAt(i)) {
						ligotList.add(ligaVector.elementAt(i).getSiduri());
					}
				}
				if (ligotList.size() > 0) {
					int confirm = JOptionPane.showConfirmDialog(BchiratLigaPanel.this,
							"הנך עומד/ת לבצע מחיקת ליגות, האם להמשיך את הפעולה");
					if (confirm == 0) {
						Liga.getActionManager(actionManagerId).mechakLigot(ligotList);
						updatePanel();
						updateUI();
						message = "ליגות נמחקו בהצלחה";
					} else {
						message = "פעולת מחיקת ליגות התבטלה";
					}
				} else {
					message = "לא נבחרו ליגות למחיקה";
				}
			}
		} catch (Exception e1) {
			message = e1.getMessage();
		}
		if (message != null) {
			JOptionPane.showMessageDialog(BchiratLigaPanel.this, message, "action", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void checkTableForResult(Integer ligaId) {
		changeSelection(table, ligaId);
		JViewport viewport = (JViewport) table.getParent();
		Rectangle rect = table.getCellRect(table.getSelectedRow(), 0, true);
		Rectangle r2 = viewport.getVisibleRect();
		table.scrollRectToVisible(new Rectangle(rect.x, rect.y, (int) r2.getWidth(), (int) r2.getHeight()));
	}

	private void changeSelection(JTable table, Integer ligaId) {
		final int ID_COL = 1;
		Integer ligaIdIndex = null;

		if (ligaId == null) {
			ligaId = 0;
		}
		int idCol = table.convertColumnIndexToView(ID_COL);
		for (int row = table.getRowCount(); --row >= 0;) {
			IDButton b = (IDButton) table.getValueAt(row, idCol);
			Integer tLigaId = b.getId();
			if (tLigaId == null) {
				tLigaId = 0;
			}
			if (tLigaId.intValue() == ligaId.intValue()) {
				ligaIdIndex = row;
			}
		}
		if (ligaIdIndex != null) {
			table.changeSelection(ligaIdIndex.intValue(), idCol, false, false);
			return;
		}
		table.clearSelection();
	}
}
