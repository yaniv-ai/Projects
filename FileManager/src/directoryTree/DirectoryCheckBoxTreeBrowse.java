package directoryTree;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import duplicateFiles.DuplicateFileTablePanel;
import helpers.EqualMapSet;
import helpers.FileMetaDataExporter;
import vo.FileVo;

public class DirectoryCheckBoxTreeBrowse {

	private JDialog d;
	private DirectoryCheckBoxTree directoryCheckBoxTree = null;
	private EqualMapSet<String, FileVo> fileList;

	public DirectoryCheckBoxTreeBrowse() {
		directoryCheckBoxTree = new DirectoryCheckBoxTree();
		JButton ishur = new JButton("אישור");
		ishur.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				FileMetaDataExporter dataExporter = new FileMetaDataExporter(false, false, 0, false);
				try {
					dataExporter.exportFileList(null, directoryCheckBoxTree.getSelectedDirs());
				} catch (Exception e1) {
					DuplicateFileTablePanel.postMesage(e1.getMessage());
				}

				EqualMapSet<String, FileVo> rawFileList = sortByValue(dataExporter.getFileList());
				fileList = groupByLengthAndSignature(rawFileList, true);
				d.dispose();
			}
		});
		JPanel bp = new JPanel(new GridLayout(1, 2));
		bp.add(new JLabel(" Enter here to choose a directory ===> "));
		bp.add(ishur);

		JPanel all = new JPanel(new BorderLayout());
		all.add(directoryCheckBoxTree, BorderLayout.CENTER);
		all.add(bp, BorderLayout.SOUTH);

		d = new JDialog();
		d.setModal(true);
		d.add(all);
		d.setSize(new Dimension(400, 400));
		d.setResizable(false);
		d.pack();
		d.setTitle("בחירת קובץ יעד לשמירה");

		GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		int x = rect.x;
		int y = rect.y;
		d.setLocation(x + 400, y + 200);
		d.setVisible(true);
	}

	public EqualMapSet<String, FileVo> sortByValue(EqualMapSet<String, FileVo> fileList) {
		if (fileList == null) {
			return null;
		}
		int n = fileList.size();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < (n - i - 1); j++) {
				if (fileList.getValueAt(j).compareTo(fileList.getValueAt(j + 1)) < 0) {
					fileList.swapElementsPosition(j, j + 1);
				}

			}
		}
		return fileList;
	}

	public EqualMapSet<String, FileVo> sortByValueReverse(EqualMapSet<String, FileVo> fileList) {
		if (fileList == null) {
			return null;
		}
		int n = fileList.size();
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < (n - i - 1); j++) {
				if (fileList.getValueAt(j).compareTo(fileList.getValueAt(j + 1)) > 0) {
					fileList.swapElementsPosition(j, j + 1);
				}

			}
		}
		return fileList;
	}

	public EqualMapSet<String, FileVo> getFileList() {
		return fileList;
	}

	private EqualMapSet<String, FileVo> groupByLengthAndSignature(EqualMapSet<String, FileVo> fileList,
			boolean removeSingles) {
		EqualMapSet<String, FileVo> newFileList = new EqualMapSet<String, FileVo>();
		int n = 1;
		boolean lastID = false;
		boolean nextID = false;
		for (int i = 0; i < fileList.size() - 1; i++) {
			nextID = false;
			if ((fileList.getValueAt(i).getLength()).compareTo(fileList.getValueAt(i + 1).getLength()) == 0) {
				if ((fileList.getValueAt(i).getFileSignature()).equals(fileList.getValueAt(i + 1).getFileSignature())) {
					nextID = true;
				}
			}

			if (lastID || nextID) {
				fileList.getValueAt(i).setFileGroupID(n);
				newFileList.put(fileList.getKeyAt(i), fileList.getValueAt(i));
				if (!nextID) {
					n++;
				}
			}
			lastID = nextID;
			nextID = false;
		}
		if (fileList.size() > 0 && lastID) {
			int i = fileList.size() - 1;
			fileList.getValueAt(fileList.size() - 1).setFileGroupID(n);
			newFileList.put(fileList.getKeyAt(i), fileList.getValueAt(i));
		}
		return newFileList;
	}
}
