package model.entities;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.TileBasedMap;

import math.Point2D;
import math.Vector2D;
import model.World;
import agentstates.AgentChaseTarget;
import agentstates.AgentStateMachine;
import agentstates.EatState;
import model.pathfinding.NavGraph;
import model.pathfinding.PathFinderHolder;

public class Agent extends MovingEntity {
	private Entity target;
	private Point2D startLoc;
	private AgentStateMachine my_state_machine;
	
	public int health;
	public int numPasses;
	public int hunger;
	public double sight;
	private PathFinderHolder pathing;
	
	public Agent(double initX, double initY, double r, double velocity, double sight, World myWorld,
			NavGraph graph) {
		super(initX, initY, r, velocity, myWorld, graph);
		startLoc = new Point2D(initX, initY);
		my_state_machine = new AgentStateMachine(this);
		numPasses = 0;
		health = 8000;
		setPathing(new PathFinderHolder(new AStarPathFinder(my_nav , Integer.MAX_VALUE, true), this));
		this.sight = sight;
		my_state_machine.ChangeState(new EatState());
		
	}

	@Override
	public void update(int delta) {
		my_state_machine.update(delta);
	}

	public Entity getTarget() {
		return target;
	}

	public void setTarget(Entity target) {
		this.target = target;
	}

	public Point2D getStartLoc() {
		return startLoc;
	}

	public AgentStateMachine getMy_state_machine() {
		return my_state_machine;
	}

	public PathFinderHolder getPathing() {
		return pathing;
	}


	public void setPathing(PathFinderHolder pathing) {
		this.pathing = pathing;
	}
}
