package model.entities;

import math.Point2D;
import math.Vector2D;
import model.World;
import model.pathfinding.NavGraph;

public abstract class MovingEntity extends Entity {
	
	protected double speed;
	protected Vector2D velocity;
	
	public MovingEntity(double initX, double initY, double r, double speed, World myWorld,
			NavGraph graph) {
		super(initX, initY,r, myWorld, graph);
		this.speed = speed;
		this.velocity = new Vector2D(0,0);
	}
	public void move(Vector2D v, int delta){
		velocity = v;
		if(v.magnitude() > Vector2D.TOL){
			v.normalize();
			velocity = v.times(speed * delta);
			Point2D newLoc = new Point2D(loc.getX() + velocity.getX(),loc.getY() + velocity.getY());
			
			if(!my_nav.blocked(newLoc)){
				loc.set(newLoc);
			}
			direction = velocity.angle();
		}
	}
	public Vector2D getVelocity() {
		return velocity;
	}
	public double getSpeed(){
		return speed;
	}
	public void setSpeed(double speed) {
		this.speed = speed;
	}
}
