package Mask;

import java.awt.image.BufferedImage;

public class MCRandomLines extends MaskCreator{
	
	int precision = 1000; //le truc a changer pour avoir plus de pouvoir
	
	public MCRandomLines(){
		super();
		name = "Random Lines";
	}
	
	public MCRandomLines(BufferedImage bufInput){
		super(bufInput);
		name="Random Lines";
		createSlider("Orientation",0,180,90,"détermine en degrés l'orientation des bandes de pixels selectionnées");
		createSlider("Size",0,precision,(int)precision/2,"Détermine la taille moyenne des bandes Selectionnées");
		createSlider("Density",0,precision,(int)precision/2,"Détermine La taille moyenne des espaces entre bandes selectionnées");
		createCheckBox("Start selected",false,"Coché si la première bande de pixel est selectionnée");
	}
	
	public boolean[][] CreateMask(){
		System.out.println("*** CREATE RANDOM LINES MASK ***");
		RefreshParamValue();
		int ori = paramValue.get(0);
		int size = paramValue.get(1);
		int dens = paramValue.get(2);
		int firstLine = paramValue.get(3);		
		outputMask = new boolean[L][H];
		boolean[] axis = generateRand(size,dens,firstLine);
		double angle = (double)ori/180*Math.PI;
		double sin = Math.sin(angle);
		double cos = Math.cos(angle);
		for(int t= 0;t<(L+H)*2;t++){			
			setComplition((double)t/(L+H)*2);
			if(axis[t]){
				double x1 = cos*(t-L-H);
				double y1 = sin*(t-L-H);
				for(float u= -L-H ; u<L+H;u++){
					double x2 = sin*u;
					double y2 = cos*u;
					
					int x =  (int) ((L/2)+x1+x2);
					int y = (int) (y1+y2);
					if(x>0 & x<L & y>0 & y<H){
						outputMask[x][y]=true;
					}
					
					
				}
			}
		}
		
		
		
		return outputMask;
		
	}
	
	/**
	 * Genere l'ordre de selection des baandes de pixels
	 * @param size
	 * @param dens
	 * @param firstLine
	 * @return une liste de boolean représentant les lignes de pixels à selectionner ou non 
	 */
	private boolean[] generateRand(int size , int dens, int firstLine){
		boolean[] axis = new boolean[(L+H)*2];
		boolean state = true;
		if(firstLine == 0){
			state = false;
		}
		
		
		
		for(int t=0;t<(L+H)*2;t++){
			//System.out.println(state);
			axis[t]=state;
			
			double random =Math.random();
			
			if(state){
				if(random>(double)size/precision){
					state= false;
				}
			}
			else{
				if(random<(double)dens/precision){
					state = true;
				}
			}
			
			
			
		}
		
		
		return axis;
		
	}

}
