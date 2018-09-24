package Mask;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class MCColor extends MaskCreator{
	
	public MCColor(){
		super();
		name = "Color";
	}
	
	public MCColor(BufferedImage bufInput){
		super(bufInput);
		name="Color";
		createSlider("R",0,255,255,"Le taux de rouge de la couleur séléctionnée");
		createSlider("G",0,255,255,"Le taux de vert de la couleur séléctionnée");
		createSlider("B",0,255,255,"Le taux de bleu de la couleur séléctionnée");
		createSlider("Precision",1,1000,20,"la précision de la selection de couleur. Plus la valeure est grande plus la selection est large.");
	}
	
	public boolean[][] CreateMask(){
		
	System.out.println("*** CREATE COLOR  MASK ***");
	RefreshParamValue();
	
	int R = paramValue.get(0);
	int G = paramValue.get(1);
	int B = paramValue.get(2);
	int Precision = paramValue.get(3)*paramValue.get(3);
	
	outputMask = new boolean[L][H];
	
	for(int i = 0;i<L;i++){
		/*
		if(i%100==0){
			System.out.println("boucle"+ i);
		}
		*/
		setComplition((double)i/L);
		for(int j = 0;j<H;j++){
			int RGB = bufInput.getRGB(i, j);
			Color c =new Color(RGB);
			int RC = c.getRed();
			int GC = c.getGreen();
			int BC = c.getBlue();
			
			int dist = (int) (Math.pow(R-RC,2)+Math.pow(G-GC,2)+Math.pow(B-BC,2));
			if (dist<Precision){
				outputMask[i][j]=true;
			}
			else{
				outputMask[i][j]=false;
			}			
		}
	}
	return outputMask;
	}
}
