package helpers;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class JSiduriShemComboBoxModel extends AbstractListModel<Integer>
		implements ComboBoxModel<Integer> {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private final Integer[] indexim;
	Integer selectedItem = null;

	public JSiduriShemComboBoxModel(Integer[] indexim) {
		this.indexim = indexim;
		if (indexim != null && indexim.length > 0) {
			setSelectedItem(indexim[0]);
		}
	}

	@Override
	public int getSize() {
		if (indexim == null) {
			return 0;
		} else {
			return indexim.length;
		}
	}

	@Override
	public Integer getElementAt(int index) {
		return indexim[index];
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedItem = null;
		if (indexim == null || anItem == null) {
			return;
		}
		if (anItem.getClass().equals(Integer.class)) {
			for (Integer o : indexim) {
				if (o != null && ((Integer) anItem).intValue() == o.intValue()) {
					selectedItem = o;
					return;
				}
			}
		}
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}
}
