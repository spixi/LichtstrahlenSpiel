package de.bwvaachen.beamoflightgame.helper;

import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.ByteOrder;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;
import de.bwvaachen.beamoflightgame.model.ILightTile;
import de.bwvaachen.beamoflightgame.model.INumberTile;
import de.bwvaachen.beamoflightgame.model.ITile;
import de.bwvaachen.beamoflightgame.model.LightTileState;
import de.bwvaachen.beamoflightgame.model.builder.BeamOfLightPuzzleBoardBuilder;
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
				ITile tile = board.getTileAt(row, col);
				//TODO This is ugly. Please use tile.toString() here!
				if (tile instanceof INumberTile) {
					builder.append(((INumberTile) tile).getNumber() + " ");
				} else {
					builder.append("_ ");
				}
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
			builder.append(turn.getX() + " " + turn.getY() + " "
					+ turn.getOldTileState().getSign() + " "
					+ turn.getNewTileState().getSign() + " " + turn.getFlags()
					+ "\n");
		}
		PrintWriter writer = new PrintWriter(output);
		writer.append(builder.toString());
		writer.flush();
	}

	@Override
	public IBeamsOfLightPuzzleBoard boardFromInputstream(InputStream input)
			throws WrongCodecException {
		Scanner scanner = new Scanner(input);

		Integer width = null;

		LinkedList<String> rowList = new LinkedList<>();
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
		}
		width = rowList.getFirst().split(" ").length;
		int height = rowList.size();

		BeamOfLightPuzzleBoardBuilder builder = new BeamOfLightPuzzleBoardBuilder(
				width, height);

		int row = 0;
		for (String rowLine : rowList) {

			String[] split = rowLine.split(" ");
			for (int col = 0; col < split.length; col++) {
				if (split[col].matches("[0-9]+")) {
					builder.setNumberTiles(row, col,
							Integer.parseInt(split[col]));
				}
			}
			row++;
		}
		
		IBeamsOfLightPuzzleBoard puzzle = builder
				.createBeamOfLightPuzzleBoard();
		return puzzle;
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
			byte flag=lineScanner.nextByte();
			turns.add(new Turn(board, row, col, oldState, newState));
			
		}
		return turns;
	}

}
