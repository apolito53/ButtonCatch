import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class CountdownPanel extends JPanel implements ActionListener
{
    Timer timer;
    int count;
    JLabel imgLbl = new JLabel();
    Icon imgOne, imgTwo, imgThree;
    
    public CountdownPanel()
    {   
        setVisible(false);
        setFocusable(false);
        
        imgOne = new ImageIcon(getClass().getClassLoader().getResource("One.png"));
        imgTwo = new ImageIcon(getClass().getClassLoader().getResource("Two.png"));
        imgThree = new ImageIcon(getClass().getClassLoader().getResource("Three.png"));
        
        setSize(100, 100);
        setLayout(new FlowLayout());
        add(imgLbl);
        imgLbl.setSize(100, 100);
        
        timer = new Timer(1000, this);
        count = 0;
        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    {
        count++;
        switch(count)
        {
            case 1:
                setVisible(true);
                imgLbl.setIcon(imgThree);
                GameFrame.frame.countdownBeep.play();
                break;
            case 2:
                imgLbl.setIcon(imgTwo);
                GameFrame.frame.countdownBeep.play();
                break;
            case 3:
                imgLbl.setIcon(imgOne);
                GameFrame.frame.countdownBeep.play();
                break;
            case 4:
                count = 0;
                setVisible(false);
                GameFrame.frame.start();
                timer.stop();
                GameFrame.frame.countdownBeep.play();
                break;
        }
    }
}
