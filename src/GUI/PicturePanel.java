package GUI;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import manager.GUIManager;

/**
 * le panel qui s'ouvre en premier lorsque on ouvre le logiciel
 * @author AUDEPIN
 *
 */
public class PicturePanel extends PanelParent {
	
	Image inputImage;
	
	private JLabel imageLabel = new JLabel();
	private JButton selectFileButton = new JButton();
	private JTextArea picInfo = new JTextArea();
	private JFileChooser fChooser = new JFileChooser();;
	
	public PicturePanel(GUIManager GM,NewMainFrame frame){
		super(GM, frame);
		System.out.println(SmallIconWidth);
		GM.setSmallIconWidth(SmallIconWidth);
		placeLabelandButton();
		setActionListeners();
		
	}

	private void setActionListeners() {
		
		// selectFile Button pressed -> ouvre la fenètre de selection de fichiers
		selectFileButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				int fileValue = fChooser.showOpenDialog(fChooser);
				if (fileValue == JFileChooser.APPROVE_OPTION){
					GM.NewInputFileChoosen(fChooser.getSelectedFile());
					if (GM.hasInputImage()){
						inputImage = GM.getInputImage();
						drawScaledMainImage(inputImage,imageLabel);
						picInfo.setText(GM.getInputInfoText());
						System.out.println("file select");
						mainFrame.getFilterPanel().setPreviousImageList(GM.getModelImageList());
						
					}
					else{
						imageLabel.setIcon(null);;
						imageLabel.setText("Wrong file type. Try .jpg or .png");
						
					}
					
					
				}
			}
		});
		

		//Lorsque la fenètre change de taille on resize l'image principale
		this.addComponentListener(new ComponentAdapter() {
		    public void componentResized(ComponentEvent componentEvent) {
		    	if (inputImage != null){
		    		drawScaledMainImage(inputImage,imageLabel);
		    	}
		    	
		    }
		});
		
	}

	private void placeLabelandButton() {
	
		//main image label
		imageLabel.setBackground(behindPicColor);
		imageLabel.setOpaque(true);
		imageLabel.setFont(bigFont);
		imageLabel.setForeground(textColor);
		imageLabel.setText("no image selected");
		imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
		add(imageLabel);
		
		selectFileButton.setText("Select Image");
		selectFileButton.setBackground(buttonColor);
		selectFileButton.setForeground(textColor);
		selectFileButton.setFont(fontButton);
		add(selectFileButton);
		
		picInfo.setText("bonjour a tous je suis le JTextArea je contiens les informations relatives à l'image selectionnée."
				+ "\nMon text sera long il me faut donc de la place pour m'écrire s'il vous plait");
		picInfo.setCaretPosition(10);
		picInfo.setFont(font);
		picInfo.setForeground(textColor);
		picInfo.setBackground(buttonColor);
		picInfo.setEditable(false);
		picInfo.setWrapStyleWord(true);
		picInfo.setLineWrap(true);
		add(picInfo);
		
		fChooser.setBackground(backGroundColor);
		fChooser.setFont(font);
		preparefChooser();
		
		setConstraintEverywhere();
	
		
	}

	/**
	 * choisi le dossier par defaut du JFileChooser parmi mes dossier proposés
	 */
	private void preparefChooser() {
		
		
		//On regarde si on connait un des deux depository par default et on ouvre l'un d'eux si on le trouve
		String inputDirectorySurface="C:\\Users\\AUDEPIN\\Dropbox\\-Input";
		String inputDirectoryOrdiFixe="C:\\Users\\Nicolas2\\Desktop\\Manoir du génie\\Dropbox\\-Input";
		File directory = new File(inputDirectorySurface);
		if(directory.exists() && directory.isDirectory()){
			fChooser.setCurrentDirectory(directory);
			System.out.println("On est sur la Surface");
		}
		else{
			directory= new File(inputDirectoryOrdiFixe);
			if(directory.exists() && directory.isDirectory()){
				fChooser.setCurrentDirectory(directory);
				System.out.println("On est sur l'ordi fixe");
			}
			else{
				System.out.println("pas de dossier par default trouvé sur cet ordi");
				directory= new File("");
				fChooser.setCurrentDirectory(directory);
			}
		}
		
	}

	private void setConstraintEverywhere() {
		//Le boutton de selection de fichier
		SL.putConstraint(N, selectFileButton, dist, N, this);
		SL.putConstraint(S, selectFileButton, RBHeigth, N, selectFileButton);
		SL.putConstraint(W, selectFileButton, dist, W, this);
		SL.putConstraint(E, selectFileButton, -dist, E, this);
		//Le panel d'information sur l'image
		SL.putConstraint(N, picInfo, dist, S, selectFileButton);
		SL.putConstraint(S, picInfo, -dist, S, this);
		SL.putConstraint(W, picInfo, -300, E, picInfo);
		SL.putConstraint(E, picInfo, -dist, E, this);
		//Le label conenant l'image
		SL.putConstraint(N, imageLabel, dist, S, selectFileButton);
		SL.putConstraint(S, imageLabel, -dist, S, this);
		SL.putConstraint(W, imageLabel, dist, W, this);
		SL.putConstraint(E, imageLabel, -dist, W, picInfo);
	
		
	}

}
