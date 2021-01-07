package helpers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;

public class FileBrowse extends JPanel implements ActionListener {

	private JSpecialField fileName = null;
	private JButton browse = null;
	private JFileChooser fileChooser = null;
	private String currentDirestoryPath = "";

	private static final long serialVersionUID = 1L;

	public FileBrowse(String currentDirestoryPath, int FIELD_TYPE) {
		this.currentDirestoryPath = currentDirestoryPath;
		this.setLayout(new BorderLayout());
		this.setPreferredSize(new Dimension(800, 10));
		fileName = new JSpecialField(FIELD_TYPE, 0);
		browse = new JButton("Browse");
		browse.addActionListener(this);
		this.add(fileName, BorderLayout.CENTER);
		this.add(browse, BorderLayout.EAST);
	}

	public void setFileName(String fileName) {
		this.fileName.setText(fileName);
	}

	public String getFileName() {
		return fileName.getText();
	}

	public void actionPerformed(ActionEvent e) {
		// Set up the file chooser.
		if (fileChooser == null) {
			fileChooser = new JFileChooser(currentDirestoryPath);

			// Add a custom file filter and disable the default
			// (Accept All) file filter.
			fileChooser.addChoosableFileFilter(new MusicFilter());
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			// // Add custom icons for file types.
			// fileChooser.setFileView(new MusicFileView());
			//
			// // Add the preview pane.
			// fileChooser.setAccessory(new ImagePreview(fileChooser));
		}

		// Show it.
		int returnVal = fileChooser.showDialog(FileBrowse.this, "Attach");

		// Process the results.
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			fileName.setText(fileChooser.getSelectedFile().getAbsolutePath());
			fileChooser.setSelectedFile(null);
		}
	}
}
