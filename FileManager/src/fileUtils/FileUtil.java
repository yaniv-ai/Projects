package fileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	public boolean hasTrash() {
		return getTrashDirectory().exists();
	}

	/**
	 * Move the given file to the system trash, if one is available.
	 * 
	 * @param files files to move
	 * @throws IOException on failure.
	 */
	public void moveToTrash(File... files) throws IOException {
		File trash = getTrashDirectory();
		if (!trash.exists()) {
			throw new IOException("No trash location found (define fileutils.trash to be the path to the trash)");
		}
		List<File> failed = new ArrayList<File>();
		for (int i = 0; i < files.length; i++) {
			File src = files[i];
			File target = new File(trash, src.getName());
			if (!src.renameTo(target)) {
				failed.add(src);
			}
		}
		if (failed.size() > 0) {
			throw new IOException("The following files could not be trashed: " + failed);
		}

	}

	private File getTrashDirectory() {
		// very simple implementation. should take care of renaming when
		// a file already exists, or any other platform-specific behavior
		File home = new File(System.getProperty("user.home"));
		File trash = new File(home, ".Trash");
		if (!trash.exists()) {
			trash = new File(home, "Trash");
			if (!trash.exists()) {
				File desktop = new File(home, "Desktop");
				if (desktop.exists()) {
					trash = new File(desktop, ".Trash");
					if (!trash.exists()) {
						trash = new File(desktop, "Trash");
						if (!trash.exists()) {
							trash = new File(System.getProperty("fileutils.trash", "Trash"));
						}
					}
				}
			}
		}
		return trash;
	}
}