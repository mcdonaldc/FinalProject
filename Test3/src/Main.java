import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.SlickException;

import controller.Game;


public class Main {

	public static void main(String[] args) {
		try {
			AppGameContainer app = new AppGameContainer(new Game());
			app.setDisplayMode(800,600,false);
			app.setTargetFrameRate(60);
			app.start();
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
