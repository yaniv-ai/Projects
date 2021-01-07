package ui;

import helpers.EqualMapSet;
import helpers.JSensitiveIntField;
import helpers.JSiduriShemComboBox;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import constraints.IrbulLefiEzorimPanelConstraints;
import dimensions.IrbulLefiEzorimPanelDimensions;

import liga.Liga;
import main.MasachRashi;

import tableHelpers.IntegerTableCellRenderer;
import tableHelpers.StringTableCellRenderer;
import templates.MyPanel;
import vo.GroupVO;
import vo.SiduriShemVO;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class IrbulLefiEzorimPanel extends MyPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JSiduriShemComboBox ezorComboBox, baitComboBox;
	private JSensitiveIntField machzor, misparMifgashim;
	private JButton cholelButton;
	private JTable table = new JTable();
	private MyTableModel myTableModel;
	final String check_COL = "סימון";
	final String groupName_COL = "שם קבוצה";
	final String ezorName_COL = "איזור";
	final String baitName_COL = "בית";
	private Vector<GroupVO> groupVector = null;
	private Vector<Boolean> checkList = null;
	private EqualMapSet<SiduriShemVO, Vector<SiduriShemVO>> ezorimBatim;
	private Integer ezorIDKodem = null;
	private Integer baitIDKodem = null;
	private final String teurBechar = "בחר איזור",
			teurLoHugderu = "לא הוגדרו איזורים",
			teurEinNetunim = "אין נתונים להצגה";
	private JScrollPane scrollPane;
	boolean reloadTable = false;

	private IrbulLefiEzorimPanelConstraints constraints;
	private IrbulLefiEzorimPanelDimensions dimensions;
	private final Integer actionManagerId;

	public IrbulLefiEzorimPanel(Dimension screenDimension,
			Integer actionManagerId) {
		super();
		this.actionManagerId = actionManagerId;
		constraints = new IrbulLefiEzorimPanelConstraints();
		dimensions = new IrbulLefiEzorimPanelDimensions(screenDimension);
	}

	public void showPanel() {
		this.removeAll();
		String message = null;
		try {
			ezorimBatim = Liga.getActionManager(actionManagerId)
					.getEzorimVeBatim();
			Vector<SiduriShemVO> ezorimBaLiga = new Vector<SiduriShemVO>(
					ezorimBatim.keySet());

			ezorComboBox = new JSiduriShemComboBox(ezorimBaLiga, teurBechar,
					teurLoHugderu, teurEinNetunim);
			ezorComboBox.addActionListener(this);
			ezorComboBox.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" איזור: ", TitledBorder.RIGHT, TitledBorder.CENTER,
					MasachRashi.FONT));

			baitComboBox = new JSiduriShemComboBox(null, teurBechar,
					teurLoHugderu, teurEinNetunim);
			baitComboBox.addActionListener(this);
			baitComboBox.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" בית: ", TitledBorder.RIGHT, TitledBorder.CENTER,
					MasachRashi.FONT));

			machzor = new JSensitiveIntField();
			// machzor.addActionListener(this);
			machzor.setLengthLimit(9);
			machzor.setHorizontalAlignment(JTextField.RIGHT);
			machzor.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" מחזור: ", TitledBorder.RIGHT, TitledBorder.CENTER,
					MasachRashi.FONT));

			misparMifgashim = new JSensitiveIntField();
			// misparMifgashim.addActionListener(this);
			misparMifgashim.setLengthLimit(9);
			misparMifgashim.setHorizontalAlignment(JTextField.RIGHT);
			misparMifgashim.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					" מספר מפגשים: ", TitledBorder.RIGHT, TitledBorder.CENTER,
					MasachRashi.FONT));

			cholelButton = new JButton("חולל משחקים");
			cholelButton.addActionListener(this);
			cholelButton.setFont(MasachRashi.FONT);

			if (ezorimBaLiga != null && ezorimBaLiga.size() > 0) {
				ezorComboBox.setSelectedIndex(0);
			}

			scrollPane = new JScrollPane(table,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setViewportView(table);

			setLayout(new GridBagLayout());
			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

			ezorComboBox.setPreferredSize(dimensions.getEzorTextDimension());
			baitComboBox.setPreferredSize(dimensions.getBaitTextDimension());
			machzor.setPreferredSize(dimensions.getMachzorTextDimension());
			misparMifgashim.setPreferredSize(dimensions
					.getMisparMifgashimButtonDimension());
			scrollPane.setPreferredSize(dimensions.getGroupTableDimension());
			cholelButton.setPreferredSize(dimensions
					.getCholelfButtonConstraints());

			add(ezorComboBox, constraints.getEzorTextConstraints());
			add(baitComboBox, constraints.getBaitTextConstraints());
			add(machzor, constraints.getMachzorTextConstraints());
			add(misparMifgashim, constraints.getMisparMifgashimConstraints());
			add(scrollPane, constraints.getGroupTableConstraints());
			add(cholelButton, constraints.getCholelfButtonConstraints());
		} catch (Exception e) {
			message = e.getMessage();
		}

		if (message != null) {
			JOptionPane.showMessageDialog(IrbulLefiEzorimPanel.this, message,
					"Error in making Players table", JOptionPane.ERROR_MESSAGE);
		}
	}

	public void fillTable(Integer ezorID, Integer baitID) {
		try {
			groupVector = Liga.getActionManager(actionManagerId)
					.getReshimatKvutzot(ezorID, baitID);
		} catch (Exception e) {
			groupVector = new Vector<GroupVO>();
		}
		checkList = new Vector<Boolean>();
		if (groupVector != null) {
			for (int i = 0; i < groupVector.size(); i++) {
				checkList.add(new Boolean(true));
			}
		}
		myTableModel = new MyTableModel();
		table.setModel(myTableModel);

		table.getTableHeader().setFont(MasachRashi.FONT);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		table.setFont(MasachRashi.FONT);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(30);

		IntegerTableCellRenderer integerCellRenderer = new IntegerTableCellRenderer();
		integerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		integerCellRenderer.setVerticalAlignment(JLabel.CENTER);

		StringTableCellRenderer stringCellRenderer = new StringTableCellRenderer();
		stringCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		stringCellRenderer.setVerticalAlignment(JLabel.CENTER);

		table.getColumnModel().getColumn(0).setMinWidth(120);
		table.getColumnModel().getColumn(0).setCellRenderer(stringCellRenderer);
		table.getColumnModel().getColumn(1).setMinWidth(120);
		table.getColumnModel().getColumn(1).setCellRenderer(stringCellRenderer);
		table.getColumnModel().getColumn(2).setMinWidth(120);
		table.getColumnModel().getColumn(2).setCellRenderer(stringCellRenderer);

		table.getColumnModel().getColumn(0).setMinWidth(80);
	}

	class MyTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] COLUMN_NAMES = { baitName_COL, ezorName_COL,
				groupName_COL, check_COL };
		private final Class<?>[] COLUMN_TYPES = new Class<?>[] { String.class,
				String.class, String.class, Boolean.class };

		public MyTableModel() {
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

	// public static void main(String[] args) {
	// try {
	// SwingUtilities.invokeLater(new Runnable() {
	// public void run() {
	// try {
	// final JFrame frame = new JFrame();
	//
	// IrbulLefiEzorimPanel ip = new IrbulLefiEzorimPanel(
	// new Dimension(800, 400), null);
	// ip.showPanel();
	// frame.getContentPane().add(ip);
	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// frame.setPreferredSize(new Dimension(500, 500));
	// frame.setMinimumSize(new Dimension(800, 500));
	// frame.pack();
	// frame.setVisible(true);
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }
	// });
	// } catch (Exception e) {
	// System.err.println(e.getMessage());
	// }
	//
	// }

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = null;
		try {
			if (e.getSource() == ezorComboBox) {
				Integer ezorIDNochechi = ezorComboBox.getSelectedSiduri();
				if (!ezorIDNochechi.equals(ezorIDKodem)) {
					reloadTable = true;
					ezorIDKodem = ezorIDNochechi;
					baitIDKodem = null;
					SiduriShemVO ezorNivchar = new SiduriShemVO(
							ezorComboBox.getSelectedSiduri(), null);
					Vector<SiduriShemVO> vector = ezorimBatim.get(ezorNivchar);
					baitComboBox.setAllData(vector);
					if (vector != null && vector.size() > 0) {
						baitComboBox.setSelectedIndex(0);
					}
				}
			}
			if (e.getSource() == baitComboBox) {
				Integer baitIDNochechi = baitComboBox.getSelectedSiduri();
				if (!baitIDNochechi.equals(baitIDKodem)) {
					reloadTable = true;
					baitIDKodem = baitIDNochechi;
				}
			}
			if (reloadTable) {
				reloadTable = false;
				fillTable(ezorIDKodem, baitIDKodem);
				updateUI();
			}
			if (e.getSource() == cholelButton) {
				Vector<GroupVO> kvutzotLeIrbul = new Vector<GroupVO>();
				Integer kamutMifgashimMinimali = null;
				Integer machzorNum = null;
				Integer ezorID = null;
				Integer baitID = null;

				try {
					ezorID = ezorComboBox.getSelectedSiduri();
				} catch (Exception e1) {
				}
				try {
					baitID = baitComboBox.getSelectedSiduri();
				} catch (Exception e1) {
				}

				try {
					machzorNum = Integer.parseInt(machzor.getText());
				} catch (Exception e1) {
				}
				try {
					kamutMifgashimMinimali = Integer.parseInt(misparMifgashim
							.getText());
				} catch (Exception e1) {
				}

				if (message == null && ezorID == null) {
					message = "נא לבחור אזור";
				}
				if (message == null && baitID == null) {
					message = "נא לבחור בית";
				}

				if (message == null && (machzorNum == null || machzorNum <= 0)) {
					message = "נא לבחור מספר מחזור";
				}
				if (message == null
						&& (kamutMifgashimMinimali == null || kamutMifgashimMinimali <= 0)) {
					message = "נא לבחור כמות מפגשים";
				}

				if (message == null) {
					for (int i = 0; i < checkList.size(); i++) {
						if (checkList.elementAt(i).booleanValue()) {
							kvutzotLeIrbul.add(new GroupVO(groupVector
									.elementAt(i)));
						}
					}
					if (kvutzotLeIrbul.size() < 2) {
						message = "חייבים לבחור לפחות שתי קבוצות לערבול";
					}
				}

				if (message == null) {
					try {
						Liga.getActionManager(actionManagerId).randomizeGames(
								kvutzotLeIrbul, ezorID, baitID, machzorNum,
								kamutMifgashimMinimali);
						message = "עדכונים בוצעו בהצלחה";
					} catch (Exception e1) {
						message = e1.getMessage();
					}
				}
			}
		} catch (Exception e1) {
			message = e1.getMessage();
		}
		if (message != null) {
			JOptionPane.showMessageDialog(IrbulLefiEzorimPanel.this, message,
					"action", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
