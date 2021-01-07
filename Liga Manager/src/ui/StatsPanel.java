package ui;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import constraints.StatsPanelConstraints;
import dimensions.StatsPanelDimensions;

import templates.MyPanel;
import vo.StatsVO;

import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import liga.Liga;
import main.MasachRashi;

/**
 * TableRenderDemo is just like TableDemo, except that it explicitly initializes
 * column sizes and it uses a combo box as an editor for the Sport column.
 */
public class StatsPanel extends MyPanel {

	private static final long serialVersionUID = 1L;

	private JLabel koteret;
	final String groupName_COL = "שם קבוצה";
	final String region_COL = "איזור";
	final String bait_COL = "בית";
	final String nekudotZchut_COL = "נקודות זכות";
	final String nekudotChova_COL = "נקודות חובה";
	final String hefreshNekudot_COL = "הפרש נקודות";
	final String wins_COL = "נצחונות";
	final String losses_COL = "הפסדים";
	final String draws_COL = "תיקו";
	final String nikud_COL = "ניקוד סופי";
	final String check_COL = "סימון";

	private Vector<JTable> leagueTavlaotVector = null;

	private StatsPanelConstraints constraints;
	private StatsPanelDimensions dimensions;

	private final Integer actionManagerId;

	public StatsPanel(Dimension screenDimension, Integer actionManagerId) {
		super();
		this.actionManagerId = actionManagerId;
		constraints = new StatsPanelConstraints();
		dimensions = new StatsPanelDimensions(screenDimension);
	}

	public void showPanel() {
		this.removeAll();
		String message = null;

		koteret = new JLabel("פאנל סטטיסטיקות", SwingConstants.CENTER);
		koteret.setBorder(BorderFactory.createTitledBorder(
				BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true), "",
				TitledBorder.CENTER, TitledBorder.CENTER, MasachRashi.FONT));

		try {
			Vector<Vector<StatsVO>> leagueStatsVector = Liga.getActionManager(
					actionManagerId).getGameStats();

			leagueTavlaotVector = new Vector<JTable>();

			for (int i = 0; leagueStatsVector != null
					&& i < leagueStatsVector.size(); i++) {
				leagueTavlaotVector.add(fillTable(leagueStatsVector
						.elementAt(i)));
			}

			MiniTableCellRenderer cellRenderer = new MiniTableCellRenderer();

			SuperTableModel leagueTableModel = new SuperTableModel(
					leagueTavlaotVector);
			JTable leagueTable = new JTable(leagueTableModel);
			leagueTable.getColumnModel().getColumn(0)
					.setCellRenderer(cellRenderer);

			JScrollPane leagueScrollPane = new JScrollPane(leagueTable,
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			leagueScrollPane.setViewportView(leagueTable);

			setLayout(new GridBagLayout());
			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

			koteret.setPreferredSize(dimensions.getKoteretDimension());
			leagueScrollPane.setPreferredSize(dimensions.getTableDimension());

			add(koteret, constraints.getKoteretConstraints());
			add(leagueScrollPane, constraints.getTableConstraints());

		} catch (Exception e) {
			message = e.getMessage();
		}

		if (message != null) {
			JOptionPane.showMessageDialog(StatsPanel.this, message,
					"Error in making Goals table", JOptionPane.ERROR_MESSAGE);
		}

	}

	public JTable fillTable(Vector<StatsVO> statGroup) {
		MiniTableModel tableModel = new MiniTableModel(statGroup);
		JTable table = new JTable(tableModel);

		table.getTableHeader().setFont(MasachRashi.FONT);
		table.getTableHeader().setReorderingAllowed(false);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(false); // if it is true, it spreads
												// endlessly
		table.setAutoCreateRowSorter(true);
		table.setFont(MasachRashi.FONT);
		table.setRowHeight(30);
		table.setEnabled(false);

		DefaultTableCellRenderer midRenderer = new DefaultTableCellRenderer();
		midRenderer.setHorizontalAlignment(JLabel.CENTER);
		table.getColumnModel().getColumn(0).setMinWidth(80);
		table.getColumnModel().getColumn(0).setCellRenderer(midRenderer);
		table.getColumnModel().getColumn(1).setMinWidth(80);
		table.getColumnModel().getColumn(1).setCellRenderer(midRenderer);
		table.getColumnModel().getColumn(2).setMinWidth(80);
		table.getColumnModel().getColumn(2).setCellRenderer(midRenderer);
		table.getColumnModel().getColumn(3).setMinWidth(80);
		table.getColumnModel().getColumn(3).setCellRenderer(midRenderer);
		table.getColumnModel().getColumn(4).setMinWidth(80);
		table.getColumnModel().getColumn(4).setCellRenderer(midRenderer);
		table.getColumnModel().getColumn(5).setMinWidth(80);
		table.getColumnModel().getColumn(5).setCellRenderer(midRenderer);
		table.getColumnModel().getColumn(6).setMinWidth(80);
		table.getColumnModel().getColumn(6).setCellRenderer(midRenderer);
		table.getColumnModel().getColumn(7).setMinWidth(100);
		table.getColumnModel().getColumn(7).setCellRenderer(midRenderer);
		table.getColumnModel().getColumn(8).setMinWidth(100);
		table.getColumnModel().getColumn(8).setCellRenderer(midRenderer);
		table.getColumnModel().getColumn(9).setMinWidth(200);
		table.getColumnModel().getColumn(9).setCellRenderer(midRenderer);
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		// table.addMouseListener(new JTableButtonMouseListener(table));

		return table;
	}

	class SuperTableModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] COLUMN_NAMES = { "לוח תוצאות" };
		private final Class<?>[] COLUMN_TYPES = new Class<?>[] { JPanel.class };

		private Vector<JTable> tables;

		public SuperTableModel(Vector<JTable> tables) {
			this.tables = tables;
		}

		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		public int getRowCount() {
			if (tables == null) {
				return 0;
			} else {
				return tables.size();
			}
		}

		public String getColumnName(int col) {
			return COLUMN_NAMES[col];
		}

		public Object getValueAt(final int rowIndex, final int columnIndex) {
			switch (columnIndex) {
			case 0:
				return tables.elementAt(rowIndex);
			default:
				return "Error";
			}
		}

		public Class<?> getColumnClass(int columnIndex) {
			return COLUMN_TYPES[columnIndex];
		}

		public boolean isCellEditable(int row, int col) {
			return false;
		}

		public Vector<JTable> getTables() {
			return tables;
		}

		public void setTables(Vector<JTable> tables) {
			this.tables = tables;
		}

	}

	class MiniTableModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		private String[] COLUMN_NAMES = { nikud_COL, losses_COL, draws_COL,
				wins_COL, hefreshNekudot_COL, nekudotChova_COL,
				nekudotZchut_COL, bait_COL, region_COL, groupName_COL };

		private final Class<?>[] COLUMN_TYPES = new Class<?>[] { Integer.class,
				Integer.class, Integer.class, Integer.class, Integer.class,
				Integer.class, Integer.class, String.class, String.class,
				String.class };

		private final Vector<StatsVO> stats;

		public MiniTableModel(Vector<StatsVO> stats) {
			this.stats = stats;
		}

		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		public int getRowCount() {
			if (stats == null) {
				return 0;
			} else {
				return stats.size();
			}
		}

		public String getColumnName(int col) {
			return COLUMN_NAMES[col];
		}

		public Object getValueAt(final int rowIndex, final int columnIndex) {
			switch (columnIndex) {
			case 0:
				return stats.elementAt(rowIndex).getNikudKolel();
			case 1:
				return stats.elementAt(rowIndex).getLosses();
			case 2:
				return stats.elementAt(rowIndex).getDraws();
			case 3:
				return stats.elementAt(rowIndex).getWins();
			case 4:
				return stats.elementAt(rowIndex).getHefreshNikud();
			case 5:
				return stats.elementAt(rowIndex).getGolimChova();
			case 6:
				return stats.elementAt(rowIndex).getGolimZchut();
			case 7:
				return stats.elementAt(rowIndex).getBaitShem();
			case 8:
				return stats.elementAt(rowIndex).getEzorShem();
			case 9:
				return stats.elementAt(rowIndex).getGroupName();
			default:
				return "Error";
			}
		}

		public Class<?> getColumnClass(int columnIndex) {
			return COLUMN_TYPES[columnIndex];
		}

		public boolean isCellEditable(int row, int col) {
			return false;
		}

		public Vector<StatsVO> getStats() {
			return stats;
		}

	}

	public class MiniTableCellRenderer extends JPanel implements
			TableCellRenderer {
		private static final long serialVersionUID = 1L;
		private List<List<Integer>> rowColHeight = new ArrayList<List<Integer>>();

		public MiniTableCellRenderer() {
			setOpaque(true);
		}

		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			if (isSelected) {
				setForeground(table.getSelectionForeground());
				setBackground(table.getSelectionBackground());
			} else {
				setForeground(table.getForeground());
				setBackground(table.getBackground());
			}
			JTable tab = null;
			JScrollPane scroll = null;
			if (value != null) {
				removeAll();
				tab = (JTable) value;
				scroll = new JScrollPane(tab,
						JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
						JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
				scroll.setPreferredSize(new Dimension(
						tab.getPreferredSize().width, tab.getSize().height
								+ tab.getTableHeader().getHeight() + 4));
				JPanel p = new JPanel();
				p.add(scroll);
				add(p);
			} else {

			}
			MiniTableModel mini = (MiniTableModel) tab.getModel();
			StatsVO stat = mini.getStats().elementAt(0);
			String koteretTavla = "  איזור: " + stat.getEzorShem() + ",  בית: "
					+ stat.getBaitShem() + ",  מחזור: " + stat.getMachzor();

			setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
					koteretTavla, TitledBorder.RIGHT, TitledBorder.CENTER,
					new Font("Dialog", Font.PLAIN, 14)));

			adjustRowHeight(table, row, column);
			return this;
		}

		private void adjustRowHeight(JTable table, int row, int column) {
			int cWidth = table.getTableHeader().getColumnModel()
					.getColumn(column).getWidth();
			setSize(new Dimension(cWidth, 1500));
			int prefH = getPreferredSize().height;
			while (rowColHeight.size() <= row) {
				rowColHeight.add(new ArrayList<Integer>(column));
			}
			List<Integer> colHeights = rowColHeight.get(row);
			while (colHeights.size() <= column) {
				colHeights.add(0);
			}
			colHeights.set(column, prefH);
			int maxH = prefH;
			for (Integer colHeight : colHeights) {
				if (colHeight > maxH) {
					maxH = colHeight;
				}
			}
			if (table.getRowHeight(row) != maxH) {
				table.setRowHeight(row, maxH);
			}
		}
	}
}
