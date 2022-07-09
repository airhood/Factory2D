package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
	
	public boolean W, A, S, D;
	public boolean Shift, Ctrl;
	public boolean Q, E, T;
	
	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) {		
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
				break;
			case KeyEvent.VK_ESCAPE:
				break;
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
		}
	}

}
