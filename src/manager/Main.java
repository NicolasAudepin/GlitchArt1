package manager;

import GUI.NewMainFrame;

/**
 * Cette classe est le vrai main c'est ici que commence l'application
 * elle cr�� un GUIManager et une frame associ�e
 * @author AUDEPIN
 *
 */
public class Main {

	private static GUIManager GM;
	public static void main(String[] args) {
		System.out.println("Main Run");
		GUIManager GM;		
		GM = new GUIManager();
		NewMainFrame MF;
		MF = new NewMainFrame(GM);
		MF.setVisible(true);

	}

}
