package helpers;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

import main.MasachRashi;

public class XLSFileBrowse {

	private JTextField filename;
	private JFileChooser fileChooser;
	private JDialog d;
	private XLSFilter xlsFilter;

	public XLSFileBrowse(Component component, String directoryPath) {
		xlsFilter = new XLSFilter();
		filename = new JTextField(directoryPath, 30);
		filename.setMinimumSize(new Dimension(200, 30));
		filename.setEditable(false);
		JButton ishur = new JButton("אישור");
		ishur.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				d.dispose();
			}
		});
		JButton browse = new JButton("Browse");
		browse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (fileChooser == null) {
					fileChooser = new JFileChooser(filename.getText());
					fileChooser.setFont(MasachRashi.FONT);

					// Add a custom file filter and disable the default
					// (Accept All) file filter.
					fileChooser.setAcceptAllFileFilterUsed(false);
					fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
					fileChooser.addChoosableFileFilter(xlsFilter);
				}

				// Show it.
				int returnVal = fileChooser.showDialog(d, "Attach");

				// Process the results.
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					String newFilename = fileChooser.getSelectedFile()
							.getAbsolutePath();
					if (!XLSFilter.validExtension(newFilename)) {
						newFilename = XLSFilter.addExtension(newFilename);
					}
					filename.setText(newFilename);
					fileChooser.setSelectedFile(null);
				}
			}
		});
		JPanel bp = new JPanel(new GridLayout(1, 2));
		bp.add(browse);
		bp.add(ishur);

		JPanel all = new JPanel(new BorderLayout());
		all.add(filename, BorderLayout.CENTER);
		all.add(bp, BorderLayout.EAST);

		JPanel p = new JPanel(new GridBagLayout());
		p.add(all);

		d = new JDialog();
		d.setModal(true);
		d.add(p);
		d.setSize(new Dimension(400, 400));
		d.setResizable(false);
		d.pack();
		d.setTitle("בחירת קובץ יעד לשמירה");

		GraphicsEnvironment env = GraphicsEnvironment
				.getLocalGraphicsEnvironment();
		Rectangle rect = env.getMaximumWindowBounds();
		int x = rect.x;
		int y = rect.y;
		d.setLocation(x + 400, y + 200);
		d.setVisible(true);
	}

	public void setFileName(String directoryPath) {
		filename.setText(directoryPath);
	}

	public String getFileName() {
		return filename.getText();
	}
}
