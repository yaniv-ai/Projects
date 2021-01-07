package tableHelpers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import main.MasachRashi;

public class IntegerTableCellRenderer extends DefaultTableCellRenderer
		implements TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public IntegerTableCellRenderer() {
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		Integer number = (Integer) value;
		JLabel label;
		if (number != null) {
			label = new JLabel(number.intValue() + "", SwingConstants.CENTER);
			label.setFont(MasachRashi.FONT);
		} else {
			label = new JLabel();
		}
		return label;
	}

}
