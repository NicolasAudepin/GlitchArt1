package GUI;

import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpringLayout;

public class PanelDeLaFamille extends JPanel{
	
	private int multiplier = 1;
	
	private int dist = 20;
	private int size = 30;
	
	private Font font = new Font("Consolas", Font.PLAIN, 15);
	
	private String W = SpringLayout.WEST;
	private String E = SpringLayout.EAST;
	private String N = SpringLayout.NORTH;
	private String S = SpringLayout.SOUTH;
	
	private JList<String> filterList = new JList<String>(); 
	
	
	public PanelDeLaFamille(){
		SpringLayout SL = new SpringLayout();
		this.setLayout(SL);
		
		//On ajoute la liste de selection des filtres
		
		DefaultListModel<String> list = new DefaultListModel<String>();
		JScrollPane scroll = new JScrollPane();
		list.addElement( "dddddddddddddda");
		list.addElement( "dddddddddddddda");
		list.addElement( "dddddddddddddda");
		list.addElement( "dddddddddddddda");
		list.addElement( "dddddddddddddda");
		list.addElement( "dddddddddddddda");
		list.addElement( "ddddddddddezeddddddda");
		list.addElement("d");
	
		filterList.setModel(list);
		scroll.setViewportView(filterList);
		add(scroll);
		SL.putConstraint(N, scroll, dist, N, this);
		SL.putConstraint(W, scroll, dist, W, this);
		SL.putConstraint(E, scroll, -dist, E, this);

		SL.putConstraint(S, scroll, -dist, S, this);
		/*JLabel joe = new JLabel("JOE");
		JTextField jimy = new JTextField("JIIIMMYH");
		
		add(jimy);
		add(joe);
		
		jack.putConstraint(W, joe, 20, W, this);
		jack.putConstraint(W, jimy, 20, E, joe);
		jack.putConstraint(E, this,  30, E, jimy);
		jack.putConstraint(N, jimy, 15, N, this);
		jack.putConstraint(S, this, 20, S, jimy);
		*/
	}

}
