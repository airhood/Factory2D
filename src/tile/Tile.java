package tile;

import java.awt.image.BufferedImage;

public class Tile<T> {
	
	public BufferedImage image;
	public boolean collision = false;
	
	public boolean interactive = false;
	
	public TileData<T> tileData;
	
	public boolean isAutomatedMachine = false;
	
	public Machine machine;
	
	public Tile() {
		
	}
	
	public Tile(TileData<T> tileData) {
		this.tileData = tileData;
	}
	
	public Tile(TileData<T> tileData, boolean isAutomatedMachine, Machine machine) {
		this.tileData = tileData;
		
		this.isAutomatedMachine = isAutomatedMachine;
		
		if (isAutomatedMachine) {
			this.machine = machine;
		} else {
			this.machine = null;
		}
	}
}
