import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Paddle extends Rectangle {
	
	int xVelocity;
	int speed = 10;
	
	Color c_paddle = new Color(0x4f0099);
	
	Paddle(int x, int y, int PADDLE_WIDTH, int PADDLE_HEIGHT){
		super(x,y,PADDLE_WIDTH, PADDLE_HEIGHT);
		
	}
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_LEFT) {
			setXDirection(-speed);
			move();
		}
		if (e.getKeyCode()==KeyEvent.VK_RIGHT) {
			setXDirection(speed);
			move();
		}
	}
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_LEFT || e.getKeyCode()==KeyEvent.VK_RIGHT) {
			setXDirection(0);
			move();
		}
	}
	public void setXDirection(int xDirection) {
		xVelocity = xDirection;
	}
	public void move() {
		x = x + xVelocity;
	}
	public void draw(Graphics g) {
		g.setColor(c_paddle);
		g.fillRect(x, y, width, height);
	}
}
