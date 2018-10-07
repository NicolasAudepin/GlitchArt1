package FramePack;

import java.awt.Image;
import java.awt.image.BufferedImage;

import Filters.FNoFilter;
import Filters.Filter;

public class RenderingLayer {
	BufferedImage buffInput;
	BufferedImage buffOutput;
	Filter filter;
	
	public RenderingLayer(){
		System.out.println("Création Layer sans input ni filtre");
		filter = new FNoFilter();
	}
	
	public RenderingLayer(BufferedImage buffInput){
		System.out.println("création de layer avec input");
		filter = new FNoFilter();
		this.buffInput =buffInput;
		this.buffOutput = buffInput;
	}
	
	public void setFilter(Filter filter){
		this.filter = filter;
	}
	
	public void setInput(BufferedImage buff){
		System.out.println("layer input set -> rendering");
		this.buffInput = buff;
		renderOutput();
	}
	
	public void renderOutput(){
		System.out.println("lazy render initiated ");
		buffOutput = buffInput;
		//TODO the stuff as you know it
	}
	
	public void setfilterParamValue(int value){
		System.out.println("filter parameter value changed"+value);
		//TODO tmtc
	}

	public Image getInput() {
		return buffInput;
	}

	public Image getOutput() {
		return buffOutput;
	}

	public Filter getFilter() {
		// TODO Auto-generated method stub
		return filter;
	}
	
	

}
