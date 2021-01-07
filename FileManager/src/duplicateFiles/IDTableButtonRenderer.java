package duplicateFiles;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;

public class IDTableButtonRenderer extends DefaultTableCellRenderer implements TableCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		IDButton button = (IDButton) value;
		Color backColor;
		int color = 0;
		if (button.getId() != null) {
			color = button.getId() % 7;
		}
		switch (color) {
		case 0:
			backColor = Color.YELLOW;
			break;
		case 1:
			backColor = Color.LIGHT_GRAY;
			break;
		case 2:
			backColor = Color.CYAN;
			break;
		case 3:
			backColor = Color.GREEN;
			break;
		case 4:
			backColor = Color.MAGENTA;
			break;
		case 5:
			backColor = Color.ORANGE;
			break;
		case 6:
			backColor = Color.PINK;
			break;
		default:
			backColor = Color.YELLOW;
			break;

		}

		if (isSelected) {
			button.setForeground(table.getSelectionForeground());
//			button.setForeground(backColor);
			button.setBackground(table.getSelectionBackground());
//			button.setBackground(backColor);
		} else {
			button.setForeground(table.getForeground());
//			button.setForeground(backColor);
//			button.setBackground(UIManager.getColor("Button.background"));
			button.setBackground(backColor);
//			button.setBackground(table.getSelectionBackground());
		}
		return button;
	}
}