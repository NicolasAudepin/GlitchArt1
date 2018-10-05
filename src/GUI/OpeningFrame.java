package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTabbedPane;

public class OpeningFrame extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					OpeningFrame frame = new OpeningFrame();
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
	public OpeningFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 1000);
		contentPane = new JPanel();
		contentPane.setBackground(Color.darkGray);
		contentPane.setBorder(new EmptyBorder(1, 1, 1, 1));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBackground(Color.BLACK);
		tabbedPane.setForeground(Color.WHITE);
		tabbedPane.add("first", new PanelDeLaFamille());
		tabbedPane.add("Salut",new WelcomePanel());
		contentPane.add(tabbedPane, BorderLayout.CENTER);
	}
	
	class ArturPanel extends JPanel {

		  public ArturPanel() {

		    JButton b1 = new JButton("New York");
		    add(b1);
		    JButton b2 = new JButton("London");
		    add(b2);
		    JButton b3 = new JButton("Hong Kong");
		    add(b3);
		    JButton b4 = new JButton("Tokyo");
		    add(b4);
		  }
		}

}
