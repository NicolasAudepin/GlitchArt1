package FramePack;

import java.awt.image.BufferedImage;

import Filters.FNoFilter;
import Filters.Filter;

public class RenderingLayer {
	BufferedImage buffInput;
	BufferedImage buffOutput;
	Filter filter;
	
	public RenderingLayer(){
		System.out.println("Layer sans input ni filtre");
		filter = new FNoFilter();
	}
	
	public void setFilter(Filter filter){
		this.filter = filter;
	}
	
	public void setInput(BufferedImage buff){
		this.buffInput = buff;
	}
	
	public void renderOutput(){
		System.out.println("render initiated ");
		//TODO the stuff as you know it
	}
	
	public void setfilterParamValue(int value){
		System.out.println("filter parameter value changed"+value);
		//TODO tmtc
	}
	
	

}
