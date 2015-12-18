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
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Rectangle;
import javax.swing.*;
import javax.swing.Box.Filler;

public class MainForm {

	private JFrame frmJcardsim;
	private HexUpperCaseField aidTextField;
	private JTextField apduTextField;
	private JButton sendApduBtn;
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
		frmJcardsim = new JFrame();
		frmJcardsim.setResizable(false);
		frmJcardsim.setTitle("jCardSim GUI");
		frmJcardsim.setBounds(100, 100, 858, 560);
		frmJcardsim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJcardsim.getContentPane().setLayout(null);
		
		JPanel statusBar = new JPanel();
		statusBar.setBorder(null);
		statusBar.setBounds(0, 511, 852, 21);
		frmJcardsim.getContentPane().add(statusBar);
		statusBar.setLayout(new BorderLayout(0, 0));
		
		javax.swing.Box.Filler filler = new Filler((Dimension) null, (Dimension) null, (Dimension) null);
		filler.setPreferredSize(new Dimension(10, 0));
		filler.setMinimumSize(new Dimension(10, 0));
		filler.setMaximumSize(new Dimension(10, 32767));
		filler.setBounds(new Rectangle(0, 0, 10, 0));
		filler.setAlignmentX(Component.LEFT_ALIGNMENT);
		statusBar.add(filler, BorderLayout.WEST);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.LIGHT_GRAY);
		separator.setBounds(0, 509, 852, 2);
		frmJcardsim.getContentPane().add(separator);
		
		JLabel classFileLabel = new JLabel("Class File: Not selected");
		classFileLabel.setMinimumSize(new Dimension(800, 14));
		classFileLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		statusBar.add(classFileLabel);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setMinimumSize(new Dimension(10000, 2));
		toolBar.setBounds(0, 0, 852, 32);
		frmJcardsim.getContentPane().add(toolBar);
		
		JButton openFileBtn = new JButton("");
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
		
		Filler filler1 = new Box.Filler((Dimension) null, (Dimension) null, (Dimension) null);
		filler1.setMaximumSize(new Dimension(10, 32767));
		filler1.setPreferredSize(new Dimension(10, 0));
		filler1.setMinimumSize(new Dimension(10, 0));
		filler1.setSize(new Dimension(10, 0));
		toolBar.add(filler1);
		openFileBtn.setToolTipText("Open JavaCard Applet Class File");
		openFileBtn.setIcon(new ImageIcon(MainForm.class.getResource("/com/sun/java/swing/plaf/windows/icons/NewFolder.gif")));
		toolBar.add(openFileBtn);
		
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
		
		apduTextField = new JTextField();
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
		aidComboBox.setBounds(440, 43, 261, 24);
		aidComboBox.setModel(comboBoxModel);
		frmJcardsim.getContentPane().add(aidComboBox);
		
		JButton btnSelect = new JButton("Select");
		btnSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//apduTextField.setEnabled(true);
				//sendApduBtn.setEnabled(true);
			}
		});
		btnSelect.setPreferredSize(new Dimension(95, 23));
		btnSelect.setToolTipText("Click to install Applet");
		btnSelect.setBounds(711, 43, 95, 24);
		frmJcardsim.getContentPane().add(btnSelect);
	}
	
	private void loadApplet(String aid, File appFile) {
		if (simulatorAdapter.installApplet(aid, appFile))
		{
			aidTextField.setText("");
			refreshAidCombo();
			writeLine("Applet Class Installed: " + classFile.getName() + " (AID: " + aid + ")");
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
}
