package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Label;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

public class PanelDeLaFamille extends JPanel{
	
	private int multiplier = 1;
	
	private int dist = 10; // la distance normale entre deux objets
	private int FSWidth = 200;
	private int FSHeigth = 200;
	private int PBHeight =30;
	private int RBLength = 160; 
	private int RBHeigth = 40;
	
	private Font fontButton = new Font("Consolas", Font.BOLD, 20);
	private Font font = new Font("Consolas", Font.PLAIN, 15);
	private Image mainImage;
	
	//utile pour ecrire les constraints plus vite et plus lisibles
	private String W = SpringLayout.WEST;
	private String E = SpringLayout.EAST;
	private String N = SpringLayout.NORTH;
	private String S = SpringLayout.SOUTH;
	
	//tout les Jcomponents et leurs potes
	private SpringLayout SL = new SpringLayout();
	
	private JButton Jrender = new JButton();
	private JButton Jsave = new JButton();
	private JLabel JsaveName = new JLabel();
 	private JLabel mainImageLabel = new JLabel();
	private JProgressBar progressBar = new JProgressBar();
	private JLabel filterListLabel =new JLabel();
	private JList<String> JfilterList = new JList<String>(); 
	private JLabel imageListLabel = new JLabel();
	private JList<ImageIcon> JimageList = new JList<ImageIcon>();
	private JScrollPane scrollFilter = new JScrollPane();
	private JScrollPane scrollImage = new JScrollPane();
	
	
	public PanelDeLaFamille(){
		placeLayoutandComponents();
		setComponentsActionListeners();
		
	}
	
	private void setComponentsActionListeners() {
		
		//Lorsque la fnètre change de taille on resize l'image principale
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	System.out.println("RESIZEEED");
		    	System.out.print("taille"+mainImageLabel.getHeight());
		    	drawScaledMainImage();
		        // do stuff
		    }
		});
		// TODO créé toute les actonListeners d l'user 
		
	}

	private void placeLayoutandComponents(){
		
		this.setBackground(Color.DARK_GRAY);
		this.setLayout(SL);
		
		
		//création du Jpanel contenant l'image
		mainImageLabel.setBackground(Color.BLACK);
		mainImageLabel.setOpaque(true);
		mainImageLabel.setFont(font);
		mainImageLabel.setForeground(Color.WHITE);
		mainImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		mainImageLabel.setText("MAIN IMAGE LABEL");
		add(mainImageLabel);
		
		//Création de la zone de selection des filtres
		filterListLabel.setText("SelectFilter");
		filterListLabel.setFont(font);
		filterListLabel.setForeground(Color.WHITE);
		add(filterListLabel);
		DefaultListModel<String> modelFilterList = new DefaultListModel<String>();
		setFilterNameList(modelFilterList);		
		JfilterList.setModel(modelFilterList);
		JfilterList.setFont(font);
		JfilterList.setBackground(Color.BLACK);
		JfilterList.setForeground(Color.WHITE);
		scrollFilter.setViewportView(JfilterList);
		add(scrollFilter);
		
		
		//créaion de la zone de selection des images
		DefaultListModel<ImageIcon> modelImageList = new DefaultListModel<ImageIcon>();
		setPreviousImageList(modelImageList);
		JimageList.setModel(modelImageList);
		JimageList.setBackground(Color.BLACK);
		scrollImage.setViewportView(JimageList);
		add(scrollImage);
		
		
		//création de la bar de progrès
		progressBar.setBackground(Color.BLACK);
		add(progressBar);
		
		//création des Buttons du haut
		Jrender.setBackground(Color.BLACK);
		Jrender.setFont(fontButton);
		Jrender.setText("RENDER");
		Jrender.setForeground(Color.WHITE);
		add(Jrender);
		
		Jsave.setBackground(Color.BLACK);
		Jsave.setFont(fontButton);
		Jsave.setText("SAVE");
		Jsave.setForeground(Color.WHITE);
		add(Jsave);
		
		JsaveName.setBackground(Color.BLACK);
		JsaveName.setOpaque(true);
		JsaveName.setText("saveName is usualy very long");
		JsaveName.setForeground(Color.WHITE);
		add(JsaveName);
		
		// une fois tout le monde créé on met les Constraints 
		putConstraintEverywhere();
		
		setMainImageToJoconde();
		drawScaledMainImage();
		
	}
	
	
	private void setMainImageToJoconde() {
		// TODO devrai ne pas exister
		File file = new File("C:\\Users\\AUDEPIN\\Dropbox\\-Input\\aaa la joconde.jpg");
			try {
				mainImage = ImageIO.read(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	/**
	 * draw the mainImage scaled to the main label 
	 */
	private void drawScaledMainImage() {
		int heigth;
		int width;
		double IW= ((BufferedImage)mainImage).getWidth();
		double IH = ((BufferedImage)mainImage).getHeight();
		double ratioI = IW/IH;
		double LW= mainImageLabel.getWidth()+1;
		double LH = mainImageLabel.getHeight()+1;
		double ratioL = LW/LH;
		if(ratioI<ratioL){
			 heigth=(int) LH;
			 width=(int)(LH*ratioI);
		}
		else{
			width=(int)LW;
			heigth=(int) (LW/ratioI);
			
		}
		 
		ImageIcon im = new ImageIcon(mainImage.getScaledInstance(width, heigth, Image.SCALE_FAST)
		);
		mainImageLabel.setIcon(im);
		mainImageLabel.setText("");
	}

	/**
	 * remplie la liste avec les images que lui donne 
	 * @param modelImageList
	 */
	private void setPreviousImageList(DefaultListModel<ImageIcon> modelImageList) {
		// TODO mettre les bonnes images sans oublier l'image ajout de nouvelle image
		try {
			//TODO mettre els bonnes images 

			File file = new File("C:\\Users\\AUDEPIN\\Dropbox\\-Input\\aaa la joconde.jpg");
			Image image = ImageIO.read(file);
			ImageIcon im = new ImageIcon(image);
			modelImageList.addElement(im);
			modelImageList.addElement(im);
			modelImageList.addElement(im);
			modelImageList.addElement(im);
			modelImageList.addElement(im);
			modelImageList.addElement(im);
			modelImageList.addElement(im);
			modelImageList.addElement(im);
			modelImageList.addElement(im);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * remplie la liste en param avec les noms de filtres que lui donne le GUI Manager
	 * @param modelFilterList
	 */
	private void setFilterNameList(DefaultListModel<String> modelFilterList) {
		modelFilterList.addElement( "1nom de filtre");
		modelFilterList.addElement( "2nom de filtre");
		modelFilterList.addElement( "3nom de filtre");
		modelFilterList.addElement( "4nom de filtre");
		modelFilterList.addElement( "5nom de filtre");
		modelFilterList.addElement( "6nom de filtre");
		modelFilterList.addElement( "autre nom de filtre");
		modelFilterList.addElement( "encore un autre nom de filtre");
		// TODO Auto-generated method stub
		
	}

	/**
	 * ajoute les constraints a tout le scomposants du panel
	 */
	private void putConstraintEverywhere(){
		//position verticales des selecteur d'image et de filtre
		SL.putConstraint(N, filterListLabel, dist, N, this);
		SL.putConstraint(E, filterListLabel, -dist, E, this);
		SL.putConstraint(S, filterListLabel, 30, N, filterListLabel);
		SL.putConstraint(W, filterListLabel, -FSWidth, E, filterListLabel);
		
		SL.putConstraint(N, scrollFilter, 0, S, filterListLabel);
		SL.putConstraint(S, scrollFilter, FSHeigth, N, scrollFilter);
		SL.putConstraint(N, scrollImage, dist, S, scrollFilter);
		SL.putConstraint(S, scrollImage, -dist, S, this);
		//position horizontal des selecteurs
		SL.putConstraint(E, scrollFilter, -dist, E, this);
		SL.putConstraint(E, scrollImage, -dist, E, this);
		SL.putConstraint(W, scrollFilter, -FSWidth, E, scrollFilter);
		SL.putConstraint(W, scrollImage, 0, W, scrollFilter);
		//Position de progressBar
		SL.putConstraint(W, progressBar, dist, W, this);
		SL.putConstraint(E, progressBar, -dist, W, scrollFilter);
		SL.putConstraint(S, progressBar, -dist, S, this);
		SL.putConstraint(N, progressBar, -PBHeight, S, progressBar);
		//position mainImageLabel
		SL.putConstraint(W, mainImageLabel, dist, W, this);
		SL.putConstraint(E, mainImageLabel, -dist, W, scrollFilter);
		SL.putConstraint(S, mainImageLabel, -dist, N, progressBar);
		SL.putConstraint(N, mainImageLabel, 200, N, this);
		//position de render
		SL.putConstraint(W, Jrender, dist, W, this);
		SL.putConstraint(E, Jrender, RBLength, W, Jrender);
		SL.putConstraint(N, Jrender, dist, N, this);
		SL.putConstraint(S, Jrender, RBHeigth, N, Jrender);
		//position de Save
		SL.putConstraint(W, Jsave, dist, E, Jrender);
		SL.putConstraint(E, Jsave, RBLength, W, Jsave);
		SL.putConstraint(N, Jsave, dist, N, this);
		SL.putConstraint(S, Jsave, RBHeigth, N, Jsave);
		//position de saveName
		SL.putConstraint(W, JsaveName, dist, E, Jsave);
		SL.putConstraint(E, JsaveName, -dist, W, scrollFilter);
		SL.putConstraint(N, JsaveName, dist, N, this);
		SL.putConstraint(S, JsaveName, RBHeigth, N, JsaveName);
		
	}

	
	
	
	
	
	
	
	
	
	
	
}
