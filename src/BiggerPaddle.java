import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class BiggerPaddle extends GameObj {
	public static final int SIZE = 10;
	public static final int SPEED = 6;

	public boolean hit = true;
	
	public void setBoolean() {
		hit = false;
	}
	
	public void turnOff() {
		hit = true;
	}
	
	public boolean getAlive() {
		return hit;
	}


	public BiggerPaddle(int pos_x, int pos_y, int court_width, int court_height) {
		super(0, SPEED, pos_x, pos_y, SIZE, SIZE, court_width, court_height);
	}

	public void draw(Graphics g) {
			Random ran = new Random();
			float r = ran.nextFloat();
			float gg = ran.nextFloat();
			float b = ran.nextFloat();
			Color randomColor = new Color(r, gg, b);
			g.setColor(randomColor);
			g.fillOval(pos_x, pos_y, width, height);
	}

}
