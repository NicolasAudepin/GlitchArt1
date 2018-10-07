package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import manager.GUIManager;

import javax.swing.JTabbedPane;

public class NewMainFrame extends JFrame {

	private JPanel contentPane;
	private static GUIManager GM;

	FilterPanel filterPanel;
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					NewMainFrame frame = new NewMainFrame(GM);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	*/

	/**
	 * Create the frame.
	 */
	public NewMainFrame(GUIManager GM) {
		NewMainFrame.GM = GM;
		
		setMinimumSize(new Dimension(800,800));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(Color.darkGray);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		filterPanel = new FilterPanel(GM,this);
		tabbedPane.setBackground(Color.BLACK);
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setFont( new Font("Consolas", Font.PLAIN, 20));
		tabbedPane.add("PictureSelection",new PicturePanel(GM,this));
		tabbedPane.add("Filter", (JPanel)filterPanel);
		tabbedPane.add("parent", new PanelParent(GM,this));
		
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}

	public FilterPanel getFilterPanel() {
		return filterPanel;
		// TODO Auto-generated method stub
		
	}
	

}