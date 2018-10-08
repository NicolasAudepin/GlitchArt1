package backgroundThreads;

import java.awt.image.BufferedImage;

import javax.swing.SwingWorker;

import Filters.Filter;
import FramePack.FilterFrame;


/**
 * Cette thread est appelée par l'interface utilisateur pour calculer l'image filtrée en background et l'afficher
 * Elle permet de ne pas freezer la fenètre et d'afficher la bar de progression.
 * doInBackground() Appelle le applyFilter de son filter 
 * done() envoi l'output à la FilterFrame
 * @author Nicolas2
 *
 */
public class ApplyFilterThread extends SwingWorker<BufferedImage, Void>{

	Filter fil;
	FilterFrame ff;
	BufferedImage input;
	BufferedImage output;
	int progress;
	
	/**
	 * 
	 * @param fil le filtre qui va calculer l'output
	 * @param input l'image d'entrée
	 * @param ff la fenètre qui fait appelle à cette thread et qui récuperera l'output
	 */
	public ApplyFilterThread(Filter fil,BufferedImage input,FilterFrame ff) {
		this.fil=fil;
		this.input=input;
		this.ff=ff;
	}
	
	/**
	 * fait le calcul applyFilter 
	 */
	@Override
	protected BufferedImage doInBackground() throws Exception {
		System.out.println("ApplyFilterThread go");
		setProgress(0);
		output = fil.applyFilter(input,this);
		System.out.println("fin calcul applyFIlter");
		System.out.println(output.getWidth());
		return output;
	}

	/**
	 * La tache est fini on envoi le résulta à la FilterFrame
	 */
	public void done() {
		System.out.println("ApplyFilterThread done");
		ff.setBufOutput(output);
		ff.setRenderIcon(output);
	}
	
	public void setProgressValue(int val) {
		//System.out.println("val      "+val);
		this.progress=val;
		setProgress(progress);
		//System.out.println("progress "+ this.getProgress());
	}
}
