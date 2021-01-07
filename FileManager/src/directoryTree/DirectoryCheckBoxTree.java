package directoryTree;

import javax.swing.*;
import javax.swing.tree.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Vector;

public class DirectoryCheckBoxTree extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 106929825981208195L;
	private HashSet<TreePath> includedPaths = new HashSet<>();
	private HashSet<TreePath> excludedPaths = new HashSet<>();
	private TreeModel treeModel;
	private DefaultMutableTreeNode topNode = new DefaultMutableTreeNode("Root");
	final private JCheckBoxTree cbt;

	public DirectoryCheckBoxTree() {
		super();
		setSize(500, 500);
		setLayout(new BorderLayout());

		File[] files = File.listRoots();

		treeModel = buildModel(files);
		LazyCheckBoxCellRenderer treeCellRenderer = new LazyCheckBoxCellRenderer();
		cbt = new JCheckBoxTree(treeModel, null, treeCellRenderer);
		treeCellRenderer.setCheckBoxTree(cbt);
		cbt.addTreeExpansionListener(new NodeExpansionListener(treeModel));

		JScrollPane s = new JScrollPane();
		s.getViewport().add(cbt);
		s.setMinimumSize(new Dimension(300, 300));
		add(s, BorderLayout.CENTER);

		cbt.addCheckChangeEventListener(new JCheckBoxTree.CheckChangeEventListener() {
			public void checkStateChanged(JCheckBoxTree.CheckChangeEvent event) {
				updatePaths(cbt, event);
			}
		});
	}

	// (As prelude)Purges any of clickedPath's children from the 2 path-sets.
	// Then adds/removes clickedPath from the 2 path-sets if appropriate.
	protected void updatePaths(JCheckBoxTree cbt, JCheckBoxTree.CheckChangeEvent event) {
		boolean parentAlreadyIncluded = false;
		boolean parentAlreadyExcluded = false;
		TreePath clickedPath = (TreePath) event.getSource();
		HashSet<TreePath> toBeRemoved = new HashSet<>();

		// When a node is included/excluded, its children are implied as
		// included/excluded.
		// Note: The direct-parent check is needed to avoid problem if immediate-parent
		// is excluded
		// but grand-father/higher-ancestor is included
		for (TreePath exp : excludedPaths) {
			if (clickedPath.isDescendant(exp)) // exp is descended from clickedPath
				toBeRemoved.add(exp);
			if (isParent(exp, clickedPath)) // clickedPath is child of exp
				parentAlreadyExcluded = true;
		}
		excludedPaths.removeAll(toBeRemoved);
		toBeRemoved.clear();

		for (TreePath inp : includedPaths) {
			if (clickedPath.isDescendant(inp)) // inp is descended from clickedPath
				toBeRemoved.add(inp);
			if (isParent(inp, clickedPath)) // clickedPath is child of inp
				parentAlreadyIncluded = true;
		}
		includedPaths.removeAll(toBeRemoved);
		toBeRemoved.clear();

		// Now add/remove clickedPath from the path-sets as appropriate
		if (cbt.getCheckMode(clickedPath)) { // selected => to be included
			if (!parentAlreadyIncluded)
				includedPaths.add(clickedPath);
			excludedPaths.remove(clickedPath);
		} else { // deselected => to be excluded
			if (!parentAlreadyExcluded)
				excludedPaths.add(clickedPath);
			includedPaths.remove(clickedPath);
		}
	}

	// returns true if aPath is immediate parent of bPath; both must be non-null
	protected boolean isParent(TreePath aPath, TreePath bPath) {
		return aPath.equals(bPath.getParentPath());
	}

	// ====================== Your Domain specific stuff goes here

	protected TreeModel buildModel(File[] dirs) {
		DefaultMutableTreeNode userDirsNode = new DefaultMutableTreeNode(new DirectoryObject("User Directories"));
		DefaultMutableTreeNode userRootNode = new DefaultMutableTreeNode(new DirectoryObject("Computer Directories"));
		topNode.add(userDirsNode);
		topNode.add(userRootNode);

		String[] userDirs = { "C:\\Users\\" + System.getProperty("user.name") + "\\Documents\\",
				"C:\\Users\\" + System.getProperty("user.name") + "\\Downloads\\",
				"C:\\Users\\" + System.getProperty("user.name") + "\\Music\\",
				"C:\\Users\\" + System.getProperty("user.name") + "\\Pictures\\",
				"C:\\Users\\" + System.getProperty("user.name") + "\\Videos\\" };

		if (userDirs != null && userDirs.length > 0) {
			for (String userDir : userDirs) {
				File dir = new File(userDir);
				if (dir.isDirectory()) {
					DirectoryObject d = new DirectoryObject(dir);
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(d);
					userDirsNode.add(node);
					node.add(new DefaultMutableTreeNode(true));
				}
			}
		}

		if (dirs != null && dirs.length > 0) {
			for (File dir : dirs) {
				if (dir.isDirectory()) {
					DirectoryObject d = new DirectoryObject(dir);
					DefaultMutableTreeNode node = new DefaultMutableTreeNode(d);
					userRootNode.add(node);
					node.add(new DefaultMutableTreeNode(true));
				}
			}
		}

		return new DefaultTreeModel(topNode);
	}

	// Making two lists. 1) selected 2) un-selected.
	// Then I remove all parent directories if they have un-selected sub directories
	// Then I remove ALL subdirectories if their parent still exist, because it
	// means all subdirectories are chosen
	public ArrayList<String> getSelectedDirs() {
		Vector<DefaultMutableTreeNode> nodes = getNodes(topNode);
		ArrayList<String> selectedDirs = new ArrayList<String>();
		ArrayList<String> unSelectedDirs = new ArrayList<String>();
		if (nodes != null) {
			for (int i = 0; i < nodes.size(); i++) {
				DefaultMutableTreeNode node = nodes.elementAt(i);
				Object o = node.getUserObject();
				// -------------
				TreePath tp = new TreePath(node.getPath());
				JCheckBoxTree.CheckedNode cn = null;
				boolean selected = false;
				if (cbt != null) {
					cn = cbt.getCheckedNode(tp);
					selected = cn.isSelected;
				}
				// ----------
				if (o.getClass() == DirectoryObject.class) {
					DirectoryObject dirObj = (DirectoryObject) o;
					File f = dirObj.getFile();
					if (f != null) {
						// Making a list of selected and unselected
						if (selected) {
							selectedDirs.add(f.getAbsolutePath());
						} else {
							unSelectedDirs.add(f.getAbsolutePath());
						}
					}
				}
			}
		}

		LinkedHashSet<String> hashSet = new LinkedHashSet<String>(selectedDirs);
		selectedDirs = new ArrayList<String>(hashSet);
		hashSet = new LinkedHashSet<String>(unSelectedDirs);
		unSelectedDirs = new ArrayList<String>(hashSet);

		// Removing parent dirs if they have unselected directories
		Iterator<String> itr = selectedDirs.iterator();
		while (itr.hasNext()) {
			String dir = itr.next();
			if (containedInDirs(dir, unSelectedDirs)) {
				itr.remove();
			}
		}

		// Removing subdirectories if parent still exist
		itr = selectedDirs.iterator();
		while (itr.hasNext()) {
			String dir = itr.next();
			if (checkForParent(dir, selectedDirs)) {
				itr.remove();
			}
		}

		Collections.sort(selectedDirs, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return s1.compareToIgnoreCase(s2);
			}
		});
		return selectedDirs;
	}

	// Checking if parent dirs have unselected directories
	private boolean containedInDirs(String dir, ArrayList<String> unSelectedDirs) {
		Iterator<String> itr = unSelectedDirs.iterator();
		while (itr.hasNext()) {
			String unSelectedDir = itr.next();
			int n = unSelectedDir.lastIndexOf(File.separator);
			String unSelectedDirParent = "";
			if (n >= 2 && n < unSelectedDir.length()) {
				unSelectedDirParent = unSelectedDir.substring(0, n + 1);
			}
			if (unSelectedDirParent.contains(dir) && unSelectedDir.length() != dir.length()) {
				return true;
			}
		}
		return false;
	}

	// Checking if subdirs have parents
	private boolean checkForParent(String dir, ArrayList<String> selectedDirs) {
		int n = dir.lastIndexOf(File.separator);
		String dirParent = "";
		if (n >= 2 && n < dir.length()) {
			dirParent = dir.substring(0, n + 1);
		}
		Iterator<String> itr = selectedDirs.iterator();
		while (itr.hasNext()) {
			String parent = itr.next();
			if (dirParent.contains(parent) && dir.length() != parent.length()) {
				return true;
			}
		}
		return false;
	}

	public Vector<DefaultMutableTreeNode> getNodes(DefaultMutableTreeNode parrentNode) {
		if (parrentNode == null) {
			return null;
		}
		Vector<DefaultMutableTreeNode> nodes = new Vector<DefaultMutableTreeNode>();
		nodes.add(parrentNode);
		if (parrentNode.getChildCount() > 0) {
			for (int i = 0; i < parrentNode.getChildCount(); i++) {
				Vector<DefaultMutableTreeNode> childNodes = getNodes(
						(DefaultMutableTreeNode) parrentNode.getChildAt(i));
				if (childNodes != null) {
					for (int j = 0; j < childNodes.size(); j++) {
						nodes.add(childNodes.elementAt(j));
					}
				}
			}
		}
		return nodes;
	}
}