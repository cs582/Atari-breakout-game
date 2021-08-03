import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JFrame{
	
	Color c_background = new Color(0x303030);
	GamePanel panel;
	
	GameFrame(){
		panel = new GamePanel();
		this.add(panel);
		this.setTitle("Breakout-v0");
		this.setResizable(false);
		this.setBackground(c_background);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		
		ImageIcon image = new ImageIcon("logo.jpeg");
		this.setIconImage(image.getImage());
	}
}
