package agentstates;

import java.awt.Point;

import math.Point2D;
import math.Vector2D;
import model.entities.Agent;
import model.managers.ConsoleLog;

public class WanderState extends AgentState {

	@Override
	public void enter(Agent e) {

		Point p = e.getNav().getRandomOpenTile();
		

		e.getPathing().generatePath(p);
	}

	@Override
	public void execute(Agent e, int delta) {

		if(e.getPathing().done()){

			Point p = e.getNav().getRandomOpenTile();

			e.getPathing().generatePath(p);
		}
		
		//e.fatigue -= delta;
		e.hunger -= delta;
		
		//if(e.fatigue < 0){
			//e.getMy_state_machine().ChangeState(new RestState());
		//}
		
		if(e.hunger < 0){
			e.getMy_state_machine().ChangeState(new EatState());
		}	
		
		e.getPathing().walkPath(delta);
	}

	@Override
	public Vector2D computeHeading(Agent e) {
	
		return new Vector2D(0,0);
	}

	@Override
	public void exit(Agent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetToStart(Agent e) {
		// Pick a random location to wander
		Point p = e.getNav().getRandomOpenTile();
	
		e.getPathing().generatePath(p);
	}

}
