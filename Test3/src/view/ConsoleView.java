package view;

import java.util.Iterator;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

import model.managers.ConsoleLog;

public class ConsoleView {
	
	public void render(ConsoleLog log, GameContainer gc, Graphics g){
		Shape rect = new Rectangle(20, 20, gc.getWidth()/2, gc.getHeight()*3/4 );
		render(log,gc,g,rect);
	}
	
	//should probably switch this to use a pane, or frame or something for the console...
	public void render(ConsoleLog log, GameContainer gc, Graphics g, Shape s){
		//changing color from default, so change it back before done
		Color c = g.getColor();
		//draw the box area
		Color newColor = new Color(.25f,.25f,.25f,.5f);
		g.setColor(newColor);
		g.fill(s);
		//reset color for writing text
		g.setColor(c);
		//figure out how many messages fit in console
		int lines = (int) (s.getHeight()/20);
		int offset = 0;
		for(Iterator<String> it = log.getLastMessages(lines); it.hasNext(); ){
			g.drawString(it.next(), s.getX() + 5, s.getY() + 20*offset);
			offset++;
		}
	}
}
