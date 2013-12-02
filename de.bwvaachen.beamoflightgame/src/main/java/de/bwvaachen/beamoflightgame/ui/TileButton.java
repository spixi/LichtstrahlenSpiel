package de.bwvaachen.beamoflightgame.ui;

import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.bwvaachen.beamoflightgame.model.IChangeableTile;
import de.bwvaachen.beamoflightgame.model.ITile;

/**
 * Eine Klasse die den JButton um Eigenschaften erweitert, die in der GUI hilfreich
 * sein k�nnen.
 * 
 * @author gbraun
 *
 */
public class TileButton extends JButton implements ChangeListener {
	
	private final ITile tile;
	
	public TileButton(final ITile t) {
		tile = t;
		if(t.isStateChangeable()) {
			t.addChangeListener(this);
		}
	}
	
	public int getRow() {
		return tile.getRow();
	}


	public int getCol() {
		return tile.getCol();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		Hashtable<Object,Object> foo = new Hashtable<Object,Object>();
		IChangeableTile tile = (IChangeableTile) e.getSource();
		tile.storeState(foo);
		//tile.getPresentation()
		
		//this.imageUpdate(img, infoflags, x, y, w, h)
		System.out.printf("Change: %s\n", foo);
	}

}
