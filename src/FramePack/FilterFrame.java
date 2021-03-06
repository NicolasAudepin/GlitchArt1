package FramePack;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.border.EmptyBorder;

import Filters.FBitSliding;
import Filters.Filter;
import backgroundThreads.ApplyFilterThread;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Image;
import java.awt.Color;
import javax.swing.JList;
import javax.swing.DefaultListModel;
import javax.swing.border.LineBorder;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionListener;
import javax.swing.plaf.FileChooserUI;
import javax.swing.event.ListSelectionEvent;

public class FilterFrame extends FrameParent implements PropertyChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//Jcomponents
	//private JPanel contentPane;
	
	private JTextField txtSaveName;
	private JLabel lblRender = new JLabel("");
	private JFileChooser fChooser;
	private JProgressBar progressBar;
	DefaultListModel<String> filterNameList = new DefaultListModel<String>();
	JList<String> FilterList = new  JList<String>(filterNameList);
	
	
	
	JLabel boby;
	JSlider betty;
	String outputFileName="No File";
	
	// repert de position graphics
	protected int line1 = 10; // position de la premi�re ligne de contenu
	protected int btnHeigth = 25;
	protected int Vspace = 12; // espace vertical entre les bouttons
	protected int lblLength = 300;
	protected int sliderLength = 400;
	protected int btnLenght = 100;
	protected int savetxtLength = 250;
	protected int JListHeigth = 150;
	
	//le seul senc� etre different du parent
	int paramZone=line1+btnHeigth+Vspace*2;

	// les datas !!!
	
	private static ClassList classList;
	
	private boolean calculating;
	private int nbFilter;
	private MainFrame MF;
	private Filter filter ; 
	private ImageIcon imagelbl;
	private BufferedImage bufInput;
	private BufferedImage bufOutput;
	
	/**
	 * Launch the application.
	 */
	/*
	public static void main(String[] args) {
		System.out.println("*** filterFrame MAIN ***");
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					System.out.println("main filterframe");
					FilterFrame frame = new FilterFrame();
					
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
		});
		
		
	}
	*/

	/**
	 * cr�� la frame et l'initialise
	 * @param buf l'image d'input
	 * @param MF la MainFrame de ce filtre 
	 * @param nbFilter la position de ce filtre dans la liste des filtres de la MF
	 */
	public FilterFrame(BufferedImage buf, MainFrame MF, int nbFilter){
		System.out.println("*** FILTERFRAME creator(buf) ***");
		this.MF = MF;
		this.nbFilter = nbFilter;
		classList = MainFrame.getClassList();
		
		initialize();
		setBufInput(buf);
		setBufOutput(buf);
		setFilter(new FBitSliding(bufInput));
		System.out.print("Filter initialized avec buffered ");
		/*Image im = new Image(buf);
		ImageIcon scaledImputIcon = new ImageIcon(buf);
		System.out.print("Filter initialdfgteryedy ");
		lblRender.setIcon(scaledImputIcon);
		*/
	}
	
	public FilterFrame() {
		// Default lauching
		System.out.println("*** FILTERFRAME creator()  ***");
		classList = MainFrame.getClassList();
		
		File file = new File("C:\\Users\\AUDEPIN\\Dropbox\\dessin\\Glitch_python\\Source\\meuf.jpg");
		outputFileName=file.getName();
		initialize();
		
		
		try {
			setBufInput(ImageIO.read(file));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFilter(new FBitSliding(bufInput));
		
	}
	
	private void saveOutput(){
		//Ouvre la fenetre de sauvegarde de l'image output si possible dans le dossier pr�s selectionn�
		System.out.println("\n*** SAVING ***");
		String pathSurface = "C:\\Users\\AUDEPIN\\Dropbox\\-Output\\";
		String pathOrdiFixe = "C:\\Users\\Nicolas2\\Desktop\\Manoir du g�nie\\Dropbox\\-Output\\"; 
		String fileName = txtSaveName.getText();
		String path="";
		
		//on cherche � savoir si on est dans un de mes deux ordi et si c'est le cas on choisit 
		File outputDirectory = new File(pathSurface);
		if(outputDirectory.exists()&&outputDirectory.isDirectory()) {
			path=pathSurface+fileName+".jpg";
		}
		else {
			outputDirectory = new File(pathOrdiFixe);
			if(outputDirectory.exists()&&outputDirectory.isDirectory()) {
				path=pathOrdiFixe+fileName+".jpg";
			}
		}
		
		
		File outputFile = new File(path);
		System.out.println("path: "+path);
		fChooser = new JFileChooser();
		fChooser.setCurrentDirectory(outputDirectory);
		//COPIE COLLE permet de mettre un nom de fichier par default au system de sauvegarde
		try {
	        FileChooserUI fcUi = fChooser.getUI();
	        Class<? extends FileChooserUI> fcClass = fcUi.getClass();
	        Method setFileName = fcClass.getMethod("setFileName", String.class);
	        setFileName.invoke(fcUi, outputFile.getName());
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
		
		int fileValue = fChooser.showOpenDialog(fChooser);//ouvre la fenetre de sauvegarde et renvoie une valeur quand elle se ferme
		
		if(fileValue == JFileChooser.APPROVE_OPTION){//verifie que on a bien valid� la sauvegarde
			outputFile=fChooser.getSelectedFile();
			
			if (outputFile.exists()){
				System.out.println("! le nom est d�j� pris !");
			}
			else{
				System.out.println("on sauvegarde");
				try {
					System.out.println("outputFile"+outputFile.getAbsolutePath());
					ImageIO.write((RenderedImage)(bufOutput), "jpg", outputFile);
					System.out.println("sauvegard�");
				} catch (IOException e) {
					System.out.println("!! Saving Failed !!");
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			}
		}
		
	}
	
	/**
	 * g�nere et fait affich� l'image pass�e � travers le filtre a partir des param�tres
	 */
	private void rendering(){
		System.out.println("\n");
		System.out.println("*** RENDERING ***");
		System.out.println(FilterList.getSelectedValue());
		System.out.print("filter index = ");
		System.out.println(FilterList.getSelectedIndex());
		//System.out.println(bufInput.toString());
		progressBar.setValue(50);
		ApplyFilterThread tifany = new ApplyFilterThread(filter,bufInput,this);
		tifany.addPropertyChangeListener(this);
		tifany.execute();
		
		progressBar.setValue(40);
		
	} 

	/**
	 * quand il y a du progr�s de calcul
	 */
	public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
        	progressBar.setIndeterminate(false);
            progressBar.setValue((Integer) evt.getNewValue());
			setTitle("calculating");
			System.out.println("progress bar val :"+ progressBar.getValue());
        }
       
        
            
    }

	

	
	
	/**
	 * cr�� la fen�tre du filtre et place les differents Jcomponents imobiles tels que l'image et les bouton save render refresh
	 * 
	 */
	private void initialize(){
		System.out.println("*** INITIALIZE ***");
		
		
		setTitle("Filter");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(200, 100, 1050, 1200);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		//le JButton pour faire les rendus
		JButton btnRender = new JButton("Render");
		btnRender.setBounds(line1, line1, btnLenght, btnHeigth);
		btnRender.setFont(new Font("Consolas", Font.PLAIN, 20));
		btnRender.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rendering();
			}
		});
		contentPane.add(btnRender);
		
		
		//le JButton de sauvegarde
		JButton btnSave = new JButton("Save");
		btnSave.setFont(new Font("Consolas", Font.PLAIN, 20));
		btnSave.setBounds(line1*3+btnLenght*2, line1, btnLenght, btnHeigth);
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//On a cliqu� sur le bouton save
				saveOutput();
			}
		});
		contentPane.add(btnSave);
		
		
		
		// La zone de preview du nom du fichier 
		txtSaveName = new JTextField();
		txtSaveName.setText(outputFileName);
		txtSaveName.setFont(new Font("Consolas", Font.PLAIN, 15));
		txtSaveName.setBounds(line1*4+btnLenght*3, line1, savetxtLength, btnHeigth);
		contentPane.add(txtSaveName);
		
		// La zone de preview de l'image
		lblRender.setHorizontalAlignment(SwingConstants.CENTER);
		lblRender.setIcon(null);
		refreshRenderPosition();
		contentPane.add(lblRender);
		
		// Le JButton qui r� intialise l'input
		JButton btnRefreshInput = new JButton("Refresh Input");
		btnRefreshInput.setBounds(line1*2+btnLenght, line1, btnLenght, btnHeigth);
		btnRefreshInput.setFont(new Font("Consolas", Font.PLAIN, 20));
		btnRefreshInput.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//On a cliqu� sur le bouton save
				refreshInput();
			}
		});
		contentPane.add(btnRefreshInput);
		
		
		//Filter Selection
		FilterList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		FilterList.setBackground(Color.WHITE);
		FilterList.setFont(new Font("Consolas", Font.PLAIN, 15));
		FilterList.setBorder(new LineBorder(new Color(0, 0, 0)));
		FilterList.setBounds(762, line1, 148, JListHeigth);
		setFilterJList();
		FilterList.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					newFilterChoosen();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}	
			}
		});
		contentPane.add(FilterList);
		
		JButton btnAddEffect = new JButton("Add Effect");
		btnAddEffect.setBounds(908, 95, 105, 29);
		btnAddEffect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				//On a cliqu� sur le bouton Add Effect
				addFilter();
			}
		});
		contentPane.add(btnAddEffect);
		
		JLabel lblFilternb = new JLabel("FilterNB");
		lblFilternb.setBounds(925, 14, 69, 20);
		contentPane.add(lblFilternb);
		
		progressBar = new JProgressBar(0,100);
		progressBar.setBounds(762, line1 + JListHeigth+ 3, 148, btnHeigth);
		contentPane.add(progressBar);
		
		System.out.println("***END INITIALIZE***");
	}	
	
	
	private void addFilter(){
		MF.addFilter();
	}
	
	/**
	 * Ce d�clenche lorsque un nouveaux filtre est choisi.
	 * efface les anciens bouttons de param�tre et fait afficher les nouveaux.
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private void newFilterChoosen() throws IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		System.out.println("\n***NEW FILTER CHOOSEN***");
		
		cleanFilterJButtons();//fait disparaitre les Jbuttons de l'ancien filtre
		
		int index = FilterList.getSelectedIndex();
		Class<? extends Filter> filterclass = classList.getFilterList().get(index);//r�cup�re la classe associ�e au filtre s�l�ctionn�
		System.out.println("trying to change the filter");
		try { // instancie le filtre puis met en place ses JButtons et met a jour le nom de fichier par default
			filter=filterclass.getDeclaredConstructor(BufferedImage.class).newInstance(bufInput);
			System.out.println("filter class changed ");
			filterParamList=filter.getFilterParamList();
			PlaceFilterJButtons(paramZone);
			txtSaveName.setText(createFileName());
			
		} catch (InstantiationException | IllegalAccessException e) {
			System.out.println("filter class beug ");
			e.printStackTrace();
		}
		
		
		
	}
	
	private void refreshInput(){
		//TODO mettre de l'ordre dans ce bordel
		ArrayList<FilterFrame> FFL = MF.getFilterFrameList();
		BufferedImage bf  = FFL.get(nbFilter-1).getBufOutput();
		setBufInput(bf);
	}
	
	/**
	 * cr�� le nom par default du fichier pour la sauvegarde � partir des valeurs des JActioners
	 * @return
	 */
	
	private String createFileName(){
		String txt=this.filter.getName()+" ";
		for (int i=0;i<paramJActionerList.size();i++){
			if(paramJActionerList.get(i).getClass()==JSlider.class){
				JSlider param = (JSlider)paramJActionerList.get(i);
				txt = txt + param.getValue() + " ";
			}
			if(paramJActionerList.get(i).getClass()==JRadioButton.class){
				JRadioButton param = (JRadioButton)paramJActionerList.get(i);
				String str;
				if(param.isSelected()){
					str="T";
				}
				else{
					str="F";
				}
				txt = txt + str ;
			}
			if(paramJActionerList.get(i).getClass()==JCheckBox.class){
				JCheckBox param = (JCheckBox)paramJActionerList.get(i);
				String str;
				if(param.isSelected()){
					str="T";
				}
				else{
					str="F";
				}
				txt = txt + str ;
			}
			
		}
		txt = txt +" "+ outputFileName;
		return txt;
	}
		
		
	@Override
	protected void refreshSaveName(){
		txtSaveName.setText(createFileName());
	}
	
		
	protected void refreshRenderPosition(){
		System.out.println("***REFRESHING RENDER POSITION***");
		int nbLines;
		if (filter== null){
			nbLines=0;
		}
		else{
			nbLines =filterParamList.get(filterParamList.size()-1).getGraphicalPlacement();
		}
		lblRender.setBounds(15,line1+ Math.max(JListHeigth + line1 + btnHeigth +5 ,paramZone+(Vspace + btnHeigth)*(nbLines+1)), 1000,1000);

		System.out.println((Vspace + btnHeigth)*paramJLabelList.size());
		contentPane.validate();
		contentPane.repaint();
	}
	
	public void setFilterJList(){
		System.out.println("***SET FILTER JLIST***");
		
		
		ArrayList<String> nameList = classList.getFilterNameList();
		for(int i = 0 ; i<nameList.size() ; i++){
		filterNameList.addElement( nameList.get(i));
		//filterNameList.addElement("Bit Sliding");
		}
		
		FilterList.setModel(filterNameList);
	}
	
	public BufferedImage getBufImput() {
		return bufInput;
	}

	public void setBufInput(BufferedImage bufInput) {
		this.bufInput = bufInput;
		setRenderIcon(bufInput);
		
		
		
	}
	/**
	 * Fait apparaitre l'image buffy sur le label lblRender
	 * est appell� par le done() de ApplyFilterThread � la fin du 
	 * @param buffy
	 */
	public void setRenderIcon(BufferedImage buffy){
		System.out.println("*** SET RENDER ICON ***");
		float ratio = (float)buffy.getTileHeight() / buffy.getTileWidth();
		System.out.println("image ratio = " + ratio);
		lblRender.setVerticalAlignment(SwingConstants.TOP);;
		lblRender.setSize(lblRender.getWidth(), (int)(ratio * lblRender.getWidth()));
		Image scaledIm = buffy.getScaledInstance(lblRender.getWidth(),(int)(ratio * lblRender.getWidth()), Image.SCALE_SMOOTH);
		//System.out.println("afterscale");
		imagelbl= new ImageIcon(scaledIm);
		System.out.println("about to set icon");
		lblRender.setIcon(imagelbl);
		System.out.println("icon set");
		
	}

	public BufferedImage getBufOutput() {
		return bufOutput;
	}

	public void setBufOutput(BufferedImage bufOutput) {
		this.bufOutput = bufOutput;
	}

	public Filter getFilter() {
		return filter;
	}

	public void setFilter(Filter filter) {
		this.filter = filter;
	}
	
	public void setOutputFileName(String str){
		outputFileName = str;
		txtSaveName.setText(outputFileName);
	}

	public boolean isCalculating() {
		return calculating;
	}

	public void setCalculating(boolean calculating) {
		this.calculating = calculating;
	}
}
