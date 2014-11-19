import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeHandler implements ActionListener
{
    @Override
    public synchronized void actionPerformed(ActionEvent ev)
    {
        GameFrame.frame.updateTime();
    } //end Handler
}