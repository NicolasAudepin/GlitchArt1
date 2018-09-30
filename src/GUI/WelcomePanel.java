package GUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.ScrollPane;

public class WelcomePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public WelcomePanel() {
		JButton chooseInputButton = new JButton("Choose Button");
		
		add(chooseInputButton);
		
		
		
		
		
		JLabel salut = new JLabel("ICI C4EST L'IMAGE");
		add(salut);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBackground(Color.DARK_GRAY);
		scrollPane.setBounds(new Rectangle(0, 0, 17, 31));
		scrollPane.setSize(200, 300);
		scrollPane.add(new JLabel("ddd"));
		add(scrollPane);

		
		ScrollPane scro = new ScrollPane();
		JTable jack = new JTable(5,2); 
		
		jack.setModel(arg0);
		scro.add(jack);
		
		add(scro);

	}

}
