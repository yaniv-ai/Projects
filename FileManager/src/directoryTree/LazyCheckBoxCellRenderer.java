package directoryTree;

import java.awt.BorderLayout;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;

public class LazyCheckBoxCellRenderer extends JPanel implements TreeCellRenderer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JCheckBoxTree cbt;
	JCheckBox checkBox;

	public LazyCheckBoxCellRenderer() {
		super();
		this.setLayout(new BorderLayout());
		checkBox = new JCheckBox();
		add(checkBox, BorderLayout.CENTER);
		setOpaque(false);
	}

	public void setCheckBoxTree(JCheckBoxTree someCbt) {
		cbt = someCbt;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object obj = node.getUserObject();

		checkBox.setText(obj.toString());

		if (obj instanceof Boolean)
			checkBox.setText("Retrieving data...");
		else {
			TreePath tp = new TreePath(node.getPath());
			JCheckBoxTree.CheckedNode cn = null;
			if (cbt != null)
				cn = cbt.getCheckedNode(tp);
			if (cn == null) {
				return this;
			}
			checkBox.setSelected(cn.isSelected);
			checkBox.setText(obj.toString());
			checkBox.setOpaque(cn.isSelected && cn.hasChildren && !cn.allChildrenSelected);
		}
		return this;
	}
}