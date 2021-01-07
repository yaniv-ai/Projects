package helpers;

import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import main.MainWindow;

public class PriceTableFieldEditor extends DefaultCellEditor {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private JSensitiveDoubleField field;

	public PriceTableFieldEditor() {
		super(new JCheckBox());
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		int mutzarCol = table.getColumnCount() - 1;
		int producerCol = table.getColumnCount() - 2;
		if (column == mutzarCol || column == producerCol) {
			return null;
		}
		Rectangle r = table.getCellRect(row, column, true);
		field = new JSensitiveDoubleField();
		field.setPreferredSize(r.getSize());
		field.setFont(MainWindow.FONT);
		if (value != null && value.getClass().equals(Double.class)) {
			field.setText(((Double) value).toString());
		}
		return field;
	}

	public Object getCellEditorValue() {
		return field.toDouble();
	}
}
