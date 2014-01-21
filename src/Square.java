/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;

/** A basic game object displayed as a black square, starting in the 
 * upper left corner of the game court.
 *
 */
public class Square extends GameObj {
	public static final int SIZE = 20;
	public static final int HEIGHT = 20;	
	public static int WIDTH = 100;	
	public static  int INIT_X = GameCourt.COURT_WIDTH/2 - WIDTH/2;
	public static final int INIT_Y = GameCourt.COURT_HEIGHT;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;
	
	public static HashMap<String, Sound> sfx = new HashMap<String, Sound>();
	
	
    /** 
     * Note that because we don't do anything special
     * when constructing a Square, we simply use the
     * superclass constructor called with the correct parameters 
     */

	public void increaseSize() {
		WIDTH += 50;
	}
	
    public Square(int x_pos, int y_pos, int courtWidth, int courtHeight){
        super(INIT_VEL_X, INIT_VEL_Y, x_pos, y_pos, 
        	WIDTH, HEIGHT, courtWidth, courtHeight);
//        sfx.put("bounce", new Sound("/smb_fireball.mp3"));
//        //sfx.put("YAYBIG", new Sound("/smb_powerup.mp3"));
//        //sfx.put("xtralife", new Sound("/smb_1-up.mp3"));
//        //sfx.put("flop", new Sound("/smb_stomp.mp3"));
    }
    
    

    @Override
    public void draw(Graphics g) {
        g.setColor(new Color(227,185, 138));
        g.fillRect(pos_x, pos_y, width, height); 
    }

	public void decreaseWidth() {
		WIDTH -= 50;
		
	}

}
