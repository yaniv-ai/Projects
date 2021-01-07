// 
// Decompiled by Procyon v0.5.36
// 

package helpers;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public class SubtitleFilter extends FileFilter
{
    @Override
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        final String extension = Utils.getExtension(f);
        return extension != null && (extension.equalsIgnoreCase("SRT") || extension.equalsIgnoreCase("SUB") || extension.equalsIgnoreCase("TXT"));
    }
    
    @Override
    public String getDescription() {
        return "Just Subtitles";
    }
}
