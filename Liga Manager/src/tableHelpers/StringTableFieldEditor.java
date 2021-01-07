package tableHelpers;

import helpers.JSensitiveTextField;

import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Rectangle;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import main.MasachRashi;

public class StringTableFieldEditor extends DefaultCellEditor {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private JSensitiveTextField field;

	public StringTableFieldEditor() {
		super(new JCheckBox());
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		Rectangle r = table.getCellRect(row, column, true);
		field = new JSensitiveTextField();
		field.setPreferredSize(r.getSize());
		field.setFont(MasachRashi.FONT);
		field.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);

		if (value != null && value.getClass().equals(String.class)) {
			field.setText(((String) value).toString());
		}
		return field;
	}

	public Object getCellEditorValue() {
		return field.getText();
	}
}
