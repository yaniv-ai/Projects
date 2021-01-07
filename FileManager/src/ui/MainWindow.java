package ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import directoryTree.DirectoryCheckBoxTree;
import duplicateFiles.DuplicateFileTablePanel;
import helpers.FileMetaDataExporter;
import helpers.JSpecialField;

public class MainWindow extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;

	public static final Font FONT = new Font("Dialog", Font.PLAIN, 14);
	public static final Font BIGFONT = new Font("Dialog", Font.BOLD, 18);
	public static final Dimension TEXT_FIELD_DIMENSION = new Dimension(200, 50);
	public static final Dimension COMBO_BOX_DIMENSION = new Dimension(200, 70);
	public static final Dimension BUTTON_DIMENSION = new Dimension(200, 30);
	public static final int HEIGHT_INSET = 30;
	public static final int WIDTH_INSET = 50;

	// Tab1
	private final String tab1 = "Export Files";
	private final String tab1Comment = "Export Files";
	private DirectoryCheckBoxTree directoryCheckBoxTree = null;
	private JButton exportButton = null;
	private JSpecialField databaseName = null;
	private JRadioButton toFileRadioButton = null;
	private JRadioButton toExcelRadioButton = null;
	private boolean toExcelDB = false;
	// End of Tab1

	// Tab2
	private final String tab2 = "Duplicate Files";
	private final String tab2Comment = "Duplicate Files";
	private DuplicateFileTablePanel duplicateFileTablePanel = null;
	// End of Tab2

	private static JTextArea messageArea = new JTextArea();

	public MainWindow() {
		super(new GridLayout(1, 1));
		JTabbedPane tabbedPane1 = makeTab();

		// setPreferredSize(new Dimension(1100, 700));

		// Add the tabbed pane to this panel.
		add(tabbedPane1);

	}

	private JTabbedPane makeTab() {
		// Tab 1
		// ------
		// 1.
		JPanel upperPanel = new JPanel(new GridLayout(3, 2));
		JLabel dbLabel = new JLabel("Database Filename: ");
		databaseName = new JSpecialField(JSpecialField.LATIN_LETTERS_ONLY_FIELD, 20);
		upperPanel.add(dbLabel);
		upperPanel.add(databaseName);

		// 2.
		directoryCheckBoxTree = new DirectoryCheckBoxTree();

		// 3.
		toFileRadioButton = new JRadioButton("Write to file");
		toFileRadioButton.setActionCommand("Writetofile");
		toFileRadioButton.setSelected(true);
		toFileRadioButton.addActionListener(this);

		toExcelRadioButton = new JRadioButton("Write to Excel");
		toExcelRadioButton.setActionCommand("WritetoExcel");
		toExcelRadioButton.addActionListener(this);

		upperPanel.add(toFileRadioButton);
		upperPanel.add(toExcelRadioButton);

		ButtonGroup group1 = new ButtonGroup();
		group1.add(toFileRadioButton);
		group1.add(toExcelRadioButton);

		// 5.
		exportButton = new JButton("Export files to Excel");
		exportButton.setActionCommand("Export Files");
		exportButton.addActionListener(this);
		JLabel buttonLabel = new JLabel("Push to export");
		upperPanel.add(buttonLabel);
		upperPanel.add(exportButton);

		// 6.
		JScrollPane s = new JScrollPane();
		s.getViewport().add(messageArea);
		add(s, BorderLayout.CENTER);
		s.setPreferredSize(new Dimension(300, 300));

		JPanel p1 = new JPanel(new BorderLayout());
		p1.add(upperPanel, BorderLayout.NORTH);
		p1.add(directoryCheckBoxTree, BorderLayout.CENTER);
		p1.add(s, BorderLayout.SOUTH);
		p1.setPreferredSize(new Dimension(500, 700));
		// End of tab 1

		// Tab 2
		// ------
		JPanel p2 = null;
		try {
			duplicateFileTablePanel = new DuplicateFileTablePanel();
			// Tab 2
			p2 = new JPanel(new BorderLayout());
			p2.add(duplicateFileTablePanel, BorderLayout.CENTER);

		} catch (IOException e) {
			postMesage("cannot add duplicate panel" + e.getMessage());
			p2 = null;
		}

		// TabbedPane
		JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab(tab1, null, p1, tab1Comment);
		tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

		if (p2 != null) {
			tabbedPane.addTab(tab2, null, p2, tab2Comment);
			tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);
			tabbedPane.setSelectedIndex(1);
		}
		// The following line enables to use scrolling tabs.
		tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

		return tabbedPane;
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == exportButton) {
			if (directoryCheckBoxTree != null) {
				FileMetaDataExporter dataExporter = new FileMetaDataExporter(toExcelDB, false, 100, true);
				try {
					dataExporter.exportFileList(databaseName.getText(), directoryCheckBoxTree.getSelectedDirs());
				} catch (Exception e1) {
					MainWindow.postMesage(e1.getMessage());
				}
			}
		}
		if (source == toFileRadioButton) {
			toExcelDB = false;
		}
		if (source == toExcelRadioButton) {
			toExcelDB = true;
		}

	}

	/**
	 * Create the GUI and show it. For thread safety, this method should be invoked
	 * from the event dispatch thread.
	 */
	private static void createAndShowGUI() {
		// Create and set up the window.
		JFrame frame = new JFrame("TabbedPaneDemo");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Add content to the window.
		frame.add(new MainWindow(), BorderLayout.CENTER);

		// Display the window.
		frame.pack();
		frame.setVisible(true);
	}

	public static void postMesage(String mesage) {
		messageArea.setText(messageArea.getText() + "\n" + mesage);
	}

	public static void main(String[] args) {
		// Schedule a job for the event dispatch thread:
		// creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				// Turn off metal's use of bold fonts
				UIManager.put("swing.boldMetal", Boolean.FALSE);
				createAndShowGUI();
			}
		});
	}
}
