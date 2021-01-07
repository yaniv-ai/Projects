// 
// Decompiled by Procyon v0.5.36
// 

package main;

import java.util.ArrayList;
import java.io.IOException;
import helpers.ImagePreview;
import helpers.SubtitleFileView;
import helpers.SubtitleFilter;
import helpers.Utils;
import java.awt.event.ActionEvent;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.File;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.ButtonGroup;
import java.awt.GridLayout;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.awt.GridBagConstraints;
import javax.swing.JTextArea;
import javax.swing.JRadioButton;
import vo.SubVO;
import java.util.Vector;
import helpers.SubTimeAdjuster;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import helpers.JSensitiveTextField;
import java.awt.Font;
import java.awt.event.ActionListener;
import templates.MyPanel;

public class MasachRashi extends MyPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final Font FONT;
	public static final String lastDirectoryFileName = "properties\\LastDirectoryLovation.txt";
	private JSensitiveTextField inSub;
	private JSensitiveTextField outSub;
	private JButton browse;
	private JButton generateDestination;
	private JButton makeNewSub;
	private JFileChooser fileChooser;
	private SubTimeAdjuster firstRow;
	private SubTimeAdjuster lastRow;
	private Vector<SubVO> subs;
	private String lastDirectory;
	private JRadioButton ansiButton;
	private JRadioButton unicodeButton;
	private static JTextArea messages;
	private GridBagConstraints inSubConstraints;
	private GridBagConstraints outSubConstraints;
	private GridBagConstraints browseConstraints;
	private GridBagConstraints generateDestinationConstraints;
	private GridBagConstraints makeNewSubConstraints;
	private GridBagConstraints firstRowConstraints;
	private GridBagConstraints lastRowConstraints;
	private GridBagConstraints rPanelConstraints;
	private GridBagConstraints messagePanelConstraints;
	private BufferedReader bufferedIn;

	static {
		FONT = new Font("Dialog", 0, 14);
		MasachRashi.messages = null;
	}

	public MasachRashi() {
		this.inSub = null;
		this.outSub = null;
		this.fileChooser = null;
		this.lastDirectory = null;
		this.bufferedIn = null;
		this.lastDirectory = this.getLastDirectory();
		(this.inSub = new JSensitiveTextField()).setPreferredSize(new Dimension(300, 20));
		this.inSub.setMinimumSize(new Dimension(300, 20));
		(this.browse = new JButton("Browse")).addActionListener(this);
		(this.outSub = new JSensitiveTextField()).setPreferredSize(new Dimension(300, 20));
		this.outSub.setMinimumSize(new Dimension(300, 20));
		(this.generateDestination = new JButton("Generate File Name")).addActionListener(this);
		final JPanel outSubPanel = new JPanel(new BorderLayout());
		outSubPanel.add(this.outSub, "Center");
		outSubPanel.add(this.generateDestination, "East");
		(this.makeNewSub = new JButton("Make New Sub")).addActionListener(this);
		this.firstRow = new SubTimeAdjuster("first");
		this.lastRow = new SubTimeAdjuster("last");
		(this.ansiButton = new JRadioButton("ANSI")).setActionCommand("ANSI");
		(this.unicodeButton = new JRadioButton("UniCODE")).setActionCommand("UniCODE");
		this.unicodeButton.setSelected(true);
		final JPanel rPanel = new JPanel(new GridLayout(1, 2));
		rPanel.add(this.unicodeButton);
		rPanel.add(this.ansiButton);
		final ButtonGroup sourceCode = new ButtonGroup();
		sourceCode.add(this.unicodeButton);
		sourceCode.add(this.ansiButton);
		MasachRashi.messages = new JTextArea();
		final JScrollPane messagesScrollPane = new JScrollPane(MasachRashi.messages, 20, 30);
		messagesScrollPane.setViewportView(MasachRashi.messages);
		messagesScrollPane
				.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.MAGENTA, 3, true),
						"    \u05d4\u05d5\u05d3\u05e2\u05d5\u05ea    ", 3, 2, MasachRashi.FONT));
		messagesScrollPane.setPreferredSize(new Dimension(500, 200));
		messagesScrollPane.setMinimumSize(new Dimension(500, 200));
		MasachRashi.messages.setEditable(false);
		this.setLayout(new GridBagLayout());
		this.calculatePanelsConstraints();
		this.add(this.inSub, this.inSubConstraints);
		this.add(this.browse, this.browseConstraints);
		this.add(outSubPanel, this.outSubConstraints);
		this.add(this.generateDestination, this.generateDestinationConstraints);
		this.add(this.firstRow, this.firstRowConstraints);
		this.add(this.lastRow, this.lastRowConstraints);
		this.add(rPanel, this.rPanelConstraints);
		this.add(this.makeNewSub, this.makeNewSubConstraints);
		this.add(messagesScrollPane, this.messagePanelConstraints);
	}

	private void calculatePanelsConstraints() {
		this.inSubConstraints = new GridBagConstraints();
		this.inSubConstraints.gridx = 0;
		this.inSubConstraints.gridy = 0;
		this.inSubConstraints.ipadx = 500;
		this.inSubConstraints.ipady = 20;
		this.inSubConstraints.gridwidth = 40;
		this.inSubConstraints.fill = 17;
		this.inSubConstraints.anchor = 17;
		this.browseConstraints = new GridBagConstraints();
		this.browseConstraints.gridx = 1;
		this.browseConstraints.gridy = 0;
		this.browseConstraints.gridwidth = 8;
		this.browseConstraints.anchor = 13;
		this.outSubConstraints = new GridBagConstraints();
		this.outSubConstraints.gridx = 0;
		this.outSubConstraints.gridy = 1;
		this.outSubConstraints.ipadx = 500;
		this.outSubConstraints.ipady = 20;
		this.outSubConstraints.gridwidth = 40;
		this.outSubConstraints.fill = 17;
		this.outSubConstraints.anchor = 17;
		this.generateDestinationConstraints = new GridBagConstraints();
		this.generateDestinationConstraints.gridx = 1;
		this.generateDestinationConstraints.gridy = 1;
		this.generateDestinationConstraints.gridwidth = 8;
		this.generateDestinationConstraints.anchor = 13;
		this.rPanelConstraints = new GridBagConstraints();
		this.rPanelConstraints.gridx = 0;
		this.rPanelConstraints.gridy = 3;
		this.makeNewSubConstraints = new GridBagConstraints();
		this.makeNewSubConstraints.gridx = 1;
		this.makeNewSubConstraints.gridy = 3;
		this.firstRowConstraints = new GridBagConstraints();
		this.firstRowConstraints.gridx = 0;
		this.firstRowConstraints.gridy = 2;
		this.lastRowConstraints = new GridBagConstraints();
		this.lastRowConstraints.gridx = 1;
		this.lastRowConstraints.gridy = 2;
		this.messagePanelConstraints = new GridBagConstraints();
		this.messagePanelConstraints.gridx = 0;
		this.messagePanelConstraints.gridy = 4;
		this.messagePanelConstraints.gridwidth = 2;
		this.messagePanelConstraints.fill = 2;
		this.messagePanelConstraints.anchor = 20;
		this.messagePanelConstraints.weightx = 0.5;
		this.messagePanelConstraints.weighty = 0.5;
	}

	private String getLastDirectory() {
		String lastDirectory = null;
		final File in = new File("properties\\LastDirectoryLovation.txt");
		BufferedReader bufferedIn = null;
		try {
			bufferedIn = new BufferedReader(new FileReader(in));
			lastDirectory = bufferedIn.readLine();
		} catch (Exception e) {
			lastDirectory = null;
		} finally {
			if (bufferedIn != null) {
				try {
					bufferedIn.close();
				} catch (Exception ex) {
				}
			}
		}
		if (bufferedIn != null) {
			try {
				bufferedIn.close();
			} catch (Exception ex2) {
			}
		}
		return lastDirectory;
	}

	private void setLastDirectory(String lastDirectory) {
		if (lastDirectory == null || lastDirectory.length() == 0) {
			return;
		}
		final int n = lastDirectory.lastIndexOf(92);
		if (n >= 0) {
			lastDirectory = lastDirectory.substring(0, n);
		}
		FileOutputStream fout = null;
		Writer writerOut = null;
		try {
			File f = new File("properties\\LastDirectoryLovation.txt");
			File dir = new File(f.getParent());
			if (!dir.exists()) {
				dir.mkdirs();
			}
			fout = new FileOutputStream(f);
			writerOut = new BufferedWriter(new OutputStreamWriter(fout));
			writerOut.append((CharSequence) lastDirectory).append((CharSequence) "\r\n");
			writerOut.flush();
			dir = null;
			f = null;
		} catch (Exception ex) {
		} finally {
			if (writerOut != null) {
				try {
					writerOut.close();
				} catch (Exception ex2) {
				}
			}
			if (fout != null) {
				try {
					fout.close();
				} catch (Exception ex3) {
				}
			}
		}
		if (writerOut != null) {
			try {
				writerOut.close();
			} catch (Exception ex4) {
			}
		}
		if (fout != null) {
			try {
				fout.close();
			} catch (Exception ex5) {
			}
		}
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if (e.getSource() == this.makeNewSub) {
			double a = 1.0;
			double b = 0.0;
			final SubVO start = this.firstRow.getSelectedSub();
			final SubVO end = this.lastRow.getSelectedSub();
			if (start != null && end != null && Utils.isTime(start.getTimeLine().startToString())
					&& Utils.isTime(this.firstRow.getAdjustedStart()) && Utils.isTime(end.getTimeLine().startToString())
					&& Utils.isTime(this.lastRow.getAdjustedStart())) {
				try {
					final double x1 = Utils.timeLineToMiliSeconds(start.getTimeLine().startToString());
					final double y1 = Utils.timeLineToMiliSeconds(this.firstRow.getAdjustedStart());
					final double x2 = Utils.timeLineToMiliSeconds(end.getTimeLine().startToString());
					final double y2 = Utils.timeLineToMiliSeconds(this.lastRow.getAdjustedStart());
					a = (y2 - y1) / (x2 - x1);
					b = y2 - a * x2;
				} catch (Exception ex) {
				}
			}
			try {
				this.rewriteSubtitles(a, b);
			} catch (Exception ex2) {
			}
		}
		if (e.getSource() == this.browse) {
			if (this.fileChooser == null) {
				(this.fileChooser = new JFileChooser(this.lastDirectory)).addChoosableFileFilter(new SubtitleFilter());
				this.fileChooser.setAcceptAllFileFilterUsed(false);
				this.fileChooser.setFileView(new SubtitleFileView());
				this.fileChooser.setAccessory(new ImagePreview(this.fileChooser));
			}
			final int returnVal = this.fileChooser.showDialog(this, "Attach");
			if (returnVal == 0) {
				final String filename = this.fileChooser.getSelectedFile().getAbsolutePath();
				this.inSub.setText(filename);
				this.fileChooser.setSelectedFile(null);
				if (filename != null) {
					this.setLastDirectory(filename);
					this.subs = null;
					try {
						this.subs = this.getSubs(filename);
						this.firstRow.setSubVector(this.subs);
						this.lastRow.setSubVector(this.subs);
						if (this.subs.size() > 0) {
							this.firstRow.setSelectedIndex(0);
							this.lastRow.setSelectedIndex(this.subs.size() - 1);
						}
					} catch (Exception ex3) {
					}
				}
			}
		}
		if (e.getSource() == this.generateDestination) {
			final String newFileName = this.generateNewFileName(this.inSub.getText());
			this.outSub.setText(newFileName);
		}
	}

	private void rewriteSubtitles(final double a, final double b) throws Exception {
		Writer writerOut = null;
		String line = null;
		try {
			final String outFileName = this.outSub.getText();
			if (outFileName != null && outFileName.length() > 0) {
				if (this.ansiButton.isSelected()) {
					writerOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileName), "UTF8"));
				} else {
					writerOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFileName), "ASCII"));
				}
				for (int i = 0; i < this.subs.size(); ++i) {
					final SubVO sub = this.subs.get(i);
					line = new StringBuilder(String.valueOf(i + 1)).toString();
					writerOut.append((CharSequence) line).append((CharSequence) "\r\n");
					writerOut.flush();
					line = Utils.ajustTime(sub.getTimeLine().toString(), a, b);
					writerOut.append((CharSequence) line).append((CharSequence) "\r\n");
					writerOut.flush();
					if (sub.getLine1() != null && sub.getLine1().length() > 0) {
						line = sub.getLine1();
						writerOut.append((CharSequence) line).append((CharSequence) "\r\n");
						writerOut.flush();
					}
					if (sub.getLine2() != null && sub.getLine2().length() > 0) {
						line = sub.getLine2();
						writerOut.append((CharSequence) line).append((CharSequence) "\r\n");
						writerOut.flush();
					}
					if (sub.getLine3() != null && sub.getLine3().length() > 0) {
						line = sub.getLine3();
						writerOut.append((CharSequence) line).append((CharSequence) "\r\n");
						writerOut.flush();
					}
					if (sub.getLine4() != null && sub.getLine4().length() > 0) {
						line = sub.getLine4();
						writerOut.append((CharSequence) line).append((CharSequence) "\r\n");
						writerOut.flush();
					}
					line = "";
					writerOut.append((CharSequence) line).append((CharSequence) "\r\n");
					writerOut.flush();
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (writerOut != null) {
				writerOut.flush();
				writerOut.close();
			}
		}
		if (writerOut != null) {
			writerOut.flush();
			writerOut.close();
		}
	}

	private String generateNewFileName(final String oldFileName) {
		boolean pointExist = true;
		boolean nameFound = false;
		String newFileName = null;
		final String addition = " ";
		if (oldFileName == null || oldFileName.length() == 0) {
			return null;
		}
		final int endIndex = oldFileName.lastIndexOf(46);
		if (endIndex < 0) {
			pointExist = false;
		}
		for (Integer i = 0; i < 999 && !nameFound; ++i) {
			if (!pointExist) {
				newFileName = String.valueOf(oldFileName) + addition + i.toString();
			} else {
				newFileName = String.valueOf(oldFileName.substring(0, endIndex)) + addition + i.toString()
						+ oldFileName.substring(endIndex);
			}
			final File f = new File(newFileName);
			if (!f.exists()) {
				nameFound = true;
			}
		}
		return newFileName;
	}

	private Vector<SubVO> getSubs(final String filename) throws Exception {
		final File in = new File(filename);
		this.bufferedIn = new BufferedReader(new FileReader(in));
		final String line = this.bufferedIn.readLine();
		if (line.substring(0, 1).equalsIgnoreCase("{")) {
			return this.getQuarterSubs(line);
		}
		return this.getTimedSubs(line);
	}

	private Vector<SubVO> getTimedSubs(String line) throws IOException {
		final Vector<SubVO> subs = new Vector<SubVO>();
		int counter = 0;
		int linesNum = 0;
		boolean endOfPack = false;
		final String[] pack = new String[6];
		while (line != null && !Utils.isTimeLine(line)) {
			line = this.bufferedIn.readLine();
		}
		pack[linesNum] = line;
		SubVO sub;
		int i;
		for (line = this.bufferedIn.readLine(); line != null; line = this.bufferedIn.readLine()) {
			if (Utils.isTimeLine(line)) {
				endOfPack = false;
				if (linesNum > 0) {
					sub = new SubVO(++counter, Utils.stringToTimeLine(pack[0]), pack[1], pack[2], pack[3], pack[4]);
					subs.add(sub);
				}
				linesNum = 0;
				for (i = 0; i < pack.length; ++i) {
					pack[i] = null;
				}
				pack[linesNum] = line;
			} else if (!endOfPack) {
				if (line.length() == 0 || linesNum >= pack.length - 1) {
					endOfPack = true;
				} else {
					pack[++linesNum] = line;
				}
			}
		}
		if (linesNum > 0) {
			sub = new SubVO(++counter, Utils.stringToTimeLine(pack[0]), pack[1], pack[2], pack[3], pack[4]);
			subs.add(sub);
		}
		return subs;
	}

	private Vector<SubVO> getQuarterSubs(String line) throws IOException {
		final Vector<SubVO> subs = new Vector<SubVO>();
		int counter = 0;
		String[] pack = null;
		while (line != null) {
			pack = this.getQuarteredLine(line);
			final SubVO sub = new SubVO(++counter, Utils.stringToTimeLine(pack[0]), pack[1], pack[2], pack[3], pack[4]);
			subs.add(sub);
			line = this.bufferedIn.readLine();
		}
		return subs;
	}

	private String[] getQuarteredLine(String line) {
		final String[] pack = new String[6];
		final ArrayList<String> parts = new ArrayList<String>();
		String start = null;
		String end = null;
		boolean isPack = false;
		try {
			int i = line.indexOf("{");
			if (i >= 0) {
				line = line.substring(i + 1);
				i = line.indexOf("}");
				if (i >= 0) {
					start = line.substring(0, i);
					i = line.indexOf("{");
					if (i >= 0) {
						line = line.substring(i + 1);
						i = line.indexOf("}");
						if (i >= 0) {
							end = line.substring(0, i);
							i = line.indexOf("}");
							line = line.substring(i + 1);
							if (line.length() > 0) {
								isPack = true;
								final double startTime = Double.parseDouble(start) * 1000.0 / 25.0;
								final double endTime = Double.parseDouble(end) * 1000.0 / 25.0;
								parts.add(String.valueOf(Utils.miliSecondsToTimeLine(startTime)) + " --> "
										+ Utils.miliSecondsToTimeLine(endTime));
								while (line.indexOf("|") >= 0) {
									parts.add(line.substring(0, line.indexOf("|")));
									line = line.substring(line.indexOf("|") + 1);
								}
								parts.add(line);
							}
						}
					}
				}
			}
			if (isPack) {
				for (int j = 0; j < parts.size(); ++j) {
					if (j >= 6) {
						break;
					}
					pack[j] = parts.get(j);
				}
			}
		} catch (Exception ex) {
		}
		return pack;
	}
}
