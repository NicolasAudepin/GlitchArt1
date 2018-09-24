package FramePack;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Mask.Mask;
import Parameter.ButtonGroupParameter;
import Parameter.CheckBoxParameter;
import Parameter.ParameterParent;
import Parameter.MaskParameter;
import Parameter.SliderParameter;

public class FrameParent extends JFrame {

	JPanel contentPane;
	int line1 = 10;
	protected int btnHeigth = 25;
	protected int Vspace = 12;
	protected int lblLength = 120;
	protected int sliderLength = 400;
	protected int JListHeigth = 150;
	int paramZone = 300;
	
	
	private static ClassList classList;
	/**
	 * la liste des JLabel des nom des paramètres
	 */
	ArrayList<JLabel> paramJLabelList = new ArrayList<JLabel>(); 
	
	/**
	 * La liste des JLabel des descriptions de parametres
	 */
	ArrayList<JTextArea> paramDescrtiptionJLabelList = new ArrayList<JTextArea>(); 
	ArrayList<JComponent> paramJActionerList = new ArrayList<JComponent>();
	ArrayList<JComponent> otherJActionerList = new ArrayList<JComponent>();
	
	/**
	 * doit etre la liste des param du filtre ou maskCreator selectionné
	 */
	ArrayList<ParameterParent> filterParamList; 

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}); 
	}

	/**
	 * Create the frame.
	 */
	public FrameParent() {
		FrameParent.classList = MainFrame.getClassList();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
	}

	void refreshSaveName(){
		System.out.println("***REFRESH SAVE NAME***");
		System.out.println("****CETTE METHODE DEVRAIT ETRE OVERRIDE!!!***");
	}
	
	/**
	 * retire tout les JComponents en lien avec le filtre
	 */
	protected void cleanFilterJButtons(){
		System.out.println("***CLEANING JBUTTON***");
		for (int i=0; i < paramJActionerList.size() ;i++){
			contentPane.remove(paramJActionerList.get(i));
		}
		
		for (int i=0; i < paramDescrtiptionJLabelList.size() ;i++){
			contentPane.remove(paramDescrtiptionJLabelList.get(i));
		}
		
		for (int i=0; i < otherJActionerList.size() ;i++){
			contentPane.remove(otherJActionerList.get(i));
		}
		
		for (int i=0; i < paramJLabelList.size() ;i++){
			contentPane.remove(paramJLabelList.get(i));

		}
		
		contentPane.validate();
		contentPane.repaint();
		
	}
	
	/**
	 * Ajoute tout les ActionListener des paramètres 
	 */
	protected void setJActionerAction(){
		System.out.println("***SET J-ACTIONERS ACTIONS***");

		for (int i=0; i < paramJActionerList.size() ;i++){
			
			//On met en place les actionListener des noms des param pour qu'ils affiches les descriptions
			JLabel boby = paramJLabelList.get(i);
			JTextArea barn = paramDescrtiptionJLabelList.get(i);
			boby.addMouseListener(new MouseListener() {



				@Override
				public void mouseEntered(MouseEvent arg0) {
					barn.setOpaque(true);
					barn.setVisible(true);
				}

				@Override
				public void mouseExited(MouseEvent arg0) {
				
					barn.setVisible(false);
					
				}

				//Les trois Overrides sont la sinon le mouse listener boude
				@Override
				public void mouseClicked(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mousePressed(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					// TODO Auto-generated method stub
					
				}


				
			});
			
			int ibis = i ;
			JComponent actioner =paramJActionerList.get(i);
			if(actioner.getClass() == JComboBox.class){
				//Mask Selection Actioner
				@SuppressWarnings("unchecked")
				JComboBox<String> list =(JComboBox<String>)actioner;
				list.addActionListener(new ActionListener(){
					@Override
					public void  actionPerformed(ActionEvent arg0) {
						
						int value = list.getSelectedIndex();
						MaskParameter MP = (MaskParameter) filterParamList.get(ibis);
						MP.setValue(value);
						System.out.println("ComboBox nb="+ibis);
						System.out.println("Value="+value);
						System.out.println(MP.getValue());
						
						
						
						refreshSaveName();
						
					}
				});
				
				list.addFocusListener(new FocusAdapter() {
					@Override
					public void focusGained(FocusEvent arg0) {
						System.out.println("*** REFRESH MASK LIST ***");
						DefaultListModel<String> MaskNameList = new DefaultListModel<String>();
						ArrayList<Mask> maskList= new ArrayList<Mask>();
						list.removeAllItems();
						maskList = classList.getMaskList();
						for(int nb = 0 ; nb<maskList.size();nb++ ){
							Mask mask = maskList.get(nb);
							MaskNameList.addElement(mask.getName());
							list.addItem(mask.getName());
						}
					}
				});
				
			}
			
			if(actioner.getClass() == JSlider.class){
				//pour chaque slider met a jour sa value correspondante chez le filtre
				JSlider slider =(JSlider)actioner;
				slider.addChangeListener(new ChangeListener(){
					@Override
					public void stateChanged(ChangeEvent arg0) {
						
						int value = slider.getValue();
						SliderParameter SP = (SliderParameter) filterParamList.get(ibis);
						SP.setValue(value);
						System.out.println("Slider nb="+ibis);
						System.out.println("Value="+value);
						System.out.println(SP.getValue());
						
						refreshSaveName();
						
					}
				});
			}
			if(actioner.getClass() == JRadioButton.class){
				JRadioButton JRB=(JRadioButton)actioner;
				JRB.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("");
						for (int nb = 0;nb<paramJActionerList.size();nb++){
							if (paramJActionerList.get(nb).getClass()==JRadioButton.class){
								JRadioButton JB=(JRadioButton)paramJActionerList.get(nb);
								ButtonGroupParameter BGP = (ButtonGroupParameter)filterParamList.get(nb);
								System.out.println(BGP.getValueName()+BGP.getValue());
								if (JB.isSelected()){
									BGP.setValue(1);
								}
								else{
									BGP.setValue(0);
								}
								System.out.println("Radio nb="+ibis);
								System.out.println(BGP.getValueName()+BGP.getValue());
							}
						}
							//paramJActionerList = new ArrayList<JComponent>();
						refreshSaveName();
					}
				});
			}
			if(actioner.getClass() == JCheckBox.class){
				//pour chaque slider met a jour sa value correspondante chez le filtre
				JCheckBox CB = (JCheckBox)actioner;
				CB.addChangeListener(new ChangeListener(){
					@Override
					public void stateChanged(ChangeEvent arg0) {
						
						boolean value = CB.isSelected();
						CheckBoxParameter CBP = (CheckBoxParameter) filterParamList.get(ibis);
						if (value){
							CBP.setValue(1);	
						}
						else{
							CBP.setValue(0);
						}
						
						System.out.println("CheckBox nb="+ibis);
						System.out.println("Value="+value);
						System.out.println(CBP.getValue());
						
						refreshSaveName();
					}
				});
			}
		}
	}

	
	/**
	 * 
	 * gere la partie du frame contenant les paramètres du filtre séléctionné.
	 * créé les JLabel et JSLidder en fonction de filterParamList
	 * Créé aussi les JLabel invisibles des descriptions
	 * lie les sliders à leur filtre
	 * 	
	 * @param paramZone le nombre de pixels entre les bouttons du premier paramètre et le haut de la fenètre
	 */
	protected void PlaceFilterJButtons(int paramZone){
		System.out.println("***PLACE FILTER JBUTTON***");
		
		
		paramJLabelList = new ArrayList<JLabel>();
		paramJActionerList = new ArrayList<JComponent>();
		otherJActionerList = new ArrayList<JComponent>();
		paramDescrtiptionJLabelList = new ArrayList<JTextArea>(); 
		ArrayList<String> buttonGroupNameList= new ArrayList<String>();
		ArrayList<ButtonGroup> buttonGroupList= new ArrayList<ButtonGroup>();
		
		System.out.println("nb param "+filterParamList.size());
		for(int i=0; i < filterParamList.size() ;i++){ //On créé tout les sliders et autre à partir de la liste des param
			
			// boby est le JLabel qui affiche le nom du paramètre 
			// le label boby ne dépend pas du type de parametre
			ParameterParent parameter = filterParamList.get(i);
			JLabel boby = new JLabel();
			boby.setText(parameter.getName());
			boby.setFont(new Font("Consolas", Font.PLAIN, 18));

			boby.setBounds(15, paramZone +parameter.getGraphicalPlacement()*(btnHeigth + Vspace), lblLength, btnHeigth);
			boby.setHorizontalAlignment(SwingConstants.RIGHT);
			paramJLabelList.add(boby);
			contentPane.add(boby);
			
			// barnabé est le JLabel de la description qui apparait quand la souris passe sur boby
			JTextArea barnabe = new JTextArea();
			barnabe.setText(parameter.getDescription());
			System.out.println(barnabe.getText());
			barnabe.setFont(new Font("Consolas", Font.PLAIN, 14));
			barnabe.setBounds(15, paramZone +parameter.getGraphicalPlacement()*(btnHeigth + Vspace)-btnHeigth*2, sliderLength, btnHeigth*2);
			barnabe.setForeground(Color.WHITE);
			barnabe.setBackground(Color.DARK_GRAY);
			paramDescrtiptionJLabelList.add(barnabe);
			barnabe.setVisible(false);
			barnabe.setLineWrap(true);
			barnabe.setWrapStyleWord(true);
			barnabe.setAutoscrolls(true);
			barnabe.setMargin(new Insets(10, 10, 10, 10));
			
			contentPane.add(barnabe);
			
			
			
			
			if (parameter.getClass() == MaskParameter.class){
				MaskParameter param = (MaskParameter) filterParamList.get(i);
				JComboBox<String> bella = new JComboBox<String>();
				bella.setBounds(lblLength+2*Vspace, paramZone +param.getGraphicalPlacement()*(btnHeigth + Vspace),lblLength, btnHeigth);
				DefaultListModel<String> MaskNameList = new DefaultListModel<String>();
				ArrayList<Mask> maskList= new ArrayList<Mask>();
				maskList = classList.getMaskList();
				for(int nb = 0 ; nb<maskList.size();nb++ ){
					Mask mask = maskList.get(nb);
					MaskNameList.addElement(mask.getName());
					bella.addItem(mask.getName());
				}				
				paramJActionerList.add(param.getPlacement(), bella);
			}
			
			
			if (parameter.getClass() == SliderParameter.class){
				SliderParameter param = (SliderParameter) filterParamList.get(i);
				JSlider betty= new JSlider();
				betty.setMaximum(param.getMax());
				betty.setMinimum(param.getMin());
				betty.setValue(param.getDefaultValue());
				betty.setLabelTable(betty.createStandardLabels((int)(Math.abs(param.getMax()-param.getMin())/5)+1));
				betty.setBounds(lblLength+2*Vspace, paramZone +param.getGraphicalPlacement()*(btnHeigth + Vspace), sliderLength, btnHeigth);
				betty.setPaintTicks(true);
				
				JSpinner beth = new JSpinner();
				beth.setFont(new Font("Consolas", Font.PLAIN, 16));
				beth.setBounds(lblLength+2*Vspace + sliderLength, paramZone +param.getGraphicalPlacement()*(btnHeigth + Vspace), 50, btnHeigth);

				otherJActionerList.add(beth);
				paramJActionerList.add(param.getPlacement(), betty);
				contentPane.add(beth);
				linkValue(betty,beth);
				
			//contentPane.add(betty);	
			}
			
			if (parameter.getClass() == CheckBoxParameter.class){
				CheckBoxParameter param = (CheckBoxParameter) filterParamList.get(i);
								
				JCheckBox bianca= new JCheckBox();
				
				bianca.setText("");
				bianca.setSelected(param.getDefaultValue());
				bianca.setBounds(lblLength+2*Vspace, paramZone +param.getGraphicalPlacement()*(btnHeigth + Vspace), 50, btnHeigth);
				paramJActionerList.add(param.getPlacement(), bianca);
			//contentPane.add(betty);	
			}
			
			// On place les boutons RadioButton
			if(parameter.getClass() == ButtonGroupParameter.class){
				ButtonGroup bertha = new ButtonGroup();
				ButtonGroupParameter param = (ButtonGroupParameter) filterParamList.get(i);
				JRadioButton billy = new JRadioButton();
					
				//On choisi le ButtonGroup bertha 
				if(buttonGroupNameList.contains(param.getName())){
					int BGnb = buttonGroupNameList.indexOf(param.getName());
					bertha = buttonGroupList.get(BGnb);
				}
				else{
					buttonGroupList.add(bertha);
					buttonGroupNameList.add(param.getName());
				}
				billy.setFont(new Font("Consolas", Font.PLAIN, 16));
				billy.setText(param.getValueName());
				billy.setName(param.getValueName());
				billy.setBounds(lblLength+2*Vspace+bertha.getButtonCount()*90, paramZone +param.getGraphicalPlacement()*(btnHeigth + Vspace), 80, btnHeigth);
				bertha.add(billy);
				System.out.print(billy.getName());
				System.out.println(" "+i+" "+param.getPlacement());
				paramJActionerList.add(param.getPlacement(), billy);
			}
		}
		//On assigne chaque actioner à sa valeur chez le filtre
		
		setJActionerAction();
				
		//les labels et sliders sont créé on les affiches
		for (int i=0; i < paramJActionerList.size() ;i++){
			contentPane.add(paramJActionerList.get(i));
			contentPane.add(paramJLabelList.get(i));

			//on donne au slidders leurs valeurs par default qui ont été mise à zéro 
			if(paramJActionerList.get(i).getClass() == JSlider.class){
				SliderParameter g = (SliderParameter) filterParamList.get(i);
				g.setValue(g.getDefaultValue());
			}
			//On met à Zero tout les RadioButton;
			if(paramJActionerList.get(i).getClass() == JRadioButton.class){
				ButtonGroupParameter j = (ButtonGroupParameter) filterParamList.get(i);
				j.setValue(0);
			}
			
			System.out.println(filterParamList.get(i).getName() + " is shown");
		}
		refreshRenderPosition();

		
		
	}
	
	
	/**
	 * Lie les valeurs d'un JSlider et d'un JSpinner en leurs ajoutant des ChangeListener
	 * @param slider
	 * @param spinner
	 */
	protected void linkValue(JSlider slider, JSpinner spinner){
		
		slider.addChangeListener(new ChangeListener(){ //Si le slider est modifié on met à jour le spinner
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int value = slider.getValue();
				spinner.setValue(value);
				System.out.println("slider to spinner" + value);
				
			}
		});
		
		spinner.addChangeListener(new ChangeListener(){  //Si le spiner est modifié on met à jour le slidder
			@Override
			public void stateChanged(ChangeEvent arg0) {
				slider.setVisible(false);
				int value = (int) spinner.getValue();
				int max = slider.getMaximum();
				int min = slider.getMinimum();
				if (value>max){ // Si on met une valeur du spinner au dessus du max du slider elle se remet au max
					value=max;
					spinner.setValue(max);
				}
				if(value<min){ //same
					value=min;
					spinner.setValue(min);
				}

				slider.setValueIsAdjusting(true);
				slider.setValue(value);

				slider.setValueIsAdjusting(false);
				System.out.println("slider to spinner" + value);
				
				
				slider.setVisible(true);
			}
		});
	}

	protected void refreshRenderPosition() {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
}
