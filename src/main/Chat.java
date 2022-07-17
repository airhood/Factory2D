package main;

import java.awt.Graphics2D;
import java.util.ArrayList;

public class Chat implements GUIDrawer {
	
	public GamePanel gamePanel;
	
	public boolean inputFieldFocused;
	
	public String inputFieldText;
	
	public boolean chatScreenShowing;
	
	public ArrayList<String> chatLog = new ArrayList<String>();
	
	public Chat(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		chatScreenShowing = false;
		
		inputFieldFocused = false;
		
		inputFieldText = "";
	}
	
	public void chat(String chat) {
		if (chat.charAt(0) == '/') {
			commandInterpreter(chat);
		} else {
			chatLog.add(chat);
		}
	}
	
	public void commandInterpreter(String command) {
		
	}

	@Override
	public void draw(Graphics2D g2) {
		gamePanel.chat.chatLog.add("<Hyunjun>Hi!");
		g2.drawString(gamePanel.chat.chatLog.get(0), 10, 670);
		g2.drawImage(gamePanel.ui.chatInputField, 10, 685, 1175, 25, null);
		
		if (gamePanel.chat.inputFieldFocused) {
			if (gamePanel.ui.chatInputAnimationState) {
				g2.drawString(gamePanel.chat.inputFieldText + "_", 15, 704);
			} else {
				g2.drawString(gamePanel.chat.inputFieldText, 15, 704);
			}
		}
	}
	
	public void chatInputAnimationUpdate() {
		gamePanel.ui.chatInputAnimationState = !gamePanel.ui.chatInputAnimationState;
	}
	
	public void checkChatInputField() {
		if (gamePanel.ui.mouseX >= 175 && gamePanel.ui.mouseX <= 1350 && gamePanel.ui.mouseY >= 770 && gamePanel.ui.mouseY <= 795) {
			 gamePanel.chat.inputFieldFocused = true;
			 return;
		}
		gamePanel.chat.inputFieldFocused = false;
	}
}
