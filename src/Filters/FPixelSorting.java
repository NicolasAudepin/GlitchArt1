package Filters;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

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
	static Color c;
	
	public FPixelSorting(BufferedImage buff){
		//Ici on créé les parametres du filtre en utilisant les fonctions prédéfinits "create..." 
		super(buff);
		name="Pixel Sorting";
		createMask("Input",0,"La zone d'ou partent le haut des lignes de pixels trié");
		createMask("Output",0,"la zone ou sont déssiné les changements");
		createSlider("amplitude",1,600,50,"la taille minimum des lignes de pixels(en nombre de pixels)");
		String[] mode = {"numérique","réaliste","rouge","noir","hue"};
		createButtonGroup("Modes",mode,"Chaque mode est une manière de classer les pixels differente.");
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
		mode = paramValue.get(4)+2*paramValue.get(5)+3*paramValue.get(6)+4*paramValue.get(7);
		
		
	}
	
	private int PixValue(int R , int G , int B,int mode ){
		//choisi la formule de classification des pixels en fonction de la valeur de mode
		int val=0;
		if(mode==0){ //le plus classique et rapide a la fois souvent suffisant
			val=R+G+B;
		}
		if(mode==1){ // plus réaliste que mode=0 mais plus lent , pas forcement utile
			val = R*R+G*G+B*B;	
		}
		if(mode==2) { // pour le test
			val= -R;
		}
		if(mode==3) { // opposé direct de mode=0 
			val= -R-G-B;
		}
		if(mode==4) { // classe les couleurs selon leur hue. Parrait vaguement beugué mais fait a peu près le taf
			float[] bob; 
			if(R==G &&G==B) {
				val=-1;
			}
			else {
				bob = Color.RGBtoHSB(R, G, B, null);
				val=(int) (bob[0]*1000);
		
			}	
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
				line.add(PixValue(c.getRed(),c.getGreen(),c.getBlue(),mode));
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
		System.out.println(maxmat.size()+","+maxmat.get(2).size());
		
		// le travail préparatoir est fini
		// on créé les ligne de pixels à partir des maximaux de matmax puis on les trie
		
		System.out.println("MAGGIIIIIIC");
		for(int i= 1 ; i<wid; i++){
			boolean select = false;
			
			// initialisation des paramètre au start de chaque colonne
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
						System.out.println("début le ligne");
						pixselect = new ArrayList<Integer>();
						valueSelect = new ArrayList<Integer>();
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
					if (marge==0 || j==hei-3){
						// c'est le moment de trier la ligne de pixel et de réinitialiser des valeurs
						System.out.println("tri");
						select=false;
						int len = pixselect.size();
						quicksort(valueSelect,pixselect,0,len-1); // tri est la fonction qui organise les pixels en fonction de leurs valeurs
						
						
						// on remplace les pixels sur l'image par les pixel triés rendus par le tri
						for(int k=0;k<len-1;k++){
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
	


	//copiécollé du web puis modifié
	public static void quicksort(ArrayList<Integer> tableau,ArrayList<Integer> tableau2, int start, int fin) {
		//trie deux liste à la fois suivant un quicksort sur la première est utile pour le trie be pixel obviously
		//le tableau1 est trié et le tableau deux subit les mêmes transformation que le tableau 1 
	    if (start < fin) {
	        int indicePivot = partition(tableau, tableau2, start, fin);
	        quicksort(tableau, tableau2, start, indicePivot-1);
	        quicksort(tableau, tableau2, indicePivot+1, fin);
	    }
	}
	
	//copiécollé du web puis modifié
	public static int partition (ArrayList<Integer> t,ArrayList<Integer> t2, int start, int fin) {
		//Utile a quicksort modifié
	    int valeurPivot = t.get(start);
	    int valeurPivot2 = t2.get(start);
	    int d = start+1;
	    int f = fin;
	    while (d < f) {
	        while(d < f && t.get(f) >= valeurPivot) f--;
	        while(d < f && t.get(d) <= valeurPivot) d++;
	        int temp = t.get(d);
	        t.set(d, t.get(f));
	        t.set(f,temp);
	        
	        int temp2 = t2.get(d);
	        t2.set(d, t2.get(f));
	        t2.set(f,temp2);
	        
	    }
	    if (t.get(d) > valeurPivot) d--;
	    t.set(start, t.get(d));
	    int val =valeurPivot;
	    t.set(d, val);
	    
	    t2.set(start, t2.get(d));
	    t2.set(d, valeurPivot2);
	    return d;
	}

}
