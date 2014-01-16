package de.bwvaachen.beamoflightgame.helper;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class IndexedSet<T> extends AbstractCollection<T> implements Set<T> {
    private final List<T> list = new ArrayList<T>();
    private final Set<T>   set = new HashSet<T>();
    
    @Override
    public boolean add(T e) {
        boolean added = set.add(e);
        if (added) {
            list.add(e);
        }
        return added;
    }

    @Override
	public void clear() {
        set.clear();
        list.clear();
    }

    @Override
    public boolean contains(Object o) {
        return set.contains(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return set.containsAll(c);
    }

    public T get(int index) {
        return list.get(index);
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
	public Iterator<T> iterator() {
        return new Iterator<T>() {
            Iterator<T> realIterator = list.iterator();
            T current = null;

            @Override
			public boolean hasNext() {
                return realIterator.hasNext();
            }

            @Override
			public T next() {
                T next = realIterator.next();
                current = next;
                return next;
            }

            @Override
			public void remove() {
                realIterator.remove();
                set.remove(current);
                current = null;
            }
        };
    }

    public T remove(int index) {
        T t = list.get(index);
        remove(t);
        return t;
    }

    @Override
    public boolean remove(Object o) {
        boolean removed = set.remove(o);
        if (removed) {
            list.remove(o);
        }
        return removed;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean removed = false;
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T next = it.next();
            if (!c.contains(next)) {
                it.remove();
                set.remove(it);
                removed = true;
            }
        }
        return removed;
    }

    @Override
	public int size() {
        return list.size();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }
}