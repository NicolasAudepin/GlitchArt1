package Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import FramePack.ClassList;
import FramePack.MainFrame;
import Mask.Mask;
import Parameter.FilterParameter;

public class FPixel extends Filter {
	
	static ClassList classList;
	ArrayList<Integer> args;
	int length;
	int height;
	
	public FPixel(BufferedImage buff){
		
		super(buff);
		name="Default PixelbyPixel";
		createMask("Mask",0,"la zone ou s'applique le filtre");
		classList = MainFrame.getClassList();
	}
	
	public FPixel(){
		
		super();
		name="Default Pixel by Pixel";
	}
	
	@Override
	protected void getParamValue(){
		RefreshParamValue();
	}
	
	
	int pixelFilter(int ARGB , ArrayList<Integer> args,int i,int j){
		
		int ARGB2 = ARGB;
		return ARGB2;
	}
	@Override
	public BufferedImage applyFilter(BufferedImage input){
		System.out.println("*** APPLY PIXEL BY PIXEL ***");
		
		this.input = input;
		this.output = deepCopy(input);
		height = input.getHeight();
		length = input.getWidth();
		args = new ArrayList<Integer>();
		getParamValue();
		/*System.out.println(input.toString());

		System.out.println("input ^ output \\/ ");
		System.out.println(output.toString());
		*/
		int maskNb = paramValue.get(0);
		Mask mask = classList.getMaskList().get(maskNb);
		System.out.println("mask = " + mask.getName());
		boolean[][] matrix = mask.getMatrix();
		//the actual effect of the filter
		for(int i = 0; i < height; i++){
			if(i % printFreq ==0){
				System.out.print(height +"  ");
				System.out.println(i);
			}
			for(int j = 0; j < length; j++){
				
				if (matrix[j][i]){
				
					int ARGB = input.getRGB(j,i);
					int ARGB2 = pixelFilter(ARGB, args,j,i);
					
					output.setRGB(j, i, ARGB2);
				}
				
			}
		}
		return output;
	}}