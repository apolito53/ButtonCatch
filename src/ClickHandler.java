import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
    
public class ClickHandler implements ActionListener
{
    FunButton fBtn;
    public int count = 0;
    
    public ClickHandler(FunButton fb)
    {
        fBtn = fb;
    }   //sets class variable fBtn equal to FunButton passed to constructor
        
    @Override
    public synchronized void actionPerformed(ActionEvent ev)
    {
        if(ev.getSource() == fBtn)  //if button is clicked
        {
            GameFrame.frame.updateScore();
            GameFrame.frame.background.stop();
            fBtn.stop();
            fBtn.setVisible(false);
            
            int n = JOptionPane.showConfirmDialog(GameFrame.frame, "You Win!\n" + "Play again?", 
                    "You Win!", JOptionPane.YES_NO_OPTION);
                //Dialog box that dispalys if button is clicked
                //Prompts user with yes/no box
                
            if(n == 0) //if yes
            {
                fBtn.setLocation(GameFrame.randomPoint()); 
                    //call randomPoint() method of FunButton and setLocation
                    //of btn to that location
                fBtn.hh.right = fBtn.getRandomBoolean();
                    //randomize horizontal direction
                fBtn.vh.up = fBtn.getRandomBoolean();
                    //randomize vertical direction
                GameFrame.frame.countdown();
                    //displays countdown panel again, resetting button
            } else if(n == 1) //if no
            {
                System.exit(0); //exit application
            } //end if
        }  //end if
    } //end actionPerformed
} //end ClickHandler