package model.entities;

import model.World;
import model.pathfinding.NavGraph;

public class Gem extends Entity {
	
	public boolean caught;
	
	public Gem(double initX, double initY, double r, World myWorld, NavGraph graph) {
		super(initX, initY, r, myWorld, graph);

	}

	@Override
	public void update(int delta) {

	}

	public void catchIt() {
		caught = true;

	}
	
	
}
