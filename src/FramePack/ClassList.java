package FramePack;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Filters.FBitRedux;
import Filters.FBitSliding;
import Filters.FColorShift;
import Filters.FFuckIt;
import Filters.FTest;
import Filters.Filter;
import Mask.MCColor;
import Mask.MCRandomLines;
import Mask.MCSquare;
import Mask.Mask;
import Mask.MaskCreator;

public class ClassList {
	
	private ArrayList<Class<? extends Filter>> filterList = new ArrayList<Class<? extends Filter>>();
	private ArrayList<String> filterNameList = new ArrayList<String>();
	
	
	private ArrayList<Class<? extends MaskCreator>> MCList = new ArrayList<Class<? extends MaskCreator>>();
	private ArrayList<String> MCNameList = new ArrayList<String>();
	
	private ArrayList<Mask> MaskList = new ArrayList<Mask>(); 
	
	
	public ClassList(BufferedImage buffInput){
		System.out.println("*** CLASS LIST buffInput ***");
		createFilterList();
		createMCList();
		MaskCreator MC = new MaskCreator(buffInput);
		boolean[][] matrix = MC.CreateMask();
		Mask NoMask = new Mask("No Mask", matrix);
		addMask(NoMask);
	}
	
	public ClassList(){
		createFilterList();
		createMCList();
		//Mask NoMask = Mask("No Mask",);
	}
	
	private void createFilterList(){
		System.out.println("***CREATE FILTER LIST***");
		
		// We put here the classes of all the filter
		
		filterList.add(FColorShift.class);
		filterList.add(FBitSliding.class);
		filterList.add(FFuckIt.class);
		filterList.add(FBitRedux.class);
		filterList.add(FTest.class);
		
		
		for (Class<? extends Filter>c:filterList){
			
			try {
				Filter filter = c.newInstance();
				String name = filter.getName();
				System.out.println(name);
				filterNameList.add(name);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void createMCList(){
		System.out.println("*** CREATE MC LIST***");
		
		// We Put here the classes of the Mask Creator 
		
		MCList.add(MaskCreator.class);
		MCList.add(MCSquare.class);
		MCList.add(MCColor.class);
		MCList.add(MCRandomLines.class);
		
		for (Class<? extends MaskCreator>c:MCList){
			
			try {
				MaskCreator MC = c.newInstance();
				String name = MC.getName();
				System.out.println(name);
				MCNameList.add(name);
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	//Getter And Setter

	public ArrayList<Class<? extends Filter>> getFilterList() {
		return filterList;
	}

	public void setFilterList(ArrayList<Class<? extends Filter>> filterList) {
		this.filterList = filterList;
	}


	public ArrayList<String> getFilterNameList(){
		return filterNameList;
	}


	public ArrayList<Class<? extends MaskCreator>> getMCList() {
		return MCList;
	}

	public void setMCList(ArrayList<Class<? extends MaskCreator>> mCList) {
		MCList = mCList;
	}

	public void setFilterNameList(ArrayList<String> filterNameList) {
		this.filterNameList = filterNameList;
	}

	public ArrayList<String> getMCNameList() {
		return MCNameList;
	}

	public void setMCNameList(ArrayList<String> mCNameList) {
		MCNameList = mCNameList;
	}

	public ArrayList<Mask> getMaskList() {
		return MaskList;
	}

	public void setMaskList(ArrayList<Mask> maskList) {
		MaskList = maskList;
	}
	
	public void addMask(Mask mask){
		System.out.println("*** ADD MASK ***");
		MaskList.add(mask);
	}

}
