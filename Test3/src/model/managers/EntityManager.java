package model.managers;

import java.util.ArrayList;
import java.util.Iterator;

import model.entities.Entity;


public class EntityManager implements Iterable<Entity>{
	private static EntityManager em;
	private ArrayList<Entity> entities;
	
	public static EntityManager getInstance(){
		if(em == null){
			em = new EntityManager();
		}
		return em;
	}

	
	private EntityManager() {
		super();
		this.entities = new ArrayList<Entity>();
	}
	
	public void addEntity( Entity e){
		entities.add(e);
	}
	public void addEntity(int pos, Entity e){
		if(pos < 0){
			entities.add(e);
		}else{
			entities.add(pos, e);
		}
	}
	@Override
	public Iterator<Entity> iterator() {
		return entities.iterator();
	}

}
