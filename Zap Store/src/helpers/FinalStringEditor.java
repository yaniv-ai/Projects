package helpers;

import java.awt.Component;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

public class FinalStringEditor extends DefaultCellEditor {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public FinalStringEditor() {
		super(new JCheckBox());
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		return null;
	}
}
