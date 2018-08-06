package FramePack;

import java.awt.BorderLayout;
import java.awt.Choice;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.ListModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Mask.Mask;
import Parameter.ButtonGroupParameter;
import Parameter.CheckBoxParameter;
import Parameter.FilterParameter;
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
	ArrayList<JLabel> paramJLabelList = new ArrayList<JLabel>();
	ArrayList<JComponent> paramJActionerList = new ArrayList<JComponent>();
	ArrayList<JComponent> otherJActionerList = new ArrayList<JComponent>();
	ArrayList<FilterParameter> filterParamList; // doit etre la liste des param du filtre ou maskCreator

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrameParent frame = new FrameParent();
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
	
	protected void cleanFilterJButtons(){
		System.out.println("***CLEANING JBUTTON***");
		for (int i=0; i < paramJActionerList.size() ;i++){
			contentPane.remove(paramJActionerList.get(i));
		}
		
		for (int i=0; i < otherJActionerList.size() ;i++){
			contentPane.remove(otherJActionerList.get(i));
		}
		
		for (int i=0; i < paramJLabelList.size() ;i++){
			contentPane.remove(paramJLabelList.get(i));
		
			
			contentPane.validate();
			contentPane.repaint();
			//contentPane.remove(betty);
		}
		
	}
	
	protected void setJActionerAction(){
		System.out.println("***SET J-ACTIONERS ACTIONS***");

		for (int i=0; i < paramJActionerList.size() ;i++){
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

	protected void PlaceFilterJButtons(int paramZone){
		/* gere la partie du frame assign�e au filtre s�l�ctionn�.
		 * cr�� les JLabel et JSLidder en fonction de filterParamList
		 * lie les sliders � leur filtre
		 * 
		*/
		System.out.println("***PLACE FILTER JBUTTON***");
		
		
		paramJLabelList = new ArrayList<JLabel>();
		paramJActionerList = new ArrayList<JComponent>();
		otherJActionerList = new ArrayList<JComponent>();
		ArrayList<String> buttonGroupNameList= new ArrayList<String>();
		ArrayList<ButtonGroup> buttonGroupList= new ArrayList<ButtonGroup>();
		
		System.out.println("nb param "+filterParamList.size());
		for(int i=0; i < filterParamList.size() ;i++){
			//On cr�� tout les sliders et autre � partir de la liste des param
			
			// le label boby ne d�pend pas du type de parametre
			FilterParameter parameter = filterParamList.get(i);
			JLabel boby = new JLabel();
			boby.setText(parameter.getName());
			boby.setFont(new Font("Tahoma", Font.PLAIN, 18));

			boby.setBounds(15, paramZone +parameter.getGraphicalPlacement()*(btnHeigth + Vspace), lblLength, btnHeigth);
			boby.setHorizontalAlignment(SwingConstants.RIGHT);
			paramJLabelList.add(boby);
			contentPane.add(boby);
			
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
				billy.setText(param.getValueName());
				billy.setName(param.getValueName());
				billy.setBounds(lblLength+2*Vspace+bertha.getButtonCount()*110, paramZone +param.getGraphicalPlacement()*(btnHeigth + Vspace), 100, btnHeigth);
				bertha.add(billy);
				System.out.print(billy.getName());
				System.out.println(" "+i+" "+param.getPlacement());
				paramJActionerList.add(param.getPlacement(), billy);
			}
		}
		//On assigne chaque actioner � sa valeur chez le filtre
		
		setJActionerAction();
				
		//les labels et sliders sont cr�� on les affiches
		for (int i=0; i < paramJActionerList.size() ;i++){
			contentPane.add(paramJActionerList.get(i));
			contentPane.add(paramJLabelList.get(i));

			//on donne au slidders leurs valeurs par default qui ont �t� mise � z�ro 
			if(paramJActionerList.get(i).getClass() == JSlider.class){
				SliderParameter g = (SliderParameter) filterParamList.get(i);
				g.setValue(g.getDefaultValue());
			}
			//On met � Zero tout les RadioButton;
			if(paramJActionerList.get(i).getClass() == JRadioButton.class){
				ButtonGroupParameter j = (ButtonGroupParameter) filterParamList.get(i);
				j.setValue(0);
			}
			
			System.out.println(filterParamList.get(i).getName() + " is shown");
		}
		refreshRenderPosition();

		
		
	}
	
	protected void linkValue(JSlider slider, JSpinner spinner){
		
		slider.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int value = slider.getValue();
				spinner.setValue(value);
				System.out.println("slider to spinner" + value);
				
			}
		});
		
		spinner.addChangeListener(new ChangeListener(){
			@Override
			public void stateChanged(ChangeEvent arg0) {
				int value = (int) spinner.getValue();
				int max = slider.getMaximum();
				int min = slider.getMinimum();
				if (value>max){
					value=max;
					spinner.setValue(max);
				}
				if(value<min){
					value=min;
					spinner.setValue(min);
				}

				slider.setValueIsAdjusting(true);
				slider.setValue(value);

				slider.setValueIsAdjusting(false);
				System.out.println("slider to spinner" + value);
				
				slider.setVisible(false);
				slider.setVisible(true);
			}
		});
	}

	protected void refreshRenderPosition() {
		// TODO Auto-generated method stub
		
	}
	
	

	
	
}