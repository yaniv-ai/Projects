package helpers;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import vo.GroupVO;

public class JGroupComboBox extends JComboBox<Integer> {
	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;
	private final Font FONT = new Font("Dialog", Font.PLAIN, 24);
	private Integer[] groupsIndex = null;
	private Integer[] groupIDs = null;
	private String[] groupNames = null;

	public JGroupComboBox() {
		super();
		setAdditionalThings();
	}

	public JGroupComboBox(ComboBoxModel<Integer> aModel, Vector<GroupVO> groups) {
		super(aModel);
		setAdditionalThings();
		setAllData(groups);
	}

	public JGroupComboBox(Integer[] items, Vector<GroupVO> groups) {
		super(items);
		setAdditionalThings();
		setAllData(groups);
	}

	public JGroupComboBox(Vector<Integer> items, Vector<GroupVO> groups) {
		super(items);
		setAdditionalThings();
		setAllData(groups);
	}

	public JGroupComboBox(Vector<GroupVO> groups) {
		super();
		setAdditionalThings();
		setAllData(groups);
	}

	private void setAdditionalThings() {
		GroupComboRenderer renderer = new GroupComboRenderer();
		this.setRenderer(renderer);
		this.setMaximumRowCount(6);
		this.setMinimumSize(new Dimension(120, 20));
	}

	private void setAllData(Vector<GroupVO> groups) {
		if (groups != null) {
			int vectorSize = groups.size() + 1;
			groupNames = new String[vectorSize];
			groupIDs = new Integer[vectorSize];
			groupsIndex = new Integer[vectorSize];
			Iterator<GroupVO> i = groups.iterator();
			groupIDs[0] = null;
			groupNames[0] = "בחר קבוצה";
			groupsIndex[0] = 0;
			for (int j = 1; i.hasNext(); j++) {
				GroupVO group = i.next();
				groupIDs[j] = group.getGroupID();
				groupNames[j] = group.getGroupName();
				groupsIndex[j] = new Integer(j);
			}

		} else {
			groupNames = new String[1];
			groupIDs = new Integer[1];
			groupsIndex = new Integer[1];
			groupIDs[0] = null;
			groupNames[0] = "לא הוגדרו קבוצות";
			groupsIndex[0] = 0;
		}
		setModel(new JGroupsComboBoxModel(groupsIndex));
		setFont(FONT);
	}

	public Integer getSelectedGroupID() {
		Integer selectedGroupID = null;
		Integer ind = getSelectedIndex();
		if (ind != null && ind >= 0 && groupIDs != null
				&& ind < groupIDs.length) {
			selectedGroupID = groupIDs[ind];
		}
		return selectedGroupID;
	}

	public String getSelectedGroupName() {
		String selectedGroupName = null;
		Integer ind = getSelectedIndex();
		if (ind != null && ind >= 0 && groupNames != null
				&& ind < groupNames.length) {
			selectedGroupName = groupNames[ind];
		}
		return selectedGroupName;
	}

	private class GroupComboRenderer implements ListCellRenderer<Integer> {

		public GroupComboRenderer() {
		}

		@Override
		public Component getListCellRendererComponent(
				JList<? extends Integer> list, Integer value, int index,
				boolean isSelected, boolean cellHasFocus) {
			Integer selectedIndex;
			try {
				selectedIndex = ((Integer) value).intValue();
			} catch (Exception e) {
				selectedIndex = null;
			}

			JLabel label = new JLabel();
			label.setOpaque(true);
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setVerticalAlignment(SwingConstants.CENTER);
			label.setFont(FONT);

			if (selectedIndex == null) {
				label.setText("אין קבוצות להצגה");
			} else {
				if (selectedIndex >= 0 && groupNames != null
						&& selectedIndex < groupNames.length) {
					label.setText(groupNames[selectedIndex]);
				} else {
					label.setText("קבוצה מספר: " + selectedIndex);
				}
			}
			if (isSelected) {
				label.setBackground(list.getSelectionBackground());
				label.setForeground(list.getSelectionForeground());
			} else {
				label.setBackground(list.getBackground());
				label.setForeground(list.getForeground());
			}
			return label;
		}
	}
}