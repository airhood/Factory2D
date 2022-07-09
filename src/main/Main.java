package main;

import java.awt.Cursor;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Main {
	
	private static Image AppIconIamge = new ImageIcon("src/images/appIconImage.png").getImage();
	
	static JFrame window = new JFrame();
	
	public static void main(String[] arg) {
		//JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.setTitle("Pixel Adventure");
		window.setIconImage(AppIconIamge);
		
		GamePanel gamePanel = new GamePanel();
		window.add(gamePanel);
		
		window.pack();
		
		window.setLocationRelativeTo(null);
		window.setVisible(true);
		
		Main main = new Main();
		
		gamePanel.SetUpGame();
		gamePanel.startGameThread();
	}
	
	public void setCursor(String name) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(getClass().getResource("/cursors/" + name + ".png"));
		Cursor cursor = toolkit.createCustomCursor(image, new Point(0, 0), "img");
		
		window.setCursor(cursor);
	}
}
