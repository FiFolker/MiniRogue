package controls;

import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.SwingUtilities;

public class MouseHandler implements MouseListener, MouseMotionListener, MouseWheelListener {
	
	public int x;
	public int y;
	public Point location = new Point(0, 0);
	
	public boolean leftClicked, leftClickedOnceTime;

	@Override
	public void mouseMoved(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		location = e.getPoint();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)){
			leftClickedOnceTime = true;
		}
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(SwingUtilities.isLeftMouseButton(e)){
			leftClicked = true;
			
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {

		if (SwingUtilities.isLeftMouseButton(e)) {
			leftClicked = false;
			leftClickedOnceTime = false;
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		//nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		
		//nothing
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		//nothing
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		//nothing
	}

	
	
}
