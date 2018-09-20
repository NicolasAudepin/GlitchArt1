package Filters;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;


public class FBitSliding extends FPixel {
	
	int maskNb;
	int baseOffset=0;
	int XSliding=0;
	int YSliding=0;
	int mode=1;
	int blur=0;
	String[] modes = {"RGB","ARGB","Original"};
	
	public FBitSliding(BufferedImage buff){
		
		super(buff);
		name="Bit Sliding";
		
		createSlider("Bit Offset", 0 , 64 , 0,"le nombre de bits de décalage vers la droite");
		createSlider("X Sliding", -200 , 200 , 0,"la variation d'offset selon l'axe horizontal");
		createSlider("Y Sliding", -200 , 200 , 0,"la variation d'offset selon l'axe vertical");
		createButtonGroup("mode", modes,"RGB est le mode de base. ARGB est le mode ou la transparence est prise en compte. Original est le mode de déplacement beugué original.");
		createCheckBox("Blur",false,"floute les transition entre les zones d'offset different.");
	}
	
	public FBitSliding(){
		
		super();
		name="Bit Sliding";
	}
	
	@Override
	protected void getParamValue(){
		RefreshParamValue();
		baseOffset = paramValue.get(1);
		XSliding = paramValue.get(2);
		YSliding = paramValue.get(3);
		mode = paramValue.get(4)+paramValue.get(5)*2+paramValue.get(6)*3;
		blur=paramValue.get(7);
		
		args =new ArrayList<Integer>();
		
		args.add(length);
		args.add(height);
		args.add(XSliding);
		args.add(YSliding);
		args.add(mode);
		args.add(blur);
	}
	
	@Override 
	int pixelFilter(int ARGB, ArrayList<Integer> args, int i, int j){
		
		int length =args.get(0);
		int height =args.get(1);
		int XSliding =args.get(2);
		int YSliding =args.get(3);
		int mode = 	args.get(4);
		int blur = args.get(5);
		
		int offset1=0;
		int n=0;
		if (blur==1){
			Random rand = new Random();
			 
			n = (int)(rand.nextInt(length+height)*5/(Math.abs(XSliding+YSliding)+1));
			
		}

		offset1= baseOffset+(int)(XSliding*(i+n)/length+YSliding*(j+n)/height);
		
		if(mode==1){
			int offset=offset1 % 24;
			int RGB= (ARGB & 0x00FFFFFF);
			int RGB2 = RGB<<offset;
			int RGB3 = RGB >> (24-offset);
			int ARGB2 = ((RGB2 | RGB3)| 0xFF000000)&0xFFFFFFFF;
			/*if(j==5 & i % 20 == 0){
				System.out.println("mode =" + mode);
				System.out.println("RGB  "+Integer.toBinaryString(RGB));
				
				System.out.println("RGB2 "+Integer.toBinaryString(RGB2));
				System.out.println("RGB3 "+Integer.toBinaryString(RGB3));
				
				System.out.println("offset " + offset);
			}
			*/
			return ARGB2;
			
		}
		if(mode==2){
			int offset=offset1 % 32;
			int RGB= ARGB<<offset;
			int RGB2 = ARGB>>32-offset;
			int ARGB2 = ((RGB2 | RGB)| 0xFF000000)&0xFFFFFFFF;
			if(i==5 & j % 20 == 0){
				System.out.println("mode =" + mode);
				System.out.println(Integer.toBinaryString(ARGB2));
			}
			return ARGB2;
			
		}
		
		if(mode==3){
			int offset=offset1 % 32;
			int RGB= (ARGB & 0x00FFFFFF)>>(32-offset);
			int RGB2 = (ARGB<<offset)& 0xFFFFFFFF;
			int ARGB2 = (RGB + RGB2)| 0xFF000000;
			if(i==5 & j % 20 == 0){
				System.out.println("mode =" + mode);
				System.out.println(Integer.toBinaryString(ARGB2));
			}
			return ARGB2;
			
		}
		
		if(i%20==0 & j== 0){
			System.out.println("mode =" + mode);
		}
		return ARGB;
		
		
		
	}
	
	

}