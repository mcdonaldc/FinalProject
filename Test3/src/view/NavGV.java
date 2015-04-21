package view;

import java.awt.Point;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.tiled.TiledMap;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

import model.entities.Agent;
import model.pathfinding.NavGraph;

public class NavGV {
	private NavGraph nav;
	private TiledMap map;
	
	public NavGV(NavGraph nav, TiledMap map) {
		super();
		this.nav = nav;
		this.map = map;
	}
	public void render(GameContainer gc, Graphics g){
		for(int ty = 0; ty < map.getHeight(); ty++){
			for(int tx = 0; tx < map.getWidth(); tx++){
				int cur_x = tx * map.getTileWidth() + map.getTileWidth()/2;
				int cur_y = ty * map.getTileHeight() + map.getTileHeight()/2;
				if(!nav.blocked(null,tx,ty)){
					for(Point p: getNeighbors(tx, ty)){
						int nx = p.x * map.getTileWidth() + map.getTileWidth()/2;
						int ny = p.y * map.getTileHeight() + map.getTileHeight()/2;
						g.drawLine(cur_x, cur_y, nx, ny);
						//System.out.println("Line: " + cur_x + " " + cur_y+  " " + nx + " " + ny);
					}
				}
			}
		}	
	}
	public void renderAgentPath(Agent e, Color c, GameContainer gc, Graphics g){
		Path p = e.getPathing().getCur_path();
		if(p != null){
			Color last = g.getColor();
			g.setColor(c);
			for(int i=0; i<p.getLength()-1;i++){
				Step s = p.getStep(i);
				int cur_x = s.getX() * map.getTileWidth() + map.getTileWidth()/2;
				int cur_y = s.getY() * map.getTileHeight() + map.getTileHeight()/2;
				
				Step s2 = p.getStep(i+1);
				int nex_x = s2.getX() * map.getTileWidth() + map.getTileWidth()/2;
				int nex_y = s2.getY() * map.getTileHeight() + map.getTileHeight()/2;
				g.drawLine(cur_x, cur_y, nex_x, nex_y);
			}
			g.setColor(last);
		}
	}
	// Returns list starting at NW and going seq across each row
	public ArrayList<Point> getNeighbors(int x, int y){
		//System.out.println(x + " " + y);
		ArrayList<Point> neighbors = new ArrayList<Point>();
		for(int ny=1; ny > -2; ny--){
			for(int nx=-1; nx < 2; nx++){
				//make sure stay in bounds
				if(nx+x > -1 && nx+x < map.getWidth() && ny+y > -1 && ny+y < map.getHeight()){
					//System.out.println((nx + x) + " " + (ny + y));
					if(!nav.blocked(x, y, nx+x, ny+y)){
						neighbors.add(new Point(nx+x, ny+y));
					}
				}
			}
		}
		return neighbors;
	}
}
