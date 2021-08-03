import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {
	static final int GAME_WIDTH = 750;
	static final int GAME_HEIGHT = (int)(GAME_WIDTH * 0.5555);
	static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
	static final int BALL_DIAMETER = 15;
	static final int PADDLE_WIDTH = 100;
	static final int PADDLE_HEIGHT = (int)(PADDLE_WIDTH*0.15);
	static final int N_BLOCKS_PER_LINE = 15;
	static final int N_LINES = 6;
	static final int BLOCK_WIDTH = (int)(GAME_WIDTH/N_BLOCKS_PER_LINE);
	static final int BLOCK_HEIGHT = (int)(BLOCK_WIDTH/4);
	static final int N_BLOCKS = N_LINES*N_BLOCKS_PER_LINE;
	Block[][] blocks;
	Thread gameThread;
	Image image;
	Graphics graphics;
	Random random;
	Paddle paddle;
	Ball ball;
	Score score;
	int curNumberOfBlocks;
	int maxSpeed;
	boolean newLife;
	boolean difficultyHard;
	boolean done;
	
	GamePanel(){
		done = false;
		random = new Random();
		curNumberOfBlocks = N_BLOCKS;
		difficultyHard = false;
		newLife = false;
		newBlocks();
		newPaddle();
		newBall();
		score = new Score(GAME_WIDTH, GAME_HEIGHT);
		this.setFocusable(true);
		this.addKeyListener(new AL());
		this.setPreferredSize(SCREEN_SIZE);
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	public void newBlocks() {
		int start = 75;
		String[] colors = {"Red","Orange","Yellow","Green","Aqua","Blue"};
		blocks = new Block[N_LINES][N_BLOCKS_PER_LINE];
		for(int i = 0; i < N_LINES; i++) {
			for(int j = 0; j < N_BLOCKS_PER_LINE; j++) {
				blocks[i][j] = new Block(BLOCK_WIDTH*j, start+(BLOCK_HEIGHT*i), BLOCK_WIDTH, BLOCK_HEIGHT, colors[i]);
			}
		}
	}
	
	public void newBall() {
		ball = new Ball((GAME_WIDTH/2)-(BALL_DIAMETER/2), (GAME_HEIGHT/2)-(BALL_DIAMETER/2), BALL_DIAMETER, BALL_DIAMETER);
	}
	
	public void newPaddle() {
		paddle = new Paddle((GAME_WIDTH/2)-(PADDLE_WIDTH/2), GAME_HEIGHT-2*PADDLE_HEIGHT, PADDLE_WIDTH, PADDLE_HEIGHT);
	}
	
	public void paint(Graphics g) {
		image = createImage(getWidth(), getHeight());
		graphics = image.getGraphics();
		draw(graphics);
		g.drawImage(image,0,0,this);
		
	}
	
	public void checkBlocks() {
		if (!difficultyHard && Math.abs(ball.xVelocity) == ball.speed && curNumberOfBlocks <= 4*N_BLOCKS/5) {
			if (ball.xVelocity > 0) ball.xVelocity++;
			else 					ball.xVelocity--;
			System.out.println("Vx = " + ball.xVelocity + ", Vy = " + ball.yVelocity);
			difficultyHard = true;
		}
		if (curNumberOfBlocks == 0) {
			done = true;
		}
		if (score.remainingBalls == 0) {
			done = true;
		}
	}
	
	public void draw(Graphics g) {
		paddle.draw(g);
		ball.draw(g);
		score.draw(g);
		for(int i = 0; i < N_LINES; i++)
			for(int j = 0; j < N_BLOCKS_PER_LINE; j++)
				if(!blocks[i][j].destroyed) blocks[i][j].draw(g);
	}
	
	public void move() {
		paddle.move();
		ball.move();
	}
	
	public void checkCollision() {
		//bounce ball
		if (ball.y <= 0) {
			ball.setYDirection(-ball.yVelocity);
		}
		if (ball.x <= 0) {
			ball.setXDirection(-ball.xVelocity);
		}
		if (ball.x >= GAME_WIDTH-BALL_DIAMETER) {
			ball.setXDirection(-ball.xVelocity);
		}
		
		//bounce ball off blocks
		for(int i = 0; i < N_LINES; i++) {
			for(int j = 0; j < N_BLOCKS_PER_LINE; j++) {
				if (!blocks[i][j].destroyed && ball.intersects(blocks[i][j])) {
					System.out.println("Vx = " + ball.xVelocity + ", Vy = " + ball.xVelocity);
					//check left side
					if(blocks[i][j].x >= ball.x+BALL_DIAMETER-4) {
						ball.xVelocity = -Math.abs(ball.xVelocity);
						blocks[i][j].destroy();
					}
					//check right side
					else if(blocks[i][j].x+BLOCK_WIDTH-4 <= ball.x) {
						ball.xVelocity = Math.abs(ball.xVelocity);
						blocks[i][j].destroy();
					}
					//check top
					else if(blocks[i][j].y >= ball.y+BALL_DIAMETER-4) {
						ball.yVelocity = -Math.abs(ball.yVelocity);
						blocks[i][j].destroy();
					}
					//check bottom 
					else if(blocks[i][j].y+BLOCK_HEIGHT-4 <= ball.y) {
						ball.yVelocity = Math.abs(ball.yVelocity);
						blocks[i][j].destroy();
					}
					if (blocks[i][j].destroyed) {
						if (Math.abs(ball.xVelocity) <= ball.speed && difficultyHard) {
							if (ball.xVelocity > 0) ball.xVelocity++;
							else 					ball.xVelocity--;
						}
						System.out.println("Vx = " + ball.xVelocity + ", Vy = " + ball.yVelocity);
						score.player += blocks[i][j].points;
						curNumberOfBlocks--;
					}
				}
				ball.setXDirection(ball.xVelocity);
				ball.setYDirection(ball.yVelocity);
			}
		}
		
		//bounce ball off paddle
		if(ball.intersects(paddle)) {
			if (ball.y+BALL_DIAMETER <= paddle.y+3) {
				ball.yVelocity = -ball.yVelocity;
				if (ball.x+(BALL_DIAMETER/2) > paddle.x+PADDLE_WIDTH/2)
					ball.xVelocity = Math.abs(ball.xVelocity);
				if (ball.x+(BALL_DIAMETER/2) < paddle.x+PADDLE_WIDTH/2)
					ball.xVelocity = -Math.abs(ball.xVelocity);
				if (Math.abs(ball.yVelocity) >= 5 || Math.abs(ball.yVelocity) <= 2) {
					ball.yVelocity = -Math.abs(ball.xVelocity);
				} else {
					ball.yVelocity = -Math.abs(ball.yVelocity+random.nextInt(2));
				}
				ball.setXDirection(ball.xVelocity);
				ball.setYDirection(ball.yVelocity);
			}
		}
		
		//Stops paddle at edges
		if(paddle.x <= 0) {
			paddle.x = 1;
		}
		if(paddle.x >= (GAME_WIDTH-PADDLE_WIDTH)) {
			paddle.x = GAME_WIDTH-PADDLE_WIDTH-1;
		}
		
		//player loses the game and creates a new ball
		if(ball.y > paddle.y) {
			score.remainingBalls--;
			newPaddle();
			newBall();
			newLife = true;
		}
	}
	
	public void run() {
		//game loop
		long lastTime = System.nanoTime();
		double amountOfTicks = 90.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;
		while(!done) {
			long now = System.nanoTime();
			delta += (now - lastTime)/ns;
			lastTime = now;
			if (delta >= 1) {
				move();
				checkCollision();
				repaint();
				checkBlocks();
				delta--;
			}
		}
	}
	
	public class AL extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			paddle.keyPressed(e);
		}
		public void keyReleased(KeyEvent e) {
			paddle.keyReleased(e);
		}
	}
}
