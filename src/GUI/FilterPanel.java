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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import manager.GUIManager;

public class FilterPanel extends PanelParent{
	
	private int multiplier = 1;
	

	private Image mainImage;
	
	//tout les Jcomponents et leurs potes
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
	
	
	public FilterPanel(GUIManager GM, NewMainFrame frame){
		super(GM, frame);
		placeLayoutandComponents();
		setComponentsActionListeners();
		
	}
	
	private void setComponentsActionListeners() {
		
		//Lorsque la fenètre change de taille on resize l'image principale
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	drawScaledMainImage(mainImage,mainImageLabel);
		    }
		});
		// TODO créé toute les actonListeners d l'user 
		
	}

	private void placeLayoutandComponents(){
		
		
		
		
		//création du Jpanel contenant l'image
		mainImageLabel.setBackground(behindPicColor);
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
		JfilterList.setBackground(buttonColor);
		JfilterList.setForeground(Color.WHITE);
		scrollFilter.setViewportView(JfilterList);
		add(scrollFilter);
		
		
		//créaion de la zone de selection des images
		imageListLabel.setText("Previous States");
		imageListLabel.setForeground(Color.WHITE);
		imageListLabel.setFont(font);
		add(imageListLabel);
		
		DefaultListModel<ImageIcon> modelImageList = new DefaultListModel<ImageIcon>();
		setPreviousImageList(modelImageList);
		JimageList.setModel(modelImageList);
		JimageList.setBackground(behindPicColor);
		
		scrollImage.setViewportView(JimageList);
		add(scrollImage);
		
		
		//création de la bar de progrès
		progressBar.setBackground(buttonColor);
		add(progressBar);
		
		//création des Buttons du haut
		Jrender.setBackground(buttonColor);
		Jrender.setFont(fontButton);
		Jrender.setText("RENDER");
		Jrender.setForeground(Color.WHITE);
		add(Jrender);
		
		Jsave.setBackground(buttonColor);
		Jsave.setFont(fontButton);
		Jsave.setText("SAVE");
		Jsave.setForeground(Color.WHITE);
		add(Jsave);
		
		JsaveName.setBackground(buttonColor);
		JsaveName.setOpaque(true);
		JsaveName.setText("saveName is usualy very long");
		JsaveName.setForeground(Color.WHITE);
		add(JsaveName);
		
		// une fois tout le monde créé on met les Constraints 
		putConstraintEverywhere();
		
		setMainImageToJoconde();
		drawScaledMainImage(mainImage,mainImageLabel);
		
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
	 * remplie la liste avec les images que lui donne 
	 * @param modelImageList
	 */
	public void setPreviousImageList(DefaultListModel<ImageIcon> modelImageList) {
		
		ArrayList<ImageIcon> list = GM.getsmallIconList();
		for(int i =0;i< list.size();i++){
			ImageIcon im = list.get(i);
			modelImageList.add(i, im);
		}
		
		/*try {
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
		*/
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
		SL.putConstraint(E, scrollFilter, -dist, E, this);
		SL.putConstraint(W, scrollFilter, -FSWidth, E, scrollFilter);
		//position horizontal des selecteurs
		SL.putConstraint(N, imageListLabel, dist, S, scrollFilter);
		SL.putConstraint(E, imageListLabel, -dist, E, this);
		SL.putConstraint(S, imageListLabel, 30, N, imageListLabel);
		SL.putConstraint(W, imageListLabel, -FSWidth, E, imageListLabel);
		
		SL.putConstraint(N, scrollImage, 0, S, imageListLabel);
		SL.putConstraint(S, scrollImage, -dist, S, this);
		SL.putConstraint(E, scrollImage, -dist, E, this);
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
