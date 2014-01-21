import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;


public class Brick extends GameObj {
	public static final int SIZE = 20;
	public static final int HEIGHT = 20;
	public static final int WIDTH = 80;
	public static final int INIT_X = 0;
	public static final int INIT_Y = 0;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	
	public boolean alive = true;
		
	
	
	public void setBoolean() {
		alive = false;
	}
	
	public boolean getAlive() {
		return alive;
	}

	public Brick(int x, int y) {
		super(INIT_VEL_X, INIT_VEL_Y, x, y, WIDTH, HEIGHT,
				GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
	}

	@Override
	public void draw(Graphics g) {
		if (alive) {
			if(this.pos_y > 50) g.setColor(new Color(138, 145, 227));
			else if(this.pos_y <= 50 && this.pos_y > 25) g.setColor(new Color(138,171,227));
			else g.setColor(new Color(227, 138, 154));
			g.fillRect(pos_x, pos_y, WIDTH, HEIGHT);
		}
	}
}

