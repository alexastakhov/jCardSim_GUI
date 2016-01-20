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
import javax.swing.border.LineBorder;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.SystemColor;

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
	private DefaultComboBoxModel<String> comboBoxModel;
	private AboutDialog aboutDialog;
	private JLabel selectedAidLabel;
	private ScriptFrame scriptFrame;
	
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
		aboutDialog = new AboutDialog();
		scriptFrame = new ScriptFrame();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		int sizeWidth = 968;
		int sizeHeight = 617;
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int locationX = (screenSize.width - sizeWidth) / 2;
		int locationY = (screenSize.height - sizeHeight) / 2;
		
		frmJcardsim = new JFrame();
		frmJcardsim.setResizable(false);
		frmJcardsim.setTitle("jCardSim GUI 1.0");
		frmJcardsim.setBounds(locationX, locationY, sizeWidth, sizeHeight);
		frmJcardsim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJcardsim.getContentPane().setLayout(null);
		
		JPanel statusBar = new JPanel();
		statusBar.setBorder(null);
		statusBar.setBounds(0, 567, 962, 21);
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
		separator.setBounds(0, 565, 962, 2);
		frmJcardsim.getContentPane().add(separator);
		
		JLabel classFileLabel = new JLabel("Loaded File: Not loaded");
		classFileLabel.setMaximumSize(new Dimension(400, 14));
		classFileLabel.setPreferredSize(new Dimension(400, 14));
		classFileLabel.setMinimumSize(new Dimension(203, 14));
		classFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		statusBar.add(classFileLabel);
		
		selectedAidLabel = new JLabel("Selected AID: Not selected");
		selectedAidLabel.setMaximumSize(new Dimension(350, 14));
		selectedAidLabel.setPreferredSize(new Dimension(350, 14));
		statusBar.add(selectedAidLabel);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setSize(new Dimension(0, 19));
		toolBar.setPreferredSize(new Dimension(15, 19));
		toolBar.setMaximumSize(new Dimension(15, 19));
		toolBar.setMinimumSize(new Dimension(10000, 2));
		toolBar.setBounds(0, 0, 962, 37);
		frmJcardsim.getContentPane().add(toolBar);
		
		JButton openFileBtn = new JButton("");
		openFileBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				openFileBtn.setBorder(new LineBorder(Color.GRAY));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				openFileBtn.setBorder(null);
			}
		});
		openFileBtn.setFocusable(false);
		openFileBtn.setBorder(null);
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
		newButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				scriptFrame.setVisible(true);
			}
		});
		newButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				newButton.setBorder(new LineBorder(Color.GRAY));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				newButton.setBorder(null);
			}
		});
		newButton.setFocusable(false);
		newButton.setBorder(null);
		newButton.setMaximumSize(new Dimension(29, 29));
		newButton.setMinimumSize(new Dimension(29, 29));
		newButton.setPreferredSize(new Dimension(29, 29));
		newButton.setIcon(new ImageIcon("C:\\Users\\alex\\eclipse_jc_workspace\\jCardSim_GUI\\icons\\document.png"));
		newButton.setToolTipText("Create new script");
		toolBar.add(newButton);
		
		toolBar.addSeparator(new Dimension(3, 28));
		
		openFileBtn.setToolTipText("Open JavaCard Applet class file");
		openFileBtn.setIcon(new ImageIcon("C:\\Users\\alex\\eclipse_jc_workspace\\jCardSim_GUI\\icons\\folder-open.png"));
		toolBar.add(openFileBtn);
		
		toolBar.addSeparator(new Dimension(3, 28));
		
		JButton saveButton = new JButton("");
		saveButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				saveButton.setBorder(new LineBorder(Color.GRAY));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				saveButton.setBorder(null);
			}
		});
		
		JButton openScriptFileBtn = new JButton("");
		openScriptFileBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				openScriptFileBtn.setBorder(new LineBorder(Color.GRAY));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				openScriptFileBtn.setBorder(null);
			}
		});
		openScriptFileBtn.setIcon(new ImageIcon("C:\\Users\\alex\\eclipse_jc_workspace\\jCardSim_GUI\\icons\\folder-open-document-text.png"));
		openScriptFileBtn.setToolTipText("Open APDU script file");
		openScriptFileBtn.setPreferredSize(new Dimension(29, 29));
		openScriptFileBtn.setMinimumSize(new Dimension(29, 29));
		openScriptFileBtn.setMaximumSize(new Dimension(29, 29));
		openScriptFileBtn.setFocusable(false);
		openScriptFileBtn.setBorder(null);
		toolBar.add(openScriptFileBtn);
		
		toolBar.addSeparator(new Dimension(3, 28));
		
		saveButton.setFocusable(false);
		saveButton.setBorder(null);
		saveButton.setMaximumSize(new Dimension(29, 29));
		saveButton.setMinimumSize(new Dimension(29, 29));
		saveButton.setPreferredSize(new Dimension(29, 29));
		saveButton.setIcon(new ImageIcon("C:\\Users\\alex\\eclipse_jc_workspace\\jCardSim_GUI\\icons\\disk.png"));
		saveButton.setToolTipText("Save printout log file");
		toolBar.add(saveButton);
		
		JSeparator separator1 = new JSeparator(JSeparator.VERTICAL);
		separator1.setSize(new Dimension(2, 26));
		separator1.setPreferredSize(new Dimension(2, 26));
		separator1.setMaximumSize(new Dimension(2, 26));
		toolBar.addSeparator(new Dimension(6, 28));
		toolBar.add(separator1);
		toolBar.addSeparator(new Dimension(6, 28));
		
		JButton appListButton = new JButton("");
		appListButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				appListButton.setBorder(new LineBorder(Color.GRAY));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				appListButton.setBorder(null);
			}
		});
		appListButton.setFocusable(false);
		appListButton.setBorder(null);
		appListButton.setPreferredSize(new Dimension(29, 29));
		appListButton.setMinimumSize(new Dimension(29, 29));
		appListButton.setMaximumSize(new Dimension(29, 29));
		appListButton.setIcon(new ImageIcon("C:\\Users\\alex\\eclipse_jc_workspace\\jCardSim_GUI\\icons\\credit-card-green.png"));
		appListButton.setToolTipText("Show loaded applets and virtual JCRE info");
		toolBar.add(appListButton);

		toolBar.addSeparator(new Dimension(3, 28));
		
		JButton restartButton = new JButton("");
		restartButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				byte[] atr = simulatorAdapter.resetRuntime();
				writeLine();
				writeLine("Restart JavaCard Runtime performed (!)");
				writeLine("ATR = " + bytesToString(atr, true));
			}
		});
		restartButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				restartButton.setBorder(new LineBorder(Color.GRAY));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				restartButton.setBorder(null);
			}
		});
		restartButton.setFocusable(false);
		restartButton.setBorder(null);
		restartButton.setPreferredSize(new Dimension(29, 29));
		restartButton.setMinimumSize(new Dimension(29, 29));
		restartButton.setMaximumSize(new Dimension(29, 29));
		restartButton.setIcon(new ImageIcon("C:\\Users\\alex\\eclipse_jc_workspace\\jCardSim_GUI\\icons\\arrow_refresh.png"));
		restartButton.setToolTipText("Restart JavaCard Runtime");
		toolBar.add(restartButton);
		
		toolBar.addSeparator(new Dimension(3, 28));
		
		JButton powerButton = new JButton("");
		powerButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				byte[] atr = simulatorAdapter.reset();
				writeLine();
				writeLine("Simulated card power Off-On performed (!)");
				writeLine("ATR = " + bytesToString(atr, true));
			}
		});
		powerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				powerButton.setBorder(new LineBorder(Color.GRAY));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				powerButton.setBorder(null);
			}
		});
		powerButton.setFocusable(false);
		powerButton.setBorder(null);
		powerButton.setPreferredSize(new Dimension(29, 29));
		powerButton.setMinimumSize(new Dimension(29, 29));
		powerButton.setMaximumSize(new Dimension(29, 29));
		powerButton.setIcon(new ImageIcon("C:\\Users\\alex\\eclipse_jc_workspace\\jCardSim_GUI\\icons\\lightning.png"));
		powerButton.setToolTipText("Simulated card power On/Off");
		toolBar.add(powerButton);
		
		toolBar.add(Box.createHorizontalGlue());
		
		JButton infoButton = new JButton("");
		infoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				aboutDialog.setVisible(true);
			}
		});
		infoButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				infoButton.setBorder(new LineBorder(Color.GRAY));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				infoButton.setBorder(null);
			}
		});
		infoButton.setIcon(new ImageIcon("C:\\Users\\alex\\eclipse_jc_workspace\\jCardSim_GUI\\icons\\information.png"));
		infoButton.setToolTipText("About jCardSim GUI");
		infoButton.setPreferredSize(new Dimension(29, 29));
		infoButton.setMinimumSize(new Dimension(29, 29));
		infoButton.setMaximumSize(new Dimension(29, 29));
		infoButton.setFocusable(false);
		infoButton.setBorder(null);
		toolBar.add(infoButton);
		
		toolBar.addSeparator(new Dimension(10, 28));
		
		JLabel lblNewLabel = new JLabel("AID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(24, 57, 27, 14);
		frmJcardsim.getContentPane().add(lblNewLabel);
		
		aidTextField = new HexUpperCaseField();
		aidTextField.setToolTipText("Enter AID");
		aidTextField.setFont(new Font("Courier New", Font.PLAIN, 12));
		aidTextField.setBounds(51, 52, 243, 24);
		frmJcardsim.getContentPane().add(aidTextField);
		aidTextField.setMaxLenght(32);
		aidTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 90, 942, 425);
		frmJcardsim.getContentPane().add(scrollPane);
		
		outputTextPane = new JTextPane();
		outputTextPane.setSelectedTextColor(SystemColor.desktop);
		outputTextPane.setSelectionColor(SystemColor.inactiveCaption);
		outputTextPane.setFont(new Font("Courier New", Font.PLAIN, 12));
		outputTextPane.setEditable(false);
		scrollPane.setViewportView(outputTextPane);
		
		apduTextField = new HexUpperCaseField();
		apduTextField.setEnabled(false);
		apduTextField.setFont(new Font("Courier New", Font.PLAIN, 12));
		apduTextField.setBounds(10, 528, 827, 24);
		frmJcardsim.getContentPane().add(apduTextField);
		apduTextField.setColumns(10);
		
		sendApduBtn = new JButton("Send APDU");
		sendApduBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sendAPDU();
			}
		});
		sendApduBtn.setEnabled(false);
		sendApduBtn.setBounds(847, 528, 105, 24);
		frmJcardsim.getContentPane().add(sendApduBtn);
		
		JButton loadAppletBtn = new JButton("Install Applet");
		loadAppletBtn.setToolTipText("Click to install Applet");
		loadAppletBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				loadApplet(aidTextField.getText(), classFile);
			}
		});
		loadAppletBtn.setBounds(304, 52, 95, 24);
		frmJcardsim.getContentPane().add(loadAppletBtn);
		
		comboBoxModel = new DefaultComboBoxModel<String>();
		aidComboBox = new JComboBox<String>();
		aidComboBox.setFont(new Font("Courier New", Font.PLAIN, 12));
		aidComboBox.setEnabled(false);
		aidComboBox.setBounds(440, 52, 261, 24);
		aidComboBox.setModel(comboBoxModel);
		frmJcardsim.getContentPane().add(aidComboBox);
		
		selectAppBtn = new JButton("Select");
		selectAppBtn.setEnabled(false);
		selectAppBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				selectApplet();
			}
		});
		selectAppBtn.setPreferredSize(new Dimension(95, 23));
		selectAppBtn.setToolTipText("Click to install Applet");
		selectAppBtn.setBounds(711, 52, 95, 24);
		frmJcardsim.getContentPane().add(selectAppBtn);
	}
	
	private void sendAPDU() {
		int len = apduTextField.getText().length();
		byte[] response;
		
		if (len < 8) {
			JOptionPane.showMessageDialog(new JFrame(), "APDU has to contain morethan 4 bytes.", "Warning", JOptionPane.WARNING_MESSAGE);
		}
		
		if (len % 2 != 0) {
			JOptionPane.showMessageDialog(new JFrame(), "APDU has to contain even number of symbols.", "Warning", JOptionPane.WARNING_MESSAGE);
			return;
		}
			
		response = simulatorAdapter.sendAPDU(stringToBytes(apduTextField.getText()));
		writeLine("Sent APDU = " + bytesToString(stringToBytes(apduTextField.getText()), true));
		writeLine("Card Response = " + bytesToString(response, true));
	}
	
	private void selectApplet() {
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
	
	private void loadApplet(String aid, File appFile) {
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
		
		if (simulatorAdapter.installApplet(aid, appFile))
		{
			aidTextField.setText("");
			refreshAidCombo();
			aidComboBox.setEnabled(true);
			selectAppBtn.setEnabled(true);
			apduTextField.setEnabled(true);
			sendApduBtn.setEnabled(true);
			selectedAidLabel.setText("Selected AID: " + aid);
			
			writeLine("Applet Class Installed and Selected: " + classFile.getName() + " [AID: " + aid + "]");
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
		
		if (bytes != null) {
			for (int i = 0; i < bytes.length; i++) {
				sb.append(String.format("%02X", bytes[i]));
				if (insertSpace)
				sb.append(" ");
			}
		}
		else {
			sb.append("null");
		}
		return sb.toString();
	}
	
    private static byte[] stringToBytes(String bytes) {
    	byte[] bArr;
    	
    	bytes = bytes.replace(" ", "");
    	
    	if (bytes == null || bytes.length() == 0)
    		return new byte[] {};
    		
    	if (bytes.length() % 2 != 0)
    		bytes += "0";
    	
    	bArr = new byte[bytes.length() / 2];
    	for (int i = 0; i < bArr.length; i++)
    	{
    		bArr[i] = (byte)((charCodeToByte(bytes.charAt(i * 2)) << 4) & 0xF0 | charCodeToByte(bytes.charAt(i * 2 + 1))); 
    	}
    	
    	return bArr;
    }
    
    private static byte charCodeToByte(char c) {
    	switch (c) {
    		case '1' : return 1;
    		case '2' : return 2;
    		case '3' : return 3;
    		case '4' : return 4;
    		case '5' : return 5;
    		case '6' : return 6;
    		case '7' : return 7;
    		case '8' : return 8;
    		case '9' : return 9;
    		case 'A' : return 10;
    		case 'B' : return 11;
    		case 'C' : return 12;
    		case 'D' : return 13;
    		case 'E' : return 14;
    		case 'F' : return 15;
    		default : return 0;
    	}
    }
}
