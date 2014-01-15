package de.bwvaachen.beamoflightgame.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;


/**
 * Speichert unser Spiel als ZipDatei
 * @author Basti - Andi
 *
 */
public class ZipPersister implements IPersistenceHelper {

	private ICodec codec;

	public ZipPersister(ICodec saveCodec) {
		this.codec = saveCodec;
	}

	@Override
	public Pair<IBeamsOfLightPuzzleBoard,List<Turn>> load(File path) throws IOException, WrongCodecException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		    ICodec codec = null;
			FileInputStream fileInputStream = new FileInputStream(path);
			ZipInputStream zipIn = new ZipInputStream(fileInputStream);
			ZipEntry entry;
			IBeamsOfLightPuzzleBoard board = null;
			List<Turn> turns=null;
			
			Map<String,String> sections = new HashMap<String,String>();

			while ((entry = zipIn.getNextEntry()) != null) {
				StringWriter sw = new StringWriter();
				int data;
				while((data = zipIn.read()) != -1) {
					sw.append((char)data);
				}
				sections.put(entry.getName(),sw.toString());
			}

			//depricated
//			if(!sections.containsKey("codec")) throw new WrongCodecException();
			if(!sections.containsKey("board")) throw new IllegalStateException();
//			if(!sections.containsKey("turns")) throw new WrongCodecException();
			
			
			
			codec = //(ICodec)Class.forName(sections.get("codec")).newInstance();
					new de.bwvaachen.beamoflightgame.helper.SimpleASCIICodec();
			
			//TODO: Refactor this stuff, we could also try to get rid of the codec trash
			board = codec.boardFromInputstream(new StringBufferInputStream(sections.get("board")));
			turns = codec.turnsFromInputstream(new StringBufferInputStream(sections.get("turns")),board);
			
			return new Pair<IBeamsOfLightPuzzleBoard, List<Turn>>(board,turns);
	}

	@Override
	public void save(File path, IBeamsOfLightPuzzleBoard board, List<Turn> turns)
			throws IOException {

		FileOutputStream fileOutputStream = new FileOutputStream(path);
		ZipOutputStream zipOut = new ZipOutputStream(fileOutputStream);
		try {

			zipOut.putNextEntry(new ZipEntry("codec"));
			zipOut.write(codec.getClass().toString().getBytes());
			zipOut.closeEntry();

			zipOut.putNextEntry(new ZipEntry("board"));
			codec.boardToOutputstream(zipOut, board);
			zipOut.closeEntry();

			zipOut.putNextEntry(new ZipEntry("turns"));
			codec.turnsToOutputstream(zipOut, turns);
			zipOut.closeEntry();
			
			zipOut.finish();
			
			zipOut.flush();
			fileOutputStream.flush();
		} catch (Throwable t) {
			zipOut.close();
			fileOutputStream.close();
			throw t;
		}

	}

}
