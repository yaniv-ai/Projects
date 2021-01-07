package templates;

import java.util.Hashtable;
import java.util.Enumeration;
import javax.swing.Action;
import javax.swing.AbstractAction;

public abstract class PanelAction extends AbstractAction {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	/**
	 * Creates a new JTextAction object.
	 * 
	 * @param name
	 *            the name of the action
	 */
	public PanelAction(String name) {
		super(name);
	}

	public static final Action[] augmentList(Action[] list1) {
		Hashtable<String, Action> h = new Hashtable<String, Action>();
		for (Action a : list1) {
			String value = (String) a.getValue(Action.NAME);
			h.put((value != null ? value : ""), a);
		}
		// for (Action a : list2) {
		// String value = (String) a.getValue(Action.NAME);
		// h.put((value != null ? value : ""), a);
		// }
		Action[] actions = new Action[h.size()];
		int index = 0;
		for (Enumeration<Action> e = h.elements(); e.hasMoreElements();) {
			actions[index++] = (Action) e.nextElement();
		}
		return actions;
	}
}