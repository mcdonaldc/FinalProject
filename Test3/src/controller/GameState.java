package controller;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import math.Point2D;
import model.World;
import model.entities.Agent;
import model.entities.Entity;
import model.entities.Player;
import model.entities.Gem;
import model.entities.Tower;
import model.managers.ConsoleLog;
import model.managers.EntityManager;
import model.pathfinding.NavGraph;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.MouseListener;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.tiled.TiledMap;

import agentstates.EatState;
import agentstates.WanderState;
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

	private ArrayList<Agent> agents = new ArrayList<Agent>();
	
	private Music music;
	private World world;
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
		
		Agent agent1 = new Agent(78.0, 99.0, .75, .005, 25.0, world, mygraph);
		Agent agent2 = new Agent(78.0, 99.0, .75, .005, 25.0, world, mygraph);
		Agent agent3 = new Agent(78.0, 99.0, .75, .005, 25.0, world, mygraph);

		agents.add(agent1);
		agents.add(agent2);
		agents.add(agent3);
		
		EntityManager e = EntityManager.getInstance();
		e.addEntity(agent1);
		e.addEntity(agent2);
		e.addEntity(agent3);
		
		
		//music = new Music("src/Super_Mario_Bros.ogg");

		//music.setVolume(0.5f);

		//music.loop();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g)
			throws SlickException {

		Point p = translator.worldToScreen(world.getWorldW(), world.getWorldH());
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
			
		}
		camera.untranslateGraphics();
			
		if(showLog)
		{
			consoleView.render(ConsoleLog.getInstance(),gc, g);
		}
	}

	
	@Override
	public void update(GameContainer gc, StateBasedGame game, int t)
			throws SlickException {
		
		if(Mouse.isButtonDown(0))
		{
			int x = Mouse.getX();
			int y = Mouse.getY();
			
			Point2D p = translator.screenToWorld(x, y);
			double distanceToAdd = 0;
			distanceToAdd = p.getY() - 81;
			
			EntityManager.getInstance().addEntity(new Tower(75+p.getX(), 100-distanceToAdd, .75, 25, world, mygraph));
		}
		
		for (Entity e : EntityManager.getInstance()) {
			
			e.update(t);
		}
		
		for(Agent a : agents)
		{
			if(a.getY() < 82)
			{
				a.setLoc(new Point2D(78.0, 99.0));
				a.getMy_state_machine().ChangeState(new EatState());
				a.numPasses++;
			}
			
			
		}
		spriteRender.updateAnims(t);
		
		
	}

	@Override
	public void keyPressed(int key, char c) {
		
		if(c == '2'){
			if(showPath){
				showPath = false;
			}
			else{
				showPath = true;
		
			}
		}
		
		if (c == '`'){
			if(showLog){
				showLog = false;
			}else{
					showLog = true;
				
			}
		}
	}



}
