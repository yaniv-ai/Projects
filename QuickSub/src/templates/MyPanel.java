// 
// Decompiled by Procyon v0.5.36
// 

package templates;

import java.beans.PropertyChangeEvent;
import javax.swing.JComponent;
import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.beans.PropertyChangeListener;
import javax.swing.Action;
import javax.swing.JPanel;

public class MyPanel extends JPanel {
	private String command;
	private static final long serialVersionUID = 1L;
	private Action action;
	private PropertyChangeListener actionPropertyChangeListener;

	public MyPanel() {
		this.command = null;
	}

	public MyPanel(final boolean isDoubleBuffered) {
		super(isDoubleBuffered);
		this.command = null;
	}

	public MyPanel(final LayoutManager layout, final boolean isDoubleBuffered) {
		super(layout, isDoubleBuffered);
		this.command = null;
	}

	public MyPanel(final LayoutManager layout) {
		super(layout);
		this.command = null;
	}

	public synchronized void addActionListener(final ActionListener l) {
		this.listenerList.add(ActionListener.class, l);
	}

	public synchronized void removeActionListener(final ActionListener l) {
		if (l != null && this.getAction() == l) {
			this.setAction(null);
		} else {
			this.listenerList.remove(ActionListener.class, l);
		}
	}

	public synchronized ActionListener[] getActionListeners() {
		return this.listenerList.getListeners(ActionListener.class);
	}

	protected void fireActionPerformed() {
		final Object[] listeners = this.listenerList.getListenerList();
		int modifiers = 0;
		final AWTEvent currentEvent = EventQueue.getCurrentEvent();
		if (currentEvent instanceof InputEvent) {
			modifiers = ((InputEvent) currentEvent).getModifiersEx();
		} else if (currentEvent instanceof ActionEvent) {
			modifiers = ((ActionEvent) currentEvent).getModifiers();
		}
		final ActionEvent e = new ActionEvent(this, 1001,
				(this.command != null) ? this.command : this.getClass().toString(), EventQueue.getMostRecentEventTime(),
				modifiers);
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == ActionListener.class) {
				((ActionListener) listeners[i + 1]).actionPerformed(e);
			}
		}
	}

	public void setActionCommand(final String command) {
		this.command = command;
	}

	public void setAction(final Action a) {
		final Action oldValue = this.getAction();
		if (this.action == null || !this.action.equals(a)) {
			this.action = a;
			if (oldValue != null) {
				this.removeActionListener(oldValue);
				oldValue.removePropertyChangeListener(this.actionPropertyChangeListener);
				this.actionPropertyChangeListener = null;
			}
			this.configurePropertiesFromAction(this.action);
			if (this.action != null) {
				if (!this.isListener(ActionListener.class, this.action)) {
					this.addActionListener(this.action);
				}
				this.actionPropertyChangeListener = this.createActionPropertyChangeListener(this.action);
				this.action.addPropertyChangeListener(this.actionPropertyChangeListener);
			}
			this.firePropertyChange("action", oldValue, this.action);
		}
	}

	protected PropertyChangeListener createActionPropertyChangeListener(final Action a) {
		return new MyPanelActionPropertyChangeListener(this, a);
	}

	private boolean isListener(final Class<?> c, final ActionListener a) {
		boolean isListener = false;
		final Object[] listeners = this.listenerList.getListenerList();
		for (int i = listeners.length - 2; i >= 0; i -= 2) {
			if (listeners[i] == c && listeners[i + 1] == a) {
				isListener = true;
			}
		}
		return isListener;
	}

	public Action getAction() {
		return this.action;
	}

	protected void configurePropertiesFromAction(final Action a) {
		setEnabledFromAction(this, a);
		setToolTipTextFromAction(this, a);
		this.setActionCommandFromAction(a);
	}

	protected void actionPropertyChanged(final Action action, final String propertyName) {
		if (propertyName == "ActionCommandKey") {
			this.setActionCommandFromAction(action);
		} else if (propertyName == "enabled") {
			setEnabledFromAction(this, action);
		} else if (propertyName == "ShortDescription") {
			setToolTipTextFromAction(this, action);
		}
	}

	private void setActionCommandFromAction(final Action action) {
		this.setActionCommand((action == null) ? null : ((String) action.getValue("ActionCommandKey")));
	}

	public void postActionEvent() {
		this.fireActionPerformed();
	}

	static void setEnabledFromAction(final JComponent c, final Action a) {
		c.setEnabled(a == null || a.isEnabled());
	}

	static void setToolTipTextFromAction(final JComponent c, final Action a) {
		c.setToolTipText((a != null) ? ((String) a.getValue("ShortDescription")) : null);
	}

	private static class MyPanelActionPropertyChangeListener extends MyActionPropertyChangeListener<MyPanel> {
		private static final long serialVersionUID = 1L;

		public MyPanelActionPropertyChangeListener(final MyPanel c, final Action a) {
			super(c, a);
		}

		@Override
		protected void actionPropertyChanged(final MyPanel myPanel, final Action action, final PropertyChangeEvent e) {
			if (MyActionPropertyChangeListener.shouldReconfigure(e)) {
				myPanel.configurePropertiesFromAction(action);
			} else {
				myPanel.actionPropertyChanged(action, e.getPropertyName());
			}
		}
	}
}
