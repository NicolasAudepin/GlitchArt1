package FramePack;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import Parameter.ButtonGroupParameter;
import Parameter.CheckBoxParameter;
import Parameter.MaskParameter;
import Parameter.ParameterParent;
import Parameter.SliderParameter;

public class StuffWithParameter {
	protected int printFreq = 50;
	
	protected String name;
	
	/**
	 * La liste des valeurs des paramètres généréer par RefreshParamValue 
	 */
	protected ArrayList<Integer> paramValue = new ArrayList<Integer>();
	
	/**
	 * La liste des Paramètres
	 */
	protected ArrayList<ParameterParent> filterParamList = new ArrayList<ParameterParent>();
	
	/**
	 * le taux de comlétion du calcul du filtre.
	 */
	protected double complition = 0;
	
	/**
	 * Copiée sur internet 
	 * @param bi BufferedImage l'image a copier
	 * @return une image identique mais modifiable sans toucher à l'original
	 * 
	 */
	protected static BufferedImage deepCopy(BufferedImage bi) {
		// to keep the input still while modifying the output 
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	
	/**
	 * vide la liste paramValue puis la remplie avec un int par paramètre 
	 * le numéro du mask pour les masks 
	 * la valeur du slider
	 * Un 1/0 pour les checkbox 
	 * pour les buttonsGroup il y a un param par boutton remplis avec des 1/0 
	 */
	protected void RefreshParamValue(){
		//this is not overriden
		System.out.println("***REFRESH PARAM VALUE***");
		paramValue.clear();
		for (int i=0 ; i<filterParamList.size() ; i++){ //On ajoute la valeur de chaque param a la liste
			ParameterParent param= filterParamList.get(i);
				paramValue.add(param.getValue());
				String paramName=param.getName();
				System.out.println("No"+i+" "+paramName+" Value: "+param.getValue());
			
			
		}
		
	}
	
	/**
	 * renvoi la liste des valeurs des paramètres traités 
	 * @return
	 */
	protected ArrayList<Integer> getTreatedParamValue(){
		//This is overriden to assign the different parameters
		RefreshParamValue();
		ArrayList<Integer> treatedParamValue = new ArrayList<Integer>();
		int len = paramValue.size();
		treatedParamValue.add(paramValue.get(0));
		int multi = 1;
		for (int i = 1;i<len;i++){
			int value = paramValue.get(i);
			
			String name = filterParamList.get(i).getName();
			if (filterParamList.get(i-1).getName() != name){
				treatedParamValue.add(value);
				multi = 1;
			}
			else{
				multi++;
				int last = treatedParamValue.size() - 1;
				int lastValue = treatedParamValue.get(last);
				
				treatedParamValue.set(last, value * multi+ lastValue);
			}
		}
		System.out.println("treatedParamValue"+treatedParamValue);
		return treatedParamValue;
		
	}
	
	/**
	 * This is used when creating new parameter
	 * Using this the parameter will be shown and used in the order they are created
	 * @return the next position and graphical position for the filter parameters

	 */
	private ArrayList<Integer> getNextPos(){
		ArrayList<Integer> bart = new ArrayList<Integer>();
		
		int length = filterParamList.size();
		if (length == 0){
			bart.add(0);
			bart.add(0);
		}
		else{
			int graphPos=filterParamList.get(length-1).getGraphicalPlacement()+1;
			bart.add(length);
			bart.add(graphPos);
		}
		
		
		return bart;
		
	}
	

	/**
	 * 
	 * @param name
	 * @param defaultValue
	 * @param description
	 */
	protected void createMask(String name, int defaultValue,String description){
		System.out.println("*** CREATE MASK PARAM ***");
		ArrayList<Integer> positions = getNextPos();
		int pos=positions.get(0);
		int graphPos= positions.get(1)+1;
		ParameterParent param = new MaskParameter( name,description, pos, graphPos, defaultValue);
		filterParamList.add(param);
	}
	
	
	/**
	 * 
	 * @param name
	 * @param min
	 * @param max
	 * @param defaultValue
	 * @param description
	 */
	protected void createSlider(String name,int min, int max, int defaultValue,String description){
		System.out.println("*** CREATE SLIDER PARAM ***");
		ArrayList<Integer> positions = getNextPos();
		int pos=positions.get(0);
		int graphPos= positions.get(1);
		SliderParameter param=new SliderParameter(name,pos,graphPos,min, max, defaultValue, description);
		filterParamList.add(param);
	}
	
	
	/**
	 * 
	 * @param groupName
	 * @param groupNamesList
	 * @param description
	 */
	protected void createButtonGroup(String groupName,String[] groupNamesList,String description){
		System.out.println("*** CREATE BUTTON GROUP PARAM ***");
		ArrayList<Integer> positions = getNextPos();
		int pos=positions.get(0);
		int graphPos= positions.get(1);
		for(int i=0;i<groupNamesList.length;i++){
			
			ButtonGroupParameter button =new ButtonGroupParameter(groupName,description, pos+i,groupNamesList[i],graphPos );
			filterParamList.add(button);
		}
	}
	
	/**
	 * 
	 * @param name
	 * @param defaultValue
	 * @param description
	 */
	protected void createCheckBox(String name,boolean defaultValue,String description){
		System.out.println("*** CREATE CHECK BOX PARAM ***");
		ArrayList<Integer> positions = getNextPos();
		int pos=positions.get(0);
		int graphPos= positions.get(1);
		CheckBoxParameter CBP = new CheckBoxParameter(name, description, pos, graphPos, defaultValue);
		filterParamList.add(CBP);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<ParameterParent> getFilterParamList(){
		return filterParamList;
	}

	public double getComplition() {
		return complition;
	}
	/**
	 * 
	 * @param complitionPercentage
	 */
	public void setComplition(double complitionPercentage) {
		//System.out.println("comp "+complition);
		this.complition = complitionPercentage;
	}
	

}
