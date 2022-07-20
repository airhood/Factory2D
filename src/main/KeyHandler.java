package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Arrays;

public class KeyHandler implements KeyListener{
	
	GamePanel gamePanel;
	
	public boolean W, A, S, D;
	public boolean Shift, Ctrl;
	public boolean Q, E, T;
	
	private ArrayList<Integer> chatInputKeyException = new ArrayList<>(Arrays.asList(KeyEvent.VK_SHIFT, KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_WINDOWS, KeyEvent.VK_CAPS_LOCK, KeyEvent.VK_INSERT, KeyEvent.VK_NUM_LOCK, KeyEvent.VK_UP, KeyEvent.VK_DOWN, 263));
	
	public KeyHandler(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (gamePanel.chat.chatScreenShowing & gamePanel.chat.inputFieldFocused) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				gamePanel.chat.chat(gamePanel.chat.inputFieldText);
				gamePanel.chat.inputFieldText = "";
				gamePanel.chat.currentPointer = 0;
				return;
			} else if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
				if (gamePanel.chat.inputFieldText.length() <= 1) {
					gamePanel.chat.inputFieldText = "";
				} else {
					gamePanel.chat.inputFieldText = gamePanel.chat.inputFieldText.substring(0, gamePanel.chat.inputFieldText.length() - 1);
				}
				
				if (gamePanel.chat.currentPointer > 0) {
					gamePanel.chat.currentPointer--;
				}
			} else if (e.getKeyCode() == KeyEvent.VK_DELETE) {
				
			} else if (chatInputKeyException.contains(e.getKeyCode())) {
				
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				if (gamePanel.chat.currentPointer > 0) {
					gamePanel.chat.currentPointer--;
				}
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				if (gamePanel.chat.inputFieldText.length() != gamePanel.chat.currentPointer) {
					gamePanel.chat.currentPointer++;
				}
			} else {
				gamePanel.chat.inputFieldText += Character.toString(e.getKeyChar());
				gamePanel.chat.currentPointer++;
			}
		} else {
			switch(e.getKeyCode()) {
				case KeyEvent.VK_W:
					W = true;
					break;
				case KeyEvent.VK_A:
					A = true;
					break;
				case KeyEvent.VK_S:
					S = true;
					break;
				case KeyEvent.VK_D:
					D = true;
					break;
				case KeyEvent.VK_SHIFT:
					Shift = true;
					break;
				case KeyEvent.VK_CONTROL:
					Ctrl = true;
					break;
				case KeyEvent.VK_Q:
					break;
				case KeyEvent.VK_E:
					break;
				case KeyEvent.VK_T:
					gamePanel.chat.chatScreenShowing = !gamePanel.chat.chatScreenShowing;
					gamePanel.chat.inputFieldFocused = true;
					break;
				case KeyEvent.VK_ESCAPE:
					break;
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()) {
			case KeyEvent.VK_W:
				W = false;
				break;
			case KeyEvent.VK_A:
				A = false;
				break;
			case KeyEvent.VK_S:
				S = false;
				break;
			case KeyEvent.VK_D:
				D = false;
				break;
			case KeyEvent.VK_SHIFT:
				Shift = false;
				break;
			case KeyEvent.VK_CONTROL:
				Ctrl = false;
				break;
			case KeyEvent.VK_Q:
				break;
			case KeyEvent.VK_E:
				break;
			case KeyEvent.VK_T:
				break;
			case KeyEvent.VK_ESCAPE:
				break;
		}
	}

}
