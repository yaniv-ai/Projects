// 
// Decompiled by Procyon v0.5.36
// 

package helpers;

import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.Component;
import javax.swing.JList;
import java.awt.Dimension;
import javax.swing.ListCellRenderer;
import vo.SubVO;
import java.util.Vector;
import java.awt.Font;
import javax.swing.JComboBox;

public class JSubComboBox extends JComboBox<Integer> {
	private static final long serialVersionUID = 1L;
	private final Font FONT;
	private Integer[] indexim;
	private Vector<SubVO> subs;

	public JSubComboBox() {
		this.FONT = new Font("Dialog", 0, 18);
		this.indexim = null;
		this.subs = null;
		this.setAdditionalThings();
	}

	private void setAdditionalThings() {
		final StringComboRenderer renderer = new StringComboRenderer();
		this.setRenderer(renderer);
		this.setMaximumRowCount(6);
		this.setMinimumSize(new Dimension(120, 20));
	}

	public void setAllData(final Vector<SubVO> subs) {
		if (subs != null) {
			this.subs = new Vector<SubVO>(subs.size());
			final int vectorSize = subs.size();
			this.indexim = new Integer[vectorSize];
			for (int i = 0; i < vectorSize; ++i) {
				this.indexim[i] = i;
				this.subs.add(i, new SubVO(subs.elementAt(i)));
			}
			this.subs = subs;
		} else {
			this.indexim = new Integer[0];
			this.subs = null;
		}
		this.setModel(new JSubComboBoxModel(this.indexim));
	}

	public SubVO getSelectedSub() {
		SubVO subVO = null;
		if (this.subs != null) {
			final int i = this.getSelectedIndex();
			if (i >= 0) {
				subVO = new SubVO(this.subs.get(i));
			}
		}
		return subVO;
	}

	private class StringComboRenderer implements ListCellRenderer<Integer> {
		public StringComboRenderer() {
		}

		@Override
		public Component getListCellRendererComponent(final JList<? extends Integer> list, final Integer value,
				final int index, final boolean isSelected, final boolean cellHasFocus) {
			Integer selectedIndex;
			try {
				selectedIndex = value;
			} catch (Exception e) {
				selectedIndex = null;
			}
			final JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			panel.setOpaque(true);
			String shem = "";
			if (selectedIndex != null) {
				if (selectedIndex >= 0 && JSubComboBox.this.subs != null
						&& selectedIndex < JSubComboBox.this.subs.size()) {
					shem = String.valueOf(JSubComboBox.this.subs.get(selectedIndex).getTimeLine().toString()) + " - "
							+ JSubComboBox.this.subs.get(selectedIndex).getLine1();
				} else {
					shem = "";
				}
			}
			final JLabel label = new JLabel(shem, 2);
			label.setFont(JSubComboBox.this.FONT);
			panel.add(label);
			if (isSelected) {
				panel.setBackground(list.getSelectionBackground());
				panel.setForeground(list.getSelectionForeground());
			} else {
				panel.setBackground(list.getBackground());
				panel.setForeground(list.getForeground());
			}
			return panel;
		}
	}
}
