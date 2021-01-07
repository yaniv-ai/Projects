package fileHelpers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileAsArray extends FileArray {
	public static final int BUFFER_LENGTH = 1000000;
	private byte[][] byteFileArray = null;
	private long byteFileSize = 0l;
	private File file = null;

	public FileAsArray() {
	}

	public void loadFile(File file) throws IOException {
		setFile(file);
		byteFileArray = null;
		byteFileSize = -1l;
		initByteFileArray(file.length());
		copyFileToArray(file);
	}

	private void initByteFileArray(long length) {
		byteFileSize = length;
		int numberOfBuffers = length == 0l ? 0
				: (int) ((length - 1l) / BUFFER_LENGTH) + 1;

		if (byteFileArray == null) {
			byteFileArray = new byte[numberOfBuffers][];
			for (int i = 0; i < byteFileArray.length; i++) {
				byteFileArray[i] = new byte[BUFFER_LENGTH];
			}
		} else {
			if (byteFileArray.length < numberOfBuffers) {
				byte[][] temp = byteFileArray;
				byteFileArray = new byte[numberOfBuffers][];
				for (int i = 0; i < byteFileArray.length; i++) {
					if (i < temp.length) {
						byteFileArray[i] = temp[i];
					} else {
						byteFileArray[i] = new byte[BUFFER_LENGTH];
					}
				}
			}
		}
	}

	private void copyFileToArray(File file) throws IOException {
		DataInputStream dis = new DataInputStream(new FileInputStream(file));
		byte[] byteArray = new byte[1000];
		int totalBytesRead = 0;
		int bufferNumber = 0;
		int bufferIndex = 0;
		while (totalBytesRead >= 0) {
			totalBytesRead = dis.read(byteArray);
			for (int i = 0; i < totalBytesRead; i++) {
				byteFileArray[bufferNumber][bufferIndex] = byteArray[i];
				bufferIndex++;
				if (bufferIndex == BUFFER_LENGTH) {
					bufferNumber++;
					bufferIndex = 0;
				}
			}
		}
		dis.close();
	}

	/**
	 * @param index
	 * @return
	 * @throws IOException
	 * @throws FileArrayException
	 */
	public byte get(long index) {
		if (index < 0) {
			throw new ArrayIndexOutOfBoundsException("Index cannot be negative");
		}
		if (index >= byteFileSize) {
			throw new ArrayIndexOutOfBoundsException("Index out of range");
		}
		int bufferNumber = (int) (index / BUFFER_LENGTH);
		int bufferIndex = (int) (index % (long) BUFFER_LENGTH);
		byte b = byteFileArray[bufferNumber][bufferIndex];
		return b;
	}

	public long size() {
		return byteFileSize;
	}

	@Override
	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public File getFile() {
		return this.file;
	}

}
