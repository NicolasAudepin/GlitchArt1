package GUI;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import Filters.Filter;
import Parameter.ParameterParent;
import manager.GUIManager;

public class ParamPanel extends PanelParent{
	
	
	private PanelParent dady;
	private SpringLayout SLparam;
	private Filter filter;

	private ArrayList<JLabel> paramJLabelList = new ArrayList<JLabel>();
	private ArrayList<ParameterParent> paramList;
	

	public ParamPanel(GUIManager GM,NewMainFrame mainFrame,PanelParent dady){
		super(GM, mainFrame);
		this.dady = dady;
		SLparam = new SpringLayout();
		setLayout(SLparam);;
		setBackground(backGroundColor);
	}
	
	public ParamPanel(GUIManager GM,NewMainFrame mainFrame,PanelParent dady,Filter filter){
		super(GM, mainFrame);
		this.dady = dady;
		this.filter = filter;
		SLparam = new SpringLayout();
		setLayout(SLparam);;
		setBackground(Color.BLUE);
		SetParamJComponents( filter);
	}

	/**
	 * met en place les JComponents du Layer demandé
	 * @param layerPosition
	 */
	private void SetParamJComponents(Filter filter) {
		
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
			boby.setBackground(Color.RED);
			boby.setOpaque(true);
			boby.setHorizontalAlignment(SwingConstants.CENTER);
			
			
			//puting constrains on the boby JLabel
			
			paramJLabelList.add(boby);
			
			add(boby);
			
			
		}
		
		for (int i = 0; i<nbParam;i++){
			System.out.println("puting constaints "+i);
			JLabel boby=paramJLabelList.get(i);
			
			if (i==0){
				System.out.println("first");
				SLparam.putConstraint(N, boby, dist, N, this);
			}
			else{
				System.out.println("not first");
				SLparam.putConstraint(N, paramJLabelList.get(i), dist, S, paramJLabelList.get(i-1));
			}
			SLparam.putConstraint(S, paramJLabelList.get(i), btnHeigth, N, paramJLabelList.get(i));
			SLparam.putConstraint(W, paramJLabelList.get(i), 1, W, this);
			SLparam.putConstraint(E, paramJLabelList.get(i), lblLength, W, paramJLabelList.get(i));
			System.out.println("done for "+i);
			
		}
		
		
		revalidate();
		repaint();	
	}

	

}
