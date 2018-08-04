package Filters;

import java.awt.image.BufferedImage;

import Filters.Filter;

public class FPixelSorting extends Filter{
	
	int violence;
	int mode;
	
	public FPixelSorting(BufferedImage buff){
		
		super(buff);
		name="Pixel Sorting";
		createSlider("Violence",0,100,0);
		String[] mode = {"violente","calme"};
		createButtonGroup("Modes",mode);
		
	}
	
	public FPixelSorting(){
		
		super();
		name="Pixel Sorting";
	}
	
	@Override 
	public BufferedImage applyFilter(BufferedImage input){
		BufferedImage output = deepCopy(input);
		
		for 
		
		return output;
	}

}
