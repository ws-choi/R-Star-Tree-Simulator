package r.graphics;

import java.util.concurrent.Semaphore;

public class Sprite_thread extends Thread{
	
	myJPanel panel;
	Sprite sprite;
	Semaphore sem;
	int delay;
	
	public Sprite_thread(myJPanel panel, Sprite sprite, Semaphore sem, int delay) {
		this.panel = panel;
		this.sprite = sprite;
		this.sem = sem;
		this.delay = delay;
	}

	@Override
	public void run() {

		try {
			sem.acquire();
			while(!sprite.isArrived())
			{
				try {
					sleep(delay);
					sprite.move();
//					panel.repaint();
					panel.request_paint();
					
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
			sem.release();
			
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
	}

}
