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
	
	//On doit définir dès ici les variables issues des parametre du filtre. ex: les mask 
	int amp;
	int mode;
	boolean[][] inputMaskMatrix;
	boolean[][] outputMaskMatrix;
	static ClassList classList;
	
	public FPixelSorting(BufferedImage buff){
		//Ici on créé les parametres du filtre en utilisant les fonctions prédéfinits "create..." 
		super(buff);
		name="Pixel Sorting";
		createMask("Input",0);
		createMask("Output",0);
		createSlider("amplitude",1,300,50);
		String[] mode = {"lumière","obscure","osef"};
		createButtonGroup("Modes",mode);
		classList = MainFrame.getClassList(); //Utile pour avoir accès au Masks
		
	}
	
	protected void getParamValue(){
		//initialise les valeurs des paramètres définits en entete à partir de la liste de int fournie par RefrsehParamValue

		RefreshParamValue();//Hérité de la classe Filter
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
		// APPLIQUE L'EFFET Va récuperer les valeurs des diferents parametre puis output l'image une fois traitée
		getParamValue(); //On ce prépare tranquillement
		BufferedImage output = deepCopy(input);
		int wid = output.getWidth();
		int hei = output.getHeight();
		
		// création d'une matrice de valeurs représentant les valeurs des pixels de l'image selon l'algo de tri choisi 
		
		System.out.println("création de la matrice");
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
		
		//création d'une autre matrice maxmat
		//elle contient un  true a chaque maximum local des colones de la matrice des valeurs de la photo et un false sinon
		// cela servira à placer le haut des ligne de pixels triés
		
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
		
		// le travail préparatoir est fini
		// on créé les ligne de pixels à partir des maximaux de matmax puis on les trie
		
		System.out.println("MAGGIIIIIIC");
		for(int i= 1 ; i<wid; i++){
			boolean select = false;
			
			// initialisation des paramètre au début de chaque colonne
			int marge = amp; //longueur des lignes de pixels triés
			int first = 0;
			System.out.println(marge);
			ArrayList<Boolean> maxline = maxmat.get(i-1); 
			ArrayList<Integer> valline = matrix.get(i-1);
			ArrayList<Integer> valueSelect = new ArrayList<Integer>();// création de la ligne des valeurs des pixels
			ArrayList<Integer> pixselect = new ArrayList<Integer>(); // création de la ligne de pixel 
			
			for(int j=1 ; j<hei-2;j++){
				// on on décide de creer une ligne de pixel trier à cet endroit tant que select = true la ligne continue
				if (select==false && maxline.get(j) && inputMaskMatrix[i][j]){
						System.out.println("on passe a true");
						pixselect = new ArrayList<Integer>();
						select= true;
						marge=amp;
						first=j;
						
				}
				
				
				if (select){
					
					//On ajoute des pixels à la ligne tant que la valeur de marge n'à pas atteind 0
					pixselect.add(output.getRGB(i, j));
					valueSelect.add(valline.get(j));
					
					
					if(valline.get(j)<valline.get(j+1)){
						//System.out.println("on est dans le cas");
						marge=marge-1;
					}
					if (marge==0){
						// c'est le moment de trier la ligne de pixel et de réinitialiser des valeurs
						System.out.println("tri");
						select=false;
						pixselect=tri(pixselect,valueSelect); // tri est la fonction qui organise les pixels en fonction de leurs valeurs
						int len = pixselect.size();
						
						// on remplace les pixels sur l'image par les pixel triés rendus par le tri
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
		//Est sensé trier les pixels de pixList en utilisant valList comme règle de tri
		//pixList.sort(null);
		pixList=triRecuwala(pixList,valList);
		return pixList;
		
	}

}
