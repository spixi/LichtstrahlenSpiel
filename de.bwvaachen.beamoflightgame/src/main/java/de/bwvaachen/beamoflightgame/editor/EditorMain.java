package de.bwvaachen.beamoflightgame.editor;

/*
Copyright (C) 2013 - 2014 by Georg Braun, Christian Frühholz, Marius Spix, Christopher Müller and Bastian Winzen Part of the Beam Of Lights Puzzle Project

This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.
This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY.

See the COPYING file for more details.
*/

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class EditorMain extends JFrame
	implements ActionListener{

	private int 					width ;
	private int 					height ;
	private int 					maxHeight ;
	private int 					maxWidth ;
	private Dimension 				screenSize ;
	private Dimension 				frameSize ;
	private JTextField 				inputWidthTF ;
	private JTextField				inputHeightTF ;
	private JLabel 					inputWidthL ;
	private JLabel					inputHeightL ;
	private JLabel					editorTypeL ;
	private JLabel					header ;
	private JPanel 					contentPanel ;
	private JPanel 					boardSizePanel ;
	private JPanel 					editorTypePanel ; 
	private JPanel 					buttonPanel ;
	private JButton 				okButton ;
	private JButton					cancelButton ;
	private JComboBox<EditorType> 	editorTypeBox ;
	
	public EditorMain(){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		maxWidth = (screenSize.width-128)/128;
		maxHeight = (screenSize.height-128)/128;
		setSize(400,120) ;			
		frameSize = getSize() ;									
		setMinimumSize(frameSize) ;								
		setLocation((screenSize.width - frameSize.width)/2,(screenSize.height - frameSize.height)/2);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("BeamOfLightGame Editor");
		
		initComponents();
		setLayout(new BorderLayout());
		add(BorderLayout.NORTH, header);
		add(BorderLayout.CENTER, contentPanel);
		pack();
		setVisible(true);
	}
	
	public void initComponents(){
		contentPanel = new JPanel() ;
		contentPanel.setLayout(new BorderLayout());
		
		boardSizePanel = new JPanel();
		editorTypePanel = new JPanel();
		buttonPanel = new JPanel();
		
		header = new JLabel("Enter dimensions for BeamOfLightGame board:");
		header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		
		inputWidthTF = new JTextField("4", 10);
		inputHeightTF = new JTextField("4", 10);
		inputWidthL = new JLabel("Width:");
		inputHeightL = new JLabel("Height:");
		
		editorTypeL = new JLabel("Editor Mode:");
		editorTypeL.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		editorTypeBox = new JComboBox<EditorType>(EditorType.values());
		
		boardSizePanel.add(inputWidthL);
		boardSizePanel.add(inputWidthTF);
		boardSizePanel.add(inputHeightL);
		boardSizePanel.add(inputHeightTF);
		
		editorTypePanel.add(editorTypeL);
		editorTypePanel.add(editorTypeBox);
		
		okButton = new JButton("OK") ;
		okButton.addActionListener(this);
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(this);
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		contentPanel.add(BorderLayout.NORTH, boardSizePanel);
		contentPanel.add(BorderLayout.CENTER, editorTypePanel);
		contentPanel.add(BorderLayout.SOUTH, buttonPanel) ;
	}
	
	public void actionPerformed(ActionEvent e) {
		int inputWidth = Integer.valueOf(inputWidthTF.getText());
		int inputHeight = Integer.valueOf(inputHeightTF.getText());
		
		if(e.getSource() == okButton && (inputWidth > maxWidth || inputHeight > maxHeight)){
			JOptionPane.showMessageDialog(	this,
											"Unable to display with current resolution!\nMaximum Width: " + maxWidth + "\nMaximum Height: " + maxHeight + "\n",
											"Error",
											JOptionPane.ERROR_MESSAGE
										 );
		}else{
			if(e.getSource() == okButton && editorTypeBox.getSelectedItem() == EditorType.LineEditor){
				width = Integer.valueOf(inputWidthTF.getText());
				height = Integer.valueOf(inputHeightTF.getText());
				new LineEditor(width,height);
				this.dispose();
			}
			if(e.getSource() == okButton && editorTypeBox.getSelectedItem() == EditorType.NumberEditor){
				width = Integer.valueOf(inputWidthTF.getText());
				height = Integer.valueOf(inputHeightTF.getText());
				new NumberEditor(width,height);
				this.dispose();
			}
			if(e.getSource() == cancelButton){
				this.dispose();
			}
		}
	}
}
