package helpers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

public class Utils {
	private EqualMapSet<String, String> extensionType = null;
	private String extensionPropertieFilename = "ExtensionProperties.txt";

	public Utils() throws IOException {
		extensionType = new EqualMapSet<String, String>();
		File f = new File(extensionPropertieFilename);
		if (f.exists()) {

		} else {
			Writer w = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f)));
			String line = "List of known extensions:";
			w.append(line).append("\r\n");
			w.flush();
			w.close();
		}
	}

	public String getFileType(String extension) {
		String type = extensionType.get(extension);
		if (type == null) {
			type = extension;
		}
		return type;
	}
}
