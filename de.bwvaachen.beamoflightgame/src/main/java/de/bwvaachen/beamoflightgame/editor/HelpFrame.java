package de.bwvaachen.beamoflightgame.editor;

/*
Copyright (C) 2013 - 2014 by Andreas Pauls, Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

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
