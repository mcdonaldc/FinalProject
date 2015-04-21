package model.pathfinding;

import java.awt.Point;

import math.Point2D;
import math.Vector2D;
import model.entities.Agent;

import org.newdawn.slick.util.pathfinding.AStarPathFinder;
import org.newdawn.slick.util.pathfinding.Path;
import org.newdawn.slick.util.pathfinding.Path.Step;

public class PathFinderHolder {
	private AStarPathFinder path_finder;
	private Path currentPath;
	private int currrentStep;
	private Agent a;
	
	public PathFinderHolder(AStarPathFinder path_finder, Agent e){
		this.path_finder = path_finder;
		this.a = e;
	}
	public void generatePath(Point2D dest){
		Point cur_tile = a.getNav().worldToTile(a.getLoc());
		Point dest_tile = a.getNav().worldToTile(dest);
		currentPath = path_finder.findPath(a, cur_tile.x, cur_tile.y, dest_tile.x, dest_tile.y);


		currrentStep = 0;
	}
	public void generatePath(Point p) {
		Point cur_tile = a.getNav().worldToTile(a.getLoc());
		currentPath = path_finder.findPath(a, cur_tile.x, cur_tile.y, p.x, p.y);

		currrentStep = 0;
	}
	
	
	public Path getOpenStraightPath(Point tail, Point head){
		//System.out.println("tail:" + tail + " head:" + head );
		Path strP = new Path();
		strP.appendStep(tail.x, tail.y);
		int x_dif = tail.x - head.x;
		int y_dif = tail.y - head.y;
		//System.out.println(x_dif + " " + y_dif);
		while(x_dif != 0 || y_dif != 0){
			//System.out.println("x: " + tail.x + " y: " + tail.y);
			int cx = tail.x;
			int cy = tail.y;
			
			//get one tile closer
			tail.x -= Integer.signum(x_dif);
			tail.y -= Integer.signum(y_dif);
			if(a.getNav().blocked(cx, cy, tail.x, tail.y)){
				return null;
			}
			strP.appendStep(tail.x, tail.y);
			x_dif -= Integer.signum(x_dif);
			y_dif -= Integer.signum(y_dif);
		}
		return strP;
	}
	public void walkPath(int delta){
		if(currentPath == null || currrentStep >= currentPath.getLength()){
			return;
		}
		Step curStep = currentPath.getStep(currrentStep);
		Point2D curDest = a.getNav().tileToWorld(curStep.getX(), curStep.getY());
		//System.out.println(curStep.getX() + " " + curStep.getY());
		
		// Move to next location
		Vector2D move_vect = new Vector2D(curDest.getX(), curDest.getY());
		//System.out.println(move_vect);
		move_vect.scalePlusEquals(-1, new Vector2D(a.getLoc().getX(), a.getLoc().getY()));
		//System.out.println(move_vect);
		a.move(move_vect, delta);
		
		// If at location, move to next step
		if(a.contains(curDest)){
			//System.out.println("next step");
			currrentStep++;
		}
	}
	//Start Getters/Setters -------------
	public Path getCur_path() {
		return currentPath;
	}

	public void setCur_path(Path cur_path) {
		this.currentPath = cur_path;
	}

	public AStarPathFinder getPath_finder() {
		return path_finder;
	}

	public void setPath_finder(AStarPathFinder path_finder) {
		this.path_finder = path_finder;
	}
	public boolean done() {
		if(currentPath == null){
			return true;
		}
		return (currrentStep >= currentPath.getLength());
	}
}
