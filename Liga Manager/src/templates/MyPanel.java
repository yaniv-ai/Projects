package templates;

import java.awt.AWTEvent;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.event.EventListenerList;

public class MyPanel extends JPanel {
	private String command = null;
	private Dimension screenDimension = null;

	private static final long serialVersionUID = 1L;

	public MyPanel() {
		super();

	}

	public MyPanel(Dimension screenDimension) {
		super();
		this.screenDimension = screenDimension;
	}

	public MyPanel(boolean isDoubleBuffered) {
		super(isDoubleBuffered);
	}

	public MyPanel(LayoutManager layout, boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
	}

	public MyPanel(LayoutManager layout) {
		super(layout);
	}

	public Dimension getScreenDimension() {
		return screenDimension;
	}

	public void setScreenDimension(Dimension screenDimension) {
		this.screenDimension = screenDimension;
	}

	/**
	 * Adds the specified action listener to receive action events from this
	 * textfield.
	 * 
	 * @param l
	 *            the action listener to be added
	 */
	public synchronized void addActionListener(ActionListener l) {
		listenerList.add(ActionListener.class, l);
	}

	/**
	 * Removes the specified action listener so that it no longer receives
	 * action events from this textfield.
	 * 
	 * @param l
	 *            the action listener to be removed
	 */
	public synchronized void removeActionListener(ActionListener l) {
		if ((l != null) && (getAction() == l)) {
			setAction(null);
		} else {
			listenerList.remove(ActionListener.class, l);
		}
	}

	/**
	 * Returns an array of all the <code>ActionListener</code>s added to this
	 * JTextField with addActionListener().
	 * 
	 * @return all of the <code>ActionListener</code>s added or an empty array
	 *         if no listeners have been added
	 * @since 1.4
	 */
	public synchronized ActionListener[] getActionListeners() {
		return listenerList.getListeners(ActionListener.class);
	}

	/**
	 * Notifies all listeners that have registered interest for notification on
	 * this event type. The event instance is lazily created. The listener list
	 * is processed in last to first order.
	 * 
	 * @see EventListenerList
	 */
	protected void fireActionPerformed() {
		// Guaranteed to return a non-null array
		Object[] listeners = listenerList.getListenerList();
		int modifiers = 0;
		AWTEvent currentEvent = EventQueue.getCurrentEvent();
		if (currentEvent instanceof InputEvent) {
			modifiers = ((InputEvent) currentEvent).getModifiers();
		} else if (currentEvent instanceof ActionEvent) {
			modifiers = ((ActionEvent) currentEvent).getModifiers();
		}
		ActionEvent e = new ActionEvent(this, ActionEvent.ACTION_PERFORMED,
				(command != null) ? command : this.getClass().toString(),
				EventQueue.getMostRecentEventTime(), modifiers);

		// Process the listeners last to first, notifying
		// those that are interested in this event
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ActionListener.class) {
				((ActionListener) listeners[i + 1]).actionPerformed(e);
			}
		}
	}

	/**
	 * Sets the command string used for action events.
	 * 
	 * @param command
	 *            the command string
	 */
	public void setActionCommand(String command) {
		this.command = command;
	}

	private Action action;
	private PropertyChangeListener actionPropertyChangeListener;

	public void setAction(Action a) {
		Action oldValue = getAction();
		if (action == null || !action.equals(a)) {
			action = a;
			if (oldValue != null) {
				removeActionListener(oldValue);
				oldValue.removePropertyChangeListener(actionPropertyChangeListener);
				actionPropertyChangeListener = null;
			}
			configurePropertiesFromAction(action);
			if (action != null) {
				// Don't add if it is already a listener
				if (!isListener(ActionListener.class, action)) {
					addActionListener(action);
				}
				// Reverse linkage:
				actionPropertyChangeListener = createActionPropertyChangeListener(action);
				action.addPropertyChangeListener(actionPropertyChangeListener);
			}
			firePropertyChange("action", oldValue, action);
		}
	}

	protected PropertyChangeListener createActionPropertyChangeListener(Action a) {
		return new MyPanelActionPropertyChangeListener(this, a);
	}

	private static class MyPanelActionPropertyChangeListener extends
			MyActionPropertyChangeListener<MyPanel> {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public MyPanelActionPropertyChangeListener(MyPanel c, Action a) {
			super(c, a);
		}

		@Override
		protected void actionPropertyChanged(MyPanel myPanel, Action action,
				PropertyChangeEvent e) {
			if (MyPanelActionPropertyChangeListener.shouldReconfigure(e)) {
				myPanel.configurePropertiesFromAction(action);
			} else {
				myPanel.actionPropertyChanged(action, e.getPropertyName());
			}
		}

	}

	private boolean isListener(Class<?> c, ActionListener a) {
		boolean isListener = false;
		Object[] listeners = listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == c && listeners[i + 1] == a) {
				isListener = true;
			}
		}
		return isListener;
	}

	/**
	 * Returns the currently set <code>Action</code> for this
	 * <code>ActionEvent</code> source, or <code>null</code> if no
	 * <code>Action</code> is set.
	 * 
	 * @return the <code>Action</code> for this <code>ActionEvent</code> source,
	 *         or <code>null</code>
	 * @since 1.3
	 * @see Action
	 * @see #setAction
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * Sets the properties on this textfield to match those in the specified
	 * <code>Action</code>. Refer to <a href="Action.html#buttonActions"> Swing
	 * Components Supporting <code>Action</code></a> for more details as to
	 * which properties this sets.
	 * 
	 * @param a
	 *            the <code>Action</code> from which to get the properties, or
	 *            <code>null</code>
	 * @since 1.3
	 * @see Action
	 * @see #setAction
	 */
	protected void configurePropertiesFromAction(Action a) {
		setEnabledFromAction(this, a);
		setToolTipTextFromAction(this, a);
		setActionCommandFromAction(a);
	}

	/**
	 * Updates the textfield's state in response to property changes in
	 * associated action. This method is invoked from the
	 * {@code PropertyChangeListener} returned from
	 * {@code createActionPropertyChangeListener}. Subclasses do not normally
	 * need to invoke this. Subclasses that support additional {@code Action}
	 * properties should override this and {@code configurePropertiesFromAction}
	 * .
	 * <p>
	 * Refer to the table at <a href="Action.html#buttonActions"> Swing
	 * Components Supporting <code>Action</code></a> for a list of the
	 * properties this method sets.
	 * 
	 * @param action
	 *            the <code>Action</code> associated with this textfield
	 * @param propertyName
	 *            the name of the property that changed
	 * @since 1.6
	 * @see Action
	 * @see #configurePropertiesFromAction
	 */
	protected void actionPropertyChanged(Action action, String propertyName) {
		if (propertyName == Action.ACTION_COMMAND_KEY) {
			setActionCommandFromAction(action);
		} else if (propertyName == "enabled") {
			setEnabledFromAction(this, action);
		} else if (propertyName == Action.SHORT_DESCRIPTION) {
			setToolTipTextFromAction(this, action);
		}
	}

	private void setActionCommandFromAction(Action action) {
		setActionCommand((action == null) ? null : (String) action
				.getValue(Action.ACTION_COMMAND_KEY));
	}

	/**
	 * Processes action events occurring on this textfield by dispatching them
	 * to any registered <code>ActionListener</code> objects. This is normally
	 * called by the controller registered with textfield.
	 */
	public void postActionEvent() {
		fireActionPerformed();
	}

	/**
	 * Sets the enabled state of a component from an Action.
	 * 
	 * @param c
	 *            the Component to set the enabled state on
	 * @param a
	 *            the Action to set the enabled state from, may be null
	 */
	static void setEnabledFromAction(JComponent c, Action a) {
		c.setEnabled((a != null) ? a.isEnabled() : true);
	}

	/**
	 * Sets the tooltip text of a component from an Action.
	 * 
	 * @param c
	 *            the Component to set the tooltip text on
	 * @param a
	 *            the Action to set the tooltip text from, may be null
	 */
	static void setToolTipTextFromAction(JComponent c, Action a) {
		c.setToolTipText(a != null ? (String) a
				.getValue(Action.SHORT_DESCRIPTION) : null);
	}

}
