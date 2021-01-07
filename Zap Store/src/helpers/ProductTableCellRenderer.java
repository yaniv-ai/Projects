package helpers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import main.MainWindow;

public class ProductTableCellRenderer extends DefaultTableCellRenderer
		implements TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label;

		int mutzarCol = table.getColumnCount() - 1;
		int producerCol = table.getColumnCount() - 2;
		int kamutCol = table.getColumnCount() - 3;
		try {
			if (column == mutzarCol) {
				label = new JLabel((String) value, SwingConstants.CENTER);
				label.setFont(MainWindow.FONT);
			} else if (column == producerCol) {
				label = new JLabel((String) value, SwingConstants.CENTER);
				label.setFont(MainWindow.FONT);
			} else if (column == kamutCol) {
				label = new JLabel(((Integer) value).toString(),
						SwingConstants.CENTER);
				label.setFont(MainWindow.FONT);
			} else {
				String price = ((Double) value).toString() + "00";
				int lastDot = price.lastIndexOf(".");

				price = price.substring(0, lastDot + 3);
				label = new JLabel(price, SwingConstants.CENTER);
				label.setFont(MainWindow.FONT);
			}
		} catch (Exception e) {
			label = new JLabel();
		}
		return label;
	}

}
