package GUI;

import java.awt.Color;
import java.awt.Component;
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
import java.util.Random;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Parameter.CheckBoxParameter;
import Parameter.MaskParameter;
import Parameter.ParameterParent;
import Parameter.SliderParameter;
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
	private JScrollPane scrollParam = new JScrollPane();
	JPanel filterPan = new JPanel();
	
	
	int currentLayerIndex = 0;
	ArrayList<ParameterParent> paramList;


	private ArrayList<JLabel> paramJLabelList = new ArrayList<JLabel>();


	private int maxScrollParamHeigth = 200; 
	
	public FilterPanel(GUIManager GM, NewMainFrame frame){
		super(GM, frame);
		placeLayoutandComponents();
		setComponentsActionListeners();
		
	}
	
	private void setComponentsActionListeners() {
		
		//Lorsque la fen�tre change de taille on resize l'image principale
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	drawScaledMainImage(mainImage,mainImageLabel);
		    }
		});
		
		
		//Selection d'un autre filtre
		JfilterList.addListSelectionListener(new ListSelectionListener(){

			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				int filterIndex = JfilterList.getSelectedIndex();
				String filterName = JfilterList.getSelectedValue();
				System.out.println("\n*** New Filter selected: "+filterName+ "***");
				GM.setLayerFilter(currentLayerIndex , filterIndex );
				SetParamJComponents(currentLayerIndex);
			}
			
		});
		
		// TODO cr�� toute les actonListeners d l'user 
		
	}

	/**
	 * initialise tout les JComponents de ce panel tel que les boutotn render
	 * ou le label de l'image principale
	 */
	private void placeLayoutandComponents(){
		
		
		
		
		//cr�ation du Jpanel contenant l'image
		mainImageLabel.setBackground(behindPicColor);
		mainImageLabel.setOpaque(true);
		mainImageLabel.setFont(font);
		mainImageLabel.setForeground(Color.WHITE);
		mainImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		mainImageLabel.setText("MAIN IMAGE LABEL");
		add(mainImageLabel);
		
		//Cr�ation de la zone de selection des filtres
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
		
		
		//Cr�ation de la zone des params
		
		filterPan.setBackground(backGroundColor);;
		scrollParam.setViewportView(filterPan);
		add(scrollParam);
		
		
		//cr�aion de la zone de selection des images
		imageListLabel.setText("Previous States");
		imageListLabel.setForeground(Color.WHITE);
		imageListLabel.setFont(font);
		add(imageListLabel);
		
		DefaultListModel<ImageIcon> modelImageList = new DefaultListModel<ImageIcon>();
		JimageList.setModel(modelImageList);
		JimageList.setBackground(behindPicColor);
		
		scrollImage.setViewportView(JimageList);
		add(scrollImage);
		
		
		//cr�ation de la bar de progr�s
		progressBar.setBackground(buttonColor);
		add(progressBar);
		
		//cr�ation des Buttons du haut
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
		
		// une fois tout le monde cr�� on met les Constraints 
		putConstraintEverywhere();
		
		setMainImageToJoconde();
		drawScaledMainImage(mainImage,mainImageLabel);
		
	}
	
	public void setMainImage(Image im){
		mainImage = im;
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
	public void setPreviousImageList() {
		DefaultListModel<ImageIcon> newModelList = new DefaultListModel<ImageIcon>();
		System.out.println("set icons");
		ArrayList<ImageIcon> list = GM.getsmallIconList();
		//ImageIcon plus = new ImageIcon(this.getClass().getResource("Images/Plus.jpg" ));
		//list.add(plus);
		for(int i =0;i< list.size();i++){
			System.out.println("for"+i);
			ImageIcon im = list.get(i);			
			newModelList.addElement(im);
			
		}
		
		
		JimageList.setModel(newModelList);
		JimageList.repaint();
		
	}

	/**
	 * remplie la liste en param avec les noms de filtres que lui donne le GUI Manager
	 * ne repaint pas car il est utilis� seuleemnt une fois normalement
	 * @param modelFilterList
	 */
	private void setFilterNameList(DefaultListModel<String> modelFilterList) {
		ArrayList<String> list =GM.getFilterNameList();
		for(int i = 0; i < list.size();i++){
			modelFilterList.addElement(list.get(i));
		}
		
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
		//position de la zone de param
		SL.putConstraint(N, scrollParam, dist, S, Jrender);
		SL.putConstraint(S, scrollParam, 3*dist, N, scrollParam);
		SL.putConstraint(W, scrollParam, dist, W, this);
		SL.putConstraint(E, scrollParam, -dist, W, scrollFilter);
		
		//position mainImageLabel
		SL.putConstraint(W, mainImageLabel, dist, W, this);
		SL.putConstraint(E, mainImageLabel, -dist, W, scrollFilter);
		SL.putConstraint(S, mainImageLabel, -dist, N, progressBar);
		SL.putConstraint(N, mainImageLabel, dist, S, scrollParam);
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

	
	/**
	 * une image a �t� selectionn�e par le PicturPanel
	 */
	public void newInputChoosen() {
		System.out.println("* New Input Choosen for the filterPanel *");
		setPreviousImageList();
		goToLayer(0);
		
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * affiche les param et l'input du layer demand�
	 * @param layerPosition
	 */
	private void goToLayer(int layerPosition){
		System.out.println("*** GO TO LAYER "+ layerPosition +" ***");
		currentLayerIndex = layerPosition;
		setMainImage(GM.getLayerOutput(layerPosition));
		SetSelectedFilter(layerPosition);
		
		
		
		
	}

	/**
	 * selectionne le filtre du layer demand�
	 * @param layerPosition
	 */
	private void SetSelectedFilter(int layerPosition) {
		int index = GM.getLayerFilterNumber(layerPosition);
		JfilterList.setSelectedIndex(index); // show the filter selected in the list

		SetParamJComponents(layerPosition);//met tout les params en place
		
	}

	/**
	 * met en place les JComponents du Layer demand�
	 * @param layerPosition
	 */
	private void SetParamJComponents(int layerPosition) {
		System.out.println("\nsetting the Jparam of this layer");
		paramList = GM.getparamList(currentLayerIndex);
		int nbParam = paramList.size();
		System.out.println("there are "+nbParam+" param");
		int btnHeigth = 50;
		int lblLength = 100;
		for (int i = 0; i<nbParam;i++){//Pour chaque param on cr�� ici ses JComponents

			// boby est le JLabel qui affiche le nom du param�tre 
			// le label boby ne d�pend pas du type de parametre
			ParameterParent parameter = paramList.get(i);
			JLabel boby = new JLabel();
			boby.setText(parameter.getName());
			boby.setFont(new Font("Consolas", Font.PLAIN, 18));
			boby.setForeground(textColor);
			
			
			boby.setBounds(15, 100+parameter.getGraphicalPlacement()*(btnHeigth + dist), lblLength, btnHeigth);
			boby.setHorizontalAlignment(SwingConstants.RIGHT);
			paramJLabelList .add(boby);
			filterPan.add(boby);
			
			
		}
		filterPan.revalidate();
		int scrollSize = Math.min(maxScrollParamHeigth ,neededSize(paramList));
		SL.putConstraint(S, scrollParam,scrollSize, N, scrollParam);
		revalidate();
		
	}

	private int neededSize(ArrayList<ParameterParent> list) {
		int size = 0;
		//TODO faire tout �a mieux
		
		int nbParam = list.size();
		for (int i = 0; i<nbParam;i++){
			if (list.get(i).getClass()==MaskParameter.class){
				size += 30;
			}
			else if(list.get(i).getClass()==SliderParameter.class){
				size+=30;
			}
			else if(list.get(i).getClass()==CheckBoxParameter.class){
				size+=3000;
			}
			
		}
		return size;
	}

	
	
	
	
	
	
	
	
	
	
	
}
