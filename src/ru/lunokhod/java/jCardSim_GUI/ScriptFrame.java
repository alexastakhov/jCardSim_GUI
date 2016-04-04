package ru.lunokhod.java.jCardSim_GUI;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.JToolBar;
import javax.swing.JButton;
import java.awt.Font;

@SuppressWarnings("serial")
public class ScriptFrame extends JFrame {
	/**
	 * Create the frame.
	 */
	public ScriptFrame() {
		setTitle("jCardSim Script Editor");
		setBounds(100, 100, 948, 533);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setMaximumSize(new Dimension(32767, 23));
		panel.setPreferredSize(new Dimension(10, 23));
		getContentPane().add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));
		
		JSeparator separator = new JSeparator();
		panel.add(separator, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(null);
		panel_1.setPreferredSize(new Dimension(10, 20));
		panel.add(panel_1, BorderLayout.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		JEditorPane scriptEditorPane = new JEditorPane();
		scriptEditorPane.setFont(new Font("Courier New", Font.PLAIN, 12));
		scrollPane.setViewportView(scriptEditorPane);
		
		JToolBar toolBar = new JToolBar();
		getContentPane().add(toolBar, BorderLayout.NORTH);
		toolBar.setPreferredSize(new Dimension(13, 37));
		
		toolBar.addSeparator(new Dimension(10, 28));
		
		JButton button = new JButton("");
		button.setToolTipText("Create new script");
		button.setPreferredSize(new Dimension(29, 29));
		button.setMinimumSize(new Dimension(29, 29));
		button.setMaximumSize(new Dimension(29, 29));
		button.setFocusable(false);
		button.setBorder(null);
		toolBar.add(button);
	}
	
	private void hideFrame() {
		this.setVisible(false);
	}

}
