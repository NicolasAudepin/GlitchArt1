package manager;

import GUI.NewMainFrame;

/**
 * Cette classe est le vrai main c'est ici que commence l'application
 * elle cr�� un GUIManager et une frame associ�e
 * @author AUDEPIN
 *
 */
public class Main {

	public static void main(String[] args) {
		System.out.println("Main Run");
		GUIManager GM;
		NewMainFrame MF;
		
		
		GM = new GUIManager();
		MF = new NewMainFrame(GM);
		MF.setVisible(true);
		// TODO Auto-generated method stub

	}

}
