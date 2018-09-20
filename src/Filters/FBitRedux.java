package Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FBitRedux extends FPixel{
	
	int nbBit = 2;
	int filerValue = 0 ;
	
	public FBitRedux(BufferedImage buff){
		super(buff);
		name="Bit Redux";
		createSlider("Simplification",0,8,2,"le nombre de bits finaux remplacés. 8 étant le maximun");
		String[] mode = {"0","1","random"};
		createButtonGroup("Filling bit",mode,"0 remplace les bits par des 0, 1 les remplaces par des 1 et random les remplace aléatoirement.");
	}

	
	public FBitRedux(){
		super();
		name = "Bit Redux";
	}
	
	@Override
	protected void getParamValue(){
		RefreshParamValue();
		nbBit = paramValue.get(1);
		filerValue = paramValue.get(2)+2*paramValue.get(3)+3*paramValue.get(4);
		
		args.add(nbBit);
		args.add(filerValue);
	}
	int pixelFilter(int ARGB, ArrayList<Integer> args, int i ,int j ){
		
		
		Color c =new Color(ARGB);
		int ARGB2 = 0;
		
		
		if (filerValue == 2){
			
			
			int filer = 0b11111111>>(8-nbBit);
			//1 filer mode
			int R =c.getRed();
			R = R | filer;
			//System.out.println(R);
			//System.out.println(filer);
			
			int G = c.getGreen();
			G = G | filer;
			int B = c.getBlue();
			B = B | filer;
			Color d = new Color(R,G,B);
			ARGB2 = d.getRGB();
			
			
			
		}
		if(filerValue <= 1 ){
			// 0 filer mode
			int filer = 0b11111111<<nbBit;
			int R =c.getRed();
			R = R & filer;
			int G = c.getGreen();
			G = G & filer;
			int B = c.getBlue();
			B = B & filer;
			Color d = new Color(R,G,B);
			ARGB2 = d.getRGB();
		}
		if(filerValue==3){
			// random filer mode
			int filer = 0b11111111<<nbBit;
			int R =c.getRed();
			R = R & filer;
			int G = c.getGreen();
			G = G & filer;
			int B = c.getBlue();
			B = B & filer;
			
			filer =  (int)(Math.random() * (Math.pow(2, nbBit) ));
			
			R = R | filer;
			G = G | filer;
			B = B | filer;
			
			Color d = new Color(R,G,B);		
			ARGB2 = d.getRGB();
		}
		
		
		
		
		return ARGB2;
		
		
	}
	
	
	
}
