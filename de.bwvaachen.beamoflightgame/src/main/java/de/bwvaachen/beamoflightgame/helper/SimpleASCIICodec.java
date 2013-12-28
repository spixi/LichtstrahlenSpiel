package de.bwvaachen.beamoflightgame.helper;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.LightTile;
import de.bwvaachen.beamoflightgame.model.NumberTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.impl.BeamsOfLightPuzzleBoard;

public class SimpleASCIICodec implements ICodec {

	@Override
	public void boardToOutputstream(OutputStream output,
			IBeamsOfLightPuzzleBoard board) {
		StringBuilder builder = new StringBuilder();
		String lineBreak = "";
		for (int row = 0; row < board.getHeight(); row++) {
			builder.append(lineBreak);
			for (int col = 0; col < board.getWidth(); col++) {
				ITile tile = board.getTileAt(col, row);
				builder.append(tile.toString() + " ");
			}
			lineBreak = "\n";
		}
		PrintWriter writer = new PrintWriter(output);
		writer.append(builder.toString());
		writer.flush();
	}

	@Override
	public void turnsToOutputstream(OutputStream output, List<Turn> turns) {
		StringBuilder builder = new StringBuilder();
		for (Turn turn : turns) {
			builder.append(turn + "\n");
		}
		PrintWriter writer = new PrintWriter(output);
		writer.append(builder.toString());
		writer.flush();
	}

	@Override
	public IBeamsOfLightPuzzleBoard boardFromInputstream(InputStream input)
			throws WrongCodecException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Scanner scanner = new Scanner(input);
		
		final IBeamsOfLightPuzzleBoard puzzle = new BeamsOfLightPuzzleBoard();
		
		int row = -1, col = 0, maxCol = 0;
		
		while(scanner.hasNextLine()) {
			row++;
		    String line   = scanner.nextLine();
			if(!line.matches("([nesw\\-(\\d+)] )+")){
				throw new WrongCodecException();
			}
		    
		    final LinkedList<ITile> tileList = new LinkedList<ITile>();
		    col = 0;
		    for(String token: line.split(" ")) {
		    	ITile tile = getTileByToken(token, puzzle, col, row);
		    	puzzle.enqueueTile(tile);
		    	col++;
		    	maxCol = Math.max(maxCol,col);
		    }
		}
		
		puzzle.init(maxCol,row+1);
		puzzle.flush();
	
		return puzzle;
	}
	
	private ITile getTileByToken(String token, IBeamsOfLightPuzzleBoard b, int row, int col) {
		if(token.matches("[0-9]+")) {
				return new NumberTile((IBeamsOfLightPuzzleBoard) b, Integer.parseInt(token), row, col);
		}
		else {
			char sign = '\0';
			try {
				sign = token.charAt(0); 
			}
			catch (StringIndexOutOfBoundsException e) {
			}
			return new LightTile((IBeamsOfLightPuzzleBoard) b, row, col, LightTileState.signToState(sign));
		}
	}

	@Override
	public List<Turn> turnsFromInputstream(InputStream input, IBeamsOfLightPuzzleBoard board)
			throws WrongCodecException {
		List<Turn>turns=new LinkedList<>();
		Scanner scanner = new Scanner(input);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			if(!line.matches("(\\d+ ){2}([nesw\\-] ){2}\\d{1,3}")){
				throw new WrongCodecException();
			}
			Scanner lineScanner = new Scanner(line);//TODO Check values
			int row = lineScanner.nextInt();
			int col=lineScanner.nextInt();
			LightTileState oldState=LightTileState.signToState(lineScanner.next().charAt(0));
			LightTileState newState=LightTileState.signToState(lineScanner.next().charAt(0));
			turns.add(new Turn(board, row, col, oldState, newState));
			
		}
		return turns;
	}

}
