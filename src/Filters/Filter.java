package Filters ;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import javax.swing.JFrame;

import FramePack.StuffWithParameter;
import Parameter.ButtonGroupParameter;
import Parameter.CheckBoxParameter;
import Parameter.FilterParameter;
import Parameter.SliderParameter;


/**
 * classe parente de tout les filtres 
 * contient les bufferedImage input et output
 * 
 * @author AUDEPIN
 *
 */
public class Filter extends StuffWithParameter{
	//
	
	BufferedImage input;
	BufferedImage output;
	
	//ArrayList<FilterParameter> paramList = new ArrayList<FilterParameter>();
	
	
	
	
	/** 
	 * renvoie une BufferedImage ayant subi les transformations du filtre selectionné
	 * Cette fonction est override differement pour chaque filtre héritant de cette classe
	 * @author AUDEPIN
	 * @param input l'image qui va être modifiée par le filtre
	 * @return BufferedImage l'image qui a subit le filtre 
	 */
	public BufferedImage applyFilter(BufferedImage input){
		
		getParamValue();
		System.out.println("*** APPLY FILTER ***");
		this.input = input;
		this.output = deepCopy(input);
		System.out.println("applyingfilter");
		int height = input.getHeight();
		int length = input.getWidth();
		/*System.out.println(input.toString());

		System.out.println("input ^ output \\/ ");
		System.out.println(output.toString());
		*/
		
		//the actual effect of the filter
		for(int i = 0; i < height; i++){
			System.out.println("boucle for");
			System.out.print(height +"  ");
			System.out.println(i);
			for(int j = 0; j < length; j++){
				//try{
					int RGB = input.getRGB(j,i);
					Color c = new Color(RGB);
					int red = c.getRed();
					int green = c.getGreen();
					int blue = c.getBlue();
					Color d = new Color(blue,red, green);
					int RGB2 = d.getRGB();
					output.setRGB(j, i, RGB2);
				//}catch(Exception e){}
				
				
			}
		}
		return output;
		
	}
	
	public Filter(BufferedImage input){
		
		this.output = deepCopy(input);
		this.input = input;
		name="Default Effect";
		
	}
	
	public Filter(){
		name="Default Effect";
		
	}
	
	
	
	//Getter & Setter
	
	public BufferedImage getInput() {
		return input;
	}
	public void setInput(BufferedImage input) {
		this.input = input;
	}
	public BufferedImage getOutput() {
		return output;
	}
	public void setOutput(BufferedImage output) {
		this.output = output;
	}
	

}
