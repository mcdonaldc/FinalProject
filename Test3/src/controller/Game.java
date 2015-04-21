package controller;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Game extends StateBasedGame {
	
		public Game ()
	{
		super("Game");
	}

		@Override
		public void initStatesList(GameContainer gc) throws SlickException {
			// TODO Auto-generated method stub
			addState(new GameState());
			addState(new VictoryState());
			addState(new FailState());
		}
}
