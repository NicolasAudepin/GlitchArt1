package FramePack;
import java.awt.EventQueue;

import javax.swing.JFrame;



public class DaMainWindow {
	
	private JFrame mainFrame;
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DaMainWindow window = new DaMainWindow();
					window.mainFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public DaMainWindow() {
		initialize();
	}
	
	private void initialize(){
	
	}

}
