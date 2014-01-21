/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;

/** A basic game object displayed as a yellow circle, starting in the 
 * upper left corner of the game court.
 *
 */
public class Circle extends GameObj {

	public static final int SIZE = 20;       
	public static final int INIT_POS_X = GameCourt.COURT_WIDTH/2 - SIZE;  
	public static final int INIT_POS_Y = 170; 
	public static final int INIT_VEL_X = 2;
	public static final int INIT_VEL_Y = 7;
	
	
	// For power ups
	public Circle(int velX, int velY, int posx, int posy, 
				int size, int size2, int courtWidth, int courtHeight) {
		super(velX, velY, posx, posy, 
				SIZE, SIZE, courtWidth, courtHeight);
	}

	// For Snitch
	public Circle(int courtWidth, int courtHeight) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, 
				SIZE, SIZE, courtWidth, courtHeight);
	}
	

	@Override
	public void draw(Graphics g) {
		g.setColor(new Color(184, 86, 37));
		g.fillOval(pos_x, pos_y, width, height); 
	}
	
	public void drawTail(Graphics2D g) {
		g.setStroke(new BasicStroke(10));
		GradientPaint cyantowhite = new GradientPaint(0,0,Color.CYAN,100, 0,Color.WHITE);
		// g.drawOval(pos_x + this.width/2,pos_y + this.height/2 ,pos_x - (this.v_x * 15) ,pos_y - (this.v_y * 15));
		g.setPaint(cyantowhite);
		g.fill(new Ellipse2D.Double(pos_x + this.width/2,pos_y + this.height/2 ,
				pos_x - (this.v_x * 15) ,pos_y - (this.v_y * 15)));
//		g.drawLine(pos_x + this.width/2, pos_y + this.height
//				, pos_x - (this.v_x * 15), pos_y - (this.v_y * 15));
	}

		



}