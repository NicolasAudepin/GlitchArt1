package Mask;

import java.awt.image.BufferedImage;

public class MCSquare extends MaskCreator{
	
	public MCSquare(){
		super();
		name="Square";
	}
	
	public MCSquare(BufferedImage bufInput){
		super(bufInput);
		name="Square";
		createSlider("Top",0,H,(int)(H/4));
		createSlider("Bottom",0,H,(int)(H/2));
		createSlider("Left",0,L,(int)(L/4));
		createSlider("Right",0,L,(int)(L/2));
		
	}
	public boolean[][] CreateMask(){
		System.out.println("*** CREATE SQUARE MASK ***");
		RefreshParamValue();
		int top = paramValue.get(0);
		int bottom = paramValue.get(1);
		int left = paramValue.get(2);
		int right = paramValue.get(3);
		
		outputMask = new boolean[L][H];
		
		for(int i = 0;i<L;i++){
			if(i%100==0){
				System.out.println("boucle"+ i);
			}
			for(int j = 0;j<H;j++){
				
				if (i<right & i> left &j > top & j< bottom){
					outputMask[i][j]=true;
				}
				else{
					outputMask[i][j]=false;
				}
			}
		}
		
		
		System.out.println("Mask Grid created");
		return outputMask;
		
	}

}
