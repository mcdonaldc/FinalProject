package model.entities;

import java.util.ArrayList;

import model.World;
import model.pathfinding.NavGraph;

public class Tower extends Entity{
	
	private Entity target;
	public double sight;
	public ArrayList<Ammunition> ammo;
	

	public Tower(double initX, double initY, double r, double sight, World myWorld, NavGraph graph) {
		super(initX, initY, r, myWorld, graph);
		this.sight = sight;
		ammo = new ArrayList<Ammunition>();
		
		for(int i =0; i<20; i++)
		{
			Ammunition a = new Ammunition(initX+.75, initY+.75, .25, .005, .01, myWorld, graph);
			ammo.add(a);
		}
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
