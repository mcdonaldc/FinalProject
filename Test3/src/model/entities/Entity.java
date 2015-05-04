package model.entities;

import org.newdawn.slick.util.pathfinding.Mover;

import math.Point2D;
import math.Vector2D;
import model.World;
import model.managers.ConsoleLog;
import model.pathfinding.NavGraph;

public abstract class Entity implements Mover {
	private int id;
	private static int nextId;
	
	protected Point2D loc;
	public double direction;
	protected double radius;
	public World myWorld;
	
	protected NavGraph my_nav;
	
	public Entity(double initX, double initY, double direction,double r, World myWorld,
			NavGraph graph){
		loc = new Point2D(initX, initY);
		this.direction = direction;
		radius = r;
		this.myWorld = myWorld;
		my_nav = graph;
		//keep a unique id for every Entity
		id = (nextId++);	
	}
	public Entity(double initX, double initY, double r, World myWorld,
			NavGraph graph){
		loc = new Point2D(initX, initY);
		this.direction = 0;
		radius = r;
		this.myWorld = myWorld;
		my_nav = graph;
		//keep a unique id for every Entity
		id = (nextId++);
	}
	public abstract void update(int delta);
	
	public boolean isCollided(Entity e){
		double a = loc.getX() - e.getX();
		double b = loc.getY() - e.getY();
		double r = radius + e.getRadius();
		r = r*r;
		double d = a*a + b*b;
		if((r > d)){
			ConsoleLog.getInstance().log("Collision Detected: D^2=" + d + "R^2=" + r );
			return true;
		}
		return false;
	}
	//whether the entity contains the point
	public boolean contains(Point2D p){
		double a = loc.getX() - p.getX();
		double b = loc.getY() - p.getY();
		double r = radius * radius;
		double d = a*a + b*b;
		
		return ( r > d );
	}
	//Getters & Setters ---------------------------------------
	public double getX(){
		return loc.getX();
	}
	public double getY(){
		return loc.getY();
	}
	public void setX(double x){
		loc.setX(x);
	}
	public void setY(double y){
		loc.setY(y);
	}
	public Point2D getLoc() {
		return loc;
	}
	public void setLoc(Point2D loc) {
		this.loc.set(loc);
	}
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	public int getId() {
		return id;
	}
	public double getDirection(){
		return direction;
	}
	public World getWorld(){
		return myWorld;
	}
	public NavGraph getNav(){
		return my_nav;
	}
	public void resetToStart() {
		// TODO Auto-generated method stub
		
	}
}
