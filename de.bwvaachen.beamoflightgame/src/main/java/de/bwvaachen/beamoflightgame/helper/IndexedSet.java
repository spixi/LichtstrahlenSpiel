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
import java.util.ListIterator;
import java.util.Set;

public class IndexedSet<T> extends AbstractCollection<T> implements Set<T>, List<T> {
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

	@Override
	public void add(int index, T o) {
        boolean added = set.add(o);
        if (added) {
            list.add(index,o);
        }
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
        Iterator<T> it = list.iterator();
        boolean listHasChanged = false;
        
        while (it.hasNext()) {
            T next = it.next();
            boolean added = set.add(next);
            if (added) {
                list.add(index,next);
                listHasChanged = true;
            }
        }
		return listHasChanged;
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<T> listIterator() {
		return listIterator(0);
	}

	@Override
	public ListIterator<T> listIterator(final int index) {
		// TODO Auto-generated method stub
		return new ListIterator<T>() {
			ListIterator<T> realIterator = list.listIterator(index);
			T current = null;

			@Override
			public void add(Object e) {
				if(set.add((T) e)) {
					realIterator.add((T) e);
				}
			}

			@Override
			public boolean hasNext() {
				return realIterator.hasNext();
			}

			@Override
			public boolean hasPrevious() {
				return realIterator.hasPrevious();
			}

			@Override
			public T next() {
                T next = realIterator.next();
                current = next;
                return next;
			}

			@Override
			public int nextIndex() {
				return realIterator.nextIndex();
			}

			@Override
			public T previous() {
                T previous = realIterator.previous();
                current = previous;
                return previous;
			}

			@Override
			public int previousIndex() {
				return realIterator.previousIndex();
			}

			@Override
			public void remove() {
                realIterator.remove();
                set.remove(current);
                current = null;
			}

			@Override
			public void set(Object e) {
				set.remove(current);
				set.add((T) e);
				realIterator.set((T) e);
			}
			
		};
	}

	@Override
	public T set(int index, T element) {
		T oldElement;
		oldElement = list.set(index, element);
		
		set.remove(oldElement);
		set.add(element);
		return oldElement;
	}

	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
}