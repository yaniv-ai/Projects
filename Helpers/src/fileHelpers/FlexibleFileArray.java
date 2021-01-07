package fileHelpers;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class FlexibleFileArray extends FileArray {
	private int BUFFER_LIMIT = 100000000;
	public static final byte FILE_AS_ARRAY_SOURCE = 0;
	public static final byte FILE_AS_BUFFER_SOURCE = 1;
	private final FileAsArray fileAsArray;
	private final FileAsBuffer fileAsBuffer;
	private byte arraySource = 0;

	public FlexibleFileArray() {
		fileAsArray = new FileAsArray();
		fileAsBuffer = new FileAsBuffer();
	}

	public void loadFile(File file) throws IOException, TimeoutException {
		if (file.length() > BUFFER_LIMIT) {
			arraySource = FILE_AS_BUFFER_SOURCE;
			fileAsBuffer.loadFile(file);
		} else {
			arraySource = FILE_AS_ARRAY_SOURCE;
			fileAsArray.loadFile(file);
		}
	}

	@Override
	public byte get(long index) throws IOException, TimeoutException {
		byte b;
		switch (arraySource) {
		case FILE_AS_BUFFER_SOURCE:
			b = fileAsBuffer.get(index);
			break;
		case FILE_AS_ARRAY_SOURCE:
		default:
			b = fileAsArray.get(index);
			break;
		}
		return b;
	}

	@Override
	public void setFile(File file) {
		switch (arraySource) {
		case FILE_AS_BUFFER_SOURCE:
			fileAsBuffer.setFile(file);
			break;
		case FILE_AS_ARRAY_SOURCE:
		default:
			fileAsArray.setFile(file);
			break;
		}
	}

	@Override
	public File getFile() {
		File file = null;
		switch (arraySource) {
		case FILE_AS_BUFFER_SOURCE:
			file = fileAsBuffer.getFile();
			break;
		case FILE_AS_ARRAY_SOURCE:
		default:
			file = fileAsArray.getFile();
			break;

		}
		return file;
	}

	@Override
	public long size() {
		long size = -1l;
		switch (arraySource) {
		case FILE_AS_BUFFER_SOURCE:
			size = fileAsBuffer.size();
			break;
		case FILE_AS_ARRAY_SOURCE:
		default:
			size = fileAsArray.size();
			break;
		}
		return size;
	}
}
