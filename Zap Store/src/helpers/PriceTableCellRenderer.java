package helpers;

import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

import vo.ProductNameVO;

import main.MainWindow;

public class PriceTableCellRenderer extends DefaultTableCellRenderer implements
		TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private DualMapSet<Integer, ProductNameVO> catalogToProductname;

	public PriceTableCellRenderer(
			DualMapSet<Integer, ProductNameVO> catalogToProductname) {
		this.catalogToProductname = catalogToProductname;
	}

	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		JLabel label;

		int mutzarCol = table.getColumnCount() - 1;
		int producerCol = table.getColumnCount() - 2;
		try {
			if (column == mutzarCol) {
				String name = catalogToProductname.getRightKey((Integer) value)
						.getProductName();
				label = new JLabel(name, SwingConstants.CENTER);
				label.setFont(MainWindow.FONT);
			} else if (column == producerCol) {
				String name = catalogToProductname.getRightKey((Integer) value)
						.getProductProducer();
				label = new JLabel(name, SwingConstants.CENTER);
				label.setFont(MainWindow.FONT);
			} else {
				label = new JLabel(((Double) value).toString(),
						SwingConstants.CENTER);
				label.setFont(MainWindow.FONT);
			}
		} catch (Exception e) {
			label = new JLabel();
		}
		return label;
	}

}
