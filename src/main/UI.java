package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class UI {
	
	public int mouseX;
	public int mouseY;
	
	GamePanel gamePanel;
	Font font_Arial_20;
	BufferedImage chatInputField;
	
	public boolean chatInputAnimationState;
	
	// UI
	Chat chat;
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		chatInputAnimationState = true;
		
		font_Arial_20 = new Font("Arial", Font.PLAIN, 20);
		
		try {
			chatInputField = ImageIO.read(getClass().getResourceAsStream("/ui/ChatInputField.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		chat = new Chat(gamePanel);
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(font_Arial_20);
		g2.setColor(Color.white);
		
		if (gamePanel.chat.chatScreenShowing) {
			chat.draw(g2);;
		}
	}
	
	
}
