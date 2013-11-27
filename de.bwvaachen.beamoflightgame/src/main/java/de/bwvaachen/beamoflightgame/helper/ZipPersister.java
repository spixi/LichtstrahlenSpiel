package de.bwvaachen.beamoflightgame.helper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import de.bwvaachen.beamoflightgame.controller.Turn;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightPuzzleBoard;

public class ZipPersister implements IPersistenceHelper {

	private ICodec codec;

	public ZipPersister(ICodec saveCodec) {
		this.codec = saveCodec;
	}

	@Override
	public void save(File path, IBeamsOfLightPuzzleBoard board, List<Turn> turns)
			throws IOException {

		FileOutputStream fileOutputStream = new FileOutputStream(path);
		ZipOutputStream zipOut = new ZipOutputStream(fileOutputStream);
		try {

			zipOut.putNextEntry(new ZipEntry("codec"));
			zipOut.write(codec.getClass().toString().getBytes());

			zipOut.putNextEntry(new ZipEntry("board"));
			codec.boardToOutputstream(zipOut, board);

			zipOut.putNextEntry(new ZipEntry("turns"));
			codec.turnsToOutputstream(zipOut, turns);
			zipOut.flush();
			fileOutputStream.flush();
		} catch (Throwable t) {
			zipOut.close();
			fileOutputStream.close();
			throw t;
		}

	}

	@Override
	public void load(File path) throws IOException, WrongCodecException, InstantiationException, IllegalAccessException, ClassNotFoundException {

		ICodec codec = null;
		for (int i = 0; i < 2; i++) {

			FileInputStream fileInputStream = new FileInputStream(path);
			ZipInputStream zipIn = new ZipInputStream(fileInputStream);
			ZipEntry entry = zipIn.getNextEntry();
			IBeamsOfLightPuzzleBoard board = null;
			List<Turn> turns=null;
			while (entry != null) {
				String name = entry.getName();
				System.out.println(name);
				if (codec == null && name.equals("codec")) {
					byte[] buffer = new byte[2048];
					int countBytes = zipIn.read(buffer);
					String codecName = new String(Arrays.copyOf(buffer,
							countBytes));
					try {
						codec = (ICodec) fileInputStream.getClass()
								.getClassLoader().loadClass(codecName)
								.newInstance();
					} catch (Exception e) {
						throw new WrongCodecException("");
					}
					break;
				} else if (codec != null && board != null
						&& name.equals("turns")) {

					turns= codec.turnsFromInputstream(zipIn, board);

				} else if (name.equals("board")) {
					board = codec.boardFromInputstream(zipIn);
				}

			}
			entry = zipIn.getNextEntry();

		}
	}

	public static void main(String[] args) {
		ZipPersister test = new ZipPersister(new SimpleASCIICodec());
		try {
			test.save(new File("Test.zip"), null, null);
			test.load(new File("Test.zip"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
