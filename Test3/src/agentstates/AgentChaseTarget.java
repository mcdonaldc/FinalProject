package agentstates;

import math.Vector2D;
import model.entities.Agent;

public class AgentChaseTarget extends AgentState {
	
	@Override
	public void enter(Agent e) {
		// clear the agents path, as it doesn't exist anymore
		e.getPathing().setCur_path(null);
	}

	@Override
	public void execute(Agent e, int delta) {
		// get hungrier and more tired
		e.fatigue -= delta;
		e.hunger -= delta;
		
	
		if(e.fatigue < 0){
			e.getMy_state_machine().ChangeState(new RestState());
		}
		
		
		if(e.hunger < 0){
			e.getMy_state_machine().ChangeState(new EatState());
		}	
		//move in the direction
		e.move(computeHeading(e), delta);
	}

	@Override
	public Vector2D computeHeading(Agent e) {
		return  new Vector2D(
				e.getTarget().getX() - e.getLoc().getX(), //x
				e.getTarget().getY() - e.getLoc().getY()); //y
	}

	@Override
	public void exit(Agent e) {
	
		
	}

	@Override
	public void resetToStart(Agent e) {
	
		
	}

}
