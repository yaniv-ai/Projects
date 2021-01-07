package helpers;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class JGroupsComboBoxModel extends AbstractListModel<Integer> implements
		ComboBoxModel<Integer> {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private final Integer[] groupsIndex;
	Integer selectedItem = null;

	public JGroupsComboBoxModel(Integer[] groupsIndex) {
		this.groupsIndex = groupsIndex;
		if (groupsIndex != null && groupsIndex.length > 0) {
			setSelectedItem(groupsIndex[0]);
		}
	}

	@Override
	public int getSize() {
		if (groupsIndex == null) {
			return 0;
		} else {
			return groupsIndex.length;
		}
	}

	@Override
	public Integer getElementAt(int index) {
		return groupsIndex[index];
	}

	@Override
	public void setSelectedItem(Object anItem) {
		selectedItem = null;
		if (groupsIndex == null || anItem == null) {
			return;
		}
		if (anItem.getClass().equals(Integer.class)) {
			for (Integer o : groupsIndex) {
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
