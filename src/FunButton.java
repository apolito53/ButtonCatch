import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class FunButton extends JButton
{
    public Timer vrtTmr;    //timer handling vertical movement
    public Timer hrTmr;     //timer handling horizontal movement
    public Timer runner;    //timer controlling the other two timers
    VerticalTimerHandler vh = new VerticalTimerHandler();   
        //ActionListener for vertical timer
    HorizontalTimerHandler hh = new HorizontalTimerHandler();
        //ActionListener for horizontal timer
    int difficulty;     //variable determining how fast button moves
    int xRate;
    int yRate;
    
    public FunButton(int x)
    {
        setFocusable(false);
        setVisible(false);
        setEnabled(false);
        
        difficulty = x;
        
        xRate = randomInt();
        yRate = randomInt();       
        
        vrtTmr = new Timer(xRate, vh);
        hrTmr = new Timer(yRate, hh);
    }
    
    public class VerticalTimerHandler implements ActionListener
    {
        public boolean up = getRandomBoolean();
        
        @Override
        public synchronized void actionPerformed(ActionEvent e) 
        {
            int y = getY();
            if(up == true)
            {
                y -= (1 * (1 + difficulty));
            }   //if up is true, move button up at rate of 1 * 1 + difficulty [1 to 3]
            else if(up == false)
            {
                y += (1 * (1 + difficulty));
            }   //if up is false, move button down ""           ""          ""          ""
            
            if(getBounds().getMinY() <= 0)
            {
                change();  
                y = 1;
                GameFrame.frame.bounce.play();
            }   //changes y direction if button hits top of frame
            else if(getBounds().getMaxY() > getParent().getHeight())
            {
                y = (int) ((getParent().getHeight()) - (getBounds().height));
                 //set y location equal to height of frame minus height of button
                change();
                GameFrame.frame.bounce.play();
            }   //changes y direction if button hits bottom of frame
            
            setLocation(getX(), y);
            
            try
            {
                wait(1);
            } catch(Exception ex){}
        }
        
        public void change()
        {
            if(up == true)
            {
                up = false;
            }
            else if(up == false)
            {
                up = true;
            }
        }   //reverses up variable
    }
    
    public class HorizontalTimerHandler implements ActionListener
    {
        public boolean right = getRandomBoolean();
        
        @Override
        public synchronized void actionPerformed(ActionEvent e)
        {
            int x = getX();
            
            if(right == true)
            {
                x += (1 * (1 + difficulty));
            }   //if right is true, move button right at rate of 1 * 1 + difficulty [1 to 3]
            else if(right == false)
            {
                x -= (1 * (1 + difficulty));
            }   //if right is false, move button left ""        ""          ""          ""
            
            if(getBounds().getMinX() <= 0)
            {
                change();
                x = 1;
                GameFrame.frame.bounce.play();
            }   //changes x direction if button hits left side of frame
            else if(getBounds().getMaxX() > getParent().getWidth())
            {
                x = (int) (getParent().getWidth() - (getBounds().width));
                //set x location equal to width of frame minus width of button
                change();
                GameFrame.frame.bounce.play();
            }   //changes x direction if button hits right side of frame
            
            setLocation(x, getY());
            
            try
            {
                wait(1);
            } catch(Exception ex){}
        }
        
        public void change()
        {
         if(right == true)
            {
                right = false;
            }
            else if(right == false)
            {
                right = true;
            }
        }   //reverses variable right 
    }
    
    //stops both timers
    public void stop()
    {
        hrTmr.stop();
        vrtTmr.stop();
    }
    
    //restarts both timers again and randomizes the delay
    public final void startTimers()
    {
        setVisible(true);
        setEnabled(true);
        vrtTmr = new Timer(randomInt(), vh);
        hrTmr = new Timer(randomInt(), hh);
        vrtTmr.start();
        hrTmr.start();
        hrTmr.setDelay(randomInt());
        vrtTmr.setDelay(randomInt());
    }
    
    //returns a random boolean value
    public boolean getRandomBoolean()
    {
        Random rnd = new Random();
        return rnd.nextBoolean();
    }   
    
    //returns a random integer between 1 and 10
    private int randomInt()
    {
        Random rnd = new Random();
        int n = 0;
        
        if(difficulty == 0)
        {
            n = 10;
        } else if(difficulty == 1)
        {
            n = 5;
        } else if(difficulty == 2)
        {
            n = 1;
        } 
        
        return n;
    }   
}