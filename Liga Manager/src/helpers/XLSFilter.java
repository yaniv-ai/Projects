package helpers;

import java.io.File;
import javax.swing.filechooser.*;

public class XLSFilter extends FileFilter {

	public boolean accept(File f) {
		if (f == null) {
			return false;
		}
		if (f.isDirectory()) {
			return true;
		}

		return XLSFilter.validExtension(f);
	}

	private static String getExtension(File file) {
		if (file == null) {
			return null;
		} else {
			return getExtension(file.getName());
		}
	}

	private static String getExtension(String filename) {
		if (filename == null) {
			return null;
		}
		String ext = null;
		int i = filename.lastIndexOf('.');

		if (i > 0 && i < filename.length() - 1) {
			ext = filename.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	public static boolean validExtension(File file) {
		String extension = XLSFilter.getExtension(file);
		if (extension != null && extension.equalsIgnoreCase("XLS")) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean validExtension(String filename) {
		String extension = XLSFilter.getExtension(filename);
		if (extension != null && extension.equalsIgnoreCase("XLS")) {
			return true;
		} else {
			return false;
		}
	}

	public static String addExtension(String filename) {
		if (filename != null) {
			return filename + ".xls";
		}
		return null;
	}

	// The description of this filter
	public String getDescription() {
		return "XLS fils only";
	}
}
