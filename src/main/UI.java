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
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(font_Arial_20);
		g2.setColor(Color.white);
		
		if (gamePanel.chat.chatScreenShowing) {
			drawChat(g2);
		}
	}
	
	public void drawChat(Graphics2D g2) {
		gamePanel.chat.chatLog.add("<Hyunjun>Hi!");
		g2.drawString(gamePanel.chat.chatLog.get(0), 10, 670);
		g2.drawImage(chatInputField, 10, 685, 1175, 25, null);
		
		if (gamePanel.chat.inputFieldFocused) {
			if (chatInputAnimationState) {
				g2.drawString(gamePanel.chat.inputFieldText + "_", 15, 704);
			} else {
				g2.drawString(gamePanel.chat.inputFieldText, 15, 704);
			}
		}
	}
	
	public void chatInputAnimationUpdate() {
		chatInputAnimationState = !chatInputAnimationState;
	}
	
	public void checkChatInputField() {
		if (gamePanel.ui.mouseX >= 175 && gamePanel.ui.mouseX <= 1350 && gamePanel.ui.mouseY >= 770 && gamePanel.ui.mouseY <= 795) {
			 gamePanel.chat.inputFieldFocused = true;
			 return;
		}
		gamePanel.chat.inputFieldFocused = false;
	}
}
