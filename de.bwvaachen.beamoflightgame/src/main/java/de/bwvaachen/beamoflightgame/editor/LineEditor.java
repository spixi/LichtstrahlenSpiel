package de.bwvaachen.beamoflightgame.editor;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.imageio.IIOException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

@SuppressWarnings("serial")
public class LineEditor extends BeamsOfLightEditor
	implements MouseMotionListener, MouseListener{
	
	private Point lineStart, lineEnd;
	private Line2D line;
	private boolean validLine ;
	private double rotationAngle ;
	private TilePanel tile;
	private ArrayList<TilePanel> tileList ;
	private int tileCount ;
		
	public LineEditor(int width, int height){
		super(EditorType.LineEditor, width, height);
		
		tilesPanel.addMouseMotionListener(this);
		tilesPanel.addMouseListener(this);
	}
	
	@Override
	public void initComponents(){
		
		leftButton.addActionListener(this);
		middleButton.addActionListener(this);
		rightButton.addActionListener(this);
		
		validLine = false ;
		rotationAngle = 0.0 ;
		tileCount = 0;
		tileList = new ArrayList<TilePanel>();
		
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				tile = new TilePanel(j,i);
				tile.setSize(128,128);
				tile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				tilesPanel.add(tile);
				tileList.add(tile);
			}
		}
	}
	
	@Override
	public String getTileStats() {
		this.remainingTiles = totalTiles ;
		
		for(TilePanel tile : tileList){
			if(tile.getState() == TileState.NUMBER){
				this.remainingTiles -= (tile.getLightPower() + 1); 
			}
		}
		return "Felder (Gesamt): "+totalTiles+"\nFelder (Verbleibend): "+remainingTiles;
	}

	@Override
	public BeamsOfLightPuzzleBoard convertToBoard() {
		BeamsOfLightPuzzleBoard target = new BeamsOfLightPuzzleBoard();
		target.init(col,row);
		
		for(TilePanel tile : tileList){
			if(tile.getState() == TileState.EMPTY){
				// TODO error for empty tiles
				target.putTile(new LightTile(target,tile.getCol(),tile.getRow()));
			}
			if(tile.getState() == TileState.H_LIGHT || tile.getState() == TileState.V_LIGHT){
				target.putTile(new LightTile(target,tile.getCol(),tile.getRow()));
			}
			if(tile.getState() == TileState.NUMBER){
				target.putTile(new NumberTile(target, tile.getLightPower(),tile.getCol(),tile.getRow()));
			}
		}
		return target;
	}
	
	private double checkLine(TilePanel startTile, TilePanel endTile) throws Exception{
		
		int deltaXabs = Math.abs(startTile.getCol() - endTile.getCol());
		int deltaYabs = Math.abs(startTile.getRow() - endTile.getRow());
		int deltaX = startTile.getCol() - endTile.getCol();
		int deltaY = startTile.getRow() - endTile.getRow();
				
		double angle = 0.0 ;
		boolean crossingNonEmptyTile = false ;
		boolean errorDisplayed = false ;
		
		for(TilePanel tile : tileList){
			if(	line.intersects(tile.getBounds())
			&&  !(tile.getBounds().contains(lineStart)))
			{
				if(tile.getState() != TileState.EMPTY){
					crossingNonEmptyTile = true ;
					break ;
				}
			}
		}
		
		if(		startTile != endTile 
			&&  (deltaXabs == 0 ^ deltaYabs == 0) 
			&& 	(startTile.getState() == TileState.NUMBER || startTile.getState() == TileState.EMPTY)
			&& 	endTile.getState() == TileState.EMPTY
			&&	!crossingNonEmptyTile
		  ){
			validLine = true ;			
		}else{ 
			if(deltaXabs >= 1 && deltaYabs >= 1 && !errorDisplayed){
				validLine = false ;
				JOptionPane.showMessageDialog(	this,
												"Diagonalen sind nicht moeglich!",
												"Fehler",
												JOptionPane.ERROR_MESSAGE);
				errorDisplayed = true ;
			}
			if(startTile == endTile && !errorDisplayed){
				validLine = false ;
				JOptionPane.showMessageDialog(	this,
												"Start- und Endfeld duerfen nicht gleich sein!",
												"Fehler",
												JOptionPane.ERROR_MESSAGE);
				errorDisplayed = true ;
			}
			if(!(startTile.getState() == TileState.EMPTY || startTile.getState() == TileState.NUMBER) && !errorDisplayed){
				validLine = false ;
				JOptionPane.showMessageDialog(	this,
												"Startfeld muss leer oder bereits Zahlenfeld sein!\nFeldbelegung loeschen mit Rechtsklick.",
												"Fehler",
												JOptionPane.ERROR_MESSAGE);
				errorDisplayed = true ;
			}
			if(crossingNonEmptyTile && !errorDisplayed){
				validLine = false ;
				JOptionPane.showMessageDialog(	this,
												"Linie darf kein gefuelltes Feld schneiden!\nFeldbelegung loeschen mit Rechtsklick.",
												"Fehler",
												JOptionPane.ERROR_MESSAGE);
				errorDisplayed = true ;
			}
		}
		if(deltaXabs > 0){
			angle = 90.0 ;
		}
		if(deltaX > 0){
			angle = 270.0 ;
		}
		if(deltaY < 0){
			angle = 180.0 ;
		}
		return angle ;
	}
	
	@Override
	public void mouseClicked(MouseEvent me) {
		Point clicked = me.getPoint();
		
		if(SwingUtilities.isRightMouseButton(me)){
			for(TilePanel tile : tileList)
				if(tile.getBounds().contains(clicked)){
					tile.reset();
				}
		}
	}

	@Override
	public void mouseEntered(MouseEvent me) {
	}

	@Override
	public void mouseExited(MouseEvent me) {
	}

	@Override
	public void mousePressed(MouseEvent me) {
		if(SwingUtilities.isLeftMouseButton(me))
			lineStart = me.getPoint();
	}

	@Override
	public void mouseReleased(MouseEvent me) {
		if(SwingUtilities.isLeftMouseButton(me)){

			lineEnd = me.getPoint();
			TilePanel startTile = null ;
			TilePanel endTile = null ;
			
			try{
				for(TilePanel tile : tileList){
					if(line.intersects(tile.getBounds()) && !(tile.getBounds().contains(lineStart)) && tile.getState() == TileState.EMPTY){
						tileCount++;
					}
					if(tile.getBounds().contains(lineStart)){
						startTile = tile ;
					}
					if(tile.getBounds().contains(lineEnd)){
						endTile = tile ;
					}
				}
				
				rotationAngle = checkLine(startTile,endTile);
				
				if(validLine){
					endTile.setImage("resources/themes/moon/light2.png",rotationAngle);
					startTile.setLightPower(tileCount);
					
					for(TilePanel tile : tileList){
						if(	line.intersects(tile.getBounds())
						&&  !(tile.getBounds().contains(lineStart))
						&&  !(tile.getBounds().contains(lineEnd)))
						{
							tile.setImage("resources/themes/moon/light1.png",rotationAngle);
						}
					}
				}
			}catch(IIOException iioe){
				//TODO
			}catch(NumberFormatException nfe){
				//TODO
			}catch(Exception e){
				//TODO
			}finally{
				//System.out.println("START: "+ startTile.getCol() +","+ startTile.getRow());
				//System.out.println("END: "+ endTile.getCol() +","+ endTile.getRow());
				tileStats.setText(getTileStats());
				tileCount = 0 ;
				line = new Line2D.Double();
				validLine = false ;
				rotationAngle = 0.0;
				tilesPanel.setLine(line);
				tilesPanel.repaint();
			}
		} //if(SwingUtilities.isLeftMouseButton(me))
}

	@Override
	public void mouseDragged(MouseEvent me) {
		if(SwingUtilities.isLeftMouseButton(me)){
			lineEnd = me.getPoint();
			line = new Line2D.Double(lineStart,lineEnd);
			tilesPanel.setLine(line);
			tilesPanel.repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent me) {
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		if(ae.getSource() == leftButton){
			
		}
		if(ae.getSource() == middleButton){
			
		}
		if(ae.getSource() == rightButton){
			for(TilePanel tile : tileList){
				tile.reset();
			}
		}
	}
}
