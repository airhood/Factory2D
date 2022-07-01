package main;

import entity.Direction;
import entity.Entity;

public class CollisionChecker {
	
	GamePanel gamePanel;
	
	public CollisionChecker(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void checkTile(Entity entity, Direction[] movingDirections, int estimatedWorldX, int estimatedWorldY) {
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
		int entityRightCol = entityRightWorldX / gamePanel.tileSize;
		int entityTopRow = entityTopWorldY / gamePanel.tileSize;
		int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;
		
		int tileNum1, tileNum2;
		
		boolean doubleCollision = false;
		
		
		try {
			switch(movingDirections[0]) {
				case up:
					entityTopRow = (entityTopWorldY - (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
					
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.up;
					}
					
					doubleCollision = true;
					break;
				case down:
					entityBottomRow = (entityBottomWorldY + (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
					
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.down;
					}
					
					doubleCollision = true;
					break;
				case left:
					entityLeftCol = (entityLeftWorldX - (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
					
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.left;
					}
				
					doubleCollision = true;
					break;
				case right:
					entityRightCol = (entityRightWorldX + (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
					
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.right;
					}
					
					doubleCollision = true;
					break;
				default:
					entity.collisionOnDirections[0] = null;
					entity.collisionOnDirections[1] = null;
					return;
			}
		
			if (movingDirections[1] == null) return;
		
			switch(movingDirections[1]) {
				case up:
					entityTopRow = (entityTopWorldY - (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						if (doubleCollision) {
						entity.collisionOnDirections[1] = Direction.up;
						} else {
							entity.collisionOnDirections[0] = Direction.up;
						entity.collisionOnDirections[1] = null;
						}
					}
				
					break;
				case down:
					entityTopRow = (entityBottomWorldY - (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						if (doubleCollision) {
							entity.collisionOnDirections[1] = Direction.down;
						} else {
							entity.collisionOnDirections[0] = Direction.down;
							entity.collisionOnDirections[1] = null;
						}
					}
					
					break;
				case left:
					entityTopRow = (entityTopWorldY - (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						if (doubleCollision) {
							entity.collisionOnDirections[1] = Direction.left;
						} else {
							entity.collisionOnDirections[0] = Direction.left;
							entity.collisionOnDirections[1] = null;
						}
					}
					
					break;
				case right:
					entityTopRow = (entityTopWorldY - (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						if (doubleCollision) {
							entity.collisionOnDirections[1] = Direction.right;
						} else {
							entity.collisionOnDirections[0] = Direction.right;
							entity.collisionOnDirections[1] = null;
						}
					}
					
					break;
				default:
					return;
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
	}
}
