package helpers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

public class EqualSet<K> implements Set<K> {
	private Vector<K> set = null;

	public EqualSet() {
		super();
		set = new Vector<>();
	}

	public EqualSet(K[] k) {
		super();
		set = new Vector<>();
		addAll(k);
	}

	public EqualSet(Vector<K> vector) {
		super();
		set = new Vector<>();
		addAll(vector);
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public Iterator<K> iterator() {
		return set.iterator();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return set.toArray(a);
	}

	@Override
	public boolean add(K e) {
		if (e != null) {
			if (set.contains(e)) {
				int index = set.indexOf(e);
				set.remove(index);
				set.add(index, e);
				return true;
			} else {
				set.add(set.size(), e);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends K> c) {
		boolean b = false;
		Iterator<? extends K> i = c.iterator();
		while (i.hasNext()) {
			add(i.next());
			b = true;
		}
		return b;
	}

	public boolean addAll(K[] c) {
		boolean b = false;
		for (int i = 0; i < c.length; i++) {
			add(c[i]);
			b = true;
		}
		return b;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	@Override
	public void clear() {
		set.removeAllElements();
	}

}
