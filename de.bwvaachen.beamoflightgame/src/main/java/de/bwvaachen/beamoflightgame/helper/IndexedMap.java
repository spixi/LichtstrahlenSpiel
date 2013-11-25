package de.bwvaachen.beamoflightgame.helper;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IndexedMap<K,V> extends HashMap<K,V> {
	
	private IndexedSet<Map.Entry<K, V>> entries;
	private HashMap<K,V> actualMap;
	
	public IndexedMap() {
		super();
		entries   = new IndexedSet<Map.Entry<K, V>>();
		actualMap = new HashMap<K, V>();
	}

	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return entries;
	}

	@Override
	public int size() {
		return actualMap.size();
	}

	@Override
	public boolean isEmpty() {
		return actualMap.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return actualMap.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return actualMap.containsValue(value);
	}

	@Override
	public V get(Object key) {
		return actualMap.get(key);
	}
	
	public Map.Entry<K,V> getEntryByIndex(int index) {
		return entries.get(index);
	}
	
	public K getKeyByIndex(int index) {
		return getEntryByIndex(index).getKey();
	}
	
	public V getValueByIndex(int index) {
		return getEntryByIndex(index).getValue();
	}

	@Override
	public V put(K key, V value) {
		V oldValue = actualMap.get(key);
		actualMap.put(key, value);
		entries.add(new AbstractMap.SimpleEntry<K,V>(key, value) {
			@Override
			public V setValue(V value) {
				return IndexedMap.this.put(getKey(), getValue());
			}
		}
		);
		
		return oldValue;
	}

	@Override
	public V remove(Object key) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void clear() {
		entries.clear();
		actualMap.clear();	
	}

	@Override
	public IndexedSet<K> keySet() {
		IndexedSet<K> set = new IndexedSet<K>();
		for(Map.Entry<K, V> entry: entries) {
			set.add(entry.getKey());
		}
		return set;
	}

	@Override
	public Collection<V> values() {
		LinkedList<V> list = new LinkedList<V>();
		for(Map.Entry<K, V> entry: entries) {
			list.add(entry.getValue());
		}
		return list;
	}


}
