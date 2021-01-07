package helpers;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import main.MainWindow;

import vo.ProductNameVO;

public class ProductListCellRenderer implements ListCellRenderer<Integer> {
	private DualMapSet<Integer, ProductNameVO> catalogToProductname;

	public ProductListCellRenderer(
			DualMapSet<Integer, ProductNameVO> catalogToProductname) {
		this.catalogToProductname = catalogToProductname;
	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends Integer> list, Integer value, int index,
			boolean isSelected, boolean cellHasFocus) {
		JLabel label;
		JPanel panel = new JPanel();

		try {
			String prodName = catalogToProductname.getRightKey((Integer) value)
					.getProductName();
			String prodProducer = catalogToProductname.getRightKey(
					(Integer) value).getProductProducer();
			label = new JLabel(prodName + ", " + prodProducer,
					SwingConstants.CENTER);

		} catch (Exception e) {
			label = new JLabel();
		}
		label.setFont(MainWindow.FONT);
		panel.add(label);
		if (isSelected) {
			label.setForeground(Color.WHITE);
			panel.setBackground(Color.BLUE);
		} else {
			label.setForeground(Color.BLACK);
			panel.setBackground(Color.WHITE);
		}
		return panel;
	}
}
