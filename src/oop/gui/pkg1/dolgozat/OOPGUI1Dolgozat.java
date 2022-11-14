/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package oop.gui.pkg1.dolgozat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;

/**
 *
 * @author gyorgy.krisztian
 */
public class OOPGUI1Dolgozat implements ActionListener {

    private JFrame frame;
    private Component[] jbtns;
    private JMenuBar jbar;
    private JTabbedPane container;
    private JPanel login;
    private JPanel game;
    private JTextField pin_field;
    private JCheckBox shuffle;
    private JPanel pin_panel;
    private JPanel settings;
    private Color btncolor;
    
    public static void main(String[] args) {
        new OOPGUI1Dolgozat();
    }

    public OOPGUI1Dolgozat() {
        frame = new JFrame("OOP-GUI 1.dolgozat");
        
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(dm.width / 2 - 150, dm.height / 2 - 125, 410, 350);
        frame.addWindowListener(new Exit());
        jbar = new JMenuBar();

        JMenu mopt1 = new JMenu("Program");
        JMenu mopt2 = new JMenu("Játék elrendezése");
        jbar.add(mopt1);
        jbar.add(mopt2);

        JMenuItem opt1 = new JMenuItem("Újra");
        JMenuItem opt2 = new JMenuItem("Kilépés");
        mopt1.add(opt1);
        mopt1.add(opt2);

        opt2.addActionListener(new Quit());
        
        JRadioButtonMenuItem opt3 = new JRadioButtonMenuItem("Vizszintes");
        JRadioButtonMenuItem opt4 = new JRadioButtonMenuItem("Függőleges");
        ButtonGroup mbtns = new ButtonGroup();
        mbtns.add(opt4);
        mbtns.add(opt3);
        mopt2.add(opt3);
        mopt2.add(opt4);
        opt3.setSelected(true);

        opt1.addActionListener(new Restart());
        
        container = new JTabbedPane();
        
        start();
        frame.setResizable(false);
        frame.setJMenuBar(jbar);
        frame.add(container);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        setNumbers(shuffle(shuffle.isSelected()));
    }
    
    private void start(){
        container.removeAll();
        login = new JPanel();
        game = new JPanel();
        login.updateUI();
        game.updateUI();
        container.addTab("Bejelentkezés", login);
        container.addTab("Játék", game);

//        login.add(new JLabel("login"));
//        game.add(new JLabel("game"));
        pin_panel = new JPanel();
        settings = new JPanel();
        pin_panel.setBorder(new TitledBorder("Pin kód"));
        settings.setBorder(new TitledBorder("Beállítások"));

        login.setLayout(new GridLayout(1, 2));
        pin_panel.setLayout(new GridLayout(4, 3));
        settings.setLayout(new GridLayout(3, 1));

        login.add(pin_panel);
        login.add(settings);

        shuffle = new JCheckBox("Kever");
        shuffle.setSelected(false);
        JLabel string1 = new JLabel("Kód:");
        pin_field = new JTextField();

        settings.add(shuffle);
        settings.add(string1);
        settings.add(pin_field);
        createButtons(pin_panel);

        setPinAction(jbtns);

        shuffle.addActionListener(this);
        
    }

    private void createButtons(JPanel parent) {
        for (int i = 0; i < 10; i++) {
            parent.add(new JButton("" + (i + 1)));
        }
        jbtns = parent.getComponents();
        JButton last = (JButton) jbtns[jbtns.length - 1];
        last.setText("0");
        btncolor = last.getBackground();
    }

    private void setPinAction(Component[] cs) {
        for (Component c : cs) {
            JButton button = (JButton) c;
            button.addActionListener(new Pins());
        }
    }

    private ArrayList shuffle(boolean selected) {
        ArrayList<Integer> nums = new ArrayList<Integer>();
        for (int i = 1; i < 11; i++) {
            nums.add(i);}
        nums.set(nums.size()-1, 0);
        if(selected){
            Collections.shuffle(nums);
        }
        return nums;
    }
    
    private void setNumbers(ArrayList<Integer> a){
        for (int i = 0; i < jbtns.length; i++) {
            JButton jbtn = (JButton) jbtns[i];
            jbtn.setText(""+a.get(i));
            jbtn.setBackground(btncolor);
            pin_field.setText("");
        }
    }
            private void clickExit(){
            int v = JOptionPane.showConfirmDialog(null, "Biztosan kilépsz?", "KILÉPÉS", JOptionPane.YES_NO_OPTION);
            if (v == 0) {
            System.exit(0);
        }
        }
    
    class Restart implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            start();
        }
        
    }

    class Exit implements WindowListener {

        @Override
        public void windowOpened(WindowEvent e) {
           
        }

        @Override
        public void windowClosing(WindowEvent e) {
            clickExit();
        }

        @Override
        public void windowClosed(WindowEvent e) {
           
        }

        @Override
        public void windowIconified(WindowEvent e) {
            
        }

        @Override
        public void windowDeiconified(WindowEvent e) {
           
        }

        @Override
        public void windowActivated(WindowEvent e) {
            
        }

        @Override
        public void windowDeactivated(WindowEvent e) {
           
        }
 
        
    }
    
    class Quit implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
           clickExit();
        }
        
    }
    class Pins implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            click(e);
        }

        private void click(ActionEvent event){
            JButton button = (JButton) event.getSource();
            button.setBackground(Color.GRAY);
            String text = pin_field.getText();
            pin_field.setText(text + button.getText());
        }
    }

}
