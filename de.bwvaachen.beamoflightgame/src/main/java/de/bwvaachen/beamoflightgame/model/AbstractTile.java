package de.bwvaachen.beamoflightgame.model;

import java.util.Hashtable;
import java.util.LinkedHashSet;
import java.util.LinkedList;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public abstract class AbstractTile<T extends ITileState> implements ITile<T> {
	private final int row, col;
	private final Class<T> allowedStateClass;
	private ITileState tileState;
	protected LinkedHashSet<ChangeListener> changeListeners;
	
	public boolean isStateChangeable() {
		return this instanceof IChangeableTile;
	}

	public final void storeState(Hashtable<Object, Object> state) {
		if (! isStateChangeable()) throw new UnsupportedOperationException();
		state.put("state", tileState);
	}
	
	public final void restoreState(Hashtable<?, ?> state) {
		if (! isStateChangeable()) throw new UnsupportedOperationException();
		ITileState received      = (ITileState) state.get("state");
        
		if(received == null) {
			return;
		}
		
		Class<?>   receivedClass = received.getClass();
		
		if (! allowedStateClass.isAssignableFrom(receivedClass))
			throw new IllegalStateException("Die Zustandsklasse " + receivedClass + " ist nicht kompatibel mit " + allowedStateClass);
		tileState = (ITileState) received;
	}
	
	protected AbstractTile(final int row, final int col, Class<T> allowedStateClass) {
		this.row               = row;
		this.col               = col;
		this.allowedStateClass = allowedStateClass;
		this.changeListeners   = new LinkedHashSet<ChangeListener>();
	}

	@Override
	public int getRow() {
		return row;
	}

	@Override
	public int getCol() {
		return col;
	}
	
	@SuppressWarnings("unchecked")
	public T getTileState() {
		return (T)tileState;
	}
	
	protected final void setTileState(T tileState) {
		this.tileState = tileState;
		for(ChangeListener l : changeListeners) {
			l.stateChanged(new ChangeEvent(this));
		}
	}
	
	public void addChangeListener(ChangeListener cl) {
		changeListeners.add(cl);
	}
	
	public void removeChangeListener(ChangeListener cl) {
		changeListeners.remove(cl);
	}
	
	public String toString() {
		return getTileState().toString();
	}
	
}
