package GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import manager.GUIManager;

public class PanelParent extends JPanel{
	
	protected static GUIManager GM;
	protected NewMainFrame mainFrame;
	//les distances fixes entre les composents
	protected int dist = 10; // la distance normale entre deux objets
	protected int FSWidth = 200; //la largeur de la zone de selecion de filtre
	protected int FSHeigth = 200;//hauteur de la zone de selection de filtre
	protected int PBHeight =30; //hauteur de la bare de progrès
	protected int RBLength = 160; //largeur du boutton render
	protected int RBHeigth = 40; //hauteur du bouton render
	protected int smallIconWidth = 200; // la largeure des images dans la selection d'état
	
	//utile pour ecrire les constraints plus vite et plus lisibles
	protected String W = SpringLayout.WEST;
	protected String E = SpringLayout.EAST;
	protected String N = SpringLayout.NORTH;
	protected String S = SpringLayout.SOUTH;
	
	protected SpringLayout SL = new SpringLayout();	
	
	//Les couleurs et font des JComponents
	protected Color buttonColor = new Color(40,40,40);
	protected Color backGroundColor = Color.DARK_GRAY;
	protected Color behindPicColor = Color.BLACK;
	protected Color textColor = Color.WHITE;
	protected Font fontButton = new Font("Consolas", Font.BOLD, 20);
	protected Font font = new Font("Consolas", Font.PLAIN, 18);
	protected Font bigFont = new Font("Consolas", Font.PLAIN, 40);
	
	
	public PanelParent(GUIManager GM,NewMainFrame mainFrame){
		PanelParent.GM = GM;
		this.mainFrame= mainFrame;
		this.setBackground(backGroundColor);
		System.out.println("panel creation");
		this.setLayout(SL);
		
	}
	
	/**
	 * draw the image in the label a its best size without deforming it 
	 * @param image will be drawn in the label
	 * @param label the JLabel where the image will be drawn
	 */
	protected void drawScaledMainImage(Image image,JLabel label) {
		int heigth;
		int width;
		double IW= ((BufferedImage)image).getWidth();
		double IH = ((BufferedImage)image).getHeight();
		double ratioI = IW/IH;
		double LW= label.getWidth()+1;
		double LH = label.getHeight()+1;
		double ratioL = LW/LH;
		if(ratioI<ratioL){
			 heigth=(int) LH;
			 width=(int)(LH*ratioI);
		}
		else{
			width=(int)LW;
			heigth=(int) (LW/ratioI);
			
		}
		 
		ImageIcon im = new ImageIcon(image.getScaledInstance(width, heigth, Image.SCALE_FAST)
		);
		label.setIcon(im);
		label.setText("");
	}

	public int getSmallIconWidth(){
		return smallIconWidth;
	}

}
