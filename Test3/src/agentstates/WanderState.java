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
		
		e.fatigue -= delta;
		e.hunger -= delta;
		
		if(e.fatigue < 0){
			e.getMy_state_machine().ChangeState(new RestState());
		}
		
		if(e.hunger < 0){
			e.getMy_state_machine().ChangeState(new EatState());
		}	
		
		Point2D target_loc = e.getTarget().getLoc();
		Vector2D d_p = target_loc.minus(e.getLoc());
		if(d_p.dot(d_p) <= (e.sight * e.sight)){
			Vector2D a_view = e.getVelocity();
			if(a_view.dot(d_p) >= 0){
				if(e.getPathing().getOpenStraightPath(e.getNav().worldToTile(e.getLoc()), e.getNav().worldToTile(target_loc)) != null){
					
					ConsoleLog.getInstance().log("Found player at angle: " + Math.acos(a_view.getNormalized().dot(d_p.getNormalized())));
					e.getMy_state_machine().ChangeState(new AgentChaseTarget());
				}
			}	
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
