package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import excelWriter.FileMetaDataExcelWriter;
import fileHelpers.FlexibleFileArray;
import ui.MainWindow;
import vo.FileVo;

public class FileMetaDataExporter {
	private EqualMapSet<String, FileVo> fileList;
	private boolean toExcelDB = false;
	private boolean checkMP3Stamp = false;
	private FileVo currentFile = null;
	private File databaseFile = null;
	private FlexibleFileArray flexibleFileArray = null;

	private String dbDirectoryName = "C:\\databases\\";
	private String dbTxtExtension = ".txt";
	private String dbCSVExtension = ".csv";
	private Writer writerOut = null;
	private FileMetaDataExcelWriter excelWriter = null;
	private HSSFWorkbook workbook = null;
	private FileOutputStream fileOut = null;

	private int maxFilesLimitInArray = 0;
	private boolean useCommit = false;
	private int ind;
	private int numberOfFilesToScan;
	private double progress = 0.0;
	private int stationCheck = 1000;
	private int stampAmount = 4;

	public FileMetaDataExporter(boolean toExcelDB, boolean checkMP3Stamp, int maxFilesLimitInArray, boolean useCommit) {
		this(toExcelDB, checkMP3Stamp, maxFilesLimitInArray, useCommit, 4);
	}

	public FileMetaDataExporter(boolean toExcelDB, boolean checkMP3Stamp, int maxFilesLimitInArray, boolean useCommit,
			int stampAmount) {
		flexibleFileArray = new FlexibleFileArray();
		// this.toExcelDB = toExcelDB;
		this.toExcelDB = false;
		this.checkMP3Stamp = checkMP3Stamp;
		this.maxFilesLimitInArray = maxFilesLimitInArray;
		this.useCommit = checkMP3Stamp;
		this.stampAmount = stampAmount;
	}

	public void exportFileList(String databaseName, ArrayList<String> dirList) throws Exception {
		fileList = new EqualMapSet<String, FileVo>();
		numberOfFilesToScan = 0;
		if (dirList == null || dirList.size() <= 0) {
			return;
		}
		Iterator<String> itr = dirList.iterator();
		while (itr.hasNext()) {
			File dir = new File(itr.next());
			numberOfFilesToScan += countFiles(dir);
		}
		if (numberOfFilesToScan <= 0) {
			// post something
			// TODO
			return;
		}
		ind = 0;

		if (useCommit) {
			openDB(databaseName);
		}

		itr = dirList.iterator();
		while (itr.hasNext()) {
			File dir = new File(itr.next());
			createFileList(dir);
		}
		if (useCommit) {
			saveFileList();
			closeDB();
		}
	}

	private int countFiles(File dir) {
		if (dir == null || dir.isFile() && dir.length() < 0 || !dir.isFile() && !dir.isDirectory()) {
			return 0;
		}
		int n = 0;
		if (dir.isDirectory()) {
			File[] files = dir.listFiles();
			for (int i = 0; files != null && i < files.length; i++) {
				n += countFiles(files[i]);
			}
			return n;
		} else {
			return 1;
		}
	}

	private void openDB(String databaseName) throws Exception {
		String databaseFilename = "ScannedDataBase";
		if (databaseName != null && databaseName.length() > 0) {
			databaseFilename = databaseName;
		}
		(new File(dbDirectoryName)).mkdirs();

		if (toExcelDB) {
			workbook = new HSSFWorkbook();
			databaseFile = new File(dbDirectoryName + databaseFilename + dbCSVExtension);
			fileOut = new FileOutputStream(databaseFile);
			excelWriter = new FileMetaDataExcelWriter(workbook.createSheet("FileMetaData"));
		} else {

			databaseFile = new File(dbDirectoryName + databaseFilename + dbTxtExtension);

			writerOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(databaseFile)));
			String line = "FULLPATHNAME|FULLDIRECTORYNAME|FILENAME|EXTENSION|FILE_SIGNATURE|MP3_SIGNATURE|MP3_F_AMOUNT|LASTMODIFIED|LENGTH";
			writerOut.append(line).append("\r\n");
			writerOut.flush();
		}
	}

	private void closeDB() throws ClassNotFoundException, IOException {
		if (toExcelDB) {
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		} else {
			writerOut.flush();
			if (writerOut != null) {
				writerOut.close();
			}
		}
	}

	private void createFileList(File f) throws Exception {
		if (f == null || f.isFile() && f.length() < 0 || !f.isFile() && !f.isDirectory()) {
			return;
		}
		if (f.isDirectory()) {
			File[] files = f.listFiles();
			for (int i = 0; files != null && i < files.length; i++) {
				this.createFileList(files[i]);
			}
		} else {
			addFileMetaData(f);
			ind++;
			if (ind % stationCheck == 0) {
				progress = (double) ind / numberOfFilesToScan;
			}

			if (fileList.size() > maxFilesLimitInArray && useCommit) {
				saveFileList();
			}
		}
	}

	private void saveFileList() throws Exception {
		if (toExcelDB) {
			excelWriter.fillShit(fileList);
		} else {
			Iterator<FileVo> i = fileList.values().iterator();
			while (i.hasNext()) {
				currentFile = i.next();
				writeToFile();
			}
		}
		MainWindow.postMesage(ind + ") Now writing: (Process time: " + currentFile.getProcessTime() + ") "
				+ currentFile.getFileName());

		fileList = new EqualMapSet<String, FileVo>();
	}

	private void writeToFile() throws IOException {

		String line = currentFile.getFullPathName() + "|" + currentFile.getFullDirectory() + "|"
				+ currentFile.getFileName() + "|" + currentFile.getExtension() + "|" + currentFile.getFileSignature()
				+ "|" + currentFile.getMp3Signature() + "|" + currentFile.getMp3FrameAmount() + "|"
				+ currentFile.getLastModified() + "|" + currentFile.getLength();
		writerOut.append(line).append("\r\n");
		writerOut.flush();
	}

	private void addFileMetaData(File f) throws IOException {
		if (f == null || f.isFile() && f.length() < 0) {
			return;
		}
		long processStart = Calendar.getInstance().getTimeInMillis();
		FileVo newFile = new FileVo();

		newFile.setDrive(getDrive(f));
		newFile.setFileName(getName(f));
		newFile.setExtension(getExtension(f));
		newFile.setFullDirectory(f.getParent());
		newFile.setFullPathName(f.getPath());
		newFile.setLastModified(f.lastModified());
		newFile.setLength(f.length());
		MP3Stamp mp3Stamp = null;
		FileStamp fileStamp = null;

		try {
			if (checkMP3Stamp) {
				MainWindow.postMesage(f.getPath());
				flexibleFileArray.loadFile(f);
				mp3Stamp = new MP3Stamp(flexibleFileArray, 10, false);
				newFile.setFileSignature(mp3Stamp.getFileSignature());
				newFile.setMp3Signature(mp3Stamp.getMp3Signature());
				newFile.setMp3FrameAmount(mp3Stamp.getMp3FrameAmount());
			} else {
				fileStamp = new FileStamp(f, stampAmount);
				newFile.setFileSignature(fileStamp.getFileSignature());
			}
		} catch (Exception e) {
		}
		newFile.setProcessTime(Calendar.getInstance().getTimeInMillis() - processStart);
		fileList.put(newFile.getFullPathName(), newFile);
	}

	private String getExtension(File f) {
		String ext = "";
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1);
		}
		return ext;
	}

	private String getName(File f) {
		String name = f.getName();
		int i = name.lastIndexOf('.');

		if (i > 0) {
			name = name.substring(0, i);
		}
		return name;
	}

	private String getDrive(File f) {
		String drive = f.getPath();
		int i = drive.indexOf(':');

		if (i > 0) {
			drive = drive.substring(0, i + 1);
		}
		return drive;
	}

	public EqualMapSet<String, FileVo> getFileList() {
		return fileList;
	}
}
