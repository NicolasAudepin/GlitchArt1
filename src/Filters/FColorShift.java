package Filters;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;


public class FColorShift extends FPixel {
	
	//private SliderParameter paramH;
	//private int H;
	//private SliderParameter paramL;
	//private int L;
	private float shift = 0;
	
	public FColorShift(BufferedImage input){
		super(input);
		name="Color Shift";
		createSlider("Shift",0,360,0,"Décale toutes les couleurs sur le spectre de couleurs. 360 fait un tour complet.");
		
	}
	
	public FColorShift(){
		super();
		name="Color Shift";
		
	}
	
	@Override
	protected void getParamValue(){
		System.out.println("*** GET PARAM VALUE ***");
		RefreshParamValue();
		 //H= paramValue.get(0);
		 //L= paramValue.get(1);
		args =new ArrayList<Integer>();
		args.add(paramValue.get(1));
		System.out.println("shift = "+ shift);
	}
	
	
	
	@Override
	int pixelFilter(int ARGB, ArrayList<Integer> args, int i ,int j ){
		
		shift = (float)(args.get(0))/360;
		Color c = new Color(ARGB);
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		float[] hsv = new float[3];
		Color.RGBtoHSB(r,g,b,hsv);
		float hue = hsv[0] + shift;
		float saturation = hsv[1];
		float brightness = hsv[2];
		int RGB2 ;
		RGB2 = Color.HSBtoRGB(hue, saturation, brightness);
		
		return RGB2;
	}
	
}

