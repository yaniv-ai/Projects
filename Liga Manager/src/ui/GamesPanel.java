package ui;

import helpers.EqualMapSet;
import helpers.GameAdder;
import helpers.GameButton;
import helpers.JSiduriShemComboBox;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import constraints.GamePanelConstraints;
import dialogs.DatePicker;
import dimensions.GamePanelDimensions;

import tableHelpers.GameButtonTableCellEditor;
import tableHelpers.GameButtonTableRenderer;
import tableHelpers.IntegerTableCellRenderer;
import tableHelpers.IntegerTableFieldEditor;
import tableHelpers.JTableButtonMouseListener;
import tableHelpers.StringTableCellRenderer;
import tableHelpers.StringTableFieldEditor;
import templates.MyPanel;
import vo.SiduriShemVO;
import vo.GameListVO;
import vo.GameVO;
import vo.GroupVO;

import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Vector;

import liga.Liga;
import main.MasachRashi;

/**
 * TableRenderDemo is just like TableDemo, except that it explicitly initializes
 * column sizes and it uses a combo box as an editor for the Sport column.
 */
public class GamesPanel extends MyPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JTable gamesTable;
	private GameTableModel gamesTableModel;
	private JLabel koteret;
	private JButton simunHakolButton = null, deleteButton = null;
	private final String gameID_COL = "משחק מס.";
	private final String machzor_COL = "מח'";
	private final String sevev_COL = "סבב";
	private final String homeGroupName_COL = "קבוצת בית";
	private final String vsGroupName_COL = "קבוצה יריבה";
	private final String homeGroupGoals_COL = "גולים מקומית";
	private final String visitorsGroupGoals_COL = "גולים יריבה";
	private final String gameDate_COL = "תאריך המשחק";
	private final String bait_COL = "בית";
	private final String region_COL = "איזור";
	private final String check_COL = "סימון";
	private Vector<GameListVO> gamesVector = null;
	private Vector<Boolean> checkList = null;
	private Vector<GroupVO> groupVector = null;
	private JScrollPane scrollPane;
	private boolean simunHakol = false;
	private GameAdder gameAdder;
	private Vector<Integer> gameList = null;
	private final String hafsaka = "ללא קבוצה";

	private EqualMapSet<SiduriShemVO, Vector<SiduriShemVO>> ezorimBatim;
	private JSiduriShemComboBox ezorComboBox, baitComboBox;
	private Integer ezorIDKodem = null;
	private final String teurBechar = "בחר איזור",
			teurLoHugderu = "לא הוגדרו איזורים",
			teurEinNetunim = "אין נתונים להצגה";

	private GamePanelConstraints constraints;
	private GamePanelDimensions dimensions;
	private final Integer actionManagerId;

	public GamesPanel(Dimension screenDimension, Integer actionManagerId) {
		super();
		this.actionManagerId = actionManagerId;
		constraints = new GamePanelConstraints();
		dimensions = new GamePanelDimensions(screenDimension);
	}

	public void showPanel() {
		this.removeAll();
		String message = null;

		koteret = new JLabel("פאנל משחקים", SwingConstants.CENTER);
		koteret.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true), "",
				TitledBorder.CENTER, TitledBorder.CENTER, MasachRashi.FONT));

		// delete button
		deleteButton = new JButton("מחק משחקים מסומנים");
		deleteButton.addActionListener(this);
		deleteButton.setFont(MasachRashi.FONT);

		// delete button
		simunHakolButton = new JButton("סימון כל המשחקים");
		simunHakolButton.addActionListener(this);
		simunHakolButton.setFont(MasachRashi.FONT);

		try {
			fillTables();

			scrollPane = new JScrollPane(gamesTable,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setViewportView(gamesTable);
			scrollPane
					.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

			setLayout(new GridBagLayout());
			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

			groupVector = Liga.getActionManager(actionManagerId)
					.getReshimatKvutzot(true);

			gameAdder = new GameAdder(copyOf(groupVector));
			gameAdder.addActionListener(this);

			koteret.setPreferredSize(dimensions.getKoteretDimension());

			// מימדי שדות פאנל ההוספה

			gameAdder.getMachzor().setPreferredSize(
					dimensions.getMachzorDimension());
			gameAdder.getIpusDate().setPreferredSize(
					dimensions.getIpusDateDimension());
			gameAdder.getChooseDate().setPreferredSize(
					dimensions.getChooseDateDimension());
			gameAdder.getGameDateLabel().setPreferredSize(
					dimensions.getGameDateLabelDimension());
			gameAdder.getHomeGroupName().setPreferredSize(
					dimensions.getHomeGroupNameDimension());
			gameAdder.getVisitorsGroupName().setPreferredSize(
					dimensions.getVisitorsGroupNameDimension());
			gameAdder.getHomeScore().setPreferredSize(
					dimensions.getHomeScoreDimension());
			gameAdder.getVisitorsScore().setPreferredSize(
					dimensions.getVisitorsScoreDimension());
			JScrollPane homeGroupScroll = new JScrollPane(
					gameAdder.getHomeGroupTable());
			homeGroupScroll.setPreferredSize(dimensions
					.getHomeGroupTableDimension());
			JScrollPane visitorsGroupScroll = new JScrollPane(
					gameAdder.getVisitorsGroupTable());
			visitorsGroupScroll.setPreferredSize(dimensions
					.getVisitorsGroupTableDimension());
			gameAdder.getIpusButton().setPreferredSize(
					dimensions.getIpusButtonDimension());
			gameAdder.getHosefButton().setPreferredSize(
					dimensions.getHosefButtonDimension());

			// מימדי שדות פאנל הטבלה
			simunHakolButton.setPreferredSize(dimensions
					.getSimunHakolButtonDimension());
			deleteButton
					.setPreferredSize(dimensions.getMechakButtonDimension());
			scrollPane.setPreferredSize(dimensions.getGameTableDimension());

			add(koteret, constraints.getKoteretConstraints());

			// מילוי הגבלות פאנל ההוספה

			add(gameAdder.getMachzor(), constraints.getMachzorConstraints());
			add(gameAdder.getIpusDate(), constraints.getIpusDateConstraints());
			add(gameAdder.getChooseDate(),
					constraints.getChooseDateConstraints());
			add(gameAdder.getGameDateLabel(),
					constraints.getGameDateLabelConstraints());
			add(gameAdder.getHomeGroupName(),
					constraints.getHomeGroupNameConstraints());
			add(gameAdder.getVisitorsGroupName(),
					constraints.getVisitorsGroupNameConstraints());
			add(gameAdder.getHomeScore(), constraints.getHomeScoreConstraints());
			add(gameAdder.getVisitorsScore(),
					constraints.getVisitorsScoreConstraints());
			add(homeGroupScroll, constraints.getHomeGroupTableConstraints());
			add(visitorsGroupScroll,
					constraints.getVisitorsGroupTableConstraints());
			add(gameAdder.getIpusButton(),
					constraints.getIpusButtonConstraints());
			add(gameAdder.getHosefButton(),
					constraints.getHosefButtonConstraints());

			// מילוי הגבלות פאנל הטבלה
			add(simunHakolButton, constraints.getSimunHakolButtonConstraints());
			add(deleteButton, constraints.getMechakButtonConstraints());
			add(scrollPane, constraints.getGameTableConstraints());

		} catch (Exception e) {
			message = e.getMessage();
		}

		if (message != null) {
			JOptionPane.showMessageDialog(GamesPanel.this, message,
					"Error in making Groups table", JOptionPane.ERROR_MESSAGE);
		}

	}

	private Vector<GroupVO> copyOf(Vector<GroupVO> vector) {
		Vector<GroupVO> newVector = null;
		if (vector != null) {
			newVector = new Vector<GroupVO>();
			Iterator<GroupVO> i = vector.iterator();
			while (i.hasNext()) {
				GroupVO g = i.next();
				if (g != null) {
					newVector.add(g);
				}
			}
		}
		return newVector;
	}

	public void fillTables() throws Exception {

		ezorimBatim = Liga.getActionManager(actionManagerId).getEzorimVeBatim();
		Vector<SiduriShemVO> ezorimBaLiga = new Vector<SiduriShemVO>(
				ezorimBatim.keySet());

		ezorComboBox = new JSiduriShemComboBox(ezorimBaLiga, teurBechar,
				teurLoHugderu, teurEinNetunim);
		ezorComboBox.addActionListener(this);
		ezorComboBox.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				" איזור: ", TitledBorder.RIGHT, TitledBorder.CENTER,
				MasachRashi.FONT));

		baitComboBox = new JSiduriShemComboBox(null, teurBechar, teurLoHugderu,
				teurEinNetunim);
		baitComboBox.addActionListener(this);
		baitComboBox.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
				" בית: ", TitledBorder.RIGHT, TitledBorder.CENTER,
				MasachRashi.FONT));

		if (ezorimBaLiga != null && ezorimBaLiga.size() > 0) {
			ezorComboBox.setSelectedIndex(0);
		}

		IntegerTableCellRenderer integerCellRenderer = new IntegerTableCellRenderer();
		integerCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		integerCellRenderer.setVerticalAlignment(JLabel.CENTER);
		IntegerTableFieldEditor integerCellEditor = new IntegerTableFieldEditor();

		StringTableCellRenderer stringCellRenderer = new StringTableCellRenderer();
		stringCellRenderer.setHorizontalAlignment(JLabel.CENTER);
		stringCellRenderer.setVerticalAlignment(JLabel.CENTER);
		StringTableFieldEditor stringTableFieldEditor = new StringTableFieldEditor();

		gamesVector = Liga.getActionManager(actionManagerId).getGames();
		checkList = new Vector<Boolean>();
		if (gamesVector != null) {
			for (int i = 0; i < gamesVector.size(); i++) {
				checkList.add(new Boolean(false));
			}
		}

		gamesTableModel = new GameTableModel();
		gamesTable = new JTable(gamesTableModel);
		gamesTable.getTableHeader().setFont(MasachRashi.FONT);
		gamesTable.getTableHeader().setReorderingAllowed(false);
		// gamesTable.getTableHeader().setComponentOrientation(
		// ComponentOrientation.RIGHT_TO_LEFT);
		gamesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		gamesTable.setFillsViewportHeight(true);
		gamesTable.setAutoCreateRowSorter(true);
		gamesTable.setFont(MasachRashi.FONT);
		gamesTable.setRowHeight(50);

		TableCellRenderer buttonRenderer = new GameButtonTableRenderer();
		gamesTable.getColumnModel().getColumn(0).setMinWidth(120);
		gamesTable.getColumnModel().getColumn(0)
				.setCellRenderer(buttonRenderer);
		gamesTable.getColumn(gameDate_COL).setCellEditor(
				new GameButtonTableCellEditor());

		gamesTable.getColumnModel().getColumn(1).setMinWidth(55);
		gamesTable.getColumnModel().getColumn(1)
				.setCellRenderer(stringCellRenderer);

		gamesTable.getColumnModel().getColumn(2).setMinWidth(60);
		gamesTable.getColumnModel().getColumn(2)
				.setCellRenderer(stringCellRenderer);

		gamesTable.getColumnModel().getColumn(3).setMinWidth(55);
		gamesTable.getColumnModel().getColumn(3)
				.setCellRenderer(integerCellRenderer);
		gamesTable.getColumn(visitorsGroupGoals_COL).setCellEditor(
				integerCellEditor);

		gamesTable.getColumnModel().getColumn(4).setMinWidth(150);
		gamesTable.getColumnModel().getColumn(4)
				.setCellRenderer(stringCellRenderer);
		gamesTable.getColumnModel().getColumn(4)
				.setCellEditor(stringTableFieldEditor);

		gamesTable.getColumnModel().getColumn(5).setMinWidth(55);
		gamesTable.getColumnModel().getColumn(5)
				.setCellRenderer(integerCellRenderer);
		gamesTable.getColumn(homeGroupGoals_COL).setCellEditor(
				integerCellEditor);

		gamesTable.getColumnModel().getColumn(6).setMinWidth(150);
		gamesTable.getColumnModel().getColumn(6)
				.setCellRenderer(stringCellRenderer);
		gamesTable.getColumnModel().getColumn(6)
				.setCellEditor(stringTableFieldEditor);

		gamesTable.getColumnModel().getColumn(7).setMinWidth(25);
		gamesTable.getColumnModel().getColumn(7)
				.setCellRenderer(integerCellRenderer);

		gamesTable.getColumnModel().getColumn(8).setMinWidth(25);
		gamesTable.getColumnModel().getColumn(8)
				.setCellRenderer(integerCellRenderer);

		gamesTable.getColumnModel().getColumn(9).setMinWidth(40);
		gamesTable.getColumn(gameID_COL).setCellRenderer(integerCellRenderer);

		gamesTable.getColumnModel().getColumn(10).setMinWidth(60);

		gamesTable.addMouseListener(new JTableButtonMouseListener(gamesTable));

		gamesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	}

	public void tipulGame(GameVO game) throws Exception {
		String message = null;
		if (game != null) {
			if (message == null && game.getMekomitID() == null
					&& game.getYerivaID() == null) {
				message = "עליך לבחור לפחות קבוצה אחת";
			}
			if (game.getMekomitNekudot() == null) {
				game.setMekomitNekudot(0);
			}
			if (game.getYerivaNekudot() == null) {
				game.setYerivaNekudot(0);
			}
			if (message == null
					&& (game.getMachzor() == null || game.getMachzor() <= 0)) {
				message = "עליך לציין מחזור";
			}
			if (message == null
					&& (game.getMekomitNekudot() > 0 || game.getYerivaNekudot() > 0)
					&& game.getGameDate() == null) {
				Calendar c = Calendar.getInstance();
				game.setGameDate(c);
			}

			if (message == null) {
				int ezorID = Liga.getActionManager(actionManagerId).getEzor(
						game.getMekomitID(), game.getYerivaID());
				if (game.getEzorID() == null) {
					game.setEzorID(ezorID);
				}

				int baitID = Liga.getActionManager(actionManagerId).getBait(
						game.getMekomitID(), game.getYerivaID());
				if (game.getBaitID() == null) {
					game.setBaitID(baitID);
				}

				try {
					if (game.getGameID() == null) {
						Liga.getActionManager(actionManagerId).addGame(game);
						updatePanel();
					} else {
						Liga.getActionManager(actionManagerId).updateGame(game);
					}
					gameAdder.resetFields();
				} catch (Exception e) {
					message = e.getMessage();
				}
			}

			if (message == null) {
				message = "משחק הוסף/עודכן בהצלחה";
			}
			MasachRashi.publishMessage(message);
		}
	}

	private void updatePanel() throws Exception {
		this.remove(gamesTable);
		fillTables();
		scrollPane.setViewportView(gamesTable);
		scrollPane.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	}

	class GameTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] COLUMN_NAMES = { gameDate_COL, bait_COL, region_COL,
				visitorsGroupGoals_COL, vsGroupName_COL, homeGroupGoals_COL,
				homeGroupName_COL, sevev_COL, machzor_COL, gameID_COL,
				check_COL };
		private final Class<?>[] COLUMN_TYPES = new Class<?>[] {
				GameButton.class, String.class, String.class, Integer.class,
				String.class, Integer.class, String.class, Integer.class,
				Integer.class, Integer.class, Boolean.class };

		public GameTableModel() {
		}

		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		public int getRowCount() {
			if (gamesVector == null) {
				return 0;
			} else {
				return gamesVector.size();
			}
		}

		public String getColumnName(int col) {
			return COLUMN_NAMES[col];
		}

		public Object getValueAt(final int rowIndex, final int columnIndex) {
			switch (columnIndex) {

			case 0:
				String calString;
				try {
					Calendar cal = gamesVector.elementAt(rowIndex)
							.getGameDate();
					calString = Liga.calendarToString(cal);
				} catch (Exception e) {
					calString = null;
				}

				final GameButton button = new GameButton(calString);
				button.setGame(gamesVector.elementAt(rowIndex));
				button.setFont(MasachRashi.FONT);
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						GameVO game = button.getGame();
						Calendar newCal = new DatePicker(GamesPanel.this)
								.getPickedDate();
						Calendar oldCal = game.getGameDate();
						if (!Liga.calendarFieldAreEqual(newCal, oldCal)) {
							game.setGameDate(newCal);
							try {
								tipulGame(game);
								button.setText(Liga.calendarToString(game
										.getGameDate()));
								updateUI();
							} catch (Exception e) {
								game.setGameDate(oldCal);
								JOptionPane.showMessageDialog(GamesPanel.this,
										e.getMessage(), "game addition failed",
										JOptionPane.ERROR_MESSAGE);
							}
						}
					}
				});
				if (gamesVector.elementAt(rowIndex).getMekomitID() == null
						&& gamesVector.elementAt(rowIndex).getYerivaID() == null) {
					button.setEnabled(false);
				} else {
					button.setEnabled(true);
				}
				return button;
			case 1:
				return gamesVector.elementAt(rowIndex).getBaitShem();
			case 2:
				return gamesVector.elementAt(rowIndex).getEzorShem();
			case 3:
				return gamesVector.elementAt(rowIndex).getYerivaNekudot();
			case 4:
				if (gamesVector.elementAt(rowIndex).getYerivaShem() == null) {
					return hafsaka;
				}
				return gamesVector.elementAt(rowIndex).getYerivaShem();
			case 5:
				return gamesVector.elementAt(rowIndex).getMekomitNekudot();
			case 6:
				if (gamesVector.elementAt(rowIndex).getMekomitShem() == null) {
					return hafsaka;
				}
				return gamesVector.elementAt(rowIndex).getMekomitShem();
			case 7:
				return gamesVector.elementAt(rowIndex).getSevev();
			case 8:
				return gamesVector.elementAt(rowIndex).getMachzor();
			case 9:
				return gamesVector.elementAt(rowIndex).getGameID();
			case 10:
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
			if (gamesVector.elementAt(row).getMekomitID() == null
					&& gamesVector.elementAt(row).getYerivaID() == null) {
				return false;
			} else if (col == 3 || col == 4 || col == 6 || col == 5 || col == 0
					|| col == 10) {
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
			if (col == 10) {
				checkList.remove(row);
				checkList.add(row, (Boolean) value);
			} else {
				boolean changed = false;
				GameListVO game = gamesVector.elementAt(row);
				Integer oldYerivaNekudot = null;
				Integer oldMekomitNekudot = null;
				String newShem = null;
				try {
					oldYerivaNekudot = game.getYerivaNekudot().intValue();
				} catch (Exception e) {
				}
				try {
					oldMekomitNekudot = game.getMekomitNekudot().intValue();
				} catch (Exception e) {
				}

				switch (col) {
				case 3:
					Integer newYerivaNekudot = (Integer) value;
					if (!Liga.integerFieldAreEqual(oldYerivaNekudot,
							newYerivaNekudot)) {
						game.setYerivaNekudot(newYerivaNekudot);
						changed = true;
					}
					break;
				case 5:
					Integer newMekomitNekudot = (Integer) value;
					if (!Liga.integerFieldAreEqual(oldMekomitNekudot,
							newMekomitNekudot)) {
						game.setMekomitNekudot(newMekomitNekudot);
						changed = true;
					}
					break;
				case 4:
					newShem = (String) value;
					if ((newShem == null || newShem.length() == 0)
							&& !Liga.stringFieldAreEqual(newShem, gamesVector
									.elementAt(row).getYerivaShem())) {
						game.setYerivaID(null);
						game.setYerivaShem(null);
						changed = true;
					}
					break;
				case 6:
					newShem = (String) value;
					if ((newShem == null || newShem.length() == 0)
							&& !Liga.stringFieldAreEqual(newShem, gamesVector
									.elementAt(row).getMekomitShem())) {
						game.setMekomitID(null);
						game.setMekomitShem(null);
						changed = true;
					}
					break;
				default:
					break;
				}
				if (changed) {
					try {
						tipulGame(game);
						updateUI();
					} catch (Exception e) {
						game.setYerivaNekudot(oldYerivaNekudot);
						game.setMekomitNekudot(oldMekomitNekudot);
						JOptionPane.showMessageDialog(GamesPanel.this,
								e.getMessage(), "game addition failed",
								JOptionPane.ERROR_MESSAGE);
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
			if (source == gameAdder) {
				GameVO game = gameAdder.getGame();
				tipulGame(game);
				updateUI();
			} else if (source == simunHakolButton) {
				if (simunHakol == false) {
					simunHakol = true;
				} else {
					simunHakol = false;
				}
				int kamut = checkList.size();
				checkList = new Vector<Boolean>();
				for (int i = 0; i < kamut; i++) {
					checkList.add(new Boolean(simunHakol));
				}
				updateUI();
			} else if (source == deleteButton) {
				gameList = new Vector<Integer>();
				getGameListFromData(gamesVector, checkList);

				if (gameList.size() > 0) {
					int confirm = JOptionPane
							.showConfirmDialog(GamesPanel.this,
									"הנך עומד/ת לבצע מחיקת משחקים, האם להמשיך את הפעולה");
					if (confirm == 0) {
						Liga.getActionManager(actionManagerId).deleteGames(
								gameList);
						updatePanel();
						updateUI();
						message = "משחקים נמחקו בהצלחה";
					} else {
						message = "פעולת מחיקת משחקים התבטלה";
					}
				} else {
					message = "לא נבחרו משחקים למחיקה";
				}
			}
			if (source == ezorComboBox) {
				Integer ezorIDNochechi = ezorComboBox.getSelectedSiduri();
				if (!ezorIDNochechi.equals(ezorIDKodem)) {
					ezorIDKodem = ezorIDNochechi;
					SiduriShemVO ezorNivchar = new SiduriShemVO(
							ezorComboBox.getSelectedSiduri(),
							ezorComboBox.getSelectedShem());
					Vector<SiduriShemVO> vector = ezorimBatim.get(ezorNivchar);
					baitComboBox.setAllData(vector);
					if (vector != null && vector.size() > 0) {
						baitComboBox.setSelectedIndex(0);
					}
					updateUI();
				}
			}
		} catch (Exception e1) {
			message = e1.getMessage();
		}
		if (message != null) {
			JOptionPane.showMessageDialog(GamesPanel.this, message, "action",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	private void getGameListFromData(Vector<GameListVO> games,
			Vector<Boolean> checks) {
		if (games != null) {
			for (int i = 0; i < games.size(); i++) {
				if (checks.elementAt(i)) {
					;
					gameList.add(games.elementAt(i).getGameID());
				}
			}
		}
	}

	// private void checkTableForResult(Integer gameID) {
	// final int GAME_COL = 8;
	// changeSelection(gamesTable, gameID);
	// JViewport viewport = (JViewport) gamesTable.getParent();
	// Rectangle rect = gamesTable.getCellRect(gamesTable.getSelectedRow(),
	// GAME_COL, true);
	// Rectangle r1 = viewport.getVisibleRect();
	// gamesTable.scrollRectToVisible(new Rectangle(rect.x, rect.y, (int) r1
	// .getWidth(), (int) r1.getHeight()));
	// changeSelection(gamesTable, gameID);
	// JViewport viewport2 = (JViewport) gamesTable.getParent();
	// Rectangle rect2 = gamesTable.getCellRect(gamesTable.getSelectedRow(),
	// GAME_COL, true);
	// Rectangle r2 = viewport2.getVisibleRect();
	// gamesTable.scrollRectToVisible(new Rectangle(rect2.x, rect2.y, (int) r2
	// .getWidth(), (int) r2.getHeight()));
	// }
	//
	// private void changeSelection(JTable table, Integer gameID) {
	// final int GAME_COL = 8;
	// int gameCol = table.convertColumnIndexToView(GAME_COL);
	// if (gameID != null) {
	// for (int row = table.getRowCount(); --row >= 0;) {
	// Integer tGameID;
	// try {
	// tGameID = Integer.parseInt(((JButton) table.getValueAt(row,
	// gameCol)).getText());
	// } catch (Exception e) {
	// tGameID = null;
	// }
	// if (tGameID == null) {
	// tGameID = 0;
	// }
	// if (tGameID.intValue() == gameID.intValue()) {
	// table.changeSelection(row, gameCol, false, false);
	// return;
	// }
	// }
	// }
	// table.clearSelection();
	// }
}
