package de.bwvaachen.beamoflightgame.editor;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import static de.bwvaachen.beamoflightgame.i18n.I18N.*;

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
import javax.swing.JPanel;

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

	private ArrayList<TileButton> 	tileList ;
	
	public NumberEditor(int width, int height) {
		super(EditorType.NumberEditor, width, height);
	}
	
	@Override
	public void initComponents(){
		TileButton tile ;
		
		solveButton.addActionListener(this);
		resetButton.addActionListener(this);

		displayAllTiles = false ;
		tileList = new ArrayList<TileButton>();
	
		for(int i=0; i<row; i++){
			for(int j=0; j<col; j++){
				tile = createTileButton(i,j);
				tile.addActionListener(this);
				onlyNumberTilesPanel.add(tile);
				tileList.add(tile);
			}
		}
	}

	@Override
	public void updateTileStats() {
		totalTiles = col*row ;
		remainingTiles = totalTiles ;
		
		for(TileButton tile : tileList){
			if(tile.getState() == TileState.NUMBER){
				this.remainingTiles -= (tile.getLightPower() + 1); 
			}
		}
		tileStatsTextArea.setText(_pf("EditorTotalAndRemainingTiles",remainingTiles, totalTiles, remainingTiles));
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
	
	@Override
	public void importPuzzleBoard(IBeamsOfLightPuzzleBoard source) 
			throws NumberFormatException, IIOException, IOException{
		ITile currentTile ;
		TileButton tile;
		
		col = source.getWidth();
		row = source.getHeight();
		gl = new GridLayout(row,col);
		
		tileList.clear();
		
		tiles = new JPanel(cl);
		
		tilesPanel = new TilesPanel();
		tilesPanel.setLayout(gl);
	    
		for(int i=0;i<row;i++){
			for(int j=0;j<col;j++){
				
				currentTile = source.getTileAt(j,i);
				tile = createTileButton(i,j);
				
				if(currentTile.getClass().getSimpleName().equals("LightTile")){
					LightTileState currentTileState = (LightTileState) currentTile.getTileState();
					char c = currentTileState.getSign();
					double rotationAngle ;
					
					switch(c){
						case 'n':	rotationAngle = NORTH;
									break;
						case 'e':	rotationAngle = EAST;
									break;
						case 's':	rotationAngle = SOUTH;
									break;
						case 'w':	rotationAngle = WEST;
									break;
						default: 	rotationAngle = 0.0 ;
					}
					if(new GraficFactory(source).isEnd((LightTile)currentTile)){
						tile.setImage("resources/themes/moon/light2.png", rotationAngle);
					}else{
						tile.setImage("resources/themes/moon/light1.png", rotationAngle);
					}
				}if(currentTile.getClass().getSimpleName().equals("NumberTile")){
					NumberTileState currentTileState = (NumberTileState) currentTile.getTileState() ;
					int lightPower = currentTileState.getNumber();
					tile.setLightPower(lightPower);
				}
				tile.setEnabled(false);
				tileList.add(tile);
				tilesPanel.add(tile);
			}
		}
		tiles.add(tilesPanel,"1");
	} //importPuzzleBoard
	
	@Override
	public void convertTilesPanel(){
		TileButton temp ;
		
		onlyNumberTilesPanel = new TilesPanel();
		onlyNumberTilesPanel.setLayout(gl);
		
		for(TileButton tile : tileList){
			if(tile.getState() != TileState.NUMBER){
				temp = createTileButton(tile.getCol(),tile.getRow());
				temp.setEnabled(false);
				onlyNumberTilesPanel.add(temp);
			}else{
				temp = createTileButton(tile);
				temp.setEnabled(false);
				onlyNumberTilesPanel.add(temp);
			}
		}
		tiles.add(onlyNumberTilesPanel,"2");
	}
	
	public TileButton createTileButton(TileButton tile){
		TileButton temp = new TileButton(tile.getCol(),tile.getRow());
		temp.setImage(tile.getImage());
		
		return temp;
	}
	
	public TileButton createTileButton(int row, int col){
		TileButton temp = new TileButton(col,row);
		temp.setSize(128,128);
		temp.setMinimumSize(new Dimension(128,128));
		temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		return temp ;
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		super.actionPerformed(ae);
		
		try{
			if(ae.getSource().getClass().getSimpleName().equals("TileButton")){
				String input ;
				int maxLightPower = (col-1) + (row-1) ;
				int lightPower ;
				TileButton clickedTile  = (TileButton) ae.getSource();
			
					do{
						input = (String) JOptionPane.showInputDialog(
									this,
									_f("NumberEditorLightRange",maxLightPower),
									_("NumberEditorEnterLightRange"),
									JOptionPane.PLAIN_MESSAGE,
									null,
									null,
									"1");
						lightPower = Integer.valueOf(input);
						if(lightPower > maxLightPower){
							JOptionPane.showMessageDialog(	this,
															_f("NumberEditorLightRangeGreaterMax", maxLightPower),
															_("Error"),
															JOptionPane.ERROR_MESSAGE);
						}else if(lightPower < 1){
							JOptionPane.showMessageDialog(	this,
															_("NumberEditorLightRangeLessThanOne"),
															_("Error"),
															JOptionPane.ERROR_MESSAGE);
						}else if(remainingTiles-(lightPower+1) < 0){
							JOptionPane.showMessageDialog(	this,
															_pf("NumberEditorLightRangeLessTilesRemaining",remainingTiles,remainingTiles,remainingTiles-1),
															_("Error"),
															JOptionPane.ERROR_MESSAGE);
						}
					}while(lightPower > maxLightPower || lightPower < 1 || remainingTiles-(lightPower+1) < 0);
				
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
			updateTileStats();
			checkButtons();
		}
	}
}
