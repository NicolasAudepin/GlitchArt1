package Mask;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import FramePack.ClassList;
import FramePack.MainFrame;

/**
 * le MC qui permet de fusionner deux mask suivant les orération classiques des ensemble unions intersection soustraction negatif XOR
 * @author AUDEPIN
 *
 */
public class MCCombinaison extends MaskCreator{
	
	static ClassList classList;
	
	public MCCombinaison(){
		super();
		name="Combinaison";
	}
	
	public MCCombinaison(BufferedImage bufInput){
		super(bufInput);
		classList = MainFrame.getClassList();
		name="Combinaison";
		//TODO régler ça avec la fusion
		String[] operations = {"A+B","A*B","AxB","A\\B","B\\A","-A"};		
		createMask("inputA", 0);
		createMask("inputB", 0);
		createButtonGroup("Operation",operations);
		/*createMask("Mask A",0,"Le premier mask de l'opération. Si l'opération est -A c'est ce mask qui est inversé");
		
		createMask("Mask B",0,"Le second mask de l'opération. Ce masque est ignoré lorsque l'on choisi -A");
		
		createButtonGroup("Operation",operations,"A+B selectionne les pixels appartenant à au moins un filtre. A*B selectionne les pixels appartenant au deux filtres"
				+ "AxB selectionne les pixels appartenant a un Mask seulement A\\B selectionne ceux qui apparttiennent à A mais pas à B"
				+ "-A selectionne ceux qui n'appartiennent pas à A");
		*/
	}
	
	public boolean[][] CreateMask(){
		System.out.println("*** Creation Mask Combinaison");
		boolean[][] matrix = new boolean[L][H];
		RefreshParamValue();
		
		int maskANb = paramValue.get(0); //le premier mask
		System.out.println("oui vas y ouioui"+maskANb);
		ArrayList<Mask> list= classList.getMaskList();
		Mask maskA = list.get(maskANb);
		boolean[][] matA= maskA.getMatrix();
		
		int maskBNb = paramValue.get(1); // le second mask
		Mask maskB = classList.getMaskList().get(maskBNb);
		boolean[][] matB = maskB.getMatrix();
		
		int operation = paramValue.get(2); // le param opération
		
		if(operation == 0){ // A+B
			System.out.println("operation 0");
			for(int i=0;i<L;i++){
				for(int j = 0; j<H; j++){
					if(matB[i][j] || matA[i][j]){
						matrix[i][j] = true;
					}
					else{
						matrix[i][j] = false;
					}
				}
			}
		}
		
		
		if(operation == 1){ // A*B
			System.out.println("operation 1");
			for(int i=0;i<L;i++){
				for(int j = 0; j<H; j++){
					if(matB[i][j] && matA[i][j]){
						matrix[i][j] = true;
					}
					else{
						matrix[i][j] = false;
					}
				}
			}
		}
		
		if(operation == 2){ // A xor B
			for(int i=0;i<L;i++){
				for(int j = 0; j<H; j++){
					if((matB[i][j] && (matA[i][j]==false))||(matA[i][j] && (matB[i][j]==false))){
						matrix[i][j] = true;
					}
					else{
						matrix[i][j] = false;
					}
				}
			}
		}
		
		if(operation == 3){ // A\\B
			for(int i=0;i<L;i++){
				for(int j = 0; j<H; j++){
					if((matB[i][j]==false) && matA[i][j]){
						matrix[i][j] = true;
					}
					else{
						matrix[i][j] = false;
					}
				}
			}
		}
		
		if(operation == 4){ // B\\A
			for(int i=0;i<L;i++){
				for(int j = 0; j<H; j++){
					if(matB[i][j] && (matA[i][j]==false)){
						matrix[i][j] = true;
					}
					else{
						matrix[i][j] = false;
					}
				}
			}
		}
		
		if(operation == 5){ // -A
			for(int i=0;i<L;i++){
				for(int j = 0; j<H; j++){
					if(matB[i][j]==false ){
						matrix[i][j] = true;
					}
					else{
						matrix[i][j] = false;
					}
				}
			}
		}
		
		return matrix; 
		
	}
	
}
