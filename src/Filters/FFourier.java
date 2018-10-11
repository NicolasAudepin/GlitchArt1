package Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import FramePack.MainFrame;
import backgroundThreads.ApplyFilterThread;
import org.jtransforms.fft.FloatFFT_1D;
import org.jtransforms.dct.DoubleDCT_1D;
import org.jtransforms.dst.DoubleDST_1D;
import org.jtransforms.dst.DoubleDST_2D;
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
		createButtonGroup("Color",color,"Quelle couleur est modifi�e par le calcul");
		String[] mode = {"colonne","ligne","surface"};
		createButtonGroup("Mode",mode,"");
		String[] spook = {"not spooky","spooky","spooky RGB"};
		createButtonGroup("glitch", spook, "des versions beugu�es du mode ligne");
		createCheckBox("inverse",false,"pour revenire �l'originale apr�s une FFT il faut normalement utiliser son inverse.");
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
		
		if(TparamValueList.get(3)==1){
			backward=true;
		}else{backward=false;}
		
		int mode = TparamValueList.get(1);
		
		
		//on choisit quel mode de calcul utiliser
		if (spooky&& (mode==1||mode==0)){
			output=spooky(input);
		}
		
		else if (spookyRGB && (mode==1||mode==0)){
			output= spookyRGB(input);
		}
		
		else if (!spooky && !spookyRGB && (mode==1||mode==0)){
			output = colone(input);
		}
		
		if (mode==3){
			
			int width = input.getWidth();
			int height = input.getHeight();
			DoubleDST_2D fft = new DoubleDST_2D(width, height);
			double[][] picR = new double[width][height];
			double[][] picG = new double[width][height];
			double[][] picB = new double[width][height];
			
			System.out.println("on rempli les listes");
			for(int i=0;i<width;i++){
				for(int j=0;j<height;j++){
					Color c = new Color(input.getRGB(i, j));
					int R = c.getRed();
					int G = c.getGreen();
					int B = c.getBlue();
					
					if(!backward){
						picR[i][j]=R;
						picG[i][j]=G;
						picB[i][j]=B;
					}
					else{
						picR[i][j]=(R-127)*2;
						picG[i][j]=(G-127)*2;
						picB[i][j]=(B-127)*2;
					}
					
			
					
				}
			}
			
			System.out.println("on met tout entre -1 et 1");
			
			double mxR = 0;
			double mxG = 0;
			double mxB = 0;
			
			mxR = maxAbs(picR);
			mxG = maxAbs(picG);
			mxB = maxAbs(picB);
			System.out.println("mxR = "+mxR);
			picR=divide(picR,mxR);
			picG=divide(picG,mxG);
			picB=divide(picB,mxB);
			
			
			System.out.println("go fft");
			if (!backward){	
				System.out.println("FFT gooow");
				fft.forward(picR, true);
				fft.forward(picG, true);
				fft.forward(picB, true);
				System.out.println("done");
			}
			else{
				System.out.println("FFT baaack");
				fft.inverse(picR, true);
				fft.inverse(picG, true);
				fft.inverse(picB, true);
				System.out.println("done");
			}
			
			double maxR = 0;
			double maxG = 0;
			double maxB = 0;

			for(int i=0;i<width;i++){
				for(int j=0;j<height;j++){
					if (Math.abs(picR[i][j])>maxR){
						maxR=Math.abs(picR[i][j]);	
					}
					if (Math.abs(picG[i][j])>maxG){
						maxG=Math.abs(picG[i][j]);
					}
					if (Math.abs(picB[i][j])>maxB){
						maxB=Math.abs(picB[i][j]);
					}
				}
			}
			System.out.println("max found ="+ maxR);
			
			System.out.println("seting output");
			if(!backward){
				for(int i=0;i<width;i++){
					for(int j=0;j<height;j++){
						
						int R = (int) (picR[i][j]/maxR*128+127);
						int G = (int) (picG[i][j]/maxG*128+127);
						int B = (int) (picB[i][j]/maxB*128+127);
						
						//System.out.println(R+" "+G+" "+B);
					
						Color c = new Color(R,G,B);
						output.setRGB(i, j, c.getRGB());	
					}
				}
			
			}
			else{//inverse
				for(int i=0;i<width;i++){
					for(int j=0;j<height;j++){
						
						double M = (double)(height * width) /1500.0;
						
						int R = (int) Math.min(Math.abs(picR[i][j]*255*M/maxR),255);
						int G = (int) Math.min(Math.abs(picG[i][j]*255*M/maxG),255);
						int B = (int) Math.min(Math.abs(picB[i][j]*255*M/maxB),255);
						
						//System.out.println(R+" "+G+" "+B);
					
						Color c = new Color(R,G,B);
						output.setRGB(i, j, c.getRGB());	
					}
				}
			}
		}
		System.out.println("output ready ");
		return output;
	}
	
	
	private double[][] divide(double[][] l , double a){
		
		for(int i=0;i<l.length;i++){
			for(int j=0;j<l[i].length;j++){
				l[i][j] = l[i][j]/a;
			}
		}
		
		return l;
	}
	
	private double maxAbs(double[][] l){
		double max= 0;
		for (int i = 0; i<l.length;i++){
			
			double maxL = maxAbs(l[i]);
			if(Math.abs(maxL)>max){
				max = Math.abs(maxL);
			}
		}
		
		return max;
	}
	
	
	private double maxAbs(double[] l){
		double max= 0;
		for (int i = 0; i<l.length;i++){
			if(Math.abs(l[i])>max){
				max = Math.abs(l[i]);
			}
		}
		
		return max;
	}

	/**
	 * version beugu� de applyFilter qui renvoi une image interessante
	 * @param input
	 * @return
	 */
	
	private BufferedImage colone(BufferedImage input){
		int width = input.getWidth();
		int height = input.getHeight();
		//System.out.println("output"+output.getWidth());
		System.out.println("d�but not spook w="+width+" h="+height);
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
				System.out.println("backward fft");
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
					int R =Math.min(255,Rd.intValue()*1);
					Double Gd= Math.abs(Glist[j]/Gmax*255) ;
					int G =Math.min(255,Gd.intValue()*1);
					Double Bd= Math.abs(Blist[j]/Bmax*255) ;
					int B =Math.min(255,Bd.intValue()*1);
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
		System.out.println("d�but spookRGB w="+width+" h="+height);
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
		System.out.println("d�but Spook w="+width+" h="+height);
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