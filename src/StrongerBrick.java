import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class StrongerBrick extends GameObj {

	public static final int SIZE = 20;
	public static final int HEIGHT = 20;
	public static final int WIDTH = 80;
	public static final int INIT_X = 0;
	public static final int INIT_Y = 0;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	public int hits = 3;

	public boolean alive = true;

	
	public void setBoolean() {
		if(hits == 0)
		alive = false;
	}

	public boolean getAlive() {
		return alive;
	}

	public StrongerBrick(int x, int y) {
		super(INIT_VEL_X, INIT_VEL_Y, x, y, WIDTH, HEIGHT,
				GameCourt.COURT_WIDTH, GameCourt.COURT_HEIGHT);
	}

	@Override
	public void draw(Graphics g) {
		if (alive) {
			if (hits == 3) g.setColor(new Color(138, 227, 211));
			else if (hits == 2) g.setColor(new Color(227, 138, 154));
			else g.setColor(new Color(138, 227, 162));
			g.fillRect(pos_x, pos_y, WIDTH, HEIGHT);
		}
	}
}
