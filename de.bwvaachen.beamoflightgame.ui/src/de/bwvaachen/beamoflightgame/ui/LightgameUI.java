package de.bwvaachen.beamoflightgame.ui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.JProgressBar;

import de.bwvaachen.beamoflightgame.controller.ILightController;
import de.bwvaachen.beamoflightgame.model.IBeamsOfLightModel;
import de.bwvaachen.beamoflightgame.model.IField;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class LightgameUI extends JFrame {

	private JPanel contentPane;
	private ILightController controller=new ILightController() {
		
		@Override
		public IBeamsOfLightModel getCurrentModel() {
			return new IBeamsOfLightModel() {
				
				@Override
				public int getWidth() {
					return 5;
				}
				
				@Override
				public int getHeight() {
					return 5;
				}
				
				@Override
				public IField getFieldAt(int i, int j) {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public Iterator<IField> iterator() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public IField getFieldByIndex(int index) {
					// TODO Auto-generated method stub
					return null;
				}
			};
		}

		@Override
		public int countEmptyFields() {
			return 0;
		}

		@Override
		public int countLightFields() {
			return 0;
		}

		@Override
		public int countLightedFields() {
			return 0;
		}
	};

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LightgameUI frame = new LightgameUI();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LightgameUI() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		
		buildMenu();
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JProgressBar progressBar = new JProgressBar();
		contentPane.add(progressBar, BorderLayout.SOUTH);
		
		JPanel rasterPanel = new JPanel();
		contentPane.add(rasterPanel, BorderLayout.CENTER);

		
		IBeamsOfLightModel currentModel = controller.getCurrentModel();
		rasterPanel.setLayout(new GridLayout(0, currentModel.getWidth(), 0, 0));
		for(int row=0; row<currentModel.getHeight();row++) {
			for(int col=0;col<currentModel.getWidth();col++){
				rasterPanel.add(new JButton(row+"/"+col));
			}
		}
	}

	private void buildMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		JMenuItem mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		
		JSeparator separator_2 = new JSeparator();
		mnFile.add(separator_2);
		
		JMenuItem mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		
		JMenuItem mntmSaveAs = new JMenuItem("Save As");
		mnFile.add(mntmSaveAs);
		
		JSeparator separator_1 = new JSeparator();
		mnFile.add(separator_1);
		
		JMenuItem mntmLoad = new JMenuItem("Load");
		mnFile.add(mntmLoad);
		
		JSeparator separator = new JSeparator();
		mnFile.add(separator);
		
		JMenuItem mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
	}
	
	
}
