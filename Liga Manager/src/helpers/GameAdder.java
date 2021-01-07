package helpers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import dialogs.DatePicker;

import liga.Liga;
import main.MasachRashi;

import tableHelpers.IDTableButtonRenderer;
import tableHelpers.IDTableCellEditor;
import tableHelpers.JTableButtonMouseListener;
import templates.MyPanel;
import vo.GameVO;
import vo.GroupVO;

public class GameAdder extends MyPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private static final String ADD_BUTTON_PRESSED = "addButtonPressed";
	private JLabel koteret;
	private JSensitiveIntField machzor;
	private JButton ipusDate, chooseDate;
	private JTextField gameDateLabel;
	private Calendar gameDateCal;
	private JTextField homeGroupName, visitorsGroupName;
	private Integer homeGroupID = null, visitorsGroupID = null;
	private JSensitiveIntField homeScore, visitorsScore;
	private final String homeGroupActionCommand = "homeGroupActionCommand",
			visitorsGroupActionCommand = "visitorsGroupActionCommand";
	private JTable homeGroupTable, visitorsGroupTable;
	private JButton hosefButton, ipusButton;

	final String groupName_COL = "קבוצה";

	private final Vector<GroupVO> groupVector;

	public GameAdder(Vector<GroupVO> groupVector) {
		this.groupVector = groupVector;

		koteret = new JLabel("פאנל הוספת משחקים", SwingConstants.CENTER);
		koteret.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true), "",
				TitledBorder.CENTER, TitledBorder.CENTER, MasachRashi.FONT));

		machzor = new JSensitiveIntField();
		machzor.addActionListener(this);
		machzor.setLengthLimit(9);
		machzor.setHorizontalAlignment(JTextField.RIGHT);
		machzor.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				" מחזור: ", TitledBorder.RIGHT, TitledBorder.CENTER,
				MasachRashi.FONT));

		ipusDate = new JButton("אפס תאריך");
		ipusDate.addActionListener(this);
		ipusDate.setFont(MasachRashi.FONT);

		chooseDate = new JButton("בחר תאריך");
		chooseDate.addActionListener(this);
		chooseDate.setFont(MasachRashi.FONT);

		gameDateLabel = new JTextField(20);
		gameDateLabel.setEditable(false);
		gameDateLabel.setHorizontalAlignment(JTextField.RIGHT);
		gameDateLabel.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				" תאריך המשחק: ", TitledBorder.RIGHT, TitledBorder.CENTER,
				MasachRashi.FONT));

		homeGroupName = new JTextField(20);
		homeGroupName.setEditable(false);
		homeGroupName.setHorizontalAlignment(JTextField.RIGHT);
		homeGroupName.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				" שם קבוצת בית: ", TitledBorder.RIGHT, TitledBorder.CENTER,
				MasachRashi.FONT));

		visitorsGroupName = new JTextField(20);
		visitorsGroupName.setEditable(false);
		visitorsGroupName.setHorizontalAlignment(JTextField.RIGHT);
		visitorsGroupName.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				" שם קבוצה אורחת: ", TitledBorder.RIGHT, TitledBorder.CENTER,
				MasachRashi.FONT));

		homeScore = new JSensitiveIntField();
		homeScore.addActionListener(this);
		homeScore.setLengthLimit(9);
		homeScore.setHorizontalAlignment(JTextField.RIGHT);
		homeScore.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				" גולים קבוצת בית: ", TitledBorder.RIGHT, TitledBorder.CENTER,
				MasachRashi.FONT));

		visitorsScore = new JSensitiveIntField();
		visitorsScore.addActionListener(this);
		visitorsScore.setLengthLimit(9);
		visitorsScore.setHorizontalAlignment(JTextField.RIGHT);
		visitorsScore.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				" גולים קבוצה אורחת: ", TitledBorder.RIGHT,
				TitledBorder.CENTER, MasachRashi.FONT));

		homeGroupTable = fillGroupTable(homeGroupActionCommand);
		visitorsGroupTable = fillGroupTable(visitorsGroupActionCommand);

		ipusButton = new JButton("איפוס שדות");
		ipusButton.addActionListener(this);
		ipusButton.setFont(MasachRashi.BIGFONT);
		ipusButton.setBackground(Color.pink);

		hosefButton = new JButton("הוסף משחק");
		hosefButton.addActionListener(this);
		hosefButton.setFont(MasachRashi.BIGFONT);
		hosefButton.setBackground(Color.GREEN);

		chooseDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameDateCal = new DatePicker(new JPanel()).getPickedDate();
				String calString;
				try {
					calString = Liga.calendarToString(gameDateCal);
				} catch (Exception e) {
					calString = null;
				}
				gameDateLabel.setText(calString);
			}
		});
		ipusDate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameDateCal = null;
				gameDateLabel.setText(null);
			}
		});
		ipusButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				gameDateCal = null;
				gameDateLabel.setText(null);
				machzor.setText(null);
				homeGroupID = null;
				homeGroupName.setText(null);
				visitorsGroupID = null;
				visitorsGroupName.setText(null);
				homeScore.setText(null);
				visitorsScore.setText(null);
			}
		});

	}

	private JTable fillGroupTable(String actionCommand) {

		GroupTableModel groupTableModel = new GroupTableModel(actionCommand);
		JTable groupTable = new JTable(groupTableModel);
		groupTable.getTableHeader().setFont(MasachRashi.FONT);
		groupTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		groupTable.setFillsViewportHeight(true);
		groupTable.setAutoCreateRowSorter(true);
		groupTable.setFont(MasachRashi.FONT);
		groupTable.getTableHeader().setReorderingAllowed(false);
		groupTable.setRowHeight(MasachRashi.BUTTON_DIMENSION.height);
		DefaultTableCellRenderer midRenderer = new DefaultTableCellRenderer();
		midRenderer.setHorizontalAlignment(JLabel.RIGHT);
		groupTable.getColumnModel().getColumn(0)
				.setMinWidth(MasachRashi.TEXT_FIELD_DIMENSION.width);
		TableCellRenderer buttonRenderer = new IDTableButtonRenderer();
		groupTable.getColumn(groupName_COL).setCellRenderer(buttonRenderer);
		groupTable.addMouseListener(new JTableButtonMouseListener(groupTable));
		groupTable.getColumn(groupName_COL).setCellEditor(
				new IDTableCellEditor());
		return groupTable;
	}

	public void resetFields() {
		machzor.setText(null);
		gameDateCal = null;
		gameDateLabel.setText(null);
		homeGroupName.setText(null);
		visitorsGroupName.setText(null);
		homeGroupID = null;
		visitorsGroupID = null;
		homeScore.setText(null);
		visitorsScore.setText(null);
	}

	public GameVO getGame() {
		Integer homeScoreTemp = null, visitorsScoreTemp = null, machzorTemp = null;

		try {
			homeScoreTemp = Integer.parseInt(homeScore.getText());
		} catch (Exception ex) {

		}
		try {
			visitorsScoreTemp = Integer.parseInt(visitorsScore.getText());
		} catch (Exception ex) {

		}
		try {
			machzorTemp = Integer.parseInt(machzor.getText());
		} catch (Exception ex) {

		}

		return new GameVO(null, homeGroupID, homeScoreTemp, visitorsGroupID,
				visitorsScoreTemp, null, null, machzorTemp, 0, gameDateCal);
	}

	class GroupTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] COLUMN_NAMES = { groupName_COL };
		private final Class<?>[] COLUMN_TYPES = new Class<?>[] { IDButton.class };

		private final String actionCommand;

		public GroupTableModel(String actionCommand) {
			this.actionCommand = actionCommand;
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
				final IDButton button = new IDButton(groupVector.elementAt(
						rowIndex).getGroupName());
				button.setId(groupVector.elementAt(rowIndex).getGroupID());
				button.setFont(MasachRashi.FONT);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						if (actionCommand.equals(homeGroupActionCommand)) {
							homeGroupID = button.getId();
							homeGroupName.setText(button.getText());
						} else if (actionCommand
								.equals(visitorsGroupActionCommand)) {
							visitorsGroupID = button.getId();
							visitorsGroupName.setText(button.getText());
						}

					}
				});
				return button;

			default:
				return "Error";
			}
		}

		public Class<?> getColumnClass(int columnIndex) {
			return COLUMN_TYPES[columnIndex];
		}

		public boolean isCellEditable(int row, int col) {
			return true;
		}

		public void setValueAt(Object value, int row, int col) {
			fireTableCellUpdated(row, col);
		}

	}

	public JLabel getKoteret() {
		return koteret;
	}

	public JSensitiveIntField getMachzor() {
		return machzor;
	}

	public JButton getIpusDate() {
		return ipusDate;
	}

	public JButton getChooseDate() {
		return chooseDate;
	}

	public JTextField getGameDateLabel() {
		return gameDateLabel;
	}

	public Calendar getGameDateCal() {
		return gameDateCal;
	}

	public JTextField getHomeGroupName() {
		return homeGroupName;
	}

	public JTextField getVisitorsGroupName() {
		return visitorsGroupName;
	}

	public Integer getHomeGroupID() {
		return homeGroupID;
	}

	public Integer getVisitorsGroupID() {
		return visitorsGroupID;
	}

	public JSensitiveIntField getHomeScore() {
		return homeScore;
	}

	public JSensitiveIntField getVisitorsScore() {
		return visitorsScore;
	}

	public JTable getHomeGroupTable() {
		return homeGroupTable;
	}

	public JTable getVisitorsGroupTable() {
		return visitorsGroupTable;
	}

	public JButton getHosefButton() {
		return hosefButton;
	}

	public JButton getIpusButton() {
		return ipusButton;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == hosefButton) {
			ActionListener[] al = getActionListeners();
			for (ActionListener a : al) {
				a.actionPerformed(new ActionEvent(this, e.getID(),
						GameAdder.ADD_BUTTON_PRESSED));
			}
		}
	}

	/**
	 * Adds the specified action listener to receive action events from this
	 * gameAdder.
	 * 
	 * @param l
	 *            the action listener to be added
	 */
	public synchronized void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	/**
	 * Removes the specified action listener so that it no longer receives
	 * action events from this gameAdder.
	 * 
	 * @param l
	 *            the action listener to be removed
	 */
	public synchronized void removeActionListener(ActionListener l) {
		listenerList.remove(ActionListener.class, l);
	}

	/**
	 * Returns an array of all the <code>ActionListener</code>s added to this
	 * JTextField with addActionListener().
	 * 
	 * @return all of the <code>ActionListener</code>s added or an empty array
	 *         if no listeners have been added
	 * @since 1.4
	 */
	public synchronized ActionListener[] getActionListeners() {
		return listenerList.getListeners(ActionListener.class);
	}

}