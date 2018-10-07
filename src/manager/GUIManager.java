package manager;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import FramePack.ClassList;
import FramePack.RenderingLayer;

/**
 * Le GUI Manager est la classe qui va s'occuper de faire la liaison entre les classes du GUI et les classes de calcules
 * toutes les classes du GUI feront appels à lui pour les interaction avec les filtres ou les les images.
 * @author Nicolas2
 *
 */
public class GUIManager { 
	
	private static ClassList classList;
	
	File inputFile;
	Image inputImage;
	BufferedImage buffInput;
	
	int smallIconWidth;
	ArrayList<RenderingLayer> layerList = new ArrayList<RenderingLayer>();
	ArrayList<Image> imageList = new ArrayList<Image>();
	ArrayList<ImageIcon> smallIconList = new ArrayList<ImageIcon>();
	DefaultListModel<ImageIcon> modelImageList = new DefaultListModel<ImageIcon>();
	
	
	public GUIManager() {
		System.out.println("GUIManager Created");
		classList = new ClassList();

	}

	
	
	/**
	 * A new input has been selected by the gui. 
	 * its image will be set as the input image
	 * If there were already filterlayers it will provoc a cascade of applying filter 
	 * @param selectedFile
	 */
	public void NewInputFileChoosen(File selectedFile) {
		this.inputFile = selectedFile;
		try {
			inputImage = ImageIO.read(inputFile);
			buffInput = ImageIO.read(inputFile);
			System.out.println("GUI got the image");
			//this.setSmallIconWidth(40);
			if(layerList.size()==0){ //si le logicile viens d'être lancé et la première image selectionnée
				System.out.println("create first Layer with buff input from file");
				layerList.add(new RenderingLayer(buffInput));
			}
			replaceImageAtPosition(0,buffInput);
			
			
		} catch (IOException e) {
			System.out.println("could not read image");
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		
	}

	public boolean hasInputImage() {
		if (inputImage==null){
			return false;
		}
		return true;
	}

	public Image getInputImage() {
		return inputImage;
	}

	/**
	 * renvoi une approximation du nombre en MB aves 3 chiffres après la virgule pour les afficher plus simplement
	 * @param n le nombre à simplifier
	 * @return n en MB
	 */
	private float aproximationMB(float n){
		n=n/(float)(1024*1024)*1000;
		int m=(int)n;
		n=(float)m/(float)1000;
		return n;
	}
	
	/**
	 * renvoi un pext comportant des informations sur l'image d'input
	 * @return
	 */
	public String getInputInfoText() {
		float ratio=(float)(buffInput.getHeight())/(float)(buffInput.getWidth());
		String info = "";
		info += "File Name: " + inputFile.getName()+"\n";
		info += "File Size: "+ aproximationMB(inputFile.length())+"MB \n"; 
		info += "Picture Heigth: " + buffInput.getHeight()+"px\n";
		info += "Picture Width: "+ buffInput.getWidth()+"px\n";
		info += "Pixel Ratio: "+ ratio +"\n";
		info += "Pixel Count: "+ buffInput.getHeight()*buffInput.getWidth()+"px\n";
		
		return info;
	}	
	
	
	private void replaceImageAtPosition(int position,BufferedImage im){
		
		float ratio=(float)(im.getHeight())/(float)(im.getWidth());
		int width =smallIconWidth;
		
		int height =(int) ((float) width*ratio);
		ImageIcon imIcon = new ImageIcon(im.getScaledInstance(width, height, Image.SCALE_FAST));
		if (position>=imageList.size()){//ajoute une nouvelle image
			imageList.add(position, im);
			smallIconList.add(position, imIcon);
			modelImageList.add(position, imIcon);

		
		}
		else{//change une image qui existe déjà
			imageList.set(position, im);
			smallIconList.set(position, imIcon);
			modelImageList.set(position, imIcon);
		}
		if(position<layerList.size()){
			layerList.get(position).setInput(im);
		}
		else{
			System.out.println("mmmm wat y a pas de layer");
		}
		
		 
	}

	public ArrayList<String> getFilterNameList(){
		ArrayList<String> nameList = classList.getFilterNameList();
		return nameList;
		
	}


	public void setSmallIconWidth(int smallIconWidth) {
		this.smallIconWidth=smallIconWidth;
		
	}



	public ArrayList<ImageIcon> getsmallIconList() {
		return smallIconList;
	}



	public File getInputFile() {
		return inputFile;
	}



	public void setInputFile(File inputFile) {
		this.inputFile = inputFile;
	}



	public BufferedImage getBuffInput() {
		return buffInput;
	}



	public void setBuffInput(BufferedImage buffInput) {
		this.buffInput = buffInput;
	}



	public ArrayList<Image> getImageList() {
		return imageList;
	}



	public void setImageList(ArrayList<Image> imageList) {
		this.imageList = imageList;
	}



	public ArrayList<ImageIcon> getSmallIconList() {
		return smallIconList;
	}



	public void setSmallIconList(ArrayList<ImageIcon> smallIconList) {
		this.smallIconList = smallIconList;
	}



	public DefaultListModel<ImageIcon> getModelImageList() {
		return modelImageList;
	}



	public void setModelImageList(DefaultListModel<ImageIcon> modelImageList) {
		this.modelImageList = modelImageList;
	}



	public int getSmallIconWidth() {
		return smallIconWidth;
	}



	public void setInputImage(Image inputImage) {
		this.inputImage = inputImage;
	}



	public Image getLayerInput(int layerPosition) {
		Image im = layerList.get(layerPosition).getInput();
		
		return im;
	}
	
	public Image getLayerOutput(int layerPosition) {
		Image im = layerList.get(layerPosition).getOutput();
		
		return im;
	}
	
	
}
