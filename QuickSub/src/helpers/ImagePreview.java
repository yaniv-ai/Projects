// 
// Decompiled by Procyon v0.5.36
// 

package helpers;

import java.awt.Graphics;
import java.beans.PropertyChangeEvent;
import java.awt.Dimension;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.ImageIcon;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;

public class ImagePreview extends JComponent implements PropertyChangeListener
{
    private static final long serialVersionUID = 1L;
    ImageIcon thumbnail;
    File file;
    
    public ImagePreview(final JFileChooser fc) {
        this.thumbnail = null;
        this.file = null;
        this.setPreferredSize(new Dimension(100, 50));
        fc.addPropertyChangeListener(this);
    }
    
    public void loadImage() {
        if (this.file == null) {
            this.thumbnail = null;
            return;
        }
        final ImageIcon tmpIcon = new ImageIcon(this.file.getPath());
        if (tmpIcon != null) {
            if (tmpIcon.getIconWidth() > 90) {
                this.thumbnail = new ImageIcon(tmpIcon.getImage().getScaledInstance(90, -1, 1));
            }
            else {
                this.thumbnail = tmpIcon;
            }
        }
    }
    
    @Override
    public void propertyChange(final PropertyChangeEvent e) {
        boolean update = false;
        final String prop = e.getPropertyName();
        if ("directoryChanged".equals(prop)) {
            this.file = null;
            update = true;
        }
        else if ("SelectedFileChangedProperty".equals(prop)) {
            this.file = (File)e.getNewValue();
            update = true;
        }
        if (update) {
            this.thumbnail = null;
            if (this.isShowing()) {
                this.loadImage();
                this.repaint();
            }
        }
    }
    
    @Override
    protected void paintComponent(final Graphics g) {
        if (this.thumbnail == null) {
            this.loadImage();
        }
        if (this.thumbnail != null) {
            int x = this.getWidth() / 2 - this.thumbnail.getIconWidth() / 2;
            int y = this.getHeight() / 2 - this.thumbnail.getIconHeight() / 2;
            if (y < 0) {
                y = 0;
            }
            if (x < 5) {
                x = 5;
            }
            this.thumbnail.paintIcon(this, g, x, y);
        }
    }
}
