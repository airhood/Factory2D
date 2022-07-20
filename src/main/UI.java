package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class UI {
	
	public int mouseX;
	public int mouseY;
	
	GamePanel gamePanel;
	Font font_Arial_20;
	Font 배달의민족_주아_보통;
	BufferedImage chatInputField;
	
	public boolean chatInputAnimationState;
	
	public HashMap<Character, Integer> character_ratios = new HashMap<Character, Integer>(){{
		put('a',60);
		put('b',60);
		put('c',52);
		put('d',60);
		put('e',60);
		put('f',30);
		put('g',60);
		put('h',60);
		put('i',25);
		put('j',25);
		put('k',52);
		put('l',25);
		put('m',87);
		put('n',60);
		put('o',60);
		put('p',60);
		put('q',60);
		put('r',35);
		put('s',52);
		put('t',30);
		put('u',60);
		put('v',52);
		put('w',77);
		put('x',52);
		put('y',52);
		put('z',52);
		put('A',70);
		put('B',70);
		put('C',77);
		put('D',77);
		put('E',70);
		put('F',65);
		put('G',82);
		put('H',77);
		put('I',30);
		put('J',55);
		put('K',70);
		put('L',60);
		put('M',87);
		put('N',77);
		put('O',82);
		put('P',70);
		put('Q',82);
		put('R',77);
		put('S',70);
		put('T',65);
		put('U',77);
		put('V',70);
		put('W',100);
		put('X',70);
		put('Y',70);
		put('Z',65);
	}};
	
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
	}
	
	public void draw(Graphics2D g2) {
		g2.setFont(배달의민족_주아_보통);
		g2.setColor(Color.white);
		
		if (gamePanel.chat.chatScreenShowing) {
			gamePanel.chat.draw(g2, 배달의민족_주아_보통);;
		}
	}
	
	
}
