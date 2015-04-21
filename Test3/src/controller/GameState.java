package controller;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;

import math.Point2D;
import model.World;
import model.entities.Agent;
import model.entities.Entity;
import model.entities.Player;
import model.entities.Gem;
import model.managers.ConsoleLog;
import model.managers.EntityManager;
import model.pathfinding.NavGraph;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import view.ConsoleView;
import view.CoordinateTranslator;
import view.NavGV;
import view.SpriteRenderer;

public class GameState extends BasicGameState {

	private TiledMap map;

	private Camera camera;
	
	private boolean showLog;
	private boolean showPath;
	private boolean showGraph;
	private ConsoleView consoleView;

	private Music music;
	private World world;
	private Player player;
	private Gem prize;
	private CoordinateTranslator translator;
	private SpriteRenderer spriteRender;
	
	private String mapName;
	
	private NavGraph mygraph;
	private NavGV myview;
	private Image i;

	@Override
	public int getID() {
		return 0;
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
			throws SlickException {

		File file = new File("src/map_file.txt");
		String line;
		try (BufferedReader buffread = new BufferedReader(new FileReader(file))) {
			while ((line = buffread.readLine()) != null) {
			
				mapName = line;
			}
			try {
				buffread.close();
			} catch (IOException e1) {
			}
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		map = new TiledMap("res/"+mapName);

		camera = new Camera(gc, map);

		consoleView = new ConsoleView();
		world = new World(100, 100);
		mygraph = new NavGraph(map, world);
		myview = new NavGV(mygraph, map);
		translator = new CoordinateTranslator(world.getWorldW(),
		world.getWorldH(), camera.mapWidth, camera.mapHeight, (int)camera.cameraX, (int)camera.cameraY);
		spriteRender = new SpriteRenderer(translator);
		
		player = new Player(50, 50, .5, .01, world, mygraph);
		Agent agent1 = new Agent(40.0, 40.0, .75, .005, 25.0, world, mygraph);
		Agent agent2 = new Agent(50.0, 40.0, .75, .005, 25.0, world, mygraph);
		Agent agent3 = new Agent(60.0, 40.0, .75, .005, 25.0, world, mygraph);
		
		agent1.setTarget(player);
		agent2.setTarget(player);
		agent3.setTarget(player);
		
		prize = new Gem(50, 60, .5, world, mygraph);
		EntityManager e = EntityManager.getInstance();
		e.addEntity(player);
		e.addEntity(agent1);
		e.addEntity(agent2);
		e.addEntity(agent3);
		e.addEntity(prize);
		
		music = new Music("src/Super_Mario_Bros.ogg");

		music.setVolume(0.5f);

		music.loop();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {
		// first center camera on player
		Point p = translator.worldToScreen(player.getX(), player.getY());
		camera.centerOn((float) p.x, (float) p.y);
		camera.drawMap();
		// then draw all entities
		camera.translateGraphics();
		for (Entity e : EntityManager.getInstance()) {
			spriteRender.render(e, gc, g);
		}
		
		
		Color[] agent_colors = {Color.magenta, Color.black, Color.yellow};
		int cur = 0;
		for (Entity e : EntityManager.getInstance()) {
			spriteRender.render(e, gc, g);
			if(showPath && e instanceof Agent){
				myview.renderAgentPath((Agent)e, agent_colors[cur], gc, g);
				cur++;
			}
			if(showGraph && e instanceof Agent){
				myview.render(gc, g);
			}
			
		}
		camera.untranslateGraphics();
			
		// now draw all UI elements
		DecimalFormat df = new DecimalFormat("0.00");
		String x = df.format(player.getX());
		String y = df.format(player.getY());
		g.drawString("World Coordinates: (" + x + ", " + y + ")", 100, 0);
		x = df.format(p.x - camera.cameraX);
		y = df.format(p.y - camera.cameraY);
		g.drawString("Screen Coordinates: (" + x + ", " + y + ")", 400, 0);
		g.drawString("Gem located at: " +prize.getX()+ ", "+prize.getY(),200, 15);
		
		if(showLog)
		{
			consoleView.render(ConsoleLog.getInstance(),gc, g);
		}
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int t)
			throws SlickException {
		for (Entity e : EntityManager.getInstance()) {
			e.update(t);
		}
		spriteRender.updateAnims(t);
		//if player caught prize, move it to location on the screen
		if(prize.caught){
			
			int newX = (int) (map.getWidth() * Math.random()*32  /*camera.cameraX*/);
			int newY = (int)(map.getHeight() * Math.random()*32  /*camera.cameraY*/);
			Point2D newPoint = translator.screenToWorld(newX,newY);
			prize.setLoc(newPoint);
			prize.caught = false;
			
			if(player.getPoints() == 6){
				game.enterState(1);
			}
		}
		if(player.getDeaths() == 7){
			game.enterState(2);
		}
	}

	@Override
	public void keyPressed(int key, char c) {
		if(c== '1'){
			if(showGraph)
			{
				showGraph = false;
			}
			else{
				showGraph = true;
			}
		}
		
		if(c == '2'){
			if(showPath){
				showPath = false;
			}
			else{
				showPath = true;
		
			}
		}
		
		if (c == 'd') {
			try {
				i = new Image("res/mario_right.png");
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			spriteRender.setupSprites(i);
			player.moveRight = true;
		}
		if (c == 'a') {
			try {
				i = new Image("res/mario_left.png");
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			spriteRender.setupSprites(i);
			player.moveLeft = true;
		}
		if (c == 's') {
			try {
				i = new Image("res/mario_back.png");
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			spriteRender.setupSprites(i);
			player.moveDown = true;
		}
		if (c == 'w') {
			try {
				i = new Image("res/mario_forward.png");
			} catch (SlickException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			spriteRender.setupSprites(i);
			player.moveUp = true;
		}
		
		if (c == '`'){
			if(showLog){
				showLog = false;
			}else{
					showLog = true;
				
			}
		}
	}

	@Override
	public void keyReleased(int key, char c) {
		if (c == 'd') {
			player.moveRight = false;
		}
		if (c == 'a') {
			player.moveLeft = false;
		}
		if (c == 's') {
			player.moveDown = false;
		}
		if (c == 'w') {
			player.moveUp = false;
		}
	}

}
