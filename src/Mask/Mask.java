package Mask;

import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

public class Mask {
	int imageSize = 70;
	String name;
	int length;
	int height;
	boolean[][] matrix;
	ImageIcon icon;
	
	public ImageIcon getIcon() {
		return icon;
	}

	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}

	public Mask(String name,int length,	int width,	boolean[][] matrix){
		this.name=name;
		this.length=length;
		this.height=width;
		this.matrix =matrix;
		createIcon(matrix);
	}
	
	public Mask(String name, boolean[][] matrix){
		this.name = name;
		this.matrix = matrix;
		this.length=matrix.length;
		this.height=matrix[0].length;
		createIcon(matrix);
	}
	
	private void createIcon(boolean[][] matrix){
		System.out.println("***CREATE ICON***");
		BufferedImage bi= new BufferedImage(length, height, BufferedImage.TYPE_INT_RGB);
		Color red = new Color(255, 0, 0);
		int r = red.getRGB();
		Color white = new Color(200, 200, 200);
		int w = white.getRGB();
		for (int i = 0;i<length;i++){
			for (int j=0;j<height;j++){
				if (matrix[i][j]){
					bi.setRGB(i, j, r);
				}
				else{
					bi.setRGB(i, j, w);
				}
			}
		}
		float ratio = (float)height/length;
		Image scaledIm = bi.getScaledInstance(imageSize, (int) (imageSize*ratio), Image.SCALE_SMOOTH);
		icon=new ImageIcon(scaledIm);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getLength() {
		return length;
	}
	public void setLength(int length) {
		this.length = length;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public boolean[][] getMatrix() {
		return matrix;
	}
	public void setMatrix(boolean[][] matrix) {
		this.matrix = matrix;
	}
	
	
	
}
