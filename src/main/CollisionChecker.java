package main;

import entity.Direction;
import entity.Entity;

public class CollisionChecker {
	
	GamePanel gamePanel;
	
	public CollisionChecker(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	public void checkTile(Entity entity, Direction[] movingDirections, int estimatedWorldX, int estimatedWorldY) {
		int entityLeftWorldX = entity.systemWorldX + entity.solidArea.x;
		int entityRightWorldX = entity.systemWorldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.systemWorldY + entity.solidArea.y;
		int entityBottomWorldY = entity.systemWorldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
		int entityRightCol = entityRightWorldX / gamePanel.tileSize;
		int entityTopRow = entityTopWorldY / gamePanel.tileSize;
		int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;
		
		int tileNum1, tileNum2;
		
		boolean firstCollisionDone = false;
		
		boolean doubleCollision = false;
		
		boolean twoTouch[] = new boolean[2];
		
		
		try {
			switch(movingDirections[0]) {
				case up:
					entityTopRow = (entityTopWorldY - (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
					
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.up;
						twoTouch[0] = true;
					} else if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.up;
						twoTouch[0] = false;
					}
					
					firstCollisionDone = true;
					break;
				case down:
					entityBottomRow = (entityBottomWorldY + (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
					
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.down;
						twoTouch[0] = true;
					} else if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.down;
						twoTouch[0] = false;
					}
					
					firstCollisionDone = true;
					break;
				case left:
					entityLeftCol = (entityLeftWorldX - (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
					
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.left;
						twoTouch[0] = true;
					} else if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.left;
						twoTouch[0] = false;
					}
				
					firstCollisionDone = true;
					break;
				case right:
					entityRightCol = (entityRightWorldX + (int)entity.speed) / gamePanel.tileSize;
					tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
					tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
					
					if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.right;
						twoTouch[0] = true;
					} else if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
						entity.collisionOnDirections[0] = Direction.right;
						twoTouch[0] = false;
					}
					
					firstCollisionDone = true;
					break;
				default:
					entity.collisionOnDirections[0] = null;
					entity.collisionOnDirections[1] = null;
					return;
			}
		
			if (movingDirections[1] != null) {
				switch(movingDirections[1]) {
					case up:
						entityTopRow = (entityTopWorldY - (int)entity.speed) / gamePanel.tileSize;
						tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
						tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
						if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							if (firstCollisionDone) {
								entity.collisionOnDirections[1] = Direction.up;
							} else {
								entity.collisionOnDirections[0] = Direction.up;
								entity.collisionOnDirections[1] = null;
							}
							
							twoTouch[1] = true;
						} else if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							if (firstCollisionDone) {
								entity.collisionOnDirections[1] = Direction.up;
							} else {
								entity.collisionOnDirections[0] = Direction.up;
								entity.collisionOnDirections[1] = null;
							}
							
							twoTouch[1] = false;
						}
						
						doubleCollision = true;						
						break;
					case down:
						entityTopRow = (entityBottomWorldY - (int)entity.speed) / gamePanel.tileSize;
						tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
						tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
						if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							if (firstCollisionDone) {
								entity.collisionOnDirections[1] = Direction.down;
							} else {
								entity.collisionOnDirections[0] = Direction.down;
								entity.collisionOnDirections[1] = null;
							}
							
							twoTouch[1] = true;
						} else if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							if (firstCollisionDone) {
								entity.collisionOnDirections[1] = Direction.down;
						} else {
								entity.collisionOnDirections[0] = Direction.down;
								entity.collisionOnDirections[1] = null;
							}
							
							twoTouch[1] = false;
						}
						
						doubleCollision = true;
						break;
					case left:
						entityTopRow = (entityTopWorldY - (int)entity.speed) / gamePanel.tileSize;
						tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
						tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
						if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							if (doubleCollision) {
								entity.collisionOnDirections[1] = Direction.left;
							} else {
								entity.collisionOnDirections[0] = Direction.left;
								entity.collisionOnDirections[1] = null;
							}
							
							twoTouch[1] = true;
						} else if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							if (doubleCollision) {
								entity.collisionOnDirections[1] = Direction.left;
							} else {
								entity.collisionOnDirections[0] = Direction.left;
								entity.collisionOnDirections[1] = null;
							}
							
							twoTouch[1] = false;
						}
						
						doubleCollision = true;
						break;
					case right:
						entityTopRow = (entityTopWorldY - (int)entity.speed) / gamePanel.tileSize;
						tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
						tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
						if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							if (doubleCollision) {
								entity.collisionOnDirections[1] = Direction.right;
							} else {
								entity.collisionOnDirections[0] = Direction.right;
								entity.collisionOnDirections[1] = null;
							}
							
							twoTouch[1] = true;
						} else if (gamePanel.tileManager.tile[tileNum1 - 1].collision || gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							if (doubleCollision) {
								entity.collisionOnDirections[1] = Direction.right;
							} else {
								entity.collisionOnDirections[0] = Direction.right;
								entity.collisionOnDirections[1] = null;
							}
							
							twoTouch[1] = false;
						}
						
						doubleCollision = true;
						break;
					default:
						return;
				}
			}
			
			if (doubleCollision) {
				if (twoTouch[0] && !twoTouch[1]) {
					entity.collisionOnDirections[0] = movingDirections[0];
				} else if (!twoTouch[0] && twoTouch[1]) {
					entity.collisionOnDirections[0] = movingDirections[1];
				}
			} else {
				if (!twoTouch[0]) {
					if (entity.collisionOnDirections[0] == Direction.up ||
							entity.collisionOnDirections[0] == Direction.up) {
						entityLeftCol = (entityLeftWorldX - (int)entity.speed) / gamePanel.tileSize;
						tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
						tileNum2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
						
						if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							entity.collisionOnDirections[0] = null;
						}
						
						entityRightCol = (entityRightWorldX + (int)entity.speed) / gamePanel.tileSize;
						tileNum1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
						tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
						
						if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							entity.collisionOnDirections[0] = null;
						}
					} else if (entity.collisionOnDirections[0] == Direction.left ||
							entity.collisionOnDirections[0] == Direction.right) {
						entityTopRow = (entityTopWorldY - (int)entity.speed) / gamePanel.tileSize;
						tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
						tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
						
						if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							entity.collisionOnDirections[0] = null;
						}
						
						entityBottomRow = (entityBottomWorldY + (int)entity.speed) / gamePanel.tileSize;
						tileNum1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
						tileNum2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
						
						if (gamePanel.tileManager.tile[tileNum1 - 1].collision && gamePanel.tileManager.tile[tileNum2 - 1].collision) {
							entity.collisionOnDirections[0] = null;
						}
					}
				} else {
					
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			
		}
	}
}
