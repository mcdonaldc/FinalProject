package model.entities;

import math.Point2D;
import math.Vector2D;
import model.World;
import model.pathfinding.NavGraph;

public class Ammunition extends MovingEntity {
	
	private Entity target;
	public double speed;
	public Vector2D velocity;
	
	public Point2D origLoc;

	public Ammunition(double initX, double initY, double r, double velocity, double speed,
			World myWorld, NavGraph graph) {
		super(initX, initY, r, velocity, myWorld, graph);
		this.speed = speed;
		this.velocity = new Vector2D(0,0);
		origLoc = new Point2D(initX, initY);

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
	
	@Override
	public void update(int delta) {
		// TODO Auto-generated method stub
		
	}
	
	public Entity getTarget() {
		return target;
	}
	
	public void setTarget(Entity target) {
		this.target = target;
	}


}
