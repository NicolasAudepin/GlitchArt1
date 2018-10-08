package Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;

import FramePack.MainFrame;
import backgroundThreads.ApplyFilterThread;
import org.jtransforms.fft.FloatFFT_1D;

public class FFourier extends Filter{
	
	public FFourier(){
		super();
		name= "Fourier";
	}

	public FFourier(BufferedImage input){
		super(input);
		name="Pixel Sorting";

		
		name = "Fourier";
		String[] color = {"All","R","G","B"};
		createButtonGroup("Color",color,"Quelle couleur est modifiée par le calcul");
		String[] mode = {"ligne","colonne","surface"};
		createButtonGroup("Mode",mode,"");
		classList  = MainFrame.getClassList();
	}
	
	@Override
	public BufferedImage applyFilter(BufferedImage input,ApplyFilterThread dady){
		aft=dady;
		BufferedImage output = deepCopy(input);
		
		RefreshParamValue();
		
		int width = input.getWidth();
		int height = input.getHeight();
		System.out.println("output"+output.getWidth());
		System.out.println("début calcul w="+width+" h="+height);
		FloatFFT_1D fft = new FloatFFT_1D(height);
		System.out.println("fft ready");
		for(int i=0; i<width;i++){//collone de pixel
			System.out.println("boucle for");
			float[] Rcol = {};
			for (int j =0 ; j<height ; j++ ){
				System.out.println("color to be put un list"+ j );
				int RGB  = input.getRGB(i, j);
				System.out.println("got rgb"+ j );
				Color c = new Color(RGB);
				System.out.println("got c"+i+" "+ j );
				int R = c.getRed();
				int G = c.getGreen();
				int B = c.getBlue();
				System.out.println("before float list"+ j );
				//float Rf = ((float)R)/255;
				//Rcol[j]=Rf;
				System.out.println("end double for"+ j );
				
			}
			setComplition((double)i/width);
			System.out.println((double)i/width);
			//fft.complexForward(Rcol);
			
			for(int j = 0;j<height;j++){
				System.out.println("second for i"+i+" j"+ j );
				int R= (int) (255);
				Color c = new Color(R,R,R); 
				output.setRGB(i, j, c.getRGB());
			}
		}
		System.out.println("outputfin"+output.getWidth());
		return output;
		
	}
}
