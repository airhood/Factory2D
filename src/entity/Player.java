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
import main.UtilityTool;

public class Player extends Entity{
	
	GamePanel gamePanel;
	KeyHandler keyHandler;
	
	public String name;
	
	public final int screenX;
	public final int screenY;
	
	boolean isVerticalMove = false;
	
	public float sprintingSpeed;
	public float diagonalSprintingSpeed;
	public float sneakingSpeed;
	public float diagonalSneakingSpeed;
	
	public Player(String name, GamePanel gamePanel, KeyHandler keyHandler) {
		this.name = name;
		this.gamePanel = gamePanel;
		this.keyHandler = keyHandler;
		
		screenX = gamePanel.screenWidth / 2 - (gamePanel.tileSize / 2);
		screenY = gamePanel.screenHeight / 2 - (gamePanel.tileSize / 2);
		
		solidArea = new Rectangle(12, 20, 24, 30);
		
		setDefaultValues();
		getPlayerImage();
	}
	
	public void setDefaultValues() {
		worldX = ((gamePanel.tileSize * (gamePanel.maxWorldCol / 2)) - (gamePanel.tileSize / 2)) * (-1);
		worldX = ((gamePanel.tileSize * (gamePanel.maxWorldRow / 2)) - (gamePanel.tileSize / 2)) * (-1);
		speed = 3f;
		diagonalSpeed = 2f;
		sprintingSpeed = 4f;
		diagonalSprintingSpeed = 3f;
		sneakingSpeed = 2f;
		diagonalSneakingSpeed = 1f;
		direction = Direction.down;
		collisionOnDirections = new Direction[2];
	}
	
	public void getPlayerImage() {
		up1 = setUpPlayerImage("boy_up_1.png");
		up2 = setUpPlayerImage("boy_up_2.png");
		down1 = setUpPlayerImage("boy_down_1.png");
		down2 = setUpPlayerImage("boy_down_2.png");
		left1 = setUpPlayerImage("boy_left_1.png");
		left2 = setUpPlayerImage("boy_left_2.png");
		right1 = setUpPlayerImage("boy_right_1.png");
		right2 = setUpPlayerImage("boy_right_2.png");
	}
	
	public BufferedImage setUpPlayerImage(String imageName) {
		UtilityTool utilityTool = new UtilityTool();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(getClass().getResourceAsStream("/player/" + imageName));
			image = utilityTool.scaledImage(image, gamePanel.tileSize, gamePanel.tileSize);
		} catch(IOException e) {
			e.printStackTrace();
		}
		
		return image;
	}
	
	public void update(int fps) {
		int stackRange = 1;
		if (fps != 0) {
			stackRange = 60 / fps;
		}
		
		int animationDuration = 4;
		
		if (gamePanel.keyHandler.Ctrl & !gamePanel.keyHandler.Shift) {
			animationDuration = 6;
		} else if (gamePanel.keyHandler.Shift && !gamePanel.keyHandler.Ctrl) {
			animationDuration = 2;
		}
		
		int estimatedPlayerWorldX = worldX;
		int estimatedPlayerWorldY = worldY;
		
		float currentSpeed;
		float currentDiagonalSpeed;
		
		if (keyHandler.Shift && !keyHandler.Ctrl) {
			currentSpeed = sneakingSpeed * stackRange;
			currentDiagonalSpeed = diagonalSneakingSpeed * stackRange;
		} else if (!keyHandler.Shift && keyHandler.Ctrl) {
			currentSpeed = sprintingSpeed * stackRange;
			currentDiagonalSpeed = diagonalSprintingSpeed * stackRange;
		} else {
			currentSpeed = speed * stackRange;
			currentDiagonalSpeed = diagonalSpeed * stackRange;
		}
		
		if (!keyHandler.W && !keyHandler.S && !keyHandler.A && !keyHandler.D) {
			if (!isVerticalMove) {
				spriteNum = 1;
			}
		} else {
			boolean moved = false;
			boolean doubleMove = false;
			
			Direction[] directions = new Direction[2];
			
			if (keyHandler.W && !keyHandler.S) {
				direction = Direction.up;
				directions[0] = Direction.up;
				moved = true;
			} else if (keyHandler.S && !keyHandler.W) {
				direction = Direction.down;
				directions[0] = Direction.down;
				moved = true;
			}
			
			if (keyHandler.A && !keyHandler.D) {
				direction = Direction.left;
				
				if (moved) {
					doubleMove = true;
					directions[1] = Direction.left;
				} else {
					directions[0] = Direction.left;
				}
				
				moved = true;
			} else if (keyHandler.D && !keyHandler.A) {
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
						estimatedPlayerWorldY += currentSpeed;
						break;
					case down:
						isVerticalMove = true;
						estimatedPlayerWorldY -= currentSpeed;
						direction = Direction.down;
						break;
					case left:
						isVerticalMove = false;
						direction = Direction.left;
						estimatedPlayerWorldX += currentSpeed;
						break;
					case right:
						isVerticalMove = false;
						direction = Direction.right;
						estimatedPlayerWorldX -= currentSpeed;
						break;
				}
			} else {				
				switch(directions[0]) {
					case up:
						estimatedPlayerWorldY += currentDiagonalSpeed;
						break;
					case down:
						estimatedPlayerWorldY -= currentDiagonalSpeed;
						break;
					case left:
						//direction = Direction.left;
						estimatedPlayerWorldX += currentDiagonalSpeed;
						break;
					case right:
						//direction = Direction.right;
						estimatedPlayerWorldX -= currentDiagonalSpeed;
						break;					
				}
				
				switch(directions[1]) {
					case up:
						estimatedPlayerWorldY += currentDiagonalSpeed;
						break;
					case down:
						estimatedPlayerWorldY -= currentDiagonalSpeed;
						break;
					case left:
						//direction = Direction.left;
						estimatedPlayerWorldX += currentDiagonalSpeed;
						break;
					case right:
						//direction = Direction.right;
						estimatedPlayerWorldX -= currentDiagonalSpeed;
						break;					
				}
				
				isVerticalMove = false;
			}
			
			// check tile collision
			collisionOnDirections[0] = null;
			collisionOnDirections[1] = null;
			gamePanel.collisionChecker.checkTile(this, directions, estimatedPlayerWorldX, estimatedPlayerWorldY);
			
			if (collisionOnDirections[0] == null) {
				worldX = estimatedPlayerWorldX;
				worldY = estimatedPlayerWorldY;
			} else {
				
				switch(collisionOnDirections[0]) {
					case up:
						estimatedPlayerWorldY = worldY;
						break;
					case down:
						estimatedPlayerWorldY = worldY;
						break;
					case left:
						estimatedPlayerWorldX = worldX;
						break;
					case right:
						estimatedPlayerWorldX = worldX;
						break;
				}
				
				if (collisionOnDirections[1] != null) {
					switch(collisionOnDirections[1]) {
						case up:
							estimatedPlayerWorldY = worldY;
							break;
						case down:
							estimatedPlayerWorldY = worldY;
							break;
						case left:
							estimatedPlayerWorldX = worldX;
							break;
						case right:
							estimatedPlayerWorldX = worldX;
							break;
					}
				}
				
				worldX = estimatedPlayerWorldX;
				worldY = estimatedPlayerWorldY;
			}
			
			spriteCounter++;
			if (spriteCounter > gamePanel.targetFPS / animationDuration) {
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
