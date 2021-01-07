package fileHelpers;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FileAsBuffer extends FileArray {
	private final int MAX_NUMBER_OF_LOOPS = 20;
	private final long NUMBER_OF_PRECEDING_BYTES = 2;
	private int totalBytesRead = 0;
	private DataInputStream dis = null;
	private long currentFileByte = 0l;
	private long firstIndex = 0l;
	private long lastIndex = 0l;
	private static final int BUFFER_LIMIT = 1000000;
	private byte[] byteArray = null;

	private static final int BUFFER_LENGTH = 10000;
	private static final int BUFFER_AMOUNT = 1000;

	private byte[][] byteFileArray = null;

	private File file = null;

	public FileAsBuffer() {
		byteArray = new byte[BUFFER_LIMIT];
		byteFileArray = new byte[BUFFER_AMOUNT][];
		for (int i = 0; i < byteFileArray.length; i++) {
			byteFileArray[i] = new byte[BUFFER_LENGTH];
		}
	}

	@Override
	public void loadFile(File file) throws IOException, TimeoutException {
		setFile(file);
		dis = new DataInputStream(new FileInputStream(file));
		currentFileByte = 0l;
		readNextBuffer();
	}

	private void readNextBuffer() throws IOException, TimeoutException {
		int numberOfloops = 0;
		totalBytesRead = 0;

		while (totalBytesRead <= 0 && totalBytesRead < file.length()) {
			numberOfloops++;

			totalBytesRead = dis.read(byteArray);
			if (totalBytesRead == -1) {
				throw new ArrayIndexOutOfBoundsException(
						"File stream ended before reaching the requested index");
			}
			if (numberOfloops > MAX_NUMBER_OF_LOOPS) {
				throw new TimeoutException(
						"Number of time trying to read the stream exceeded");
			}
		}
		currentFileByte += totalBytesRead;
		copyFileToArray();
	}

	private void copyFileToArray() {
		lastIndex = currentFileByte - 1;
		firstIndex = currentFileByte - totalBytesRead - 1;
		int ind = 0;
		for (int i = 0; i < byteFileArray.length && ind < totalBytesRead; i++) {
			for (int j = 0; j < byteFileArray[i].length && ind < totalBytesRead; j++) {
				byteFileArray[i][j] = byteArray[ind++];
			}
		}
	}

	/**
	 * @param index
	 * @return
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws FileArrayException
	 */
	public byte get(long index) throws IOException, TimeoutException {
		if (index < 0) {
			throw new ArrayIndexOutOfBoundsException("Index cannot be negative");
		}

		if (!inArray(index)) {
			int numberOfloops = 0;
			long startByte = index - NUMBER_OF_PRECEDING_BYTES;
			if (startByte < 0l) {
				startByte = 0l;
			}
			while (currentFileByte != startByte) {
				numberOfloops++;
				long skipped = dis.skip(startByte - currentFileByte);
				currentFileByte += skipped;
				if (numberOfloops > MAX_NUMBER_OF_LOOPS) {
					throw new TimeoutException(
							"Number of time trying to read the stream exceeded");
				}
			}
			readNextBuffer();
		}
		byte b = byteFromInnerArray(index);
		return b;
	}

	/**
	 * This method calculates the location of the index in the file in the inner
	 * buffer according to the current cursor location and the size of the
	 * buffer.
	 * 
	 * @param The
	 *            index of the byte in the file as if it was an array. the first
	 *            byte of the file is at location [0]
	 * @return The byte at the given location
	 */
	private byte byteFromInnerArray(long index) {
		long newIndex = index - currentFileByte + totalBytesRead;
		if (newIndex < 0) {
			throw new ArrayIndexOutOfBoundsException("Index cannot be negative");
		}
		if (newIndex >= totalBytesRead) {
			throw new ArrayIndexOutOfBoundsException("Index out of range");
		}

		int bufferNumber = (int) (newIndex / BUFFER_LENGTH);
		int bufferIndex = (int) (newIndex % (long) BUFFER_LENGTH);

		return byteFileArray[bufferNumber][bufferIndex];
	}

	/**
	 * Checks if the index to be returned is currently in the buffer. The class
	 * holds the current amount of bytes read, and the size of the buffer. The
	 * minimum index will be (currentAmount - size) and the maximum will be
	 * (currentAmount - 1)
	 * 
	 * @param index
	 * @return true if the index is in range, false otherwise.
	 */
	private boolean inArray(long index) {
		if (index < firstIndex || index > lastIndex) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public File getFile() {
		return this.file;
	}

	@Override
	public long size() {
		return file.length();
	}
}
