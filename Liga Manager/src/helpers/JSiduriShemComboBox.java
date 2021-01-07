package helpers;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.SwingConstants;

import vo.SiduriShemVO;

public class JSiduriShemComboBox extends JComboBox<Integer> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final Font FONT = new Font("Dialog", Font.PLAIN, 18);
	private Integer[] indexim = null;
	private Integer[] siduriim = null;
	private String[] shemot = null;

	// private final String teurBechar;
	// private final String teurLoHugderu;
	// private final String teurEinNetunim;

	public JSiduriShemComboBox(String teurBechar, String teurLoHugderu,
			String teurEinNetunim) {
		super();
		// this.teurBechar = teurBechar;
		// this.teurLoHugderu = teurLoHugderu;
		// this.teurEinNetunim = teurEinNetunim;
		setAdditionalThings();
	}

	public JSiduriShemComboBox(ComboBoxModel<Integer> aModel,
			Vector<SiduriShemVO> siduriShemot, String teurBechar,
			String teurLoHugderu, String teurEinNetunim) {
		super(aModel);
		// this.teurBechar = teurBechar;
		// this.teurLoHugderu = teurLoHugderu;
		// this.teurEinNetunim = teurEinNetunim;
		setAdditionalThings();
		setAllData(siduriShemot);
	}

	public JSiduriShemComboBox(Integer[] items,
			Vector<SiduriShemVO> siduriShemot, String teurBechar,
			String teurLoHugderu, String teurEinNetunim) {
		super(items);
		// this.teurBechar = teurBechar;
		// this.teurLoHugderu = teurLoHugderu;
		// this.teurEinNetunim = teurEinNetunim;
		setAdditionalThings();
		setAllData(siduriShemot);
	}

	public JSiduriShemComboBox(Vector<Integer> items,
			Vector<SiduriShemVO> siduriShemot, String teurBechar,
			String teurLoHugderu, String teurEinNetunim) {
		super(items);
		// this.teurBechar = teurBechar;
		// this.teurLoHugderu = teurLoHugderu;
		// this.teurEinNetunim = teurEinNetunim;
		setAdditionalThings();
		setAllData(siduriShemot);
	}

	public JSiduriShemComboBox(Vector<SiduriShemVO> siduriShemot,
			String teurBechar, String teurLoHugderu, String teurEinNetunim) {
		super();
		// this.teurBechar = teurBechar;
		// this.teurLoHugderu = teurLoHugderu;
		// this.teurEinNetunim = teurEinNetunim;
		setAdditionalThings();
		setAllData(siduriShemot);
	}

	private void setAdditionalThings() {
		StringComboRenderer renderer = new StringComboRenderer();
		this.setRenderer(renderer);
		this.setMaximumRowCount(6);
		this.setMinimumSize(new Dimension(120, 20));
	}

	public void setAllData(Vector<SiduriShemVO> siduriShemot) {
		if (siduriShemot != null) {
			int vectorSize = siduriShemot.size();
			indexim = new Integer[vectorSize];
			siduriim = new Integer[vectorSize];
			shemot = new String[vectorSize];

			Iterator<SiduriShemVO> i = siduriShemot.iterator();

			for (int j = 0; i.hasNext(); j++) {
				SiduriShemVO siduriShem = i.next();
				indexim[j] = new Integer(j);
				siduriim[j] = siduriShem.getSiduri();
				shemot[j] = siduriShem.getShem();

			}
		} else {
			indexim = new Integer[0];
			siduriim = new Integer[0];
			shemot = new String[0];

		}
		setModel(new JSiduriShemComboBoxModel(indexim));
	}

	public Integer getSelectedSiduri() {
		Integer selectedSiduri = null;
		int ind = getSelectedIndex();
		if (ind >= 0 && siduriim != null && ind < siduriim.length) {
			selectedSiduri = siduriim[ind];
		}
		return selectedSiduri;
	}

	public String getSelectedShem() {
		String selectedShem = null;
		int ind = getSelectedIndex();
		if (ind >= 0 && shemot != null && ind < shemot.length) {
			selectedShem = shemot[ind];
		}
		return selectedShem;
	}

	private class StringComboRenderer implements ListCellRenderer<Integer> {

		public StringComboRenderer() {
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

			JPanel panel = new JPanel();
			panel.setBorder(BorderFactory.createLineBorder(Color.black, 1));
			panel.setOpaque(true);
			String shem = "";
			if (selectedIndex != null) {
				if (selectedIndex >= 0 && shemot != null
						&& selectedIndex < shemot.length) {
					shem = shemot[selectedIndex];
				} else {
					shem = "";
				}
			}
			JLabel label = new JLabel(shem, SwingConstants.CENTER);
			label.setFont(FONT);
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