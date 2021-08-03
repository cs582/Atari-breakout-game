import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Score {
	static int GAME_WIDTH;
	static int GAME_HEIGHT;
	int player = 0;
	int remainingBalls = 5;
	
	Score(int GAME_WIDTH, int GAME_HEIGHT){
		Score.GAME_WIDTH = GAME_WIDTH;
		Score.GAME_HEIGHT = GAME_HEIGHT;
	}
	
	public void draw(Graphics g) {
		g.setColor(Color.white);
		g.setFont(new Font("Consolas", Font.PLAIN, 60));
		
		int dig1 = player/100;
		int dig2 = (player%100)/10;
		int dig3 = (player%10);
		g.drawString(String.valueOf(dig1)+String.valueOf(dig2)+String.valueOf(dig3), GAME_WIDTH/2-180, 50);
		g.drawString(String.valueOf(remainingBalls), GAME_WIDTH/2+130, 50);
	}
}
