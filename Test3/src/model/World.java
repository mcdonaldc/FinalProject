package model;

public class World {
	private double worldH,worldW;//world width and world height
		
	public World(double worldH, double worldW) {
		super();
		this.worldH = worldH;
		this.worldW = worldW;

	}

	public double getWorldH() {
		return worldH;
	}

	public double getWorldW() {
		return worldW;
	}
}
