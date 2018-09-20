package Filters;
import java.awt.Color;
import java.awt.image.BufferedImage;

public class FTest extends Filter {
	

	
	
	
	public FTest(BufferedImage input){
		super(input);
		name="Test";
		
		createSlider("SL1",1,  100 , 100,"avc ton gros taxi");
		String[] list={"0","1","2","3","444444","zz","zdz","dd"};
		createButtonGroup("BG",list,"oui vas-y ouioui");
	}
	
	public FTest(){
		super();
		name="Test";
		
	}


	@Override 
	public BufferedImage applyFilter(BufferedImage input){
		System.out.println("*** APPLY FILTER ***");
		this.input = input;
		this.output = deepCopy(input);
		int height = input.getHeight();
		int length = input.getWidth();
		//System.out.println(input.toString());

		System.out.println("input ^ output \\/ ");
		System.out.println(output.toString());
		//the actual effect of the filter
		for(int i = 0; i < height; i++){
			System.out.println("boucle for");
			System.out.print(height +"  ");
			System.out.println(i);
			for(int j = 0; j < length; j++){
				//try{
					int RGB = input.getRGB(j,i);
					Color c = new Color(RGB);
					int red = (int)c.getRed()/2;
					int green = c.getGreen();
					int blue = c.getBlue();
					Color d = new Color(red,green,blue);
					int RGB2 = d.getRGB();
					output.setRGB(j, i, RGB2);
				//}catch(Exception e){}
				
				
			}
		}
		return output;
		
	}
	
	}

