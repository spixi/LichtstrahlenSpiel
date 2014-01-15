package de.bwvaachen.beamoflightgame.editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.IIOException;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;

import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.NumberTileState;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.ui.GraficFactory;


@SuppressWarnings("serial")
public class NumberEditor extends BeamsOfLightEditor 
	implements ActionListener{

	private TileButton tile ;
	private ArrayList<TileButton> tileList ;
	private int lightPower ;
	
	public NumberEditor(int width, int height) {
		super(EditorType.NumberEditor, width, height);
	}

	@Override
	public void initComponents(){
		
		solveButton.addActionListener(this);
		resetButton.addActionListener(this);
		
		tileList = new ArrayList<TileButton>();
		
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				tile = createTileButton(i,j);
				tilesPanel.add(tile);
				tileList.add(tile);
			}
		}
	}
	
	@Override
	public String getTileStats() {
		this.totalTiles = col*row ;
		this.remainingTiles = totalTiles ;
		
		for(TileButton tile : tileList){
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
		
		for(TileButton tile : tileList){
			if(tile.getState() == TileState.EMPTY){
				target.putTile(new LightTile(target,tile.getCol(),tile.getRow()));
			}
			if(tile.getState() == TileState.H_LIGHT || tile.getState() == TileState.V_LIGHT){
				target.putTile(new LightTile(target,tile.getCol(),tile.getRow()));
			}
			if(tile.getState() == TileState.NUMBER){
				target.putTile(new NumberTile(target,tile.getLightPower(),tile.getCol(),tile.getRow()));
			}
		}
		return target;
	}
	
	public TilesPanel createTilesPanel(IBeamsOfLightPuzzleBoard source) 
			throws NumberFormatException, IIOException, IOException{
		
		TilesPanel temp = new TilesPanel();
		ITile currentTile ;
		
		tileList.clear();
		temp.setLayout(new GridLayout(col,row));
		
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				currentTile = source.getTileAt(j,i);
				tile = createTileButton(i,j);
				if(currentTile.getClass().getSimpleName().equals("LightTile")){
					LightTileState currentTileState = (LightTileState) currentTile.getTileState();
					char c = currentTileState.getSign();
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
				temp.add(tile);
			}
		}
		return temp;
	}
	
	public TileButton createTileButton(int row, int col){
		TileButton temp = new TileButton(col,row);
		temp.setSize(128,128);
		temp.setMinimumSize(new Dimension(128,128));
		temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		temp.addActionListener(this);
		
		return temp ;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		super.actionPerformed(ae);
		
		try{
			if(ae.getSource() == resetButton){
				for(TileButton tile : tileList){
					tile.reset();
				}
			}else if(ae.getSource().getClass().getSimpleName().equals("TileButton")){
				String input ;
				int maxLightPower = (col-1) + (row-1) ;
				TileButton clickedTile  = (TileButton) ae.getSource();
			
					do{
						input = (String) JOptionPane.showInputDialog(
									this,
									"Lichtstaerke(1-" + maxLightPower + "):\n",
									"Lichtstaerkeneingabe",
									JOptionPane.PLAIN_MESSAGE,
									null,
									null,
									"1");
						lightPower = Integer.valueOf(input);
					}while(lightPower > maxLightPower || lightPower < 1);
				
					clickedTile.setLightPower(lightPower);
			}
		}catch(IIOException iioe){
			//TODO
			iioe.printStackTrace();
		}catch(NumberFormatException nfe){
			//TODO
			nfe.printStackTrace();
		}catch(Exception e){
			//TODO
			e.printStackTrace();
		}finally{
			tileStats.setText(getTileStats());
			checkButtons();
		}
	}
}

