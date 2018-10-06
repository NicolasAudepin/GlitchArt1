package manager;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

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
	
	public GUIManager() {
		System.out.println("I exist");

	}

	public void NewInputFileChoosen(File selectedFile) {
		this.inputFile = selectedFile;
		try {
			inputImage = ImageIO.read(inputFile);
			buffInput = ImageIO.read(inputFile);
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
		String info = "";
		info += "File Name: " + inputFile.getName()+"\n";
		info += "File Size: "+ aproximationMB(inputFile.length())+"MB \n"; 
		info += "Picture Heigth: " + buffInput.getHeight()+"\n";
		info += "Picture Width: "+ buffInput.getWidth()+"\n";
		info += "Pixel Count: "+ buffInput.getHeight()*buffInput.getWidth()+"\n";
		
		return info;
	}	
	
	
	

}
