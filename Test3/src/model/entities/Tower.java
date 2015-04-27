package model.entities;

import model.World;
import model.pathfinding.NavGraph;

public class Tower extends Entity{
	
	private Entity target;
	public double sight;
	

	public Tower(double initX, double initY, double r, double sight, World myWorld, NavGraph graph) {
		super(initX, initY, r, myWorld, graph);
		this.sight = sight;
		
	}

	public Entity getTarget() {
		return target;
	}
	
	public void setTarget(Entity target) {
		this.target = target;
	}


	@Override
	public void update(int delta) {
		
	}



}
