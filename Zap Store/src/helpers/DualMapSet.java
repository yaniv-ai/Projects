package helpers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class DualMapSet<K, V> {
	private Vector<K> leftKeyList = null;
	private Vector<V> rightKeyList = null;

	public DualMapSet() {
		leftKeyList = new Vector<K>();
		rightKeyList = new Vector<V>();
	}

	public int size() {
		return leftKeyList.size();
	}

	public boolean isEmpty() {
		return leftKeyList.isEmpty();
	}

	public boolean containsLeftKey(Object key) {
		return leftKeyList.contains(key);
	}

	public boolean containsRightKey(Object value) {
		return rightKeyList.contains(value);
	}

	public K getLeftKey(Object rightKey) {
		return leftKeyList.get(rightKeyList.indexOf(rightKey));
	}

	public V getRightKey(Object leftKey) {
		return rightKeyList.get(leftKeyList.indexOf(leftKey));
	}

	public boolean put(K leftKey, V rightKey) {
		if (leftKey != null && rightKey != null) {
			if (leftKeyList.contains(leftKey)) {
				int index = leftKeyList.indexOf(leftKey);
				rightKeyList.remove(index);
				leftKeyList.remove(index);
			}
			if (rightKeyList.contains(rightKey)) {
				int index = rightKeyList.indexOf(rightKey);
				rightKeyList.remove(index);
				leftKeyList.remove(index);
			}
			leftKeyList.add(leftKeyList.size(), leftKey);
			rightKeyList.add(rightKeyList.size(), rightKey);
			return true;
		}
		return false;
	}

	public K removeRight(Object rightKey) {
		K value = leftKeyList.remove(rightKeyList.indexOf(rightKey));
		rightKeyList.remove(rightKey);
		return value;
	}

	public V removeLeft(Object leftKey) {
		V value = rightKeyList.remove(leftKeyList.indexOf(leftKey));
		leftKeyList.remove(leftKey);
		return value;
	}

	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
			putForCreate(e.getKey(), e.getValue());
	}

	private void putForCreate(K key, V value) {
		put(key, value);
	}

	public void clear() {
		leftKeyList = new Vector<K>();
		rightKeyList = new Vector<V>();
	}

	public Set<K> leftKeySet() {
		TreeSet<K> set = null;
		if (leftKeyList != null) {
			set = new TreeSet<K>(leftKeyList);
		}
		return set;
	}

	public Set<V> rightKeySet() {
		TreeSet<V> set = null;
		if (rightKeyList != null) {
			set = new TreeSet<V>(rightKeyList);
		}
		return set;
	}

	public Collection<K> leftValues() {
		Collection<K> col = null;
		if (leftKeyList != null) {
			col = new Vector<K>(leftKeyList);
		}
		return col;
	}

	public Collection<V> rightValues() {
		Collection<V> col = null;
		if (rightKeyList != null) {
			col = new Vector<V>(rightKeyList);
		}
		return col;
	}

	public Set<java.util.Map.Entry<K, V>> entrySet() {
		TreeSet<java.util.Map.Entry<K, V>> ts = null;
		if (leftKeyList != null) {
			ts = new TreeSet<java.util.Map.Entry<K, V>>();
			Iterator<K> ik = leftKeyList.iterator();
			Iterator<V> iv = rightKeyList.iterator();
			while (ik.hasNext() && iv.hasNext()) {
				K k = ik.next();
				V v = iv.next();
				ts.add(new EntrySet(k, v));
			}
		}
		return ts;
	}

	private class EntrySet implements java.util.Map.Entry<K, V> {

		final K k;
		V v;

		public EntrySet(K k, V v) {
			this.k = k;
			this.v = v;
		}

		@Override
		public K getKey() {
			return k;
		}

		@Override
		public V getValue() {
			return v;
		}

		@Override
		public V setValue(V value) {
			this.v = value;
			return v;
		}

	}
}
