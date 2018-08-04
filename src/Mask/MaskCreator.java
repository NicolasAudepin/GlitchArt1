package Mask;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import FramePack.StuffWithParameter;
import Parameter.FilterParameter;

public class MaskCreator extends StuffWithParameter{
	
	String name;
	boolean[][] outputMask; 
	BufferedImage bufInput;
	ArrayList<FilterParameter> paramList;
	int L;
	int H;
	
	
	public MaskCreator(){
		name="Whole Picture";
	}
	

	public MaskCreator(BufferedImage bufInput){
		name="Whole Picture";
		this.bufInput = bufInput;
		L = bufInput.getWidth();
		H = bufInput.getHeight();
	}
	
	
	public boolean[][] CreateMask(){
		
		outputMask = new boolean[L][H];
		for (int i=0 ; i<L ; i++){
			for (int j=0; j<H ; j++){
				outputMask[i][j]=true;
			}
		}
		return outputMask;
		
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public boolean[][] getOutputMask() {
		return outputMask;
	}


	public void setOutputMask(boolean[][] outputMask) {
		this.outputMask = outputMask;
	}


	public BufferedImage getBufInput() {
		return bufInput;
	}


	public void setBufInput(BufferedImage bufInput) {
		this.bufInput = bufInput;
	}


	public ArrayList<FilterParameter> getParamList() {
		return paramList;
	}


	public void setParamList(ArrayList<FilterParameter> paramList) {
		this.paramList = paramList;
	}

}
