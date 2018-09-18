package FramePack;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Image;
import javax.swing.JFileChooser;

import javax.swing.JButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @wbp.nonvisual location=74,649
	 */
	
	//Jlabel and Button
	private JFileChooser fChooser;
	private final JLabel blabla1 = new JLabel("GlitchArt");
	JButton buttonChooseImage = new JButton("Chose image");
	JLabel lelabel = new JLabel("Le Label");
	JLabel imageDisp;
	private final JLabel lblImageShower = new JLabel("IMAGE");
	private final JButton btnAddEffect = new JButton("Add Effect");
	private final JLabel lblEffectCount = new JLabel("0");
	

	
	
	//Image and input
	File inputFile;	
	private Image inputImage;
	private BufferedImage bufferedInputImage;
	
	
	
	private boolean hasInput = false;
	private ArrayList<FilterFrame> filterFrameList = new ArrayList<FilterFrame>();
	private int nbFilter = 0;
	private static ClassList classList;
	private static MaskCreatorFrame maskCreatorFrame;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame mFrame = new MainFrame();
					mFrame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @return 
	 */
	private void initialize(){
		//met en place la fenetre proprement tmtc
		setTitle("Image Treating");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 50, 900, 900);
		getContentPane().setLayout(null);
		
		
	}
	
	private void fileSearch(){
		int fileValue = fChooser.showOpenDialog(fChooser);
		
		if (fileValue == JFileChooser.APPROVE_OPTION){
			try{
				inputFile =fChooser.getSelectedFile();
				
				inputImage=ImageIO.read(inputFile);
				bufferedInputImage=ImageIO.read(inputFile);
				
				lelabel.setText("image choisie");
				hasInput = true;
				
				//Affiche l'image en icon dans lblimageshower
				ImageIcon imageImputIcon = new ImageIcon(fChooser.getSelectedFile().getAbsolutePath());
				inputImage =  imageImputIcon.getImage();
				Image scaledImput = inputImage.getScaledInstance(lblImageShower.getWidth(), lblImageShower.getHeight(), Image.SCALE_SMOOTH);
				ImageIcon scaledImputIcon = new ImageIcon(scaledImput);
				lblImageShower.setIcon(scaledImputIcon);					
				classList = new ClassList(bufferedInputImage);
			}
			catch(IOException ioe){
				lelabel.setText("raté");
			}
			
		}
		else{
			lelabel.setText("pas d'image");
		}
	}
	
	private void initFileSearch(){
		//action du bouton
		buttonChooseImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				fileSearch();
			}
		});
		buttonChooseImage.setBounds(15, 50, 174, 29);
		getContentPane().add(buttonChooseImage);
		
		lelabel.setBackground(Color.WHITE);
		lelabel.setBounds(219, 54, 276, 20);
		getContentPane().add(lelabel);
		
		JButton btnCreateMask = new JButton("Create Mask");
		btnCreateMask.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openMCFrame();
				
			}
		});
		btnCreateMask.setBounds(485, 63, 134, 29);
		getContentPane().add(btnCreateMask);
		fChooser = new JFileChooser();
		
		//On regarde si on connait un des deux depository par default et on ouvre l'un d'eux si on le trouve
		String inputDirectorySurface="C:\\Users\\AUDEPIN\\Dropbox\\-Input";
		String inputDirectoryOrdiFixe="C:\\Users\\Nicolas2\\Desktop\\Manoir du génie\\Dropbox\\-Input";
		File directory = new File(inputDirectorySurface);
		if(directory.exists() && directory.isDirectory()){
			fChooser.setCurrentDirectory(directory);
		}
		else{
			directory= new File(inputDirectoryOrdiFixe);
			if(directory.exists() && directory.isDirectory()){
				fChooser.setCurrentDirectory(directory);
			}
			else{
				System.out.println("pas de dossier par default trouvé sur cet ordi");
				directory= new File("");
				fChooser.setCurrentDirectory(directory);
			}
		}
		
	}
	
	private void openMCFrame(){
		System.out.println("*** OPEN MC FRAME ***");
		if (hasInput){
			maskCreatorFrame= new MaskCreatorFrame(bufferedInputImage);
			maskCreatorFrame.setVisible(true);
		}
	}
	
	private void initFrame(){
		blabla1.setFont(new Font("Tahoma", Font.PLAIN, 24));
		blabla1.setBounds(0, 0, 428, 20);
		getContentPane().add(blabla1);
		
		lblImageShower.setHorizontalAlignment(SwingConstants.CENTER);
		lblImageShower.setIcon(null);
		
		
		lblImageShower.setBounds(25, 108, 606, 619);
		
		getContentPane().add(lblImageShower);
		
		JProgressBar progressBar = new JProgressBar();
		progressBar.setBounds(65, 776, 345, 29);
		getContentPane().add(progressBar);
		btnAddEffect.setBounds(634, 66, 115, 29);
		
		getContentPane().add(btnAddEffect);
		
		
		lblEffectCount.setBounds(756, 70, 69, 20);
		getContentPane().add(lblEffectCount);
		btnAddEffect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg1) {
				addFilter();
			};
		});
		
	}

	
	
	public MainFrame() {
		
		classList = new ClassList();
		
		initialize();
		initFrame();
		initFileSearch();

	}
	


	public void addFilter(){
		// this respond to the add Effect button 
		System.out.println("\n*** ADD FILTER ***");
		if (hasInput){
			if(filterFrameList.size()==0){
			filterFrameList.add(new FilterFrame(bufferedInputImage,this,1));
			}
			else{
				BufferedImage buffOutput = filterFrameList.get(nbFilter - 1).getBufOutput();
				
				filterFrameList.add(new FilterFrame(buffOutput, this,nbFilter));
			}
			nbFilter = nbFilter + 1;
			lblEffectCount.setText(Integer.toString(nbFilter));
			FilterFrame fF =filterFrameList.get(nbFilter - 1);
			fF.setOutputFileName(inputFile.getName());
			fF.setVisible(true);
			System.out.println("set visible created with buff Image");
			//fF.setBufInput(bufferedInputImage);
		}
		else{
			lelabel.setText("Y a pas d'image");
			System.out.println("pas d'image :/");
			}
		
	}
	
	
	// LES GETTER ET LES SETTER

	public Image getImputImage() {
		return inputImage;
	}

	public void setImputImage(BufferedImage image) {
		this.inputImage = image;
	}

	public BufferedImage getBufferedImputImage() {
		return bufferedInputImage;
	}

	public void setBufferedInputImage(BufferedImage bufferedImage) {
		this.bufferedInputImage = bufferedImage;
		setMaskCreatorFrame(new MaskCreatorFrame(bufferedImage));
	}
	
	public static ClassList getClassList(){
		if(classList == null){
			System.out.println("no classlist");
			classList = new ClassList();
		}
		return classList;
		
	}
	
	public ArrayList<FilterFrame> getFilterFrameList() {
		return filterFrameList;
	}

	public void setFilterFrameList(ArrayList<FilterFrame> filterFrameList) {
		this.filterFrameList = filterFrameList;
	}

	public static MaskCreatorFrame getMaskCreatorFrame() {
		return maskCreatorFrame;
	}

	public static void setMaskCreatorFrame(MaskCreatorFrame maskCreatorFrame) {
		MainFrame.maskCreatorFrame = maskCreatorFrame;
	}
}

