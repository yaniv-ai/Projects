package helpers;

import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

public class MusicFileView extends FileView {

	public String getName(File f) {
		return null; // let the L&F FileView figure this out
	}

	public String getDescription(File f) {
		return null; // let the L&F FileView figure this out
	}

	public Boolean isTraversable(File f) {
		return null; // let the L&F FileView figure this out
	}

	public String getTypeDescription(File f) {
		String extension = MusicFileView.getExtension(f);
		String type = null;

		if (extension != null) {
			if (extension.equalsIgnoreCase("MP3")) {
				type = "MP3 file";
			} else if (extension.equals("MPEG")) {
				type = "MPEG file";
			} else if (extension.equals("WMV")) {
				type = "WMV file";
			}
		}
		return type;
	}

	private static String getExtension(File f) {
		String ext = null;
		String s = f.getName();
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}
		return ext;
	}

	public Icon getIcon(File f) {
		// String extension = Utils.getExtension(f);
		Icon icon = null;

		// if (extension != null) {
		// if (extension.equals(Utils.jpeg) || extension.equals(Utils.jpg)) {
		// icon = jpgIcon;
		// } else if (extension.equals(Utils.gif)) {
		// icon = gifIcon;
		// } else if (extension.equals(Utils.tiff)
		// || extension.equals(Utils.tif)) {
		// icon = tiffIcon;
		// } else if (extension.equals(Utils.png)) {
		// icon = pngIcon;
		// }
		// }
		return icon;
	}
}
