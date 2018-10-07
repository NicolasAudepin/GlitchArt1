package manager;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

/**
 * Le GUI Manager est la classe qui va s'occuper de faire la liaison entre les classes du GUI et les classes de calcules
 * toutes les classes du GUI feront appels à lui pour les interaction avec les filtres ou les les images.
 * @author Nicolas2
 *
 */
public class GUIManager { 
	
	File inputFile;
	Image inputImage;
	BufferedImage buffInput;
	
	int smallIconWidth;
	ArrayList<Image> imageList = new ArrayList<Image>();
	ArrayList<ImageIcon> smallIconList = new ArrayList<ImageIcon>();
	DefaultListModel<ImageIcon> modelImageList = new DefaultListModel<ImageIcon>();

	
	public GUIManager() {
		System.out.println("GUIManager Created");

	}

	
	
	/**
	 * A new input has been selected by the gui. 
	 * its image will be set as the input image
	 * @param selectedFile
	 */
	public void NewInputFileChoosen(File selectedFile) {
		this.inputFile = selectedFile;
		try {
			inputImage = ImageIO.read(inputFile);
			buffInput = ImageIO.read(inputFile);
			this.setSmallIconWidth(40);
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
		if (position>=imageList.size()){
			imageList.add(position, im);
			smallIconList.add(position, imIcon);
			modelImageList.add(position, imIcon);

		
		}
		else{
			imageList.set(position, im);
			smallIconList.set(position, imIcon);
			modelImageList.set(position, imIcon);
		}
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
	
	
}
