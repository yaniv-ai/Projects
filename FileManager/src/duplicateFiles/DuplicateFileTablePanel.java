package duplicateFiles;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;

import directoryTree.DirectoryCheckBoxTreeBrowse;
import helpers.EqualMapSet;
import helpers.Utils;
import ui.MainWindow;
import ui.MyPanel;
import vo.FileVo;
import vo.MessageVo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class DuplicateFileTablePanel extends MyPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private final String deletedFilesDirectoryName = "\\$DuplicateFileToDelete";

	int moveDeletedFilesTo = 0;

	private JTable table;
	private MyTableModel myTableModel;
	final String drive_COL = "כונן";
	final String directoryPath_COL = "ספרייה";
	final String fileName_COL = "שם קובץ";
	final String extension_COL = "סיומת";
	final String type_COL = "סוג";
	final String fileGroupId_COL = "קבוצה";
	final String length_COL = "גודל";
	final String fileSignature_COL = "חותמת";
	private Vector<Boolean> checkList = null;
	private JScrollPane scrollPane;
	private JButton button = new JButton();

	private Utils utils;// = new
	private static JTextField messageField = null;
	private EqualMapSet<String, FileVo> fileList;
	private static ArrayList<MessageVo> messageList;

	public DuplicateFileTablePanel() throws IOException {
		super();
		messageList = new ArrayList<MessageVo>();
		fileList = new EqualMapSet<String, FileVo>();
		utils = new Utils();

		showPanel();
	}

	public void showPanel() {
		this.removeAll();
		String message = null;
		try {
			fillTable();

			scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setViewportView(table);

			messageField = new JTextField();
			button = new JButton("Choose Directories");
			button.addActionListener(this);
			JPanel lower = new JPanel();
			BoxLayout boxlayout = new BoxLayout(lower, BoxLayout.X_AXIS);
			lower.setLayout(boxlayout);
			lower.add(messageField);
			lower.add(button);

			setLayout(new BorderLayout());
			setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
			scrollPane.setPreferredSize(new Dimension(1100, 600));
			messageField.setPreferredSize(new Dimension(1100, 40));
			add(scrollPane, BorderLayout.CENTER);
			add(lower, BorderLayout.SOUTH);

		} catch (Exception e) {
			message = e.getMessage();
		}

		if (message != null) {
			postMesage("Error in making file table: " + message);
		}
	}

	public void fillTable() throws Exception {
		checkList = new Vector<Boolean>();
		if (fileList != null) {
			for (int i = 0; i < fileList.size(); i++) {
				checkList.add(false);
			}
		}

		myTableModel = new MyTableModel();
		table = new JTable(myTableModel);
		table.getTableHeader().setFont(MainWindow.FONT);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setFillsViewportHeight(true);
		table.setAutoCreateRowSorter(true);
		table.setFont(MainWindow.FONT);
		table.getTableHeader().setReorderingAllowed(false);
		table.setRowHeight(30);

		StringTableCellRenderer stringCellRenderer = new StringTableCellRenderer();
		stringCellRenderer.setHorizontalAlignment(JTextField.RIGHT);
		stringCellRenderer.setVerticalAlignment(JLabel.CENTER);
//		IDTableButtonRenderer buttonRenderer = new IDTableButtonRenderer();
//		buttonRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(0).setCellRenderer(stringCellRenderer);

		table.getColumnModel().getColumn(1).setMinWidth(400);
		table.getColumnModel().getColumn(1).setCellRenderer(stringCellRenderer);

		table.getColumnModel().getColumn(2).setMinWidth(500);
		table.getColumnModel().getColumn(2).setCellRenderer(new IDTableButtonRenderer());

		table.getColumnModel().getColumn(3).setMinWidth(30);
		table.getColumnModel().getColumn(3).setPreferredWidth(30);
		table.getColumnModel().getColumn(3).setCellRenderer(stringCellRenderer);

		table.getColumnModel().getColumn(4).setMinWidth(40);
		table.getColumnModel().getColumn(4).setPreferredWidth(40);
		table.getColumnModel().getColumn(4).setCellRenderer(stringCellRenderer);

		table.getColumnModel().getColumn(5).setMinWidth(30);
		table.getColumnModel().getColumn(5).setPreferredWidth(30);
		table.getColumnModel().getColumn(5).setCellRenderer(stringCellRenderer);

		table.getColumnModel().getColumn(6).setMinWidth(50);
		table.getColumnModel().getColumn(6).setPreferredWidth(40);
		table.getColumnModel().getColumn(6).setCellRenderer(stringCellRenderer);

		table.getColumnModel().getColumn(7).setMinWidth(100);
		table.getColumnModel().getColumn(7).setPreferredWidth(100);
		table.getColumnModel().getColumn(7).setCellRenderer(stringCellRenderer);

		table.addMouseListener(new JTableButtonMouseListener(table));
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

		// מטרת המאזין הבא הוא לשנות את הצבע של הכפתור עליו העכבר מצביע
		table.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				if (row > -1) {
					// easiest way:
					table.clearSelection();
					table.setRowSelectionInterval(row, row);
				} else {
					table.setSelectionBackground(Color.blue);
				}
			}
		});
	}

	public boolean deleteFile(String filenameKey, FileVo fileVo) {
		switch (moveDeletedFilesTo) {
		case 0:
			// this method renames the file to directory that is in:
			// deletedFilesDirectoryName but in the same disc
			// so it is faster and safe
			return deleteFileMoveToDelDir(filenameKey, fileVo, fileVo.getDrive() + deletedFilesDirectoryName);

		case 1:
			// this method renames the file to directory that is in: C:
			// deletedFilesDirectoryName
			// It can be slow because if the file is big and on another disc, it takes time
			// to move
			return deleteFileMoveToDelDir(filenameKey, fileVo, "C:" + deletedFilesDirectoryName);

		// case 2:
		// this method tries to move the file to Recycle Bin
		// return deleteFileMoveToRecycleBin(filenameKey, fileVo, "C:" +
		// deletedFilesDirectoryName);

		default:
			return deleteFileMoveToDelDir(filenameKey, fileVo, fileVo.getDrive() + deletedFilesDirectoryName);
		}
	}

	public boolean deleteFileMoveToDelDir(String filenameKey, FileVo fileVo, String dirName) {
		// Rename מעביר לספריית מחיקות כדי לא למחוק בטעות קובץ. למעשה מבצע Rename
		(new File(dirName + File.separator)).mkdirs();

		String message = null;
		if (filenameKey == null || filenameKey.length() <= 0) {
			message = "לא נבחר שם קובץ";
		}

		if (message == null) {
			try {
				boolean deleted = false;
				File fOld = new File(fileVo.getFullPathName());
				if (fOld.exists()) {
					if (fileVo.getFullDirectory().equalsIgnoreCase(dirName)) {
						postMesage("The file: " + fileVo.getFileName() + " is already in the deleted directory");
					} else {
						File fNew = new File(
								dirName + File.separator + fileVo.getFileName() + "." + fileVo.getExtension());
						if (fNew.exists()) {
							for (int rec = 0; fNew.exists(); rec++) {
								fNew = new File(dirName + File.separator + fileVo.getFileName() + " [" + rec + "]" + "."
										+ fileVo.getExtension());
							}
						}
						deleted = fOld.renameTo(fNew);
						if (deleted) {
							fileList.remove(filenameKey, fileVo);
						}
					}
				} else {
					postMesage(fileVo.getFullPathName() + " does not exist");
				}
				if (deleted) {
					postMesage("קובץ נמחק בהצלחה");
				} else {
					// message = "לא ניתן למחוק את הקובץ" + " - " + filenameKey;
					postMesage("לא ניתן למחוק את הקובץ");
				}
				updatePanel();
				checkTableForResult(fileVo.getFileGroupID());
				updateUI();
			} catch (Exception e) {
				message = e.getMessage();
			}
		}

		if (message == null) {
			return true;
		} else {
			postMesage("Error while trying to delete file: " + filenameKey + ".\n" + message + "\n");
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
//		List<Color> rowColours = Arrays.asList(Color.GREEN, Color.CYAN);

		private String[] COLUMN_NAMES = { drive_COL, directoryPath_COL, fileName_COL, extension_COL, type_COL,
				fileGroupId_COL, length_COL, fileSignature_COL };
		private final Class<?>[] COLUMN_TYPES = new Class<?>[] { String.class, String.class, IDButton.class,
				String.class, String.class, String.class, String.class, String.class };

		public MyTableModel() {
		}

		public int getColumnCount() {
			return COLUMN_NAMES.length;
		}

		public int getRowCount() {
			if (fileList == null) {
				return 0;
			} else {
				return fileList.size();
			}
		}

		public String getColumnName(int col) {
			return COLUMN_NAMES[col];
		}

		public Object getValueAt(final int rowIndex, final int columnIndex) {
			switch (columnIndex) {

			case 0:
				return fileList.getValueAt(rowIndex).getDrive();
			case 1:
				return fileList.getValueAt(rowIndex).getFullDirectory();
			case 2:
				FileVo fileVo = fileList.getValueAt(rowIndex);
				final IDButton button = new IDButton(fileVo.getFileName());
				button.setId(fileVo.getFileGroupID());
				button.setFont(MainWindow.FONT);
				button.setHorizontalAlignment(SwingConstants.LEFT);
//				if (fileVo.getFileGroupID() != null && fileVo.getFileGroupID() % 2 == 0) {
//					button.setBackground(Color.GREEN);
//				} else {
//					button.setBackground(Color.CYAN);
//				}
				button.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						String filenameKey = fileList.getKeyAt(rowIndex);
						FileVo fileVo = fileList.getValueAt(rowIndex);
						if (filenameKey != null) {
							deleteFile(filenameKey, fileVo);
						}
					}
				});

				return button;
			case 3:
				return (fileList.getValueAt(rowIndex)).getExtension();
			case 4:
				return utils.getFileType((fileList.getValueAt(rowIndex)).getExtension());
			case 5:
				return (fileList.getValueAt(rowIndex)).getFileGroupID().toString();
			case 6:
				return (fileList.getValueAt(rowIndex)).getLength().toString();
			case 7:
				return (fileList.getValueAt(rowIndex)).getFileSignature();

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
			return false;
		}

		/*
		 * Don't need to implement this method unless your table's data can change.
		 */
		public void setValueAt(Object value, int row, int col) {
		}

//		public void setRowColour(int row) {
//			if (row % 2 == 0) {
//				rowColours.set(row, Color.GREEN);
//			} else {
//				rowColours.set(row, Color.CYAN);
//
//			}
//			fireTableRowsUpdated(row, row);
//		}
//
//		public Color getRowColour(int row) {
//			return rowColours.get(row);
//		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String message = null;
		Object source = e.getSource();
		try {
			if (source == button) {
				fileList = new DirectoryCheckBoxTreeBrowse().getFileList();
				updatePanel();
			}
		} catch (Exception e1) {
			message = e1.getMessage();
		}
		if (message != null) {
			postMesage(message + "\n");
		}
	}

	private void checkTableForResult(Integer groupID) {
		changeSelection(table, groupID);
		JViewport viewport = (JViewport) table.getParent();
		Rectangle rect = table.getCellRect(table.getSelectedRow(), 0, true);
		Rectangle r2 = viewport.getVisibleRect();
		table.scrollRectToVisible(new Rectangle(rect.x, rect.y, (int) r2.getWidth(), (int) r2.getHeight()));
	}

	private void changeSelection(JTable table, Integer groupID) {
		final int ID_COL = 5;
		Integer groupIDIndex = null;

		if (groupID == null) {
			groupID = -1;
		}
		int idCol = table.convertColumnIndexToView(ID_COL);
		for (int row = table.getRowCount(); --row >= 0;) {
			String tgroupId = (String) table.getValueAt(row, idCol);
			if (tgroupId == null) {
				tgroupId = "-1";
			}
			if (tgroupId.equalsIgnoreCase(groupID.toString())) {
				groupIDIndex = row;
			}
		}
		if (groupIDIndex != null) {
			table.changeSelection(groupIDIndex.intValue(), idCol, false, false);
			return;
		}
		table.clearSelection();
	}

	public static void postMesage(String message) {
		messageList.add(new MessageVo(message));
		messageField.setText(message);
	}
}
