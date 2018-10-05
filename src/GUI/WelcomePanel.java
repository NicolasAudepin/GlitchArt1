package GUI;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.awt.Rectangle;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;
import java.awt.event.ContainerListener;

public class WelcomePanel extends JPanel {

	/**
	 * Create the panel.
	 */
	public WelcomePanel() {
		JButton chooseInputButton = new JButton("Choose Button");
		//chooseInputButton.setPreferredSize(new Dimension());
		add(chooseInputButton);
		
		JLabel salut = new JLabel("ICI C4EST L'IMAGE");
		add(salut);
		
		
		ScrollPane scro = new ScrollPane();
		scro.setSize(400, 600);
		JTable jack = new JTable(5,2); 
		jack.setBounds(100, 100, 200, 400);
		jack.setAutoResizeMode(2);
		jack.setFillsViewportHeight(true);;
		JLabel nemo = new JLabel("je suis une image");
		nemo.setOpaque(true);
		nemo.setBackground(Color.RED);
		nemo.setSize(100, 100);
		JLabel nemu = new JLabel("je suis nemu");
		nemu.setOpaque(true);
		nemu.setBackground(Color.BLUE);
		nemu.setSize(100, 100);
		jack.add(nemo, 0);
		jack.add(nemu, 0);
		//jack.setModel(arg0);
		scro.add(jack);
		
		add(scro);//

	}

}
