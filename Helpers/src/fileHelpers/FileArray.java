package fileHelpers;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

public abstract class FileArray {

	public FileArray() {
	}

	public abstract void loadFile(File file) throws IOException, TimeoutException;

	public abstract byte get(long index) throws IOException, TimeoutException;

	public abstract void setFile(File file);

	public abstract File getFile();

	public abstract long size();
}
