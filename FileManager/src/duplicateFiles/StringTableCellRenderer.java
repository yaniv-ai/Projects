package duplicateFiles;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import ui.MainWindow;

public class StringTableCellRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public StringTableCellRenderer() {
	}

	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		String text = (String) value;
		JLabel label;
		if (text != null) {
			if (column == 3) {
				label = new JLabel(text, SwingConstants.CENTER);
			} else {
				label = new JLabel(text, SwingConstants.LEFT);
			}
			label.setFont(MainWindow.FONT);
		} else {
			label = new JLabel();
		}
		return label;
	}
}
