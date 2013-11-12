package de.bwvaachen.braun.beamoflightpuzzle.model;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;

public class LightTileState implements Serializable {
	public static final LightTileState
		SOUTH = new LightTileState(Math.PI, new _('s')),
		WEST  = new LightTileState(3.0*Math.PI/2.0, new _('w')),
		NORTH = new LightTileState(0.0, new _('n')),
		EAST  = new LightTileState(Math.PI/2.0, new _('e')),
		EMPTY = new LightTileState(0.0, new _('-'));
	private double theta;
	private _ serializedForm;
	private LightTileState(double d, _ s) {
		theta          = d;
		serializedForm = s;
	}
	public double getTheta(){
		return theta;
	}
	
	private Object writeReplace() throws ObjectStreamException {
		return serializedForm;
	}
	
	//Serialized form
	private static class _ implements Serializable {
		private static final long serialVersionUID = 7472214531490192063L;
		char c;
		private _(char c) {
			this.c = c;
		}
		protected Object readResolve() throws ObjectStreamException {
	    	switch (c) {
	    	case 's': return LightTileState.SOUTH;
	    	case 'w': return LightTileState.WEST;
	    	case 'n': return LightTileState.NORTH;
	    	case 'e': return LightTileState.EAST;
	    	default : return LightTileState.EMPTY;
	    	}
	    }
	 }
     
	 //Wo ist die Test-Suite nur hin???
	 public static void main(String[] args) throws IOException {
		 ObjectOutputStream oos = new ObjectOutputStream(System.out);
		 oos.writeObject(EAST);
		 oos.writeObject(NORTH);
		 oos.writeObject(EAST);
		 oos.writeObject(EMPTY);
		 oos.writeObject(EAST);
		 oos.writeObject(EMPTY);
		 oos.writeObject(EAST);
	 }

}
