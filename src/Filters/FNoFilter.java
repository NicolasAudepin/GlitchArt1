package Filters;

import java.awt.image.BufferedImage;

import backgroundThreads.ApplyFilterThread;

public class FNoFilter extends Filter{

	public FNoFilter(){
		super();
		name="No Filter";
	}

	public FNoFilter(BufferedImage buff){
		super(buff);
		name="No Filter";
		
		
	}
	
	public BufferedImage applyFilter(BufferedImage input ,ApplyFilterThread dady ){
		this.input=input;
		this.output= deepCopy(input);
		return output;
		
	}
}

