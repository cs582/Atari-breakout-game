import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Ball extends Rectangle {
	Random random;
	int xVelocity;
	int yVelocity;
	int speed = 2;
	Color c_ball = new Color(0x9053a6);
	
	Ball(int x, int y, int width, int height) {
		super(x,y,width,height);
		random = new Random();
		int randomXDirection = random.nextInt(2);
		if (randomXDirection == 0) {
			randomXDirection--;
		}
		setXDirection(randomXDirection*speed);
		setYDirection(1*speed);
	}
	
	public void setXDirection(int randomXDirection) {
		xVelocity = randomXDirection;
	}
	
	public void setYDirection(int randomYDirection) {
		yVelocity = randomYDirection;
	}
	
	public void move() {
		x += xVelocity;
		y += yVelocity;
	}
	public void draw(Graphics g) {
		g.setColor(c_ball);
		g.fillOval(x, y, height, width);
	}
}
