package controls;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{

	public boolean spacePressed, escapePressed;

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int code = e.getKeyCode();

		if(code == KeyEvent.VK_SPACE){
			spacePressed = true;
		}
		if(code == KeyEvent.VK_ESCAPE){
			escapePressed = true;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		
		if(code == KeyEvent.VK_SPACE){
			spacePressed = false;
		}
		if(code == KeyEvent.VK_ESCAPE){
			escapePressed = false;
		}
		
	}
	
}
