package de.bwvaachen.beamoflightgame.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.IIOException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.NumberTileState;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.ui.GraficFactory;

@SuppressWarnings("serial")
public class LineEditor extends BeamsOfLightEditor
	implements MouseMotionListener, MouseListener{
	
	private Point 					lineStart;
	private Point					lineEnd;
	private Line2D 					line;
	private boolean 				validLine ;
	private int 					tileCount ;
	
	private ArrayList<TilePanel> 	tileList ;

	public LineEditor(int width, int height){
		super(EditorType.LineEditor, width, height);
	}
	
	@Override
	public void initComponents(){
		TilePanel tile ;
		
		solveButton.addActionListener(this);
		resetButton.addActionListener(this);
		
		tilesPanel.addMouseMotionListener(this);
		tilesPanel.addMouseListener(this);
		
		validLine = false ;
		tileCount = 0;
		displayAllTiles = true ;
		tileList = new ArrayList<TilePanel>();
		
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				tile = createTilePanel(i,j);
				tilesPanel.add(tile);
				tileList.add(tile);
			}
		}
	}
	
	@Override
	public String getTileStats() {
		this.totalTiles = col*row ;
		this.remainingTiles = totalTiles ;
		
		for(TilePanel tile : tileList){
			if(tile.getState() == TileState.NUMBER){
				this.remainingTiles -= (tile.getLightPower() + 1); 
			}
		}
		return "Fields (Total): "+totalTiles+"\nFields (Remaining): "+remainingTiles+"\n";
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
	
	
	public void importPuzzleBoard(IBeamsOfLightPuzzleBoard source) 
			throws NumberFormatException, IIOException, IOException{
		ITile currentTile ;
		TilePanel tile ;
		
		tileList.clear();
		
		col = source.getWidth();
		row = source.getHeight();
		gl = new GridLayout(row,col);
		
		tilesPanel.setLayout(gl);
		
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				currentTile = source.getTileAt(j,i);
				tile = createTilePanel(i,j);
				if(currentTile.getClass().getSimpleName().equals("LightTile")){
					LightTileState currentTileState = (LightTileState) currentTile.getTileState();
					char c = currentTileState.getSign();
					double rotationAngle ;
					
					switch(c){
						case 'n':	rotationAngle = 0.0;
									break;
						case 's':	rotationAngle = 180.0;
									break;
						case 'w':	rotationAngle = 270.0;
									break;
						case 'e':	rotationAngle = 90.0;
									break;
						default: 	rotationAngle = 0.0 ;
					}
					if(new GraficFactory(source).isEnd((LightTile)currentTile)){
						tile.setImage("resources/themes/moon/light2.png", rotationAngle);
					}else{
						tile.setImage("resources/themes/moon/light1.png", rotationAngle);
					}
				}else if(currentTile.getClass().getSimpleName().equals("NumberTile")){
					NumberTileState currentTileState = (NumberTileState) currentTile.getTileState() ;
					int lightPower = currentTileState.getNumber();
					tile.setLightPower(lightPower);
				}
				tileList.add(tile);
				tilesPanel.add(tile);
			}
		}
	} //importPuzzleBoard
	
	public void convertTilesPanel(){
		TilePanel temp ;
		
		onlyNumberTilesPanel = new TilesPanel();
		onlyNumberTilesPanel.setLayout(gl);
		
		for(TilePanel tile : tileList){
			if(tile.getState() != TileState.NUMBER){
				temp = createTilePanel(tile.getCol(),tile.getRow());
				onlyNumberTilesPanel.add(temp);
			}else{
				onlyNumberTilesPanel.add(tile);
			}
		}
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
												"Unable to draw diagonal beams!",
												"Error",
												JOptionPane.ERROR_MESSAGE);
				errorDisplayed = true ;
			}
			if(startTile == endTile && !errorDisplayed){
				validLine = false ;
				JOptionPane.showMessageDialog(	this,
												"Beams cannot start and end in the same field!",
												"Error",
												JOptionPane.ERROR_MESSAGE);
				errorDisplayed = true ;
			}
			if(!(startTile.getState() == TileState.EMPTY || startTile.getState() == TileState.NUMBER) && !errorDisplayed){
				validLine = false ;
				JOptionPane.showMessageDialog(	this,
												"Startfield has to be empty or already numberfield!\nRightclick on fields to clear them.",
												"Error",
												JOptionPane.ERROR_MESSAGE);
				errorDisplayed = true ;
			}
			if(crossingNonEmptyTile && !errorDisplayed){
				validLine = false ;
				JOptionPane.showMessageDialog(	this,
												"Beams must not cross!\nRightclick on fields to clear them.",
												"Error",
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
	} //checkLine
	
	@Override
	public void mouseClicked(MouseEvent me) {
		Point clicked = me.getPoint();
		
		if(SwingUtilities.isRightMouseButton(me)){
			for(TilePanel tile : tileList)
				if(tile.getBounds().contains(clicked)){
					tile.reset();
				}
			tileStats.setText(getTileStats());
			checkButtons();
		}
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
				checkButtons();
				tileCount = 0 ;
				line = new Line2D.Double();
				validLine = false ;
				rotationAngle = 0.0;
				tilesPanel.setLine(line);
				repaint();
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
	
	public TilePanel createTilePanel(int row, int col){
		TilePanel temp = new TilePanel(col,row);
		temp.setSize(128,128);
		temp.setMinimumSize(new Dimension(128,128));
		temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		return temp ;
	}
	
	public void mouseMoved(MouseEvent me) {}
	public void mouseEntered(MouseEvent me) {}
	public void mouseExited(MouseEvent me) {}
}