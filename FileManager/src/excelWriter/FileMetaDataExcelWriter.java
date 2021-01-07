package excelWriter;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;

import helpers.EqualMapSet;
import vo.FileVo;

public class FileMetaDataExcelWriter extends ExcelWriter {
	private int n = 0;

	public FileMetaDataExcelWriter(HSSFSheet sheet) throws Exception {
		super(sheet);
		HSSFRow rowhead = getSheet().createRow(0);
		rowhead.createCell(0).setCellValue("FULLPATHNAME");
		rowhead.createCell(1).setCellValue("FULLDIRECTORYNAME");
		rowhead.createCell(2).setCellValue("FILENAME");
		rowhead.createCell(3).setCellValue("EXTENSION");
		rowhead.createCell(4).setCellValue("FILE_SIGNATURE");
		rowhead.createCell(5).setCellValue("MP3_SIGNATURE");
		rowhead.createCell(6).setCellValue("MP3_F_AMOUNT");
		rowhead.createCell(7).setCellValue("LASTMODIFIED");
		rowhead.createCell(8).setCellValue("LENGTH");
	}

	public void fillShit(EqualMapSet<String, FileVo> fileList) throws Exception {
		Iterator<FileVo> i = fileList.values().iterator();
		FileVo currentFile = null;

		String fullPathName;
		String fullDirectoryName;
		String Filename;
		String Extension;
		String fileSignature;
		String mp3Signature;
		int mp3FrameAmount;
		long lastModified;
		long length;

		while (i.hasNext()) {
			currentFile = i.next();

			if (currentFile.getFullPathName() == null) {
				fullPathName = "";
			}
			try {
				fullPathName = currentFile.getFullPathName();
			} catch (Exception e) {
				fullPathName = "";
			}
			if (currentFile.getFullDirectory() == null) {
				fullDirectoryName = "";
			}
			try {
				fullDirectoryName = currentFile.getFullDirectory();
			} catch (Exception e) {
				fullDirectoryName = "";
			}
			if (currentFile.getFileName() == null) {
				Filename = "";
			}
			try {
				Filename = currentFile.getFileName();
			} catch (Exception e) {
				Filename = "";
			}
			if (currentFile.getExtension() == null) {
				Extension = "";
			}
			try {
				Extension = currentFile.getExtension();
			} catch (Exception e) {
				Extension = "";
			}
			if (currentFile.getFileSignature() == null) {
				fileSignature = "";
			}
			try {
				fileSignature = currentFile.getFileSignature();
			} catch (Exception e) {
				fileSignature = "";
			}
			if (currentFile.getMp3Signature() == null) {
				mp3Signature = "";
			}
			try {
				mp3Signature = currentFile.getMp3Signature();
			} catch (Exception e) {
				mp3Signature = "";
			}
			if (currentFile.getMp3FrameAmount() == null) {
				mp3FrameAmount = 0;
			}
			try {
				mp3FrameAmount = currentFile.getMp3FrameAmount();
			} catch (Exception e) {
				mp3FrameAmount = 0;
			}
			if (currentFile.getLastModified() == null) {
				lastModified = 0;
			}
			try {
				lastModified = currentFile.getLastModified();
			} catch (Exception e) {
				lastModified = 0;
			}
			if (currentFile.getLength() == null) {
				length = 0;
			}
			try {
				length = currentFile.getLength();
			} catch (Exception e) {
				length = 0;
			}

			HSSFRow row = getSheet().createRow(++n);

			row.createCell(0).setCellValue(fullPathName);
			row.createCell(1).setCellValue(fullDirectoryName);
			row.createCell(2).setCellValue(Filename);
			row.createCell(3).setCellValue(Extension);
			row.createCell(4).setCellValue(fileSignature);
			row.createCell(5).setCellValue(mp3Signature);
			row.createCell(6).setCellValue(mp3FrameAmount);
			row.createCell(7).setCellValue(lastModified);
			row.createCell(8).setCellValue(length);
		}
	}
}
