package de.bwvaachen.beamoflightgame.editor;
import javax.swing.SwingUtilities;


public class EditorTest {

	@SuppressWarnings("unused")
	private static EditorMain testRun ;
	
	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
			testRun = new EditorMain();
			}});
		}
}