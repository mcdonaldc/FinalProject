package view;

import java.awt.Point;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;




import model.entities.Agent;
import model.entities.Entity;
import model.entities.Player;
import model.entities.Gem;

public class SpriteRenderer {
	
	private CoordinateTranslator convert;
	private Animation mario;
	private Animation goomba;
	private Image playerImg;
	private Image agentImg;
	private Image prizeImg;
	
	public SpriteRenderer(CoordinateTranslator convert){
		this.convert = convert;
		try {
			playerImg = new Image("/res/mario_forward.png");
			agentImg = new Image("/res/goomba_move.png");
			prizeImg = new Image("/res/gem.png");
			
			mario = getAnimation(playerImg, 3, 1, 45, 57, 3, 150);
			goomba = getAnimation(agentImg,3, 1, 22, 25, 3, 150);
			
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	
	public void setupSprites(Image i)
	{
		playerImg = i;
		mario = getAnimation(playerImg, 3, 1, 45, 57, 3, 150);
	}
	
	public void render(Entity e, GameContainer gc, Graphics g){
		if(e instanceof Player){
			renderH((Player)e, gc, g);
		}else if(e instanceof Agent){
			renderH((Agent)e,gc,g);
		}else if(e instanceof Gem){
			renderH((Gem)e, gc, g);
		}
	}

	private void drawChar(Image i, Entity e){
		Point pc = convert.worldToScreen(e.getX(), e.getY());
		i.draw(((float) pc.x)-20, ((float) pc.y)-20, 40, 40);
		
	}
	
	public void updateAnims(int t)
	{
		goomba.update(t);
		mario.update(t);
	}
	
	private void drawEnemy(Image i, Entity e){
		Point pc = convert.worldToScreen(e.getX(), e.getY());
		
		float rotation = (float) Math.toDegrees(e.getDirection());
		i.setRotation(- rotation);
		i.draw(((float) pc.x)-25, ((float) pc.y)-25, 50, 50);
		
	}
	
	private void renderH(Agent e, GameContainer gc, Graphics g) {
		drawEnemy(goomba.getCurrentFrame(),e);
	}
	private void renderH(Player e, GameContainer gc, Graphics g) {
		drawChar(mario.getCurrentFrame(), e);
	}
	private void renderH(Gem e, GameContainer gc, Graphics g) {
		drawChar(prizeImg, e);
	}
	
	public Animation getAnimation (Image i , int spX, int spY , int sW , int sH, int frames, int dur)
	{
		Animation a = new Animation(false);
		
		int c = 0;
		for(int y = 0 ; y < spY; y++)
		{
			for(int x = 0 ; x < spX; x++)
			{
				if(c < frames) a.addFrame(i.getSubImage(x*sW, y*sH, sW, sH), dur);
				c++;
			}
		}
		
		return a;
	}
	
}
