package FramePack;

import java.awt.EventQueue;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Mask.Mask;
import Mask.MaskCreator;
import Parameter.ParameterParent;

import java.awt.ScrollPane;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.awt.Choice;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.Button;
import java.awt.event.ItemListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class MaskCreatorFrame extends FrameParent {

	//JPanel contentPane; must be inherited inherited 
	private JLabel lblMask;
	private JTextField txtMaskName;
	Choice choiceMaskCreator;
	ScrollPane scrollPane;
	
	private int bigButtonH = 2*btnHeigth+Vspace;
	int paramZone =bigButtonH +Vspace;
	
	private static ClassList classList;
	
	private ArrayList<ParameterParent> paramList;
	private BufferedImage bufInput;
	private boolean[][] maskGrid;
	private BufferedImage bufPreview;
	
	private MaskCreator MC;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MaskCreatorFrame frame = new MaskCreatorFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void createMask(){
		System.out.println("*** CREATE MASK ***");
		if (maskGrid == null){
			System.out.println("no mask ready");
		}
		else{
			Mask manu = new Mask(txtMaskName.getText(), maskGrid);
			classList.getMaskList().add(manu);
			refreshMaskList(manu);
		}
		
	}

	private void refreshMaskList(Mask manu){
		System.out.println("*** REFRESH MASK LIST ***");
		JLabel Joseph = new JLabel("ddd");
		Joseph.setIcon(manu.getIcon());
		scrollPane.add(Joseph);
		
		
	}
	/**
	 * Create the frame.
	 */
	
	private void initialize(){
		System.out.println("*** INITIALIZE ***");
		
		classList = MainFrame.getClassList();
		
		setTitle("Mask Creator");
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 829, 593);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		scrollPane = new ScrollPane();
		scrollPane.setBounds(591, 42, 206, 485);
		contentPane.add(scrollPane);
		
		choiceMaskCreator = new Choice();
		choiceMaskCreator.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				newMCChoosen();
			}
		});
		choiceMaskCreator.setBounds(416, 42, 144, 26);
		contentPane.add(choiceMaskCreator);	
		setMCChoice();
		
		
		
		lblMask = new JLabel("Mask");
		lblMask.setHorizontalAlignment(SwingConstants.CENTER);
		lblMask.setBounds(15, 74, 545, 435);
		contentPane.add(lblMask);
		
		JLabel lblFilterCreation = new JLabel("Creation");
		lblFilterCreation.setBounds(416, line1, 144, 20);
		contentPane.add(lblFilterCreation);
		
		Button btnCreate = new Button("Create");
		btnCreate.setBounds(10, line1, bigButtonH, bigButtonH);
		contentPane.add(btnCreate);
		btnCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				createMask();
			}
		});
		
		JLabel lblFilterList = new JLabel("Filter List");
		lblFilterList.setBounds(591, line1, 69, 20);
		contentPane.add(lblFilterList);
		
		txtMaskName = new JTextField();
		txtMaskName.setText("Mask Name");
		txtMaskName.setBounds(190, 42, 206, 26);
		contentPane.add(txtMaskName);
		txtMaskName.setColumns(10);
		
		JLabel lblMaskName = new JLabel("Mask Name");
		lblMaskName.setBounds(190, line1, 206, 20);
		contentPane.add(lblMaskName);
		
		Button btnPreview = new Button("Preview");
		btnPreview.setBounds(101, line1, bigButtonH, bigButtonH);
		btnPreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				refreshPreview();
			}
		});
		contentPane.add(btnPreview);

		
	}
	
	void refreshSaveName(){
		
		int nb = classList.getMaskList().size();
		String MCName = MC.getName();
		String maskName= nb +" "+ MCName;
		txtMaskName.setText(maskName);
		
		
		
	}
	
	private void newMCChoosen(){
		
	System.out.println("\n***NEW MC CHOOSEN***");
	cleanFilterJButtons();
	int index = choiceMaskCreator.getSelectedIndex();
	Class<? extends MaskCreator> MCclass = classList.getMCList().get(index);
	System.out.println("trying to change the MC");
	
	
		
		try {
			System.out.println("getting the MC");
			MC=MCclass.getDeclaredConstructor(BufferedImage.class).newInstance(bufInput);
			System.out.println("MC changed ");
			filterParamList=MC.getFilterParamList();
			PlaceFilterJButtons(paramZone);
			refreshSaveName();
			refreshPreviewPosition();
			
			
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			System.out.println("MC class beug ");
			e.printStackTrace();
			}		
	}
	
	
	private void setMCChoice(){
		System.out.println("*** SET MC CHOICE ***");
		ArrayList<String> MCNameList = classList.getMCNameList();
		for(String name:MCNameList){
			choiceMaskCreator.add(name);			
		}
	}
	
	public MaskCreatorFrame(){
		
		File file = new File("C:\\Users\\AUDEPIN\\Dropbox\\dessin\\Glitch_python\\Source\\meuf.jpg");
		
		
		
		initialize();
		
		try {
			setBufInput(ImageIO.read(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BufferedImage getBufInput() {
		return bufInput;
	}

	public void setBufInput(BufferedImage bufInput) {
		this.bufInput = bufInput;
	}

	public MaskCreatorFrame(BufferedImage bufInput) {
		this.bufInput=bufInput;
		
		initialize();
		refreshPreview();

		
			
	}
	
	private void refreshPreview(){
		System.out.println("*** REFRESH PREVIEW ***");
		if (MC == null){
			System.out.println("no MC ready");
			System.out.println("create Pass-All mask ");
			MC= new MaskCreator(bufInput);			
		}
	
		maskGrid=MC.CreateMask();
		bufPreview=deepCopy(bufInput);
		int H =bufPreview.getHeight();
		int W = bufPreview.getWidth();
		for (int i=0;i<W;i++){
			for (int j=0;j<H;j++){
				if (maskGrid[i][j]==false){
					int rgbI = bufPreview.getRGB(i, j);
					Color b = new Color(rgbI);
					Color c;
					if (i % 40 <20){
						c = Color.BLACK;
					}
					else{
						c =b.darker();
					}
					
					int rgb =c.getRGB();
					bufPreview.setRGB(i, j, rgb );
				}
			}
		}
		setPreviewIcon(bufPreview);
	}
	
	public void setPreviewIcon(BufferedImage buffy){
		System.out.println("*** SET PREVIEW ICON ***");
		float ratio = (float)buffy.getTileHeight() / buffy.getTileWidth();
		System.out.println("image ratio = " + ratio);
		lblMask.setVerticalAlignment(SwingConstants.TOP);;
		lblMask.setSize(lblMask.getWidth(), (int)(ratio * lblMask.getWidth()));
		Image scaledIm = buffy.getScaledInstance(lblMask.getWidth(),(int)(ratio * lblMask.getWidth()), Image.SCALE_SMOOTH);
		//System.out.println("afterscale");
		ImageIcon imagelbl= new ImageIcon(scaledIm);
		System.out.println("about to set icon");
		lblMask.setIcon(imagelbl);
		System.out.println("icon set");
		
	}
	
	protected static BufferedImage deepCopy(BufferedImage bi) {
		// to keep the input still while modifying the output 
		 ColorModel cm = bi.getColorModel();
		 boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
		 WritableRaster raster = bi.copyData(null);
		 return new BufferedImage(cm, raster, isAlphaPremultiplied, null);
		}
	
	private void refreshPreviewPosition(){
		System.out.println("***REFRESHING PREVIEW POSITION***");
		int nbLines;
		if (MC== null | filterParamList.size()==0 ){
			nbLines=0;
		}
		else{
			nbLines =filterParamList.get(filterParamList.size()-1).getGraphicalPlacement();
		}
		lblMask.setBounds(15,line1+ Math.max(200,paramZone+(Vspace + btnHeigth)*(nbLines+1)), 500,500);

		System.out.println((Vspace + btnHeigth)*paramJLabelList.size());
		contentPane.validate();
		contentPane.repaint();
	
		
	}

	public ArrayList<ParameterParent> getParamList() {
		return paramList;
	}

	public void setParamList(ArrayList<ParameterParent> paramList) {
		this.paramList = paramList;
	}
	

}
