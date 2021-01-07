package directoryTree;

import java.io.File;
import java.util.ArrayList;

import javax.swing.tree.DefaultMutableTreeNode;

// sample impl of a domain-object; should have expand method
public class DirectoryObject {
	private Object obj;
	private boolean hasChildren;

	public DirectoryObject(Object obj) {
		this.obj = obj;
		hasChildren = false;
		if (obj.getClass() == File.class && obj != null) {
			File dir = (File) obj;
			File[] subDirs = dir.listFiles(File::isDirectory);
			if (dir.isDirectory() && subDirs != null && subDirs.length > 0) {
				hasChildren = true;
			}
		}
	}

	// Expand the tree at parent node and add nodes.
	public boolean expand(DefaultMutableTreeNode parent) {
		DefaultMutableTreeNode flagNode = (DefaultMutableTreeNode) parent.getFirstChild();
		if (flagNode == null) // No flag
			return false;
		Object obj = flagNode.getUserObject();
		if (!(obj instanceof Boolean))
			return false; // Already expanded

		parent.removeAllChildren(); // Remove FlagNode

		File[] dirs = getChildren();
		if (dirs == null)
			return true;

		// Create a sorted list of domain-objects
		// ArrayList sortedChildDomainObjects = new ArrayList();
		ArrayList<DirectoryObject> sortedChildDomainObjects = new ArrayList<DirectoryObject>();

		for (File dir : dirs) {
			DirectoryObject newNode = new DirectoryObject(dir);
			boolean isAdded = false;
			for (int i = 0; i < sortedChildDomainObjects.size(); i++) {
				DirectoryObject nd = (DirectoryObject) sortedChildDomainObjects.get(i);
				if (newNode.compareTo(nd) < 0) {
					sortedChildDomainObjects.add(i, newNode);
					isAdded = true;
					break;
				}
			}
			if (!isAdded)
				sortedChildDomainObjects.add(newNode);
		}

		// Add children nodes under parent in the tree
		for (Object aChild : sortedChildDomainObjects) {
			DirectoryObject nd = (DirectoryObject) aChild;
			DefaultMutableTreeNode node = new DefaultMutableTreeNode(nd);
			parent.add(node);

			if (nd.hasChildren)
				node.add(new DefaultMutableTreeNode(true));
		}

		return true;
	}

	private int compareTo(DirectoryObject toCompare) {
		assert toCompare.obj != null;
		return obj.toString().compareToIgnoreCase(toCompare.obj.toString());
	}

	private File[] getChildren() {
		if (obj == null || (!hasChildren) || (obj.getClass() != File.class)) {
			return null;
		}

		File[] files = ((File) obj).listFiles(File::isDirectory);

		if (files != null && files.length > 0) {
			return files;
		} else
			return null;
	}

	public String toString() {
		if (obj == null) {
			return "(EMPTY)";
		}
		if (obj.getClass() == File.class && (((File) obj).getName()).length() > 0) {
			return ((File) obj).getName();
		} else {
			return obj.toString();
		}

	}

	public File getFile() {
		if (obj.getClass() == File.class) {
			return (File) obj;
		} else {
			return null;
		}
	}
}