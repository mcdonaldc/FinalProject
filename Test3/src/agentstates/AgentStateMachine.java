package agentstates;

import model.entities.Agent;

public class AgentStateMachine {

	private AgentState currentstate;
	

	
	private Agent cur_agent;
	
	public AgentStateMachine(Agent agent)
	{
		cur_agent = agent;
		currentstate = new AgentChaseTarget();
	}
	
	public void SetCurrentState(AgentState as)
	{
		currentstate = as;
	}
	public void update(int delta)
	{	if(cur_agent != null)
		{
			currentstate.execute(cur_agent, delta);
		}
	}
	public void ChangeState(AgentState as)
	{
		if(as == null)
		{
			return;
		}
		
		currentstate.exit(cur_agent);
		
		currentstate = as;
		
		currentstate.enter(cur_agent);
		
		
	}
	
}
