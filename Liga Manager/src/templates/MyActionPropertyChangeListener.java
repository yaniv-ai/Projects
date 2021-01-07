package templates;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.lang.ref.WeakReference;
import java.lang.ref.ReferenceQueue;
//import java.security.AccessController;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComponent;

//import sun.security.action.GetPropertyAction;

abstract class MyActionPropertyChangeListener<T extends JComponent> implements PropertyChangeListener, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static Boolean RECONFIGURE_ON_NULL;
	private static ReferenceQueue<JComponent> queue;

	// WeakReference's aren't serializable.
	private transient OwnedWeakReference<T> target;
	// The Component's that reference an Action do so through a strong
	// reference, so that there is no need to check for serialized.
	private Action action;

	private static ReferenceQueue<JComponent> getQueue() {
		synchronized (MyActionPropertyChangeListener.class) {
			if (queue == null) {
				queue = new ReferenceQueue<JComponent>();
			}
		}
		return queue;
	}

	public MyActionPropertyChangeListener(T c, Action a) {
		super();
		setTarget(c);
		this.action = a;
	}

	/**
	 * PropertyChangeListener method. If the target has been gc'ed this will remove
	 * the <code>PropertyChangeListener</code> from the Action, otherwise this will
	 * invoke actionPropertyChanged.
	 */
	public final void propertyChange(PropertyChangeEvent e) {
		T target = getTarget();
		if (target == null) {
			getAction().removePropertyChangeListener(this);
		} else {
			actionPropertyChanged(target, getAction(), e);
		}
	}

	/**
	 * Invoked when a property changes on the Action and the target still exists.
	 */
	protected abstract void actionPropertyChanged(T target, Action action, PropertyChangeEvent e);

	@SuppressWarnings("unchecked")
	private void setTarget(T c) {
		ReferenceQueue<JComponent> queue = getQueue();
		// Check to see whether any old buttons have
		// been enqueued for GC. If so, look up their
		// PCL instance and remove it from its Action.
		OwnedWeakReference<T> r;
		while ((r = (OwnedWeakReference<T>) queue.poll()) != null) {
			MyActionPropertyChangeListener<T> oldPCL = r.getOwner();
			Action oldAction = oldPCL.getAction();
			if (oldAction != null) {
				oldAction.removePropertyChangeListener(oldPCL);
			}
		}
		this.target = new OwnedWeakReference<T>(c, queue, this);
	}

	public T getTarget() {
		if (target == null) {
			// Will only happen if serialized and real target was null
			return null;
		}
		return this.target.get();
	}

	public Action getAction() {
		return action;
	}

	private void writeObject(ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(getTarget());
	}

	@SuppressWarnings("unchecked")
	private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		T target = (T) s.readObject();
		if (target != null) {
			setTarget(target);
		}
	}

	private static class OwnedWeakReference<U extends JComponent> extends WeakReference<U> {
		private MyActionPropertyChangeListener<U> owner;

		OwnedWeakReference(U target, ReferenceQueue<? super U> queue, MyActionPropertyChangeListener<U> owner) {
			super(target, queue);
			this.owner = owner;
		}

		public MyActionPropertyChangeListener<U> getOwner() {
			return owner;
		}
	}

	/**
	 * Whether or not to reconfigure all action properties from the specified event.
	 */
	static boolean shouldReconfigure(PropertyChangeEvent e) {
		if (e.getPropertyName() == null) {
			synchronized (AbstractAction.class) {
				// TODO Check what to do here
				// if (RECONFIGURE_ON_NULL == null) {
				// RECONFIGURE_ON_NULL = Boolean
				// .valueOf(AccessController
				// .doPrivileged(new GetPropertyAction(
				// "swing.actions.reconfigureOnNull",
				// "false")));
				// }
				return RECONFIGURE_ON_NULL;
			}
		}
		return false;
	}
}
