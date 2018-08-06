package Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

import Filters.Filter;

public class FPixelSorting extends Filter{
	
	int amp;
	int mode;
	
	public FPixelSorting(BufferedImage buff){
		
		super(buff);
		name="Pixel Sorting";
		createSlider("amplitude",1,100,1);
		String[] mode = {"lumière","obscure","osef"};
		createButtonGroup("Modes",mode);
		
	}
	
	protected void getParamValue(){
		RefreshParamValue();
		amp = paramValue.get(1);
		mode = paramValue.get(2)+2*paramValue.get(3)+3*paramValue.get(4);
		
		
	}
	
	private int PixValue(int R , int G , int B){
		
		return R+G+B;
	}
	
	public FPixelSorting(){
		
		super();
		name="Pixel Sorting";
	}
	
	@Override 
	public BufferedImage applyFilter(BufferedImage input){
		BufferedImage output = deepCopy(input);
		int wid = output.getWidth();
		int hei = output.getHeight();
		
		System.out.println("création de la matrice");
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
		for(int i= 1 ; i<wid; i++){
			ArrayList<Integer> line = new ArrayList<Integer>();
			
			for( int j=1 ; j<hei;j++){
				int pix=output.getRGB(i, j);
				Color c = new Color(pix);
				line.add(PixValue(c.getRed(),c.getGreen(),c.getBlue()));
			}
			matrix.add(line);	
			System.out.println(line);
		}
		
		System.out.println("trouver les maximunes locaux");
		ArrayList<ArrayList<Boolean>> maxmat = new ArrayList<ArrayList<Boolean>>();
		for(int i= 1 ; i<wid; i++){
			ArrayList<Boolean> maxline =new ArrayList<Boolean>();
			ArrayList<Integer> line = matrix.get(i);
			
			for( int j=2 ; j<hei;j++){
				Boolean pix = false;
				if (line.get(i)>line.get(i-1)){
					if(line.get(i)>line.get(i+1)){
						pix =true;
					}
				}
				maxline.add(pix);
				System.out.println(maxline);
			}
		}
		
		System.out.println("MAGGIIIIIIC");
		for(int i= 1 ; i<wid; i++){
			boolean select = false;
			int marge = amp;
			int first = 0;
			ArrayList<Boolean> maxline = maxmat.get(i);
			ArrayList<Integer> valline = matrix.get(i);
			ArrayList<Integer> valueSelect = null;
			ArrayList<Integer> pixselect = null;
			
			for(int j=1 ; j<hei-1;j++){
				if (!select && maxline.get(j)){
						select= true;
						marge=amp;
						first=j;
						
				}
				if (select){
					
					pixselect.add(output.getRGB(i, j));
					valueSelect.add(valline.get(j));
					
					if(valline.get(j)<valline.get(j+1)){
						marge=marge-1;
					}
					if (marge==0){
						System.out.println("tri");
						select=false;
						pixselect=tri(pixselect,valueSelect);
						int len = pixselect.size();
						for(int k=1;k<len;k++){
							output.setRGB(i, k+first, pixselect.get(k));
						}
						
					}
					
				}
				
			}
			
		}
		return output;
	}
	
	private ArrayList<Integer> tri(ArrayList<Integer>  pixList, ArrayList<Integer>  valList){
		
		pixList.sort(null);
		
		return pixList;
		
	}

}
