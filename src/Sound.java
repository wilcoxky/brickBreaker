import javax.sound.sampled.*;

public class Sound {
	
	// Made to be used whenever and reduce lag
	public static final Sound breakbrick = new Sound("/smb_breakblock.mp3");
    public static final Sound paddlebounce = new Sound("/smb_fireball.mp3");
    public static final Sound big = new Sound("/smb_powerup.mp3");
    public static final Sound life = new Sound("/smb_1-up.mp3");
    public static final Sound flop = new Sound("/smb_stomp.mp3");
	public static final Sound gameOver = new Sound("/smb_gameover.mp3");
	public static final Sound wallbounce = new Sound("/smb_kick.mp3");
	public static final Sound main = new Sound("/juicy.mp3");
	public static final Sound WIN = new Sound("/win.mp3");
	public static final Sound StrongBounce = new Sound("/strg.mp3");
	
	private Clip clip;

	public Sound(String s) {
		try{
			AudioInputStream ais = AudioSystem.getAudioInputStream
					(getClass().getResourceAsStream(s));
			AudioFormat baseFormat = ais.getFormat();
			AudioFormat decodeFormat = new AudioFormat(
					AudioFormat.Encoding.PCM_SIGNED, baseFormat.getSampleRate()
					, 16 ,
					baseFormat.getChannels(),
					baseFormat.getChannels() * 2,
					baseFormat.getSampleRate(),
					false);
			AudioInputStream dais = AudioSystem.getAudioInputStream(decodeFormat, ais);
			clip = AudioSystem.getClip();
			clip.open(dais);


		}		
		catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void play() {
		if(clip == null) return;
		stop();
		clip.setFramePosition(0);
		clip.start();
	}
	
	public void stop() {
		if(clip.isRunning()) clip.stop();
	}
	
	public void close() {
		stop();
		clip.close();
	}
	
	public void loop() {
		stop();
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

}
