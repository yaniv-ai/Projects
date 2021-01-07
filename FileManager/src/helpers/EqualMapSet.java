package helpers;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class EqualMapSet<K, V> implements Map<K, V> {
	private Vector<K> keyList = null;
	private Vector<V> valueList = null;

	public EqualMapSet() {
		keyList = new Vector<K>();
		valueList = new Vector<V>();
	}

	public boolean swapElementsPosition(int lowerPos, int higherPos) {
		if (keyList == null || valueList == null || lowerPos >= keyList.size() || higherPos >= keyList.size()) {
			return false;
		} else {
			if (lowerPos == higherPos) {
				return true;
			} else {
				if (lowerPos > higherPos) {
					int temp = lowerPos;
					lowerPos = higherPos;
					higherPos = temp;
				}

				K higherK = getKeyAt(higherPos);
				V higherV = getValueAt(higherPos);
				keyList.remove(higherPos);
				valueList.remove(higherPos);

				keyList.insertElementAt(getKeyAt(lowerPos), higherPos);
				valueList.insertElementAt(getValueAt(lowerPos), higherPos);

				keyList.remove(lowerPos);
				valueList.remove(lowerPos);

				keyList.insertElementAt(higherK, lowerPos);
				valueList.insertElementAt(higherV, lowerPos);

				return true;
			}
		}
	}

	public K getKeyAt(int index) {
		return keyList.elementAt(index);
	}

	public V getValueAt(int index) {
		return valueList.elementAt(index);
	}

	@Override
	public int size() {
		return keyList.size();
	}

	@Override
	public boolean isEmpty() {
		return keyList.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return keyList.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return valueList.contains(value);
	}

	@Override
	public V get(Object key) {
		int index = keyList.indexOf(key);
		if (index < 0) {
			return null;
		} else {
			return valueList.get(index);
		}
	}

	@Override
	public V put(K key, V value) {
		if (key != null) {
			if (keyList.contains(key)) {
				int index = keyList.indexOf(key);
				valueList.remove(index);
				valueList.add(index, value);
				return value;
			} else {
				keyList.add(keyList.size(), key);
				valueList.add(valueList.size(), value);
				return value;
			}
		}
		return value;
	}

	@Override
	public V remove(Object key) {
		int index = keyList.indexOf(key);
		if (index < 0) {
			return null;
		} else {
			V value = valueList.remove(index);
			keyList.remove(key);
			return value;
		}
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		for (Map.Entry<? extends K, ? extends V> e : m.entrySet())
			putForCreate(e.getKey(), e.getValue());
	}

	private void putForCreate(K key, V value) {
		put(key, value);
	}

	@Override
	public void clear() {
		// list = new Vector<VectorItem<K, V>>();
		keyList = new Vector<K>();
		valueList = new Vector<V>();
	}

	@Override
	public Set<K> keySet() {
		TreeSet<K> set = null;
		if (keyList != null) {
			set = new TreeSet<K>(keyList);
		}
		return set;
	}

	@Override
	public Collection<V> values() {
		Collection<V> col = null;
		if (valueList != null) {
			col = new Vector<V>(valueList);
		}
		return col;
	}

	@Override
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		TreeSet<java.util.Map.Entry<K, V>> ts = null;
		if (keyList != null) {
			ts = new TreeSet<java.util.Map.Entry<K, V>>();
			Iterator<K> ik = keyList.iterator();
			Iterator<V> iv = valueList.iterator();
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

	public synchronized K elementAt(int index) {
		return keyList.elementAt(index);
	}
}
