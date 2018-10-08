package Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import FramePack.MainFrame;
import backgroundThreads.ApplyFilterThread;
import org.jtransforms.fft.FloatFFT_1D;
import org.jtransforms.dct.DoubleDCT_1D;
import org.jtransforms.dst.DoubleDST_1D;
import org.jtransforms.fft.DoubleFFT_1D;;

public class FFourier extends Filter{
	
	private Boolean spooky = false;
	private boolean spookyRGB = false;
	private boolean backward;
	
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
		String[] mode = {"colonne","ligne","surface"};
		createButtonGroup("Mode",mode,"");
		String[] spook = {"not spooky","spooky","spooky RGB"};
		createButtonGroup("glitch", spook, "des versions beuguées du mode ligne");
		createCheckBox("inverse",false,"pour revenire àl'originale après une FFT il faut normalement utiliser son inverse.");
		classList  = MainFrame.getClassList();
		
	}
	
	@Override
	public BufferedImage applyFilter(BufferedImage input,ApplyFilterThread dady){
		aft=dady;
		BufferedImage output = deepCopy(input);
		
		
		ArrayList<Integer> TparamValueList= getTreatedParamValue();
		int spook =TparamValueList.get(2);
		if(spook == 2){
			spooky = true;
		}else {spooky = false ;}
		
		if(spook == 3){
			spookyRGB = true;
		}else{spookyRGB = false;}
		
		if(spook==1){
			backward=true;
		}else{backward=false;}
		
		int mode = TparamValueList.get(1);
		
		
		
		if (spooky){
			output=spooky(input);
		}
		
		if (spookyRGB ){
			output= spookyRGB(input);
		}
		
		if (!spooky && !spookyRGB){
			if (mode == 1 || mode == 0){
				output = colone(input);
				
			}

		}
		return output;
	}

	/**
	 * version beugué de applyFilter qui renvoi une image interessante
	 * @param input
	 * @return
	 */
	
	private BufferedImage colone(BufferedImage input){
		int width = input.getWidth();
		int height = input.getHeight();
		//System.out.println("output"+output.getWidth());
		System.out.println("début not spook w="+width+" h="+height);
		//FloatFFT_1D fft = new FloatFFT_1D(height);
		//DoubleDCT_1D ff;
		DoubleDST_1D fftR = new DoubleDST_1D(height);
		DoubleDST_1D fftG = new DoubleDST_1D(height);
		DoubleDST_1D fftB = new DoubleDST_1D(height);
		System.out.println("fft ready");
		for(int i=0; i<width;i++){//collone de pixel
			setComplition((double)i/width);
			//System.out.println("boucle for");
			double[] Rlist = new double[height*2];
			double[] Glist = new double[height*2];
			double[] Blist = new double[height*2];
			for (int j =0 ; j<height ; j++ ){
				//System.out.println("color to be put un list i="+i+" j="+ j );
				int RGB  = input.getRGB(i, j);
				//System.out.println("got rgb"+ j );
				Color c = new Color(RGB);
				//System.out.println("got c"+i+" "+ j );
				int R = c.getRed();
				int G = c.getGreen();
				int B = c.getBlue();
				//System.out.println("before float list"+ j );
				if(backward){
					
					Double Rf = (double) ((R-128)*2);
					Rlist[j]= Rf;
					Double Gf = (double) ((G-128)*2);
					Glist[j]= Gf;
					Double Bf = (double) ((B-128)*2);
					Blist[j]= Bf;
				}
				else{
					//System.out.println("double");
					Double Rf = (double) (R);
					Rlist[j]= Rf;
					Double Gf = (double) (G);
					Glist[j]= Gf;
					Double Bf = (double) (B);
					Blist[j]= Bf;
				}
				Rlist[j+height]=0;
				Glist[j+height]=0;
				Blist[j+height]=0;
				
				
				//System.out.println("end double for"+ j );
				//System.out.println(Rlist);
			}
			
			
			System.out.println("going to fft");
			//System.out.println((double)i/width);
			double[] Rclist = Rlist;
			double[] Gclist = Glist;
			double[] Bclist = Blist;
			
			if (backward == false){
				System.out.println("foward fft");
				fftR.forward(Rlist, true);
				fftG.forward(Glist,true);
				fftB.forward(Blist,true);
				System.out.println("done");
			}
			else{
				System.out.println("fft");
				fftR.inverse(Rlist, true);
				fftG.inverse(Glist,true);
				fftB.inverse(Blist,true);

				System.out.println("fft done");
			}
			/*
			fftR.complexForward(Rclist);
			fftG.complexForward(Gclist);
			fftB.complexForward(Bclist);
			*/
			//fft.complexForward(Rcol);

			double Rmax= 0;
			double Gmax= 0;
			double Bmax= 0;
			double min = 0;
			
			
			for(int j = 0;j<height;j++){
				
				
				if(Math.abs(Rlist[j])>Rmax){
					Rmax= Math.abs(Rlist[j]);
				}
				if(Math.abs(Glist[j])>Gmax){
					Gmax= Math.abs(Glist[j]);
				}
				if(Math.abs(Blist[j])>Bmax){
					Bmax= Math.abs(Blist[j]);
				}
			}
			
			//System.out.println("Rmax="+ Rmax);
			//System.out.println("Gmax="+ Gmax);
			//System.out.println("Bmax="+ Bmax);
			
			for(int j = 0;j<height;j++){
				//System.out.println("Rlist"+Rlist[j]);
				if(!backward){
					//System.out.println("setting pix");
					Double Rd = Rlist[j]/Rmax*128+127;
					int R =Rd.intValue();
					Double Gd = Glist[j]/Gmax*128+127;
					int G =Gd.intValue();
					Double Bd = Blist[j]/Bmax*128+127;
					int B =Bd.intValue();
					
					
					Color c = new Color(R,G,B); 
					output.setRGB(i, j, c.getRGB());

					//System.out.println("pix set");
				}
				else{
					//System.out.println("setting back pix");
					Double Rd= Math.abs(Rlist[j]/Rmax*255) ;
					int R =Rd.intValue();
					Double Gd= Math.abs(Glist[j]/Gmax*255) ;
					int G =Gd.intValue();
					Double Bd= Math.abs(Blist[j]/Bmax*255) ;
					int B =Bd.intValue();
					Color c = new Color(R,G,B); 
					output.setRGB(i, j, c.getRGB());
					//System.out.println("pix set");
				}
				
				
				
				
			}
		}
		System.out.println("outputfin"+output.getWidth());
	
	
	return output;
	}
	
	private BufferedImage spookyRGB(BufferedImage input){
		
		int width = input.getWidth();
		int height = input.getHeight();
		System.out.println("output"+output.getWidth());
		System.out.println("début spookRGB w="+width+" h="+height);
		//FloatFFT_1D fft = new FloatFFT_1D(height);
		DoubleFFT_1D fftR = new DoubleFFT_1D(height);
		DoubleFFT_1D fftG = new DoubleFFT_1D(height);
		DoubleFFT_1D fftB = new DoubleFFT_1D(height);
		System.out.println("fft ready");
		for(int i=0; i<width;i++){//collone de pixel
			//System.out.println("boucle for");
			double[] Rlist = new double[height];
			double[] Glist = new double[height];
			double[] Blist = new double[height];
			for (int j =0 ; j<height ; j++ ){
				//System.out.println("color to be put un list"+ j );
				int RGB  = input.getRGB(i, j);
				//System.out.println("got rgb"+ j );
				Color c = new Color(RGB);
				//System.out.println("got c"+i+" "+ j );
				int R = c.getRed();
				int G = c.getGreen();
				int B = c.getBlue();
				//System.out.println("before float list"+ j );
				Double Rf = (double) (R);
				Rlist[j]= Rf;
				Double Gf = (double) (G);
				Glist[j]= Gf;
				Double Bf = (double) (B);
				Blist[j]= Bf;
				//System.out.println("end double for"+ j );
				//System.out.println(Rlist);
			}
			setComplition((double)i/width);
			//System.out.println((double)i/width);
			
			
			
			fftR.realForward(Rlist);
			fftG.realForward(Glist);
			fftB.realForward(Blist);
			//fft.complexForward(Rcol);

			double Rmax= 0;
			double Gmax= 0;
			double Bmax= 0;
			for(int j = 0;j<height;j++){
				if(Math.abs(Rlist[j])>Rmax){
					Rmax= Math.abs(Rlist[j]);
				}
				if(Math.abs(Glist[j])>Gmax){
					Gmax= Math.abs(Glist[j]);
				}
				if(Math.abs(Blist[j])>Bmax){
					Bmax= Math.abs(Blist[j]);
				}
			}
			//System.out.println("Rmax="+ Rmax);
			//System.out.println("Gmax="+ Gmax);
			//System.out.println("Bmax="+ Bmax);
			
			for(int j = 0;j<height;j++){
				//System.out.println("Rlist"+Rlist[j]);
				
				Double Rd= Math.abs(Rlist[j]/Rmax*255) ;
				int R =Rd.intValue();
				Double Gd= Math.abs(Glist[j]/Gmax*255) ;
				int G =Gd.intValue();
				Double Bd= Math.abs(Blist[j]/Bmax*255) ;
				int B =Bd.intValue();
				Color c = new Color(R,G,B); 
				output.setRGB(i, j, c.getRGB());
			}
		}
		System.out.println("outputfin"+output.getWidth());
	
	return output;
		
	}
	
	private BufferedImage spooky(BufferedImage input) {
		int width = input.getWidth();
		int height = input.getHeight();
		System.out.println("output"+output.getWidth());
		System.out.println("début Spook w="+width+" h="+height);
		//FloatFFT_1D fft = new FloatFFT_1D(height);
		DoubleFFT_1D fftR = new DoubleFFT_1D(height);
		DoubleFFT_1D fftG = new DoubleFFT_1D(height);
		DoubleFFT_1D fftB = new DoubleFFT_1D(height);
		System.out.println("fft ready");
		for(int i=0; i<width;i++){//collone de pixel
			//System.out.println("boucle for");
			double[] Rlist = new double[height];
			double[] Glist = new double[height];
			double[] Blist = new double[height];
			for (int j =0 ; j<height ; j++ ){
				//System.out.println("color to be put un list"+ j );
				int RGB  = input.getRGB(i, j);
				//System.out.println("got rgb"+ j );
				Color c = new Color(RGB);
				//System.out.println("got c"+i+" "+ j );
				int R = c.getRed();
				int G = c.getGreen();
				int B = c.getBlue();
				//System.out.println("before float list"+ j );
				Double Rf = (double) (R);
				Rlist[j]= Rf;
				Double Gf = (double) (G);
				Glist[j]= Gf;
				Double Bf = (double) (B);
				Blist[j]= Bf;
				//System.out.println("end double for"+ j );
				//System.out.println(Rlist);
			}
			setComplition((double)i/width);
			//System.out.println((double)i/width);
			
			
			
			fftR.realForward(Rlist);
			fftG.realForward(Glist);
			fftB.realForward(Blist);
			//fft.complexForward(Rcol);

			double Rmax= 0;
			double Gmax= 0;
			double Bmax= 0;
			for(int j = 0;j<height;j++){
				if(Math.abs(Rlist[j])>Rmax){
					Rmax= Math.abs(Rlist[j]);
				}
				if(Math.abs(Rlist[j])>Gmax){
					Gmax= Math.abs(Glist[j]);
				}
				if(Math.abs(Rlist[j])>Bmax){
					Bmax= Math.abs(Blist[j]);
				}
			}
			//System.out.println("Rmax="+ Rmax);
			//System.out.println("Gmax="+ Gmax);
			//System.out.println("Bmax="+ Bmax);
			
			for(int j = 0;j<height;j++){
				//System.out.println("Rlist"+Rlist[j]);
				
				Double Rd= Math.abs(Rlist[j]/Rmax*255) ;
				int R =Rd.intValue();
				Double Gd= Math.abs(Glist[j]/Gmax*255) ;
				int G =Rd.intValue();
				Double Bd= Math.abs(Blist[j]/Bmax*255) ;
				int B =Rd.intValue();
				Color c = new Color(R,G,B); 
				output.setRGB(i, j, c.getRGB());
			}
		}
		System.out.println("outputfin"+output.getWidth());
		return output;
		
	}
}
