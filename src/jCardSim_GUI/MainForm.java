package jCardSim_GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToolBar;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JLabel;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.JFileChooser;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JTextPane;

public class MainForm {

	private JFrame frmJcardsim;
	private JTextField textField;
	private JTextField textField_1;

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
				fileOpen.setFileFilter(new OpenFileFilter());
				
                int ret = fileOpen.showDialog(null, "Открыть файл");                
                if (ret == JFileChooser.APPROVE_OPTION) {
                    File file = fileOpen.getSelectedFile();
                    System.out.println("Selected File = " + file.getName());
                }
			}
			
			class OpenFileFilter extends FileFilter {

				public OpenFileFilter()
				{
					
				}
				
				@Override
				public boolean accept(File f) {
					// TODO Auto-generated method stub
					return false;
				}

				@Override
				public String getDescription() {
					// TODO Auto-generated method stub
					return null;
				} 
            	
            }
		});
		openFileBtn.setToolTipText("Open App Class File");
		openFileBtn.setIcon(new ImageIcon(MainForm.class.getResource("/com/sun/java/swing/plaf/windows/icons/NewFolder.gif")));
		toolBar.add(openFileBtn);
		
		JLabel lblNewLabel = new JLabel("AID");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(44, 58, 27, 14);
		frmJcardsim.getContentPane().add(lblNewLabel);
		
		textField = new JTextField();
		textField.setFont(new Font("Courier New", Font.PLAIN, 12));
		textField.setBounds(69, 53, 188, 24);
		frmJcardsim.getContentPane().add(textField);
		textField.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 95, 709, 272);
		frmJcardsim.getContentPane().add(scrollPane);
		
		JTextPane textPane = new JTextPane();
		textPane.setFont(new Font("Courier New", Font.PLAIN, 12));
		textPane.setEditable(false);
		scrollPane.setViewportView(textPane);
		
		textField_1 = new JTextField();
		textField_1.setEnabled(false);
		textField_1.setFont(new Font("Courier New", Font.PLAIN, 12));
		textField_1.setBounds(10, 378, 588, 24);
		frmJcardsim.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton = new JButton("Send APDU");
		btnNewButton.setEnabled(false);
		btnNewButton.setBounds(608, 378, 105, 24);
		frmJcardsim.getContentPane().add(btnNewButton);
		
		JButton btnLoadApplet = new JButton("Load Applet");
		btnLoadApplet.setBounds(267, 53, 105, 24);
		frmJcardsim.getContentPane().add(btnLoadApplet);
	}
}
