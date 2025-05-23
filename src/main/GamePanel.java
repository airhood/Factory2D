package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.EntityHandler;
import entity.Player;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{
	
	// screen settings
	final int originalTileSize = 16;
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 25; //16 > 25 --31
	public final int maxScreenRow = 15; //12 > 15 --17
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	// world settings
	public final int maxWorldCol = 96;
	public final int maxWorldRow = 72;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	public int targetFPS = 60;
	
	
	public KeyHandler keyHandler = new KeyHandler(this);
	Sound sound = new Sound();
	Thread gameThread;
	public CollisionChecker collisionChecker = new CollisionChecker(this);
	public Player player = new Player("Airhood", this, keyHandler);
	TileManager tileManager = new TileManager(this);
	public UI ui = new UI(this);
	public EntityHandler entityHandler = new EntityHandler();
	public Chat chat = new Chat(this);
	
	int playerX = 100;
	int playerY = 100;
	int playerSpeed = 4;
	
	public GamePanel() {		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		this.addKeyListener(keyHandler);
		this.setFocusable(true);
		
		setCursor(CursorType.NormalSelects);
		
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				chat.checkChatInputField();
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}
			
		});
	}
	
	public void SetUpGame() {
		
	}
	
	public void startGameThread() {
		//int playID = sound.play(0);
		//sound.loop(playID);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {		
		double drawInterval = 1000000000/targetFPS;
		double nextDrawTime = System.nanoTime() + drawInterval;
		long lastTime = 0;
		long currentTime = 0;
		int fixedFPS = 0;
		int FPSCount = 0;
		long lastTime2 = 0;
		int lastFPS = 0;
		
		while(gameThread != null) {
			lastTime = System.nanoTime();
			
			update(targetFPS);
			repaint();
			
			try {
				double remainingTime = nextDrawTime - System.nanoTime();
				remainingTime = remainingTime/1000000;
				
				if (remainingTime <= 0) {
					remainingTime = 0;
				}
				
				Thread.sleep((long) remainingTime);
				
				nextDrawTime += drawInterval;
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			currentTime = System.nanoTime();
			
			int currentFPS = (int) (1000000000 / (currentTime - lastTime));
			lastFPS = currentFPS;
			
			FPSCount++;
			
			if ((currentTime - lastTime2) >= 1000000000) {
				lastTime2 = System.nanoTime();
				fixedFPS = FPSCount;
				FPSCount = 0;
				lastTime2 = System.nanoTime();
			}
			
			//System.out.println("liveFPS: " + currentFPS + " | fixedFPS: " + fixedFPS);
		}
		
//		double drawInterval = 1000000000/TargetFPS;
//		double delta = 0;
//		long lastTime = System.nanoTime();
//		long currentTime;
//		
//		while(gameThread != null) {
//			currentTime = System.nanoTime();
//			delta += (currentTime - lastTime) / drawInterval;
//			
//			lastTime = currentTime;
//			
//			if (delta >= 1) {
//				update();
//				repaint();
//				delta--;
//			}
//		}
	}
	
	int fpsCount = 0;
	
	public void update(int fps) {
		player.update(fps);
		//System.out.println("player pos | x: " + getPlayerXString() + " y: " + getPlayerYString());
		
		ui.mouseX = MouseInfo.getPointerInfo().getLocation().x;
		ui.mouseY = MouseInfo.getPointerInfo().getLocation().y;
		
		//System.out.println("x:" + ui.mouseX + " | y:" + ui.mouseY);
		
		fpsCount++;
		
		if (targetFPS / 2 <= fpsCount) {
			fpsCount = 0;
			chat.chatInputAnimationUpdate();
		}
	}
	
	public float divide(float n, float m) {
		return n / m;
	}
	
	public float round(float number, int n) {
	    double m = Math.pow(10.0, n);
	    
	    return (float) (Math.round(number * m) / m);
	}
	
	public String fillZero(float number, int n) {
		String str = Float.toString(number);
		
		boolean start = false;
		
		int decimalCount = 0;
		
		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == '.') {
				start = true;
			} else if (start) {
				decimalCount++;
			}
		}
		
		if (number != n) {
			for (int m = 0; m < n - decimalCount; m++) {
				str = str + "0";
			}
		}
		
		return str;
	}
	
	public String getPlayerXString() {
		System.out.println(tileManager.worldMidX + "  |  " + tileManager.worldMidY);
		return fillZero(round(divide((float)player.worldX - tileManager.worldMidX, (float)tileSize), 3), 4);
	}
	
	public String getPlayerYString() {
		return fillZero(round(divide((float)player.worldY - tileManager.worldMidY, (float)tileSize), 3), 4);
	}
	
//	public void syncPosition() {
//		player.worldX = (player.systemWorldX * (-1)) - tileManager.worldMidX;
//		player.worldY = (player.systemWorldY * (-1)) - tileManager.worldMidY;
//		
//		player.worldX = player.systemWorldX;
//		player.worldY = player.systemWorldY;
//	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		tileManager.draw(g2);
		
		player.draw(g2);
		
		ui.draw(g2);
		
		g2.dispose();
	}
	
	public void setCursor(CursorType cursor) {
		Main main = new Main();
		
		switch(cursor) {
			case AltSelect:
				main.setCursor("Alt Select");
				break;
			case Busy:
				main.setCursor("Busy");
				break;
			case Diagonal1:
				main.setCursor("Diagonal 1");
				break;
			case Diagonal2:
				main.setCursor("Diagonal 2");
				break;
			case HelpSelect:
				main.setCursor("Help Select");
				break;
			case Horizontal:
				main.setCursor("Horizontal");
				break;
			case Move:
				main.setCursor("Move");
				break;
			case NormalSelects:
				main.setCursor("Normal Selects");
				break;
			case PrecisionSelect:
				main.setCursor("Precision Select");
				break;
			case TextSelect:
				main.setCursor("Text Select");
				break;
			case Unavailable:
				main.setCursor("Unavailable");
				break;
			case Vertical:
				main.setCursor("Vertical");
				break;
			case WorkingInBackground:
				main.setCursor("Working In Background");
				break;
		}
	}
	
	public void playMusic() {
		
	}
}
