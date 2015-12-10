package jCardSim_GUI;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JToolBar;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JTextPane;

public class MainForm {

	private JFrame frmJcardsim;
	private JTextField aidTextField;
	private JTextField apduTextField;
	
	private File classFile;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
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
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmJcardsim = new JFrame();
		frmJcardsim.setResizable(false);
		frmJcardsim.setTitle("jCardSim GUI");
		frmJcardsim.setBounds(100, 100, 735, 480);
		frmJcardsim.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmJcardsim.getContentPane().setLayout(null);
		
		JToolBar toolBar = new JToolBar();
		toolBar.setBounds(0, 0, 729, 32);
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
                    System.out.println("Selected File = " + classFile.getName());
                }
			}
		});
		openFileBtn.setToolTipText("Open App Class File");
		openFileBtn.setIcon(new ImageIcon(MainForm.class.getResource("/com/sun/java/swing/plaf/windows/icons/NewFolder.gif")));
		toolBar.add(openFileBtn);
		
		JLabel lblNewLabel = new JLabel("AID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(24, 59, 27, 14);
		frmJcardsim.getContentPane().add(lblNewLabel);
		
		aidTextField = new UpperCaseField();
		aidTextField.setToolTipText("Enter AID");
		aidTextField.setFont(new Font("Courier New", Font.PLAIN, 12));
		aidTextField.setBounds(51, 54, 188, 24);
		frmJcardsim.getContentPane().add(aidTextField);
		aidTextField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 95, 709, 291);
		frmJcardsim.getContentPane().add(scrollPane);
		
		JTextPane outputTextPane = new JTextPane();
		outputTextPane.setFont(new Font("Courier New", Font.PLAIN, 12));
		outputTextPane.setEditable(false);
		scrollPane.setViewportView(outputTextPane);
		
		apduTextField = new JTextField();
		apduTextField.setEnabled(false);
		apduTextField.setFont(new Font("Courier New", Font.PLAIN, 12));
		apduTextField.setBounds(10, 397, 588, 24);
		frmJcardsim.getContentPane().add(apduTextField);
		apduTextField.setColumns(10);
		
		JButton sendApduBtn = new JButton("Send APDU");
		sendApduBtn.setEnabled(false);
		sendApduBtn.setBounds(608, 397, 105, 24);
		frmJcardsim.getContentPane().add(sendApduBtn);
		
		JButton loadAppletBtn = new JButton("Load Applet");
		loadAppletBtn.setBounds(249, 54, 105, 24);
		frmJcardsim.getContentPane().add(loadAppletBtn);
	}
}
