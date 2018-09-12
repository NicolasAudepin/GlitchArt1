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
		createSlider("amplitude",1,300,50);
		String[] mode = {"lumière","obscure","osef"};
		createButtonGroup("Modes",mode);
		
	}
	
	protected void getParamValue(){
		RefreshParamValue();
		System.out.println(paramValue);
		amp = paramValue.get(0);
		//mode = paramValue.get(2)+2*paramValue.get(3)+3*paramValue.get(4);
		
		
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
		getParamValue();
		BufferedImage output = deepCopy(input);
		int wid = output.getWidth();
		int hei = output.getHeight();
		
		System.out.println("création de la matrice");
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
		for(int i= 0 ; i<wid; i++){
			ArrayList<Integer> line = new ArrayList<Integer>();
			
			for( int j=0 ; j<hei;j++){
				int pix=output.getRGB(i, j);
				Color c = new Color(pix);
				line.add(PixValue(c.getRed(),c.getGreen(),c.getBlue()));
			}
			matrix.add(line);	
			//System.out.println(line);
		}
		System.out.println(matrix.size()+","+matrix.get(0).size());
		
		System.out.println("trouver les maximunes locaux");
		ArrayList<ArrayList<Boolean>> maxmat = new ArrayList<ArrayList<Boolean>>();
		for(int i= 1 ; i<wid; i++){
			ArrayList<Boolean> maxline =new ArrayList<Boolean>();
			ArrayList<Integer> line = matrix.get(i-1);
			maxline.add(false);
			for( int j=2 ; j<hei-1;j++){
				Boolean pix = false;
				if (line.get(j)>line.get(j-1)){
					if(line.get(j)>line.get(j+1)){
						pix =true;
					}
				}
				maxline.add(pix);
				
			}
			maxline.add(false);
			//System.out.println(maxline);
			maxmat.add(maxline);
		}
		System.out.println(maxmat.size()+","+maxmat.get(0).size());
		
		System.out.println("MAGGIIIIIIC");
		for(int i= 1 ; i<wid; i++){
			boolean select = false;
			//amp=5;
			int marge = amp;
			int first = 0;
			System.out.println(marge);
			ArrayList<Boolean> maxline = maxmat.get(i-1);
			ArrayList<Integer> valline = matrix.get(i-1);
			ArrayList<Integer> valueSelect = new ArrayList<Integer>();
			ArrayList<Integer> pixselect = new ArrayList<Integer>();
			
			for(int j=1 ; j<hei-2;j++){
				if (select==false && maxline.get(j)){
						System.out.println("on passe a true");
						pixselect = new ArrayList<Integer>();
						select= true;
						marge=amp;
						first=j;
						
				}
				if (select){
					
					pixselect.add(output.getRGB(i, j));
					valueSelect.add(valline.get(j));
					//marge=marge-1;
					if(valline.get(j)<valline.get(j+1)){
						//System.out.println("on est dans le cas");
						marge=marge-1;
					}
					if (marge==0){
						System.out.println("tri");
						select=false;
						pixselect=tri(pixselect,valueSelect);
						int len = pixselect.size();
						for(int k=1;k<len-1;k++){
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
