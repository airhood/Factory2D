package main;

import java.awt.AlphaComposite;
import java.awt.Font;
import java.awt.Graphics2D;
import java.util.ArrayList;

public class Chat implements GUIDrawer {
	
	public GamePanel gamePanel;
	
	public boolean inputFieldFocused;
	
	public int lineSpacing = 25;
	
	public String inputFieldText;
	
	public boolean chatScreenShowing;
	
	public ArrayList<String> chatLog = new ArrayList<String>();
	
	public int currentPointer;
	
	AlphaComposite alphaComposite;
	AlphaComposite defaultAlphaComposite;
	
	public Chat(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
		
		chatScreenShowing = false;
		
		inputFieldFocused = false;
		
		inputFieldText = "";
		
		alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)120/255);
		defaultAlphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)255/255);
		
		currentPointer = 0;
	}
	
	public void chat(String chat) {
		if (chat.length() != 0) {
			if (chat.charAt(0) == '/') {
				commandInterpreter(chat);
			} else {
				chatLog.add(chat);
			}
		}
	}
	
	public void commandInterpreter(String command) {
		
	}

	@Override
	public void draw(Graphics2D g2, Font font) {
		String playerName = gamePanel.player.name;
		String showingChatLog = "";
		
		g2.setFont(font);
		
		g2.setComposite(alphaComposite);
		g2.drawImage(gamePanel.ui.chatInputField, 10, 685, 1175, 25, null);
		
		g2.drawImage(gamePanel.ui.chatInputField, 10, 650 - 550 + 5, 700, 550, null);
		
		g2.setComposite(defaultAlphaComposite);
		
		
		if (gamePanel.chat.chatLog.size() != 0) {
			
			int n = 0;
			
			// y = 670
			for (int i = gamePanel.chat.chatLog.size() - 1; i >= 0; i--) {
				int y = 650 - (lineSpacing * n);
				
				if (y >= 105 + 3) {
					g2.drawString("<" + playerName + ">" + gamePanel.chat.chatLog.get(i), 10, y);
				} else {
					break;
				}
				
				n++;
			}
		}
		
		System.out.println(gamePanel.chat.currentPointer);
		
				
		if (gamePanel.ui.chatInputAnimationState) {
			g2.drawString(insertString(gamePanel.chat.inputFieldText, "_", gamePanel.chat.currentPointer), 15, 704);
		} else {
			g2.drawString(gamePanel.chat.inputFieldText, 15, 704);
		}
	}
	
	public String insertString(String originalString, String stringToBeInserted, int index) {
		if (index > originalString.length()) return "";
		
		String newString = new String();
		for (int i = 0; i <= originalString.length(); i++) {			
			if (i == index) {
				newString += stringToBeInserted;
			}
			
			if (i != originalString.length()) {
				newString += originalString.charAt(i);
			}
		}
		
		return newString;
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
