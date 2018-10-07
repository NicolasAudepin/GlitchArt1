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
	private PicturePanel picturePanel;
	private FilterPanel filterPanel;
	private PanelParent panelParent;

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
		this.GM = GM;
		
		setMinimumSize(new Dimension(800,800));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(Color.darkGray);
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.BLACK);
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.setFont( new Font("Consolas", Font.PLAIN, 20));
		
		picturePanel = new PicturePanel(GM,this);
		filterPanel = new FilterPanel(GM,this);
		panelParent = new PanelParent(GM,this);		
		tabbedPane.add("PictureSelection",picturePanel);
		tabbedPane.add("Filter", filterPanel);
		tabbedPane.add("parent", panelParent );
		System.out.println("tabbedPane");
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		contentPane.setVisible(true);
		System.out.println("tabbedPane after");
	}
	
	
	public JPanel getContentPane() {
		return contentPane;
	}

	public void setContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
	}

	public static GUIManager getGM() {
		return GM;
	}

	public static void setGM(GUIManager gM) {
		GM = gM;
	}

	public PicturePanel getPicturePanel() {
		return picturePanel;
	}

	public void setPicturePanel(PicturePanel picturePanel) {
		this.picturePanel = picturePanel;
	}

	public FilterPanel getFilterPanel() {
		return filterPanel;
	}

	public void setFilterPanel(FilterPanel filterPanel) {
		this.filterPanel = filterPanel;
	}

	public PanelParent getPanelParent() {
		return panelParent;
	}

	public void setPanelParent(PanelParent panelParent) {
		this.panelParent = panelParent;
	}

	

}
