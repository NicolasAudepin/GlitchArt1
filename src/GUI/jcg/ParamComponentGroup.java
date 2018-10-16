package GUI.jcg;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JTextField;

import Parameter.ParameterParent;

public class ParamComponentGroup {
	
	protected Color buttonColor = new Color(40,40,40);
	protected Color backGroundColor = Color.DARK_GRAY;
	protected Color behindPicColor = Color.BLACK;
	protected Color textColor = Color.WHITE;
	protected Font fontButton = new Font("Consolas", Font.BOLD, 20);
	protected Font fontDescription = new Font("Consolas", Font.PLAIN, 20);
	protected Font font = new Font("Consolas", Font.PLAIN, 18);
	protected Font bigFont = new Font("Consolas", Font.PLAIN, 40);
	
	
	private JLabel Jname;
	private JTextField Jdescription;
	
	public ParamComponentGroup(ArrayList<ParameterParent> list){
		ParameterParent p0 = list.get(0);
		
		Jdescription = new JTextField();
		initializeJname(p0.getName());
		initializeJdescription(p0.getDescription()); 
		System.out.println("paramGroup créé");
	}

	public ParamComponentGroup(ParameterParent p0) {
		initializeJname(p0.getName());
		initializeJdescription(p0.getDescription());
		System.out.println("paramGroup créé");
	}
		

	public JLabel getJname() {
		return Jname;
	}
	
	private void initializeJdescription(String description){
		Jdescription = new JTextField();
		Jdescription.setText(description);
		Jname.setForeground(textColor);
		Jname.setBackground(this.behindPicColor);
		Jname.setOpaque(true);
		Jname.setFont(font);
	}
	
	private void initializeJname(String name){
		Jname = new JLabel();
		Jname.setText(name);
		Jname.setForeground(textColor);
		Jname.setBackground(buttonColor);
		Jname.setOpaque(true);
		Jname.setFont(font);
		
	}
	
	public void setJname(JLabel jname) {
		Jname = jname;
	}

	public JTextField getJdescription() {
		return Jdescription;
	}

	public void setJdescription(JTextField jdescription) {
		Jdescription = jdescription;
	}

}
