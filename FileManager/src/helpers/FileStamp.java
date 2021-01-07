package helpers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeoutException;

import fileHelpers.FlexibleFileArray;
import variableHelpers.ByteByte;

public class FileStamp {
	protected final int STAMP_AMOUNT;
	private final int MAX_NUMBER_OF_LOOPS = 20;
	private String fileSignature = null;

	public FileStamp(File file, int stampAmount) throws IOException, TimeoutException {
		STAMP_AMOUNT = stampAmount;
		init();

		if (file == null || file.length() == 0 || !file.isFile()) {
			return;
		}
		DataInputStream dis = new DataInputStream(new FileInputStream(file));

		long gap = file.length() / STAMP_AMOUNT;
		long currentFileByte = 0l;
		ArrayList<ByteByte> stampArray = new ArrayList<ByteByte>(STAMP_AMOUNT);
		for (int i = 0; i < STAMP_AMOUNT && i < file.length(); i++) {
			long nextPoint = gap * i;
			int numberOfloops = 0;
			while (currentFileByte != nextPoint) {
				numberOfloops++;
				long skipped = dis.skip(nextPoint - currentFileByte);
				currentFileByte += skipped;
				if (numberOfloops > MAX_NUMBER_OF_LOOPS) {
					dis.close();
					throw new TimeoutException("Number of time trying to read the stream exceeded");
				}
			}
			stampArray.add(new ByteByte(dis.readByte()));
			currentFileByte++;
		}
		setFileSignature(stampArray);
		dis.close();
	}

	public FileStamp(FlexibleFileArray flexibleFileArray, int stampAmount) throws IOException, TimeoutException {
		STAMP_AMOUNT = stampAmount;
		init();
		if (flexibleFileArray == null || flexibleFileArray.size() == 0) {
			return;
		}
		long gap = flexibleFileArray.size() / STAMP_AMOUNT;
		ArrayList<ByteByte> stampArray = new ArrayList<ByteByte>(STAMP_AMOUNT);
		for (int i = 0; i < STAMP_AMOUNT && i < flexibleFileArray.size(); i++) {
			stampArray.add(new ByteByte(flexibleFileArray.get(gap * i)));
		}
		setFileSignature(stampArray);
	}

	public FileStamp(FileStamp filetamp, int stampAmount) {
		STAMP_AMOUNT = stampAmount;
		init();
		this.fileSignature = filetamp.getFileSignature();
	}

	private void init() {
		fileSignature = "";
	}

	public String getFileSignature() {
		return fileSignature;
	}

	private void setFileSignature(Collection<ByteByte> stampArray) {
		fileSignature = "";
		if (stampArray != null && stampArray.size() > 0) {
			Iterator<ByteByte> i = stampArray.iterator();
			while (i.hasNext()) {
				byte b = (byte) (mod26(i.next().getByteAsByte()) + 65);
				fileSignature = fileSignature + (char) (b & 0xFF);
			}
		}

	}

	protected int mod26(byte b) {
		int i = b;
		if (i < 0) {
			i = -i;
		}
		while (i >= 26) {
			i -= 26;
		}
		return i;
	}

//	public static void main(String[] args) {
//		File file1 = new File("C:\\A\\Test\\1.dat");
//		File file2 = new File("C:\\A\\Test\\1.avi");
//		FlexibleFileArray flexibleFileArray = new FlexibleFileArray();
//		try {
//			FileStamp stamp1 = new FileStamp(file1, 10);
//			System.out.println("1: " + stamp1.getFileSignature());
//		} catch (IOException | TimeoutException e) {
//			System.out.println("1: " + e.getMessage());
//		}
//
//		try {
//			flexibleFileArray.loadFile(file1);
//			FileStamp stamp2 = new FileStamp(flexibleFileArray, 10);
//			System.out.println("2: " + stamp2.getFileSignature());
//		} catch (IOException | TimeoutException e) {
//			System.out.println("2: " + e.getMessage());
//		}
//		try {
//			FileStamp stamp1 = new FileStamp(file2, 10);
//			System.out.println("3: " + stamp1.getFileSignature());
//		} catch (IOException | TimeoutException e) {
//			System.out.println("3: " + e.getMessage());
//		}
//
//		try {
//			flexibleFileArray.loadFile(file2);
//			FileStamp stamp2 = new FileStamp(flexibleFileArray, 10);
//			System.out.println("4: " + stamp2.getFileSignature());
//		} catch (IOException | TimeoutException e) {
//			System.out.println("4: " + e.getMessage());
//		}
//	}
}
