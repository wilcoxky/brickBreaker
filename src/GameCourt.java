/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 * GameCourt
 * 
 * This class holds the primary game logic of how different objects 
 * interact with one another.  Take time to understand how the timer 
 * interacts with the different methods and how it repaints the GUI 
 * on every tick().
 *
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {

	// the state of the game logic
	private Square paddle;          // the Black paddle, keyboard control
	private Circle snitch;         
	private ArrayList<Brick> bricks;
	private Bottom bottom;
	private ArrayList<StrongerBrick> stgbricks;
	
	// Power Ups
	private Random rand = new Random();
		// Extra life
	private int oneup_pos = rand.nextInt(18);
	private OneUP oneup;
	private boolean oneon;
	
		// Paddle Size
	private int bp_pos = rand.nextInt(18);
	private BiggerPaddle bp;
	private boolean bigon;
	private boolean bigSq = false;
		// Invert Controls
	private int iC_pos = rand.nextInt(18);
	private InvertControls iC;
	private boolean invertOn;
	private boolean invertAct = false;
	
	//Misc...
	private int lives;
	private int score;
	
	// Sound

	private int blocksLeft;
	
	public boolean playing = false;  // whether the game is running
	private JLabel status;       // Current status text (i.e. Running...)

	// Game constants
	public static final int COURT_WIDTH = 700;
	public static final int COURT_HEIGHT = 550;
	public static final int SQUARE_VELOCITY = 9;
	// Update interval for timer in milliseconds 
	public static final int INTERVAL = 35; 
	
	private Timer timer;

	public GameCourt(JLabel status){
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
		// Sound
		Sound.main.loop();

        
        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called 
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
		timer = new Timer(INTERVAL, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// this key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually 
		// moves the square.)
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_LEFT)
					paddle.v_x = -SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
					paddle.v_x = SQUARE_VELOCITY;
				else if (e.getKeyCode() == KeyEvent.VK_DOWN)
					paddle.v_y = 0;
				else if (e.getKeyCode() == KeyEvent.VK_UP)
					paddle.v_y = 0;
			}
			public void keyReleased(KeyEvent e){
				paddle.v_x = 0;
				paddle.v_y = 0;
			}
		});

		this.status = status;
	}

	/** (Re-)set the state of the game to its initial state.
	 */
	public void reset() {
		
		timer.start(); 
		timer.setInitialDelay(1000);
		timer.restart();
		
		Sound.main.loop();

		
		snitch = new Circle(COURT_WIDTH, COURT_HEIGHT);
		bricks = new ArrayList<Brick>();
		bottom = new Bottom(COURT_WIDTH, COURT_HEIGHT);
		stgbricks = new ArrayList<StrongerBrick>();
		
		
		// Power Ups
		oneon = false;
		
		invertOn = false;
		
		// Because of previous games
		if (invertAct) {
			invertAct = false;
			addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent e){
					if (e.getKeyCode() == KeyEvent.VK_LEFT)
						paddle.v_x = -SQUARE_VELOCITY;
					else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
						paddle.v_x = SQUARE_VELOCITY;
					else if (e.getKeyCode() == KeyEvent.VK_DOWN)
						paddle.v_y = 0;
					else if (e.getKeyCode() == KeyEvent.VK_UP)
						paddle.v_y = 0;
				}
				public void keyReleased(KeyEvent e){
					paddle.v_x = 0;
					paddle.v_y = 0;
				}
			});
		}
		
		bigon = false;
		
		// Because of previous games
		if (bigSq) {
			paddle.decreaseWidth();
			bigSq = false;
		}
		
		
		lives = 3;
		score = 0;
		
		
		// 35 total blocks
		for (int i = 1; i < 7; i++) {
			for (int j = 1; j < 4; j++) {
				Brick b = new Brick((i*89), (j*25));
				bricks.add(b);
			}
		}
		
		for (int i = 1; i < 7; i++) {
			for (int j = 1; j< 3; j++) {
				StrongerBrick b = new StrongerBrick((i*89),75 + (j*25));
				stgbricks.add(b);
			}
		}
		
		paddle = new Square(COURT_WIDTH/2 - 10, COURT_HEIGHT, COURT_WIDTH, COURT_HEIGHT);
		
		// Create PowerUP placement
		oneup = new OneUP(bricks.get(oneup_pos).pos_x,bricks.get(oneup_pos).pos_y, COURT_WIDTH, COURT_HEIGHT);
		bp = new BiggerPaddle(bricks.get(bp_pos).pos_x,bricks.get(bp_pos).pos_y, COURT_WIDTH, COURT_HEIGHT);
		iC = new InvertControls(bricks.get(iC_pos).pos_x,bricks.get(iC_pos).pos_y, COURT_WIDTH, COURT_HEIGHT);

				
		// Blocks left to destrroy till win
		blocksLeft = bricks.size() + stgbricks.size();
			
		playing = true;
		status.setText("TRY TO WIN!");

		// Make sure that this component has the keyboard focus
		requestFocusInWindow();
	}
	
	public void helpon() {
		timer.stop();

	}
	//when help screen is exited
	public void helpoff() {
		timer.start();
		requestFocusInWindow();

	}

    /**
     * This method is called every time the timer defined
     * in the constructor triggers.
     */
	void tick(){
		if (playing) {
			
			// advance the square and snitch in their
			// current direction.
			paddle.move();
			snitch.move();
			
			
			// make the snitch bounce off walls...
			snitch.bounce(snitch.hitWall());
			
			
			// End Game
			if(lives < 1) {
				Sound.main.stop();
				Sound.gameOver.play();
				playing = false;
				status.setText("You lose!");
			}
			else if(blocksLeft != 0) {
				playing = true;
			}
			else {
				Sound.WIN.play();
				Sound.main.stop();
				playing = false;
				status.setText("You WIN!!!!");
			}
			
			// Make hit square, changed the method because bounce did not register correctly...
			
			if (snitch.intersects(paddle)) {
				Sound.paddlebounce.play();

				// If intersect within 20 pixels close to the center, do a perfect reflect, else change the velocity
				// by adding whatever direction the paddle is going to the ball
				if ((paddle.pos_x + paddle.width / 2 ) % (snitch.pos_x + snitch.width / 2) > 10
						|| (snitch.pos_x + snitch.width / 2) % (paddle.pos_x
								+ paddle.width / 2 ) < 10) {
					snitch = new Circle(snitch.v_x, -snitch.v_y, snitch.pos_x,
							snitch.pos_y, snitch.height, snitch.width,
							COURT_WIDTH, COURT_HEIGHT);
				}

				else {
					snitch = new Circle((snitch.v_x + paddle.v_x/2),
							-snitch.v_y, snitch.pos_x, snitch.pos_y,
							snitch.height, snitch.width, COURT_WIDTH,
							COURT_HEIGHT);
				}
			}

			// Check if ball hits bottom
			if (snitch.intersects(bottom)) {
				bigon = false;
				invertOn = false;
				oneon = false;
				score --;
				lives --;
				snitch = new Circle(2, 6, COURT_WIDTH/2, 150 , snitch.width, snitch.height, COURT_WIDTH, COURT_HEIGHT);
				
				// Delay new ball
				timer.setInitialDelay(1000);
				timer.restart();
				
				// Reset Paddle
				if (bigSq) {
					paddle.decreaseWidth();
					bigSq = false;
				}
				paddle = new Square(COURT_WIDTH/2 - 10, COURT_HEIGHT, COURT_WIDTH, COURT_HEIGHT);
				
				if (invertAct) {
					invertAct = false;
					addKeyListener(new KeyAdapter(){
						public void keyPressed(KeyEvent e){
							if (e.getKeyCode() == KeyEvent.VK_LEFT)
								paddle.v_x = -SQUARE_VELOCITY;
							else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
								paddle.v_x = SQUARE_VELOCITY;
							else if (e.getKeyCode() == KeyEvent.VK_DOWN)
								paddle.v_y = 0;
							else if (e.getKeyCode() == KeyEvent.VK_UP)
								paddle.v_y = 0;
						}
						public void keyReleased(KeyEvent e){
							paddle.v_x = 0;
							paddle.v_y = 0;
						}
					});
				}
			}
			// Check if ball hits brick	
			for (int i = 0; i < bricks.size(); i++) {
				if (bricks.get(i).getAlive()) {
					if (snitch.intersects(bricks.get(i))) {
						// Bounce
						
						// Handle when it hits the corners of the bricks - still goes down instead of bouncing up
						if(snitch.pos_x == bricks.get(i).pos_x + bricks.get(i).width || 
								snitch.pos_x + snitch.width/2 == bricks.get(i).pos_x) {
							snitch = new Circle(-snitch.v_x, -snitch.v_y,
									snitch.pos_x, snitch.pos_y, snitch.height,
									snitch.width, COURT_WIDTH, COURT_HEIGHT);
						}
						else {
						snitch = new Circle(snitch.v_x, -snitch.v_y,
								snitch.pos_x, snitch.pos_y, snitch.height,
								snitch.width, COURT_WIDTH, COURT_HEIGHT);
						}

						bricks.get(i).setBoolean();
						Sound.breakbrick.play();
						// snitch.bounce(snitch.hitObj(bricks.get(i)));
						blocksLeft--;
						score++;
					}
				}
			}
			
			// Check if ball hits brick	
			for (int i = 0; i < stgbricks.size(); i++) {
				if (stgbricks.get(i).getAlive()) {
					if (snitch.intersects(stgbricks.get(i))) {
						
						// Sound
						Sound.StrongBounce.play();
						// Bounce
						snitch = new Circle(snitch.v_x, -snitch.v_y, snitch.pos_x, snitch.pos_y, 
								snitch.height, snitch.width, COURT_WIDTH, COURT_HEIGHT);

						stgbricks.get(i).hits --;
						stgbricks.get(i).setBoolean();
						if(!stgbricks.get(i).getAlive()) {
							Sound.breakbrick.play();
							score ++;
							blocksLeft --;
						}
					}
				}
			}
			
			if (!bricks.get(oneup_pos).getAlive()) {
				oneon = true;
				oneup.move();
				if (paddle.intersects(oneup)) {
					Sound.life.play();
					oneup.pos_y = COURT_HEIGHT - 50;
					oneup.pos_x = 190;
					oneup.setVel(0,0);
					oneon = false;
					lives ++;

				}
				else if (oneup.intersects(bottom)) {
					oneup.pos_y = COURT_HEIGHT - 50;
					oneup.pos_x = 190;
					oneup.setVel(0,0);
					oneon = false;
				}
				
				
			}
			if (!bricks.get(bp_pos).getAlive()) {
				bigon = true;
				bp.move();
				if (paddle.intersects(bp)) {
					Sound.big.play();
					// Reset power up spot
					bp.pos_y = COURT_HEIGHT - 50;
					bp.pos_x = 150;
					bp.setVel(0,0);
					bigon = false;
					bigSq = true;
					
					// Make new paddle
					paddle.increaseSize();
					int save = paddle.pos_x;
					paddle = new Square(save, COURT_HEIGHT - paddle.height, COURT_WIDTH, COURT_HEIGHT);
				}
				else if (bp.intersects(bottom)) { 
					bp.pos_y = COURT_HEIGHT - 50;
					bp.pos_x = 150;
					bp.setVel(0,0);
					bigon = false;
				}

			}
			if (!bricks.get(iC_pos).getAlive()) {
				invertOn = true;
				iC.move();
				if (paddle.intersects(iC)) {
					Sound.flop.play();
					// Reset power up spot
					iC.pos_y = COURT_HEIGHT - 50;
					iC.pos_x = 170;
					iC.setVel(0,0);
					invertOn = false;
					invertAct = true;
					addKeyListener(new KeyAdapter(){
						public void keyPressed(KeyEvent e){
							if (e.getKeyCode() == KeyEvent.VK_LEFT)
								paddle.v_x = SQUARE_VELOCITY;
							else if (e.getKeyCode() == KeyEvent.VK_RIGHT)
								paddle.v_x = -SQUARE_VELOCITY;
							else if (e.getKeyCode() == KeyEvent.VK_DOWN)
								paddle.v_y = 0;
							else if (e.getKeyCode() == KeyEvent.VK_UP)
								paddle.v_y = 0;
						}
						public void keyReleased(KeyEvent e){
							paddle.v_x = 0;
							paddle.v_y = 0;
						}
					});
				
				}
				else if (iC.intersects(bottom)) { 
					iC.pos_y = COURT_HEIGHT - 50;
					iC.pos_x = 170;
					iC.setVel(0,0);
					invertOn = false;
				}
			}
			

			
		
			// update the display
			repaint();
		} 
	}

	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		g.setColor(new Color(251, 252,197));
		g.fillRect(0, 0, COURT_WIDTH, COURT_HEIGHT);
		paddle.draw(g);
		snitch.draw(g);
		for (int i = 0; i < bricks.size(); i++) {
			bricks.get(i).draw(g);
		}
		
		for (int i = 0; i < stgbricks.size(); i++) {
			stgbricks.get(i).draw(g);
		}
		
		g.setColor(new Color(157,153,204));
		g.drawString("Lives", 10, COURT_HEIGHT - 10);
		g.drawString(Integer.toString(lives), 50, COURT_HEIGHT - 10);
		g.drawString("Score: ", 75 , COURT_HEIGHT - 10);
		g.drawString(Integer.toString(score), 125, COURT_HEIGHT - 10);
		g.drawString("Power Ups Dropped: ", 10, COURT_HEIGHT - 40);
		if(oneon) {
			oneup.draw(g);
		}
		if(bigon) {
			bp.draw(g);
		}
		if(invertOn) {
			iC.draw(g);
		}
		// snitch.drawTail(twog);

		


	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}
}
