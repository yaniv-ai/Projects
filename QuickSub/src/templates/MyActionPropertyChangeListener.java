// 
// Decompiled by Procyon v0.5.36
// 

package templates;

import java.lang.ref.WeakReference;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.beans.PropertyChangeEvent;
import javax.swing.Action;
import java.lang.ref.ReferenceQueue;
import java.io.Serializable;
import java.beans.PropertyChangeListener;
import javax.swing.JComponent;

abstract class MyActionPropertyChangeListener<T extends JComponent> implements PropertyChangeListener, Serializable {
	private static final long serialVersionUID = 1L;
	private static ReferenceQueue<JComponent> queue;
	private transient OwnedWeakReference<T> target;
	private Action action;

	private static ReferenceQueue<JComponent> getQueue() {
		synchronized (MyActionPropertyChangeListener.class) {
			if (MyActionPropertyChangeListener.queue == null) {
				MyActionPropertyChangeListener.queue = new ReferenceQueue<JComponent>();
			}
		}
		// monitorexit(MyActionPropertyChangeListener.class)
		return MyActionPropertyChangeListener.queue;
	}

	public MyActionPropertyChangeListener(final T c, final Action a) {
		this.setTarget(c);
		this.action = a;
	}

	@Override
	public final void propertyChange(final PropertyChangeEvent e) {
		final T target = this.getTarget();
		if (target == null) {
			this.getAction().removePropertyChangeListener(this);
		} else {
			this.actionPropertyChanged(target, this.getAction(), e);
		}
	}

	protected abstract void actionPropertyChanged(final T p0, final Action p1, final PropertyChangeEvent p2);

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void setTarget(final T c) {
		final ReferenceQueue<JComponent> queue = getQueue();
		OwnedWeakReference<T> r;
		while ((r = (OwnedWeakReference<T>) (OwnedWeakReference) queue.poll()) != null) {
			final MyActionPropertyChangeListener<T> oldPCL = r.getOwner();
			final Action oldAction = oldPCL.getAction();
			if (oldAction != null) {
				oldAction.removePropertyChangeListener(oldPCL);
			}
		}
		this.target = new OwnedWeakReference<T>(c, queue, this);
	}

	public T getTarget() {
		if (this.target == null) {
			return null;
		}
		return this.target.get();
	}

	public Action getAction() {
		return this.action;
	}

	private void writeObject(final ObjectOutputStream s) throws IOException {
		s.defaultWriteObject();
		s.writeObject(this.getTarget());
	}

	@SuppressWarnings("unchecked")
	private void readObject(final ObjectInputStream s) throws IOException, ClassNotFoundException {
		s.defaultReadObject();
		final T target = (T) s.readObject();
		if (target != null) {
			this.setTarget(target);
		}
	}

	static boolean shouldReconfigure(final PropertyChangeEvent e) {
		return false;
	}

	private static class OwnedWeakReference<U extends JComponent> extends WeakReference<U> {
		private MyActionPropertyChangeListener<U> owner;

		OwnedWeakReference(final U target, final ReferenceQueue<? super U> queue,
				final MyActionPropertyChangeListener<U> owner) {
			super(target, queue);
			this.owner = owner;
		}

		public MyActionPropertyChangeListener<U> getOwner() {
			return this.owner;
		}
	}
}
