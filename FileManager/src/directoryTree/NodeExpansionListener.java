package directoryTree;

import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

//Make sure expansion is threaded and updating the tree model
// only occurs within the event dispatching thread.
class NodeExpansionListener implements TreeExpansionListener {
	private TreeModel treeModel;

	public NodeExpansionListener(TreeModel treeModel) {
		this.treeModel = treeModel;
	}

	public void treeExpanded(TreeExpansionEvent event) {
		final DefaultMutableTreeNode node = JCheckBoxTree.getTreeNode(event.getPath());
		Object obj = node.getUserObject();

		// Expand by adding any children nodes
		Thread runner = new Thread() {
			public void run() {
				if (obj != null && ((DirectoryObject) obj).expand(node)) {
					Runnable runnable = new Runnable() {
						public void run() {
							((DefaultTreeModel) treeModel).reload(node);
						}
					};
					SwingUtilities.invokeLater(runnable);
				}
			}
		};
		runner.start();
	}

	public void treeCollapsed(TreeExpansionEvent event) {
	}
}