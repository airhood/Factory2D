package main;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	
	private static Image AppIconIamge = new ImageIcon("src/images/appIconImage.png").getImage();
	
	public static void main(String[] arg) {
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("2D Adventure");
		window.setIconImage(AppIconIamge);
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		gamePanel.startGameThread();
	}
}
