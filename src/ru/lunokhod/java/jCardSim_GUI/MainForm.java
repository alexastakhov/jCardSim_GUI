package ru.lunokhod.java.jCardSim_GUI;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import com.jgoodies.looks.plastic.Plastic3DLookAndFeel;
import com.jgoodies.looks.plastic.theme.ExperienceRoyale;
import com.licel.jcardsim.utils.AIDUtil;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleContext;
import java.awt.Color;
import java.awt.Component;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.*;
import javax.swing.Box.Filler;
import javax.swing.border.LineBorder;

public class MainForm {

	private JFrame frmJcardsim;
	private HexUpperCaseField aidTextField;
	private HexUpperCaseField apduTextField;
	private JButton sendApduBtn;
	private JButton selectAppBtn;
	private JTextPane outputTextPane;
	private JComboBox<String> aidComboBox;
	private SimulatorAdapter simulatorAdapter;
	private File classFile;
	DefaultComboBoxModel<String> comboBoxModel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Plastic3DLookAndFeel.setPlasticTheme(new ExperienceRoyale());
		
		try{
			UIManager.setLookAndFeel(new Plastic3DLookAndFeel());
		}
		catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(MainForm.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frmJcardsim.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		classFile = null;
		simulatorAdapter = new SimulatorAdapter();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = (screenSize.width - 858) / 2;
		int locationY = (screenSize.height - 560) / 2;
		int sizeWidth = 858;
		int sizeHeight = 560;
		
		frmJcardsim = new JFrame();
		frmJcardsim.setResizable(false);
		frmJcardsim.setTitle("jCardSim GUI 1.0");
		frmJcardsim.setBounds(locationX, locationY, sizeWidth, sizeHeight);
		frmJcardsim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJcardsim.getContentPane().setLayout(null);
		
		JPanel statusBar = new JPanel();
		statusBar.setBorder(null);
		statusBar.setBounds(0, 511, 852, 21);
		frmJcardsim.getContentPane().add(statusBar);
		statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.X_AXIS));
		
		javax.swing.Box.Filler filler = new Filler((Dimension) null, (Dimension) null, (Dimension) null);
		filler.setPreferredSize(new Dimension(10, 0));
		filler.setMinimumSize(new Dimension(10, 0));
		filler.setMaximumSize(new Dimension(10, 32767));
		filler.setBounds(new Rectangle(0, 0, 10, 0));
		filler.setAlignmentX(Component.LEFT_ALIGNMENT);
		statusBar.add(filler);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(0, 509, 852, 2);
		frmJcardsim.getContentPane().add(separator);
		
		JLabel classFileLabel = new JLabel("Loaded File: Not loaded");
		classFileLabel.setMaximumSize(new Dimension(400, 14));
		classFileLabel.setPreferredSize(new Dimension(400, 14));
		classFileLabel.setMinimumSize(new Dimension(203, 14));
		classFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		statusBar.add(classFileLabel);
		
		JLabel selectedAidLabel = new JLabel("Selected AID: Not selected");
		selectedAidLabel.setMaximumSize(new Dimension(350, 14));
		selectedAidLabel.setPreferredSize(new Dimension(350, 14));
		statusBar.add(selectedAidLabel);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setMinimumSize(new Dimension(10000, 2));
		toolBar.setBounds(0, 0, 852, 32);
		frmJcardsim.getContentPane().add(toolBar);
		
		JButton openFileBtn = new JButton("");
		openFileBtn.setBorder(new LineBorder(Color.LIGHT_GRAY));
		openFileBtn.setPreferredSize(new Dimension(29, 29));
		openFileBtn.setMinimumSize(new Dimension(29, 29));
		openFileBtn.setMaximumSize(new Dimension(29, 29));
		openFileBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser fileOpen = new JFileChooser();      
				
				fileOpen.setMultiSelectionEnabled(false);
				fileOpen.setFileFilter(new FileNameExtensionFilter("JavaCard Class File .class", "class"));
				
                int ret = fileOpen.showDialog(null, "Открыть файл");                
                if (ret == JFileChooser.APPROVE_OPTION) {
                	classFile = fileOpen.getSelectedFile();
                    classFileLabel.setText("Class File: " + classFile.getName());
                    classFileLabel.setToolTipText(classFile.getPath());
                    
                    writeLine("Loaded Applet Class File: " + classFile.getPath());
                }
			}
		});
		
		toolBar.addSeparator(new Dimension(10, 28));
		
		JButton newButton = new JButton("");
		newButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
		newButton.setMaximumSize(new Dimension(29, 29));
		newButton.setMinimumSize(new Dimension(29, 29));
		newButton.setPreferredSize(new Dimension(29, 29));
		newButton.setIcon(new ImageIcon(MainForm.class.getResource("/com/sun/java/swing/plaf/windows/icons/File.gif")));
		newButton.setToolTipText("Create new script");
		toolBar.add(newButton);
		
		toolBar.addSeparator(new Dimension(3, 28));
		
		openFileBtn.setToolTipText("Open JavaCard Applet Class File");
		openFileBtn.setIcon(new ImageIcon(MainForm.class.getResource("/com/sun/java/swing/plaf/windows/icons/NewFolder.gif")));
		toolBar.add(openFileBtn);
		
		toolBar.addSeparator(new Dimension(3, 28));
		
		JButton saveButton = new JButton("");
		saveButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
		saveButton.setMaximumSize(new Dimension(29, 29));
		saveButton.setMinimumSize(new Dimension(29, 29));
		saveButton.setPreferredSize(new Dimension(29, 29));
		saveButton.setIcon(new ImageIcon(MainForm.class.getResource("/com/sun/java/swing/plaf/windows/icons/FloppyDrive.gif")));
		saveButton.setToolTipText("Open JavaCard Applet Class File");
		toolBar.add(saveButton);
		
		JSeparator separator1 = new JSeparator(JSeparator.VERTICAL);
		separator1.setSize(new Dimension(2, 26));
		separator1.setPreferredSize(new Dimension(2, 26));
		separator1.setMaximumSize(new Dimension(2, 26));
		toolBar.addSeparator(new Dimension(6, 28));
		toolBar.add(separator1);
		toolBar.addSeparator(new Dimension(6, 28));
		
		JButton appListButton = new JButton("");
		appListButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
		appListButton.setPreferredSize(new Dimension(29, 29));
		appListButton.setMinimumSize(new Dimension(29, 29));
		appListButton.setMaximumSize(new Dimension(29, 29));
		appListButton.setIcon(new ImageIcon(MainForm.class.getResource("/com/sun/javafx/scene/web/skin/UnorderedListBullets_16x16_JFX.png")));
		appListButton.setToolTipText("Show loaded applets");
		toolBar.add(appListButton);

		toolBar.addSeparator(new Dimension(3, 28));
		
		JButton restartButton = new JButton("");
		restartButton.setBorder(new LineBorder(Color.LIGHT_GRAY));
		restartButton.setPreferredSize(new Dimension(29, 29));
		restartButton.setMinimumSize(new Dimension(29, 29));
		restartButton.setMaximumSize(new Dimension(29, 29));
		restartButton.setIcon(new ImageIcon(MainForm.class.getResource("/com/sun/javafx/scene/web/skin/Redo_16x16_JFX.png")));
		restartButton.setToolTipText("Restart JavaCard Runtime");
		toolBar.add(restartButton);
		
		JLabel lblNewLabel = new JLabel("AID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(24, 48, 27, 14);
		frmJcardsim.getContentPane().add(lblNewLabel);
		
		aidTextField = new HexUpperCaseField();
		aidTextField.setToolTipText("Enter AID");
		aidTextField.setFont(new Font("Courier New", Font.PLAIN, 12));
		aidTextField.setBounds(51, 43, 243, 24);
		frmJcardsim.getContentPane().add(aidTextField);
		aidTextField.setMaxLenght(32);
		aidTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 78, 832, 385);
		frmJcardsim.getContentPane().add(scrollPane);
		
		outputTextPane = new JTextPane();
		outputTextPane.setFont(new Font("Courier New", Font.PLAIN, 12));
		outputTextPane.setEditable(false);
		scrollPane.setViewportView(outputTextPane);
		
		apduTextField = new HexUpperCaseField();
		apduTextField.setEnabled(false);
		apduTextField.setFont(new Font("Courier New", Font.PLAIN, 12));
		apduTextField.setBounds(10, 474, 719, 24);
		frmJcardsim.getContentPane().add(apduTextField);
		apduTextField.setColumns(10);
		
		sendApduBtn = new JButton("Send APDU");
		sendApduBtn.setEnabled(false);
		sendApduBtn.setBounds(737, 474, 105, 24);
		frmJcardsim.getContentPane().add(sendApduBtn);
		
		JButton loadAppletBtn = new JButton("Install Applet");
		loadAppletBtn.setToolTipText("Click to install Applet");
		loadAppletBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String aid = aidTextField.getText();
				
				if (classFile == null) {
					JOptionPane.showMessageDialog(new JFrame(), "Load Applet Class File", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				if (aid.length() < 10) {
					JOptionPane.showMessageDialog(new JFrame(), "AID has to contain 5 or more bytes.", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if (aid.length() % 2 != 0) {
					JOptionPane.showMessageDialog(new JFrame(), "AID has to contain even number of symbols.", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				if (simulatorAdapter.isAppletInstalled(aid)) {
					JOptionPane.showMessageDialog(new JFrame(), "Applet with same AID is already installed.", "Warning", JOptionPane.WARNING_MESSAGE);
					return;
				}
				
				loadApplet(aid, classFile);
			}
		});
		loadAppletBtn.setBounds(304, 43, 95, 24);
		frmJcardsim.getContentPane().add(loadAppletBtn);
		
		comboBoxModel = new DefaultComboBoxModel<String>();
		aidComboBox = new JComboBox<String>();
		aidComboBox.setEnabled(false);
		aidComboBox.setBounds(440, 43, 261, 24);
		aidComboBox.setModel(comboBoxModel);
		frmJcardsim.getContentPane().add(aidComboBox);
		
		selectAppBtn = new JButton("Select");
		selectAppBtn.setEnabled(false);
		selectAppBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (comboBoxModel.getSize() > 0)
				{
					String aid = comboBoxModel.getSelectedItem().toString();
					
					if (simulatorAdapter.selectApplet(aid)) {
						AppletDescriptor appDscr = simulatorAdapter.getAppletDescriptor(aid);
						
						apduTextField.setEnabled(true);
						sendApduBtn.setEnabled(true);
						selectedAidLabel.setText("Selected AID: " + aid);
						writeLine("Applet " + appDscr.getClassName() + " [AID:" + aid + "] " + "has been selected");
						writeLine();
					}
					else {
						writeLine("Applet selecting error [AID:" + aid + "]");
					}
				}
			}
		});
		selectAppBtn.setPreferredSize(new Dimension(95, 23));
		selectAppBtn.setToolTipText("Click to install Applet");
		selectAppBtn.setBounds(711, 43, 95, 24);
		frmJcardsim.getContentPane().add(selectAppBtn);
	}
	
	private void loadApplet(String aid, File appFile) {
		if (simulatorAdapter.installApplet(aid, appFile))
		{
			aidTextField.setText("");
			refreshAidCombo();
			aidComboBox.setEnabled(true);
			selectAppBtn.setEnabled(true);
			
			writeLine("Applet Class Installed: " + classFile.getName() + " [AID: " + aid + "]");
			writeLine();
		}
		else
		{
			writeLine("Applet Class is not Installed! Invalid Superclass or file format.");
			writeLine();
		}
	}
	
	private void refreshAidCombo() {
		ArrayList<AppletDescriptor> applets = simulatorAdapter.getInstalledApplets();
		
		comboBoxModel.removeAllElements();
		for (AppletDescriptor ad : applets) {
			comboBoxModel.addElement(AIDUtil.toString(ad.getAid()));
		}
	}
	
	private void writeLine(String line) {
		Document doc = outputTextPane.getDocument();
		StyleContext context = new StyleContext();
		Style defStyle = context.getStyle(StyleContext.DEFAULT_STYLE);
		
		try {
			doc.insertString(doc.getLength(), line + "\r\n", defStyle);
			System.out.println(line);
		}
		catch (BadLocationException e) {
			System.out.println("MainForm.writeMsg BadLocationException");
		}
	}
	
	private void writeLine() {
		writeLine("");
	}
	
	private String bytesToString(byte[] bytes, boolean insertSpace) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < bytes.length; i++) {
			sb.append(toHexDigit((byte)((bytes[i] & 0xF0) >>> 4)));
			sb.append(toHexDigit((byte)(bytes[i] & 0xF)));
			if (insertSpace)
				sb.append(" ");
		}
		
		return sb.toString();
	}
	
	private String toHexDigit(byte b) {
		switch (b) {
			case 1 : return "1";
			case 2 : return "2";
			case 3 : return "3";
			case 4 : return "4";
			case 5 : return "5";
			case 6 : return "6";
			case 7 : return "7";
			case 8 : return "8";
			case 9 : return "9";
			case 10 : return "A";
			case 11 : return "B";
			case 12 : return "C";
			case 13 : return "D";
			case 14 : return "E";
			case 15 : return "F";
			default: return "0";
		}
	}
}
