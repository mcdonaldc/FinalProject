package view;

import java.awt.Point;

import math.Point2D;



public class CoordinateTranslator {
	private double worldW;
	private double worldH;
	private int screenW;
	private int screenH;

	public CoordinateTranslator(double worldW, double worldH, int screenW,
			int screenH, int wllx, int wlly) {
		super();
		this.worldW = worldW;
		this.worldH = worldH;
		this.screenW = screenW;
		this.screenH = screenH;
	}
	public Point2D screenToWorld(int x, int y){
		//shouldn't need to be casted
		double x1 = (double)((worldW/screenW)*x);
		double y1 = (double)((-(worldH/screenH)*y)+worldH);
		return new Point2D(x1,y1);
	}
	public Point2D screenToWorld(Point p){
		double x1 = (double)((worldW/screenW)*p.x);
		double y1 = (double)((-(worldH/screenH)*p.y)+worldH);
		return new Point2D(x1,y1);
	}
	public Point worldToScreen(double x, double y){
		double x1 = (double)(((x)*screenW)/worldW);
		double y1 = (double)((worldH-y)*(screenH/worldH));
		return new Point((int)x1,(int)y1);	
	}
	public Point worldToScreen(Point2D p){
		double x1 = (double)(((p.getX())*screenW)/worldW);
		double y1 = (double)((worldH-p.getY())*(screenH/worldH));
		return new Point((int)x1,(int)y1);	
	}
	public double getWorldW() {
		return worldW;
	}
	public void setWorldW(double worldW) {
		this.worldW = worldW;
	}
	public double getWorldH() {
		return worldH;
	}
	public void setWorldH(double worldH) {
		this.worldH = worldH;
	}
	public int getScreenW() {
		return screenW;
	}
	public void setScreenW(int screenW) {
		this.screenW = screenW;
	}
	public int getScreenH() {
		return screenH;
	}
	public void setScreenH(int screenH) {
		this.screenH = screenH;
	}
}

