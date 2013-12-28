package de.bwvaachen.beamoflightgame.ui;

import java.util.HashMap;
import java.util.Hashtable;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.bwvaachen.beamoflightgame.helper.Pair;
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
	
	private GraficFactory graphicFactory;
	
	/**
	 *  Referenz auf das Tile aus dem Modell
	 */
	private final ITile tile;
	
	public boolean markiert = false ;
	
	/**
	 * Getter vom Tile
	 * @return Das Tile aus dem Modell
	 */
	public ITile getTile() {
		return tile;
	}

	/**
	 * Constructor
	 * @param t Das Tile aus dem Modell
	 */
	public TileButton(final ITile t) {
		tile = t;
		if(t.isStateChangeable()) {
			t.addChangeListener(this);
		}
		
		//TODO: Remove this ugly stuff here:
		graphicFactory = new GraficFactory(t.getBoard());
	}
	
	/**
	 * @return Die Zeile von dem Tile.
	 */
	public int getRow() {
		return tile.getY();
	}


	/**
	 * @return Die Spalte von dem Tile.
	 */
	public int getCol() {
		return tile.getX();
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		Hashtable<Object,Object> foo = new Hashtable<Object,Object>();
		IChangeableTile tile = ( (Pair<?,IChangeableTile>) e.getSource() ).right;
		tile.storeState(foo);
		//tile.getPresentation()
		
		//TODO
		try {
			Thread.sleep(8);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//TODO
		this.setIcon(graphicFactory.getImage(tile));
		this.ui.update(this.getGraphics(), this);
	}

}
