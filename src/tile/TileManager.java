package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import main.GamePanel;

public class TileManager {
	
	GamePanel gamePanel;
	public Tile[] tile;
	String[] tileImageFileName = { "earth.png", "floor01.png", "grass00.png", "grass01.png", "hut.png", "road00.png", "road01.png", "road02.png", "road03.png", "road04.png", "road05.png", "road06.png", "road07.png", "road08.png", "road09.png", "road10.png", "road11.png", "road12.png", "table01.png", "tree.png", "wall.png", "water00.png", "water01.png", "water02.png", "water03.png", "water04.png", "water05.png", "water06.png", "water07.png", "water08.png", "water09.png", "water10.png", "water11.png", "water12.png", "water13.png"};
	public int mapTileNum[][];
	
	public final int tileAmount = 35;
	
	public int renderDistance = 1;
	
	public int worldMidX;
	public int worldMidY;
	
	public TileManager(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		tile = new Tile[tileAmount];
		mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];
		
		getTileImage();
		loadMap("/world01.map");
	}
	
	public void getTileImage() {
		
		try {
			InputStream inputStream = getClass().getResourceAsStream("/tiles/tileMeta.meta");
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			
			for (int n = 0; n < tileAmount; n++){
				
				String line = bufferedReader.readLine();
				
				if (line != null) {
					String variables[] = line.split(" ");
					
					tileImageFileName[n] = variables[0];
					
					tile[n] = new Tile();
					
					
					if (variables[1].equals("0")) {
						tile[n].collision = false;
					} else if (variables[1].equals("1")) {
						tile[n].collision = true;
					} else {
						System.out.println("tile load failed: index -> " + n);
						tile[n].collision = false;
					}
				}
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			for(int i = 0; i < 35; i++)
			{
				tile[i].image = ImageIO.read(getClass().getResourceAsStream("/tiles/" + tileImageFileName[i]));
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadMap(String filePath) {
		try {
			if (filePath.charAt(0) != '/') {
				filePath = "/" + filePath;
			}
			
			InputStream inputStream = getClass().getResourceAsStream("/maps" + filePath);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
			
			int col = 0;
			int row = 0;
			
			boolean mapVarSet = false;
			
			while(col < gamePanel.maxWorldCol && row < gamePanel.maxWorldRow) {
				String line = bufferedReader.readLine();
				
				if (line != null) {
					String variables[] = line.split(" ");
					
					if (variables[0].equals("###")) {
						if (!mapVarSet) mapVarSet = true;
						else mapVarSet = false;
					} else if (mapVarSet && variables[0].charAt(0) == '$') {
						String mapVarName = variables[0].replace("$", "");
						
						switch(mapVarName) {
							case "spawn":
								gamePanel.player.worldSpawnX = Integer.parseInt(variables[1]);
								gamePanel.player.worldSpawnY = Integer.parseInt(variables[2]);
								break;
							case "worldMid":
								worldMidX = Integer.parseInt(variables[1]);
								worldMidY = Integer.parseInt(variables[2]);
								break;
						}
					} else {
						String numbers[] = line.split(" ");
						
						while(col < gamePanel.maxWorldCol) {
							if (!numbers[col].equals("")) {
								int num = Integer.parseInt(numbers[col]);
								
								mapTileNum[col][row] = num;
								col++;
							}
						}
						
						if (col == gamePanel.maxWorldCol) {
							col = 0;
							row++;
						}
					}
				}
			}
			bufferedReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void draw(Graphics2D g2) {
		int worldCol = 0;
		int worldRow = 0;
		
		
		while(worldCol < gamePanel.maxWorldCol && worldRow < gamePanel.maxWorldRow) {
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * gamePanel.tileSize;
			int worldY = worldRow * gamePanel.tileSize;
			int screenX = worldX - (gamePanel.player.worldX * (-1)) + gamePanel.player.screenX;
			int screenY = worldY - (gamePanel.player.worldY * (-1)) + gamePanel.player.screenY;
			
			int outLineRenderX = ((renderDistance - 1) * gamePanel.maxScreenCol)+ (1 * gamePanel.tileSize);
			int outLineRenderY = ((renderDistance - 1) * gamePanel.maxScreenRow) + (1 * gamePanel.tileSize);
			
			
			if (worldX + outLineRenderX > (gamePanel.player.worldX * (-1)) - gamePanel.player.screenX &&
					worldX - outLineRenderX < (gamePanel.player.worldX * (-1)) + gamePanel.player.screenX &&
					worldY  + outLineRenderY > (gamePanel.player.worldY * (-1)) - gamePanel.player.screenY &&
					worldY - outLineRenderY < (gamePanel.player.worldY * (-1)) + gamePanel.player.screenY) {
				g2.drawImage(tile[tileNum - 1].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
			}
			
			
			worldCol++;
			
			if (worldCol == gamePanel.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
		}
	}
}
