package de.bwvaachen.beamoflightgame.editor;

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


@SuppressWarnings("serial")
public class EditorMain extends JFrame
	implements ActionListener{

	private int width, height ;
	private Dimension screenSize ;
	private Dimension frameSize ;
	private JTextField inputWidthTF, inputHeightTF ;
	private JLabel inputWidthL, inputHeightL, editorTypeL, header ;
	private JPanel contentPanel, boardSizePanel, editorTypePanel, buttonPanel ;
	private JButton okButton, cancelButton ;
	private JComboBox<String> editorTypeBox ;
	private String[] editorTypes = {"Zahlen","Linien"};
	
	public EditorMain(){
		screenSize = Toolkit.getDefaultToolkit().getScreenSize();
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
		
		header = new JLabel("<html>Dimensionen des Lichtstrahlen-Spielbretts angeben:<br>");
				//"(Max. Breite/Höhe bei derzeitiger Auflösung: )</html>");
		header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		
		inputWidthTF = new JTextField("5", 10);
		inputHeightTF = new JTextField("5", 10);
		inputWidthL = new JLabel("Breite:");
		inputHeightL = new JLabel("Höhe:");
		
		editorTypeL = new JLabel("<html>Editor-Modus: <br>");
		editorTypeL.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		editorTypeBox = new JComboBox<String>(editorTypes);
		
		boardSizePanel.add(inputWidthL);
		boardSizePanel.add(inputWidthTF);
		boardSizePanel.add(inputHeightL);
		boardSizePanel.add(inputHeightTF);
		
		editorTypePanel.add(editorTypeL);
		editorTypePanel.add(editorTypeBox);
		
		
		okButton = new JButton("OK") ;
		okButton.addActionListener(this);
		cancelButton = new JButton("Abbrechen");
		cancelButton.addActionListener(this);
		
		buttonPanel.add(okButton);
		buttonPanel.add(cancelButton);
		
		contentPanel.add(BorderLayout.NORTH, boardSizePanel);
		contentPanel.add(BorderLayout.CENTER, editorTypePanel);
		contentPanel.add(BorderLayout.SOUTH, buttonPanel) ;
	}
	
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == okButton && editorTypeBox.getSelectedItem() == "Linien"){
			width = Integer.valueOf(inputWidthTF.getText());
			height = Integer.valueOf(inputHeightTF.getText());
			new LineEditor(width,height);
			this.dispose();
		}
		if(e.getSource() == okButton && editorTypeBox.getSelectedItem() == "Zahlen"){
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
