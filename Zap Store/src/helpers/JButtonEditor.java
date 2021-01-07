package helpers;

import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.AbstractButton;
import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTable;

public class JButtonEditor extends DefaultCellEditor implements ItemListener {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private AbstractButton button;

	public JButtonEditor() {
		super(new JCheckBox());
	}

	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column) {
		if (value == null)
			return null;
		JPanel panel = new JPanel();
		button = (AbstractButton) value;
		button.addItemListener(this);
		panel.add(button);
		return panel;
	}

	public Object getCellEditorValue() {
		button.removeItemListener(this);
		return button;
	}

	public void itemStateChanged(ItemEvent e) {
		super.fireEditingStopped();
	}
}
