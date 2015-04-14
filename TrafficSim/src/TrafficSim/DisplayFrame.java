/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrafficSim;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
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
public class DisplayFrame extends JFrame {

    private Simulator s;
    private Landscape l;
    private JPanel panel;
    private JFrame panel2;
    private boolean settingUp = true;

    public DisplayFrame() {
        this.setSize(1000, 1000);
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
        
        
//        this.setVisible(true);
        
        
//        stats();
    }
    private JLabel vehicleCount;
    private JLabel peopleCount;
    private JLabel time;

    private void stats() {
        panel2 = new JFrame();
        panel2.setSize(250, 250);
        panel2.setVisible(true);
        panel2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        panel2.setLocationRelativeTo(this);
        panel2.setLayout(new FlowLayout());
        panel2.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton startSim = new JButton("Start");
        JButton stopSim = new JButton("Stop");
        JButton resume = new JButton("Resume");
        JButton customStart = new JButton("CustomStart");
        vehicleCount = new JLabel("vehiclecounter");
        time = new JLabel("Time");
        panel2.add(startSim);
        panel2.add(stopSim);
        panel2.add(resume);
        panel2.add(customStart);
        panel2.add(vehicleCount);
        panel2.add(time);
        
        

        startSim.addActionListener(s);
        startSim.setActionCommand("Start");

        stopSim.addActionListener(s);
        stopSim.setActionCommand("Stop");

        stopSim.addActionListener(s);
        stopSim.setActionCommand("Resume");


        customStart.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("START");
                customStart();

            }
        });
        while(true) {
            vehicleCount.setText("Number of vehicles passed: " + s.getVehicleCount());
            time.setText("TIME: " + s.getTime());
        }

    }

    private void customStart() {
        JPanel myPanel = new JPanel();
        myPanel.setLayout(new FlowLayout());

        JTextField vehicleField = new JTextField(4);
        JTextField peopleField = new JTextField(4);
        myPanel.add(new JLabel("Vehicles"));
        myPanel.add(vehicleField);
        myPanel.add(Box.createHorizontalStrut(30)); // a spacer
        myPanel.add(new JLabel("People"));
        myPanel.add(peopleField);
        myPanel.add(Box.createHorizontalStrut(30));
        int result = JOptionPane.showConfirmDialog(null, myPanel, "Please Enter Values", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            try {
                s.setVehicleRate(Integer.parseInt(vehicleField.getText()));
                s.setPeopleRate(Integer.parseInt(peopleField.getText()));

            } catch (NumberFormatException e) {
                int result2 = JOptionPane.showConfirmDialog(null, "Make sure all values are numbers", "CRITICAL ERROR", JOptionPane.OK_CANCEL_OPTION);

            }
        }
    }
 
}
