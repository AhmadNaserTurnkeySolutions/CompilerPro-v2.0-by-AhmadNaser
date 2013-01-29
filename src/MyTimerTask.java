/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */


import gui.ImagePanel;

import java.util.TimerTask;
import javax.swing.JFrame;

/**
 *
 * @author IBM
 */
public class MyTimerTask extends TimerTask {
  public  JFrame f;
  private static int count = 0;
  ImagePanel imagePanel1;
  ImagePanel imagePanel2;
  
  public MyTimerTask(JFrame f, ImagePanel imagePanel1, ImagePanel imagePanel2)
  {
  this.f=f;
this.imagePanel1=imagePanel1;
this.imagePanel2=imagePanel2;
  }

    @Override
    public void run() {
    	count++;
        if (count >= 5) {
            this.cancel();
            //this.purge();
            return;
        }
       f.repaint();
       this.imagePanel1.repaint();
       this.imagePanel2.repaint();

      
    }
    
}
