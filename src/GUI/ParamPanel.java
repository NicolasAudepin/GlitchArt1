package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import Filters.Filter;
import GUI.jcg.ParamComponentGroup;
import Parameter.ParameterParent;
import Parameter.ButtonGroupParameter;
import Parameter.CheckBoxParameter;
import Parameter.MaskParameter;
import Parameter.ParameterParent;
import Parameter.SliderParameter;
import manager.GUIManager;

public class ParamPanel extends PanelParent{
	
	
	private PanelParent dady;
	private SpringLayout SLparam;
	private Filter filter;

	private ArrayList<JLabel> paramJLabelList = new ArrayList<JLabel>();
	private ArrayList<ParameterParent> paramList;
	private ArrayList<ParamComponentGroup> paramGroupList;
	

	public ParamPanel(GUIManager GM,NewMainFrame mainFrame,PanelParent dady){
		super(GM, mainFrame);
		this.dady = dady;
		SLparam = new SpringLayout();
		setLayout(SLparam);;
		setBackground(backGroundColor);
		setPreferredSize(new Dimension(3000, 3000));
		
	}
	
	public ParamPanel(GUIManager GM,NewMainFrame mainFrame,PanelParent dady,Filter filter){
		super(GM, mainFrame);
		this.dady = dady;
		this.filter = filter;
		SLparam = new SpringLayout();
		setLayout(SLparam);
		setPreferredSize(new Dimension(800, filterHeigth(filter)));
		setBackground(super.backGroundColor);
		SetParamJComponents( filter);
	}

	/**
	 * met en place les JComponents du Layer demandé
	 * @param layerPosition
	 */
	private void SetParamJComponents(Filter filter) {
		/*
		System.out.println("\nsetting the Jparam of this layer");
		paramList = filter.getFilterParamList();
		int nbParam = paramList.size();
		System.out.println("there are "+nbParam+" param");
		int btnHeigth = 40;
		int lblLength = 300;
		for (int i = 0; i<nbParam;i++){//Pour chaque param on créé ici ses JComponents

			// boby est le JLabel qui affiche le nom du paramètre 
			// le label boby ne dépend pas du type de parametre
			ParameterParent parameter = paramList.get(i);
			JLabel boby = new JLabel();
			
			boby.setText(parameter.getName());
			boby.setFont(new Font("Consolas", Font.PLAIN, 18));
			boby.setForeground(textColor);
			boby.setBackground(super.buttonColor);
			boby.setOpaque(true);
			boby.setHorizontalAlignment(SwingConstants.CENTER);
			
			
			
			
			paramJLabelList.add(boby);
			
			add(boby);
			
			
		}
		//puting constrains on the boby JLabel
		for (int i = 0; i<nbParam;i++){
			System.out.println("puting constaints "+i);
			JLabel boby=paramJLabelList.get(i);
			int ibis = paramList.get(i).getGraphicalPlacement();
			if (i==0){
				System.out.println("first");
				SLparam.putConstraint(N, boby, dist, N, this);
			}
			else{
				System.out.println("not first");
				SLparam.putConstraint(N, boby, dist, S, paramJLabelList.get(i-1));
			}
			
			SLparam.putConstraint(S, boby, btnHeigth, N, paramJLabelList.get(i));
			SLparam.putConstraint(W, boby, 1, W, this);
			SLparam.putConstraint(E, boby, lblLength, W, paramJLabelList.get(i));
			System.out.println("done for "+i);
			
		}
		
		*/
		setParamGroupsList(filter);
		for (int i=0;i<paramGroupList.size();i++){
			ParamComponentGroup pcg = paramGroupList.get(i);
			add(pcg.getJname());
			add(pcg.getJdescription());
		}
		
		revalidate();
		repaint();	
	}
	
	
	
	/**
	 * remplie la liste des groupes de components qui pouront ettre afficcher plus facilement
	 * @param f le filtre dont les params vont etre déssinés
	 */
	private void setParamGroupsList(Filter f){
		paramGroupList = new ArrayList<ParamComponentGroup>();
		ArrayList<ParameterParent> list = f.getFilterParamList();
		int nbParam = list.size();
		for (int i = 0; i<nbParam;i++){
			if (list.get(i).getClass()==MaskParameter.class){
				ParamComponentGroup pcg = new ParamComponentGroup(list.get(i));
				paramGroupList.add(pcg);
			}
			else if(list.get(i).getClass()==SliderParameter.class){
				ParamComponentGroup pcg = new ParamComponentGroup(list.get(i));
				paramGroupList.add(pcg);
			}
			else if(list.get(i).getClass()==CheckBoxParameter.class){
				ParamComponentGroup pcg = new ParamComponentGroup(list.get(i));
				paramGroupList.add(pcg);
			}
			
			else if(list.get(i).getClass()==ButtonGroupParameter.class){
				
			}
			
		}
	}
	
	private int filterHeigth(Filter f){
		int size = 0;
		//TODO faire tout ça mieux
		ArrayList<ParameterParent> list = f.getFilterParamList();
		int nbParam = list.size();
		for (int i = 0; i<nbParam;i++){
			if (list.get(i).getClass()==MaskParameter.class){
				size += 130;
			}
			else if(list.get(i).getClass()==SliderParameter.class){
				size+=130;
			}
			else if(list.get(i).getClass()==CheckBoxParameter.class){
				size+=3000;
			}
			
		}
		return size;
	}

	

}
