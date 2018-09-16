package Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Vector;

import Filters.Filter;
import FramePack.ClassList;
import FramePack.MainFrame;
import Mask.Mask;

public class FPixelSorting extends Filter{
	
	//On doit d�finir d�s ici les variables issues des parametre du filtre. ex: les mask 
	int amp;
	int mode;
	boolean[][] inputMaskMatrix;
	boolean[][] outputMaskMatrix;
	static ClassList classList;
	
	public FPixelSorting(BufferedImage buff){
		//Ici on cr�� les parametres du filtre en utilisant les fonctions pr�d�finits "create..." 
		super(buff);
		name="Pixel Sorting";
		createMask("Input",0);
		createMask("Output",0);
		createSlider("amplitude",1,300,50);
		String[] mode = {"lumi�re","obscure","osef"};
		createButtonGroup("Modes",mode);
		classList = MainFrame.getClassList(); //Utile pour avoir acc�s au Masks
		
	}
	
	protected void getParamValue(){
		//initialise les valeurs des param�tres d�finits en entete � partir de la liste de int fournie par RefrsehParamValue

		RefreshParamValue();//H�rit� de la classe Filter
		System.out.println("paramValue"+paramValue);
		
		int inputMaskNb = paramValue.get(0);
		Mask inputMask = classList.getMaskList().get(inputMaskNb);
		System.out.println("inputmask = " + inputMask.getName());
		inputMaskMatrix = inputMask.getMatrix();
		
		int outputMaskNb = paramValue.get(1);
		Mask outputMask = classList.getMaskList().get(outputMaskNb);
		System.out.println("outputmask = " + outputMask.getName());
		outputMaskMatrix = outputMask.getMatrix();
		
		System.out.println("amp"+paramValue);
		amp = paramValue.get(2);
		//mode = paramValue.get(2)+2*paramValue.get(3)+3*paramValue.get(4);
		
		
	}
	
	private int PixValue(int R , int G , int B,int mode ){
		//choisi la formule de classification en fonction de la valeur de mode
		int val=0;
		if(mode==0){
			val=R+G+B;
		}
		if(mode==1){
			val = R*R+G*G+B*B;
			
		}
		return val;
	}
	
	public FPixelSorting(){
		
		super();
		name="Pixel Sorting";
	}
	
	@Override 
	public BufferedImage applyFilter(BufferedImage input){
		// APPLIQUE L'EFFET Va r�cuperer les valeurs des diferents parametre puis output l'image une fois trait�e
		getParamValue(); //On ce pr�pare tranquillement
		BufferedImage output = deepCopy(input);
		int wid = output.getWidth();
		int hei = output.getHeight();
		
		// cr�ation d'une matrice de valeurs repr�sentant les valeurs des pixels de l'image selon l'algo de tri choisi 
		
		System.out.println("cr�ation de la matrice");
		ArrayList<ArrayList<Integer>> matrix = new ArrayList<ArrayList<Integer>>();
		for(int i= 0 ; i<wid; i++){
			ArrayList<Integer> line = new ArrayList<Integer>();
			
			for( int j=0 ; j<hei;j++){
				int pix=output.getRGB(i, j);
				Color c = new Color(pix);
				line.add(PixValue(c.getRed(),c.getGreen(),c.getBlue()));
			}
			matrix.add(line);	
			//System.out.println(line);
		}// "matrix" est la matrice des valeurs de pixels 
		
		
		System.out.println(matrix.size()+","+matrix.get(0).size());
		
		//cr�ation d'une autre matrice maxmat
		//elle contient un  true a chaque maximum local des colones de la matrice des valeurs de la photo et un false sinon
		// cela servira � placer le haut des ligne de pixels tri�s
		
		System.out.println("trouver les maximunes locaux");
		ArrayList<ArrayList<Boolean>> maxmat = new ArrayList<ArrayList<Boolean>>();
		for(int i= 1 ; i<wid; i++){
			ArrayList<Boolean> maxline =new ArrayList<Boolean>();
			ArrayList<Integer> line = matrix.get(i-1);
			maxline.add(false);
			for( int j=2 ; j<hei-1;j++){
				Boolean pix = false;
				if (line.get(j)>line.get(j-1)){
					if(line.get(j)>line.get(j+1)){
						pix =true;
					}
				}
				maxline.add(pix);
				
			}
			maxline.add(false);
			//System.out.println(maxline);
			maxmat.add(maxline);
		}
		System.out.println(maxmat.size()+","+maxmat.get(0).size());
		
		// le travail pr�paratoir est fini
		// on cr�� les ligne de pixels � partir des maximaux de matmax puis on les trie
		
		System.out.println("MAGGIIIIIIC");
		for(int i= 1 ; i<wid; i++){
			boolean select = false;
			
			// initialisation des param�tre au d�but de chaque colonne
			int marge = amp; //longueur des lignes de pixels tri�s
			int first = 0;
			System.out.println(marge);
			ArrayList<Boolean> maxline = maxmat.get(i-1); 
			ArrayList<Integer> valline = matrix.get(i-1);
			ArrayList<Integer> valueSelect = new ArrayList<Integer>();// cr�ation de la ligne des valeurs des pixels
			ArrayList<Integer> pixselect = new ArrayList<Integer>(); // cr�ation de la ligne de pixel 
			
			for(int j=1 ; j<hei-2;j++){
				// on on d�cide de creer une ligne de pixel trier � cet endroit tant que select = true la ligne continue
				if (select==false && maxline.get(j) && inputMaskMatrix[i][j]){
						System.out.println("on passe a true");
						pixselect = new ArrayList<Integer>();
						select= true;
						marge=amp;
						first=j;
						
				}
				
				
				if (select){
					
					//On ajoute des pixels � la ligne tant que la valeur de marge n'� pas atteind 0
					pixselect.add(output.getRGB(i, j));
					valueSelect.add(valline.get(j));
					
					
					if(valline.get(j)<valline.get(j+1)){
						//System.out.println("on est dans le cas");
						marge=marge-1;
					}
					if (marge==0){
						// c'est le moment de trier la ligne de pixel et de r�initialiser des valeurs
						System.out.println("tri");
						select=false;
						pixselect=tri(pixselect,valueSelect); // tri est la fonction qui organise les pixels en fonction de leurs valeurs
						int len = pixselect.size();
						
						// on remplace les pixels sur l'image par les pixel tri�s rendus par le tri
						for(int k=1;k<len-1;k++){
							int J = k+first;
							if(outputMaskMatrix[i][J])
							output.setRGB(i, J, pixselect.get(k));
						}
						
					}
					
				}
				
			}
			
		}
		return output;
	}
	
	private ArrayList<Integer> triRecuwala(ArrayList<Integer>  pixList, ArrayList<Integer>  valList){
		int len = pixList.size();
		
		
		
		return pixList;
	}
	
	private ArrayList<Integer> tri(ArrayList<Integer>  pixList, ArrayList<Integer>  valList){
		//Est sens� trier les pixels de pixList en utilisant valList comme r�gle de tri
		//pixList.sort(null);
		pixList=triRecuwala(pixList,valList);
		return pixList;
		
	}

}
