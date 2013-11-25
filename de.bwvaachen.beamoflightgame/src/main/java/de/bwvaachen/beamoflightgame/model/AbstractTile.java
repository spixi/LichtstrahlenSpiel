package de.bwvaachen.beamoflightgame.model;

import java.util.Hashtable;

public abstract class AbstractTile<T extends ITileState> implements ITile<T> {
	private final int row, col;
	private final Class<T> allowedStateClass;
	private ITileState tileState;
	
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
	}
	
	public String toString() {
		return getTileState().toString();
	}
	
}