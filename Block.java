import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Block extends Rectangle {
	
	Color c_re = new Color(0xff375f);
	Color c_or = new Color(0xff9f0a);
	Color c_yw = new Color(0xffd60a);
	Color c_gr = new Color(0x30d158);
	Color c_aq = new Color(0x40c8e0);
	Color c_bl = new Color(0x5e5ce6);
	Color c_block;
	int points;
	boolean destroyed = false;
	
	Block(int x, int y, int BLOCK_WIDTH, int BLOCK_HEIGHT, String color){
		super(x, y, BLOCK_WIDTH, BLOCK_HEIGHT);
		switch(color){
			case "Red":		c_block = c_re; points = 7; break;
			case "Orange":	c_block = c_or; points = 7; break;
			case "Yellow":	c_block = c_yw; points = 4; break;
			case "Green":	c_block = c_gr; points = 4; break;
			case "Aqua":	c_block = c_aq; points = 1; break;
			case "Blue":	c_block = c_bl; points = 1; break;
		}
	}

	public void draw(Graphics g) {
		g.setColor(c_block);
		g.fillRect(x, y, width, height);
	}
	
	public void destroy() {
		destroyed = true;
	}
}
