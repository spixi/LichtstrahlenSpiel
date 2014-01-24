package de.bwvaachen.beamoflightgame.helper;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.util.AbstractMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class IndexedMap<K,V> extends HashMap<K,V> {
	
	private IndexedSet<Map.Entry<K, V>> entries;
	
	public IndexedMap() {
		super();
		entries   = new IndexedSet<Map.Entry<K, V>>();
	}

	@Override
	public void clear() {
		entries.clear();
		super.clear();	
	}
	
	@Override
	public Set<Map.Entry<K, V>> entrySet() {
		return entries;
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
	public IndexedSet<K> keySet() {
		IndexedSet<K> set = new IndexedSet<K>();
		for(Map.Entry<K, V> entry: entries) {
			set.add(entry.getKey());
		}
		return set;
	}

	@Override
	public V put(K key, V value) {
		V oldValue = super.put(key, value);
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
	public void putAll(Map<? extends K, ? extends V> m) {
		for(Map.Entry<? extends K, ? extends V> entry : m.entrySet()) {
			put(entry.getKey(), entry.getValue());
		}
	}

	@Override
	public V remove(Object key) {
		//Sequential search ...
		int i = 0;
		for(Map.Entry<K,V> entry: entries) {
			if (entry.getKey() == key) {
				break;
			}
			i++;
		}
		
		if(i==entries.size()) return null;
		
		entries.remove(i);
		return super.remove(i);
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
