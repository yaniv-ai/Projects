package tableHelpers;

import helpers.JSensitiveIntField;

import java.awt.Component;
import java.awt.Rectangle;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import main.MasachRashi;

public class IntegerTableFieldEditor extends DefaultCellEditor {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private JSensitiveIntField field;

	public IntegerTableFieldEditor() {
		super(new JCheckBox());
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		
		Rectangle r = table.getCellRect(row, column, true);
		field = new JSensitiveIntField();
		field.setPreferredSize(r.getSize());
		field.setFont(MasachRashi.FONT);
		if (value != null && value.getClass().equals(Integer.class)) {
			field.setText(((Integer) value).toString());
		}
		return field;
	}

	public Object getCellEditorValue() {
		return field.toInt();
	}
}
