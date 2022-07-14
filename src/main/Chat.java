package main;

import java.util.ArrayList;

public class Chat {
	
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
}
