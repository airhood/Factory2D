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
	Font 배달의민족_주아_보통;
	BufferedImage chatInputField;
	
	public boolean chatInputAnimationState;
	
	// UI
	Chat chat;
	
	public UI(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		chatInputAnimationState = true;
		
		font_Arial_20 = new Font("Arial", Font.PLAIN, 20);
		
		배달의민족_주아_보통 = new Font("배달의민족 주아", Font.PLAIN, 20);
		
		try {
			chatInputField = ImageIO.read(getClass().getResourceAsStream("/ui/ChatInputField.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		chat = new Chat(gamePanel);
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(배달의민족_주아_보통);
		g2.setColor(Color.white);
		
		if (gamePanel.chat.chatScreenShowing) {
			chat.draw(g2, 배달의민족_주아_보통);;
		}
	}
	
	
}
