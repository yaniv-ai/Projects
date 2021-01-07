// 
// Decompiled by Procyon v0.5.36
// 

package helpers;

import javax.swing.Icon;
import java.io.File;
import javax.swing.filechooser.FileView;

public class SubtitleFileView extends FileView
{
    @Override
    public String getName(final File f) {
        return null;
    }
    
    @Override
    public String getDescription(final File f) {
        return null;
    }
    
    @Override
    public Boolean isTraversable(final File f) {
        return null;
    }
    
    @Override
    public String getTypeDescription(final File f) {
        final String extension = Utils.getExtension(f);
        String type = null;
        if (extension != null) {
            if (extension.equalsIgnoreCase("SUB")) {
                type = "SUB file";
            }
            else if (extension.equals("SRT")) {
                type = "SRT file";
            }
            else if (extension.equals("TXT")) {
                type = "TXT file";
            }
        }
        return type;
    }
    
    @Override
    public Icon getIcon(final File f) {
        final Icon icon = null;
        return icon;
    }
}
