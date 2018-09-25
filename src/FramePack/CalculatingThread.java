package FramePack;

import java.awt.image.BufferedImage;

import javax.swing.JProgressBar;

import Filters.Filter;

public class CalculatingThread extends Thread{
	
	private Filter f;
	private FilterFrame ff;
	private int value;
	private boolean calculating;
	BufferedImage output;
	BufferedImage input;
	
	public CalculatingThread(BufferedImage input, Filter f,FilterFrame ff) {
		this.ff=ff;
		this.f = f;
		this.input =input;
		setCalculating(false);
	}
	
	public void run() {
		setCalculating(true);
		output = f.applyFilter(input);
		ff.setBufOutput(output);
		setCalculating(false);
		
		System.out.println("while end");
	}

	public boolean isCalculating() {
		return calculating;
	}

	public void setCalculating(boolean calculating) {
		this.calculating = calculating;
	}

}
