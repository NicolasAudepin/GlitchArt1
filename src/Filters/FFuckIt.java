package Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class FFuckIt extends FPixel{
	
	int violence;
	int mode;
	
	public FFuckIt(BufferedImage buff){
		
		super(buff);
		name="Fuck It";
		createSlider("Violence",0,100,0,"Plus la violence est élevée plus les couleurs seront variée et chaotiques.");
		String[] mode = {"violente","calme"};
		createButtonGroup("Modes",mode,"Violente sature les couleurs et les met à lulinosité fixe. Calme laisse leurs saturation et luminosité d'orignies.");
		
	}
	
	public FFuckIt(){
		
		super();
		name="Fuck It";
	}
	

	
	protected void getParamValue(){
		RefreshParamValue();
		violence = paramValue.get(1);
		mode = paramValue.get(2);
		args =new ArrayList<Integer>();
		args.add(violence);
		args.add(mode);
	}
	
	@Override 
	int pixelFilter(int ARGB, ArrayList<Integer> args, int i ,int j ){
		int violence =args.get(0);
		int mode = args.get(1);
		

		
		
		Color c = new Color(ARGB);
		int r = c.getRed();
		int g = c.getGreen();
		int b = c.getBlue();
		float[] hsv = new float[3];
		Color.RGBtoHSB(r,g,b,hsv);
		float hue = hsv[1]*violence ;
		
		float saturation;
		if (mode == 0){
			saturation = hsv[1];
		}
		else{
			saturation = 1;
		}
		
		float brightness = hsv[2];
		int ARGB2 ;
		ARGB2 = Color.HSBtoRGB(hue, saturation, brightness);
		return ARGB2;
		
	}
		
}
