/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TrafficSim;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

/**
 *
 * @author nikla_000
 */
public class DisplayFrame extends JFrame{
    private Simulator s;
    private Landscape l;
    private JPanel panel;
    
    public DisplayFrame() {
        this.setSize(1000,1000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        panel = new JPanel();
        s = new Simulator();
        l = new Landscape();
        
        panel.add(s);
        this.add(panel);
        
        l.init();
        s.setLandscape(l);
        s.init();
        
        this.setVisible(true);
    }
}
