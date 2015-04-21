package agentstates;

import math.Vector2D;
import model.entities.Agent;

public abstract class AgentState {
	
	public abstract void exit(Agent a);


	public abstract void enter(Agent a);


	public abstract void execute(Agent a, int delta);
	
	
	public abstract Vector2D computeHeading(Agent a);


	public void resetToStart(Agent e) {
		// TODO Auto-generated method stub
		
	}

	
}
