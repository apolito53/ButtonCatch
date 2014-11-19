import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import javax.swing.*;

public class GameFrame extends JFrame
{
    public static GameFrame frame;
    public FunButton btn;
    boolean shrunken;
    String[] strArray;
    int difficulty;
    int score, time;
    ClickHandler ch;
    TimeHandler th;
    Icon pic;
    JLabel lblScore, lblTime;
    public Timer background;
    protected Sound bounce, countdownBeep;

    public GameFrame()
    {
        pic = new ImageIcon
                (getClass().getClassLoader().getResource("Spellfire.png"));
            //sets image of button
        strArray = new String[]{"EASY", "HARD", "IMPOSSIBLE"};
            //Strings to be used in followin JOptionPane
        difficulty = JOptionPane.showOptionDialog
            (rootPane, 
            "Select Difficulty: ", 
            "Difficulty", 
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE, 
            null, 
            strArray, 
            null);
            //gets a value of 0-2 from user via option dialog
            //value is to be used for difficulty of game
        score = 0;
        time = 0;
        shrunken = false;
        bounce = new Sound("Blop.wav");
        countdownBeep = new Sound("CountdownBell.wav");
                
        setTitle("Button Catch");       
            //sets title of window
        setLayout(null);                
            //null layout, allows & requires all objects to be placed and sized
        setResizable(false);            
            //makes window unresizeable
        getContentPane().setBackground(Color.black);
            //sets background color of window to black
        setLocation(500, 200);
 
////////////////////////////BUTTON INITIALIZATION/////////////////////////////// 
        btn = new FunButton(difficulty);
        btn.setIcon(pic);                
            //sets picture of button
        ch = new ClickHandler(btn);            
            //new button handler
        btn.addActionListener(ch);        
            //add button handler to button
        add(btn);                        
            //add button to frame
        btn.setBounds(randomInt(800), randomInt(600), 75, 75);
            //set size of button to 75, 75 and places it at 
            //a random location on the frame
        
/////////////////////BACKGROUND TIMER INITIALIZATION////////////////////////////
        th = new TimeHandler();
        background = new Timer(1000, th);
        
////////////////////////SCORE LABEL INITIALIZATION//////////////////////////////
        lblTime = new JLabel("Time: 0 Seconds");
        add(lblTime);
        lblTime.setBounds(600, 20, 100, 20);
        lblTime.setForeground(Color.white);
        lblScore = new JLabel("Score: 0");
        add(lblScore);
        lblScore.setBounds(50, 20, 100, 20);
        lblScore.setForeground(Color.white);

        countdown();
            //see method
        
//////////////////////// BUTTON MOUSEOVER CODE ////////////////////////////////
        btn.addMouseListener(new MouseAdapter() 
        {
            int count = 0; //keeps track of time in milliseconds
            int coolCount = 0;
            boolean running;
            boolean cd;
            
            private ActionListener listener = new ActionListener()
                //listener for mouseover timers
            {
                @Override
                public synchronized void actionPerformed(ActionEvent ev)
                {
                    //if shrinkTimer ticks
                    if(ev.getSource() == shrinkTimer)
                    {
                        count++;
                    }
                    
                    //if cooldown ticks
                    if(ev.getSource() == cooldown)
                    {
                        coolCount++;
                    }
                    
                    //if watcher ticks
                    if(ev.getSource() == watcher)
                    {
                        if(shrinkTimer.isRunning())
                        {
                            running = true;
                        } else
                        {
                            running = false;
                        }
                        
                        if(count == 1) //1 second
                        {
                            shrinkTimer.stop();
                            cooldown.start();
                            cd = true;
                            count = 0;
                            normalize();
                        }
                        if(coolCount == 1)
                        {
                            cd = false;
                            cooldown.stop();
                            coolCount = 0;
                        }
                    }
                    
                    try
                    {
                        wait(1);
                    } catch(Exception ex){}
                }
            };
            
            private Timer shrinkTimer = new Timer(1000, listener); //timer handling shrinkage of button
            private Timer watcher = new Timer(25, listener);     //timer that controls shrinkTimer
            private Timer cooldown = new Timer(1000, listener);    //shrink cooldown timer
            
            //generates a random point within the area of the original sized
            //button and places the new small button at that point
            private void randomSmallPoint()
            {
                int x = btn.getLocation().x;
                int y = btn.getLocation().y;
                
                Point np = new Point((x + randomNum()), (y + randomNum()));
                
                btn.setLocation(np);
            }   
            
            //generates a random number from 1-75
            private int randomNum()
            {
                Random rnd = new Random();
                int n = rnd.nextInt(75);
                return n;
            }
            
            //shrinks button and starts shrinkTimer
            private void shrink()
            {
                shrunken = true;
                btn.setSize(new Dimension(15, 15));
                randomSmallPoint();
                shrinkTimer.start();
                watcher.start();
            }
            
            //return button to normal size
            private void normalize()
            {
                shrunken = false;
                btn.setSize(new Dimension(75, 75));
                btn.setLocation(randomPoint());
                //btn.startTimers();
            }
            
            //if mouse enters button
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                if(!running && !cd)
                {
                    shrink();
                }
            }
            
            //if mouse leaves button (not used)
            @Override
            public void mouseExited(MouseEvent e){}
        });
//////////////////////// END BUTTON MOUSEOVER CODE ////////////////////////////
    }   //End Constructor

    
    public static void main(String[] args) 
    {
        frame = new GameFrame();  //instantiate frame
        frame.setVisible(true);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
    } //end main
    
    public void updateTime()
    //updates lblTime
    {
        time++;
        lblTime.setText("Time: " + time +" seconds");
    } //end updateTime
    
    public void updateScore()
        //increments score based on selected difficulty and
        //updates lblScore accordingly
    {
        if(difficulty == 0)
        {
            if(shrunken)
            {
                score += 3;
            } else
            {
                score++;
            }
        } else if(difficulty == 1)
        {
            if(shrunken)
            {
                score += 20;
            } else
            {
                score += 2;
            }
        } else if(difficulty == 2)
        {
            if(shrunken)
            {
                score += 100;
            } else
            {
                score += 5;
            }
        }
        
        lblScore.setText("Score: " + score);
    }
    private int randomInt(int x)
    //return random integer between 1 and x
    {
        Random rnd = new Random();
        int n = rnd.nextInt(x);
        
        return n;
    } //end randomInt
    
    public static Point randomPoint()
    //returns random point within dimension xSize, ySize
    {
        Random rnd = new Random();
        Point p = new Point(rnd.nextInt(800), rnd.nextInt(600));
        return p;
    }   
    
    public final void countdown()
    {
        CountdownPanel cdPanel = new CountdownPanel();
        add(cdPanel);
        cdPanel.setBounds(350, 250, 100, 100);
    }
    
    public final void start()
    {
        btn.startTimers();
        background.start();        
    }
}
