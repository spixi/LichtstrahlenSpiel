package de.bwvaachen.beamoflightgame.editor;

import static de.bwvaachen.beamoflightgame.i18n.I18N.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

@SuppressWarnings("serial")
public class HelpFrame extends JFrame {
	
	private FileReader 		reader;
	private BufferedReader 	br;
	private JTextArea		text;
	private Dimension		screenSize;
	
	public HelpFrame(String path) throws IOException{
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(400,600) ;
		
		text = new JTextArea();
		reader = new FileReader(path);
		br = new BufferedReader(reader);
        text.read(br,null);
        br.close();
        text.requestFocus();
        
        setLayout(new BorderLayout());
		getContentPane().add(new JScrollPane(text),BorderLayout.CENTER);
		
		setLocation((screenSize.width - getSize().width)/2,(screenSize.height - getSize().height)/2);
		setResizable(false);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setTitle(_f("EditorTitle",_("Help")));
		pack();
		setVisible(true);
	}

}
