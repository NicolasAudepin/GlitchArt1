package manager;

import java.awt.Image;
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
	
	public GUIManager() {
		System.out.println("I exist");

	}

	public void NewInputFileChoosen(File selectedFile) {
		this.inputFile = selectedFile;
		try {
			inputImage = ImageIO.read(inputFile);
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
	
	
	

}
