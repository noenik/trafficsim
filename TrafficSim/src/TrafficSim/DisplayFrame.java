/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package TrafficSim;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.WindowConstants;

/**
 *
 * @author nikla_000
 */
public class DisplayFrame extends JFrame{
    private Simulator s;
    private Landscape l;
    private JPanel panel;
    private JFrame panel2;
    public DisplayFrame() {
        this.setSize(1000,1000);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel = new JPanel();
        this.setLocationRelativeTo(null);
        s = new Simulator();
        l = new Landscape();
        
        panel.add(s);
        this.add(panel);
        
        l.init();
        s.setLandscape(l);
        s.init();
        
        this.setVisible(true);
        stats();
    }
    private JLabel vehicleCount;
    private JLabel peopleCount;
    
   public void stats()
   { panel2 = new JFrame();
     panel2.setSize(250, 250);
     panel2.setVisible(true);
     panel2.setLocationRelativeTo(this);
     panel2.setLayout(new FlowLayout());
     JButton startSim = new JButton("Start");
     JButton vehicle = new JButton("Populatevehicles");
     JButton people = new JButton("Populatepeople");
     JButton stopSim = new JButton("Stop");
     JButton resume = new JButton("Resume");
     JButton customStart = new JButton("CustomStart");
     vehicleCount = new JLabel("vehiclecounter");
     peopleCount = new JLabel("peoplecounter");
   
     panel2.add(startSim);
     panel2.add(stopSim);
     panel2.add(resume);
     panel2.add(vehicle);
     panel2.add(people);
     panel2.add(customStart);
     panel2.add(vehicleCount);
     panel2.add(peopleCount);
     
     
     
        startSim.addActionListener(s);
        startSim.setActionCommand("Start");
      
        stopSim.addActionListener(s);
        stopSim.setActionCommand("Stop");
        
        stopSim.addActionListener(s);
        stopSim.setActionCommand("Resume");
        
        vehicle.addActionListener(s);
        vehicle.setActionCommand("Populatevehicles");
        
        people.addActionListener(s);
        people.setActionCommand("Populatepeople");
        
         
        customStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("START");
                customStart();
                
            }
        });
     
        
       
     
       
   }
   private void customStart() {
        try {
            JTextField vehicleField = new JTextField(4);
            JTextField peopleField = new JTextField(4);
            
            panel2.add(new JLabel("Vehicles"));
            panel2.add(vehicleField);
            panel2.add(Box.createHorizontalStrut(30)); // a spacer
            panel2.add(new JLabel("People"));
            panel2.add(peopleField);
            panel2.add(Box.createHorizontalStrut(30));
            int result = JOptionPane.showConfirmDialog(null, panel2, "Please Enter Values", JOptionPane.OK_CANCEL_OPTION);
            int vehicleValue = Integer.parseInt(vehicleField.getText());
            int peopleValue = Integer.parseInt(peopleField.getText());
            
        } catch (NumberFormatException e) {
            int result = JOptionPane.showConfirmDialog(null, "Make sure all values are numbers", "CRITICAL ERROR", JOptionPane.OK_CANCEL_OPTION);
            
        }
    }
   
   
}

