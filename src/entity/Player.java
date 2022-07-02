package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import main.GamePanel;
import main.KeyHandler;

public class Player extends Entity{
	
	GamePanel gamePanel;
	KeyHandler keyHandler;
	
	public final int screenX;
	public final int screenY;
	
	public int worldSpawnX;
	public int worldSpawnY;
	
	boolean isVerticalMove = false;
	
	public Player(GamePanel gamePanel, KeyHandler keyHandler) {
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;
		
		screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
		screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
		
		solidArea = new Rectangle(12, 20, 24, 30);
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		systemWorldX = (gamePanel.tileSize * (gamePanel.maxWorldCol / 2)) - (gamePanel.tileSize / 2);
		systemWorldY = (gamePanel.tileSize * (gamePanel.maxWorldRow / 2)) - (gamePanel.tileSize / 2);
		speed = 3f;
		diagonalSpeed = 2f;
		direction = Direction.down;
		collisionOnDirections = new Direction[2];
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_1.png"));
			up2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_up_2.png"));
			down1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_1.png"));
			down2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_down_2.png"));
			left1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_1.png"));
			left2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_left_2.png"));
			right1 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_1.png"));
			right2 = ImageIO.read(getClass().getResourceAsStream("/player/boy_right_2.png"));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update() {
		int estimatedPlayerWorldX = systemWorldX;
		int estimatedPlayerWorldY = systemWorldY;
		
		if (!keyHandler.upPressed && !keyHandler.downPressed && !keyHandler.leftPressed && !keyHandler.rightPressed) {
			if (!isVerticalMove) {
				spriteNum = 1;
			}
		} else {
			boolean moved = false;
			boolean doubleMove = false;
			
			Direction[] directions = new Direction[2];
			
			if (keyHandler.upPressed && !keyHandler.downPressed) {
				direction = Direction.up;
				directions[0] = Direction.up;
				moved = true;
			} else if (keyHandler.downPressed && !keyHandler.upPressed) {
				direction = Direction.down;
				directions[0] = Direction.down;
				moved = true;
			}
			
			if (keyHandler.leftPressed && !keyHandler.rightPressed) {
				direction = Direction.left;
				
				if (moved) {
					doubleMove = true;
					directions[1] = Direction.left;
				} else {
					directions[0] = Direction.left;
				}
				
				moved = true;
			} else if (keyHandler.rightPressed && !keyHandler.leftPressed) {
				direction = Direction.right;
				
				if (moved) {
					doubleMove = true;
					directions[1] = Direction.right;
				} else {
					directions[0] = Direction.right;
				}
				
				moved = true;
			} else if (!moved){
				spriteNum = 1;
				return;
			}
			
			if (!doubleMove) {
				switch(directions[0]) {
					case up:
						isVerticalMove = true;
						direction = Direction.up;
						estimatedPlayerWorldY -= speed;
						break;
					case down:
						isVerticalMove = true;
						estimatedPlayerWorldY += speed;
						direction = Direction.down;
						break;
					case left:
						isVerticalMove = false;
						direction = Direction.left;
						estimatedPlayerWorldX -= speed;
						break;
					case right:
						isVerticalMove = false;
						direction = Direction.right;
						estimatedPlayerWorldX += speed;
						break;
				}
			} else {				
				switch(directions[0]) {
					case up:
						estimatedPlayerWorldY -= diagonalSpeed;
						break;
					case down:
						estimatedPlayerWorldY += diagonalSpeed;
						break;
					case left:
						//direction = Direction.left;
						estimatedPlayerWorldX -= diagonalSpeed;
						break;
					case right:
						//direction = Direction.right;
						estimatedPlayerWorldX += diagonalSpeed;
						break;					
				}
				
				switch(directions[1]) {
					case up:
						estimatedPlayerWorldY -= diagonalSpeed;
						break;
					case down:
						estimatedPlayerWorldY += diagonalSpeed;
						break;
					case left:
						//direction = Direction.left;
						estimatedPlayerWorldX -= diagonalSpeed;
						break;
					case right:
						//direction = Direction.right;
						estimatedPlayerWorldX += diagonalSpeed;
						break;					
				}
				
				isVerticalMove = false;
			}
			
			// check tile collision
			collisionOnDirections[0] = null;
			collisionOnDirections[1] = null;
			gamePanel.collisionChecker.checkTile(this, directions, estimatedPlayerWorldX, estimatedPlayerWorldY);
			
			if (collisionOnDirections[0] == null) {
				systemWorldX = estimatedPlayerWorldX;
				systemWorldY = estimatedPlayerWorldY;
			} else {
				boolean xStop = false;
				boolean yStop = false;
				
				switch(collisionOnDirections[0]) {
					case up:
						estimatedPlayerWorldY = systemWorldY;
						yStop = true;
						break;
					case down:
						estimatedPlayerWorldY = systemWorldY;
						yStop = true;
						break;
					case left:
						estimatedPlayerWorldX = systemWorldX;
						xStop = true;
						break;
					case right:
						estimatedPlayerWorldX = systemWorldX;
						xStop = true;
						break;
				}
				
				if (collisionOnDirections[1] != null) {
					switch(collisionOnDirections[1]) {
						case up:
							estimatedPlayerWorldY = systemWorldY;
							yStop = true;
							break;
						case down:
							estimatedPlayerWorldY = systemWorldY;
							yStop = true;
							break;
						case left:
							estimatedPlayerWorldX = systemWorldX;
							xStop = true;
							break;
						case right:
							estimatedPlayerWorldX = systemWorldX;
							xStop = true;
							break;
					}
				}
				
				systemWorldX = estimatedPlayerWorldX;
				systemWorldY = estimatedPlayerWorldY;
			}
			
			spriteCounter++;
			if (spriteCounter > gamePanel.targetFPS / 5) {
				if (spriteNum == 1) {
					spriteNum = 2;
				} else if (spriteNum == 2) {
					spriteNum = 1;
				}
				spriteCounter = 0;
			}
		}
	}
	
	public void draw(Graphics2D g2) {
		BufferedImage image = null;
		
		switch(direction) {
			case up:
				if (spriteNum == 1) {
					image = up1;
				} else if (spriteNum == 2) {
					image = up2;
				}
				break;
			case down:
				if (spriteNum == 1) {
					image = down1;
				} else if (spriteNum == 2) {
					image = down2;
				}
				break;
			case left:
				if (spriteNum == 1) {
					image = left1;
				} else if (spriteNum == 2) {
					image = left2;
				}
				break;
			case right:
				if (spriteNum == 1) {
					image = right1;
				} else if (spriteNum == 2) {
					image = right2;
				}
				break;
		}
		
		g2.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
	}
}
