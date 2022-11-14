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
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import static java.util.Collections.list;
import java.util.Enumeration;
import javax.swing.AbstractButton;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ButtonModel;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

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
    private JPanel game_field;
    private JPanel game_settings;
    private boolean currentPlayer;
    private int[] mapsize;
    private JList<String> size_option;
    private JRadioButton p_X;
    private JRadioButton p_O;
    private ButtonGroup mbtns;
    
    
    public static void main(String[] args) {
        new OOPGUI1Dolgozat();
    }

    public OOPGUI1Dolgozat() {
        frame = new JFrame("OOP-GUI 1.dolgozat");
        frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setBounds(dm.width / 2 - 150, dm.height / 2 - 125, 410, 350);
        frame.addWindowListener(new Exit());
        jbar = new JMenuBar();
        mapsize = new int[]{3, 4, 5};
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
        opt3.addActionListener(new PlacementChanged());
        opt4.addActionListener(new PlacementChanged());
        
        mbtns = new ButtonGroup();
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

    private void start() {
        container.removeAll();
        login = new JPanel();
        game = new JPanel();

        container.addTab("Bejelentkezés", login);
        container.addTab("Játék", game);

//        login.add(new JLabel("login"));
//        game.add(new JLabel("game"));
        game.setLayout(new BoxLayout(game, 0));
        game_field = new JPanel();
        game_field.setBorder(new TitledBorder("Amőba"));
        
        currentPlayer = true;

        placeGameField(mapsize[0]);
        
        game_settings = new JPanel();
        game_settings.setBorder(new TitledBorder("Beállítások"));
        game_settings.setLayout(new BoxLayout(game_settings, 1));
        
        size_option = new JList<String>(mapSizes());
        size_option.setSize(80, 30);
        size_option.addListSelectionListener(new SizeChanged());

        JScrollPane listScroll = new JScrollPane(size_option);
        listScroll.setPreferredSize(new Dimension(30, 70));

        p_X = new JRadioButton("'X' kezd");
        p_O = new JRadioButton("'O' kezd");
        
        ButtonGroup first_player = new ButtonGroup();
        
        first_player.add(p_X);
        first_player.add(p_O);
        p_X.setSelected(true);

        game_settings.add(size_option);
        game_settings.add(p_X);
        game_settings.add(p_O);

        game.add(game_field);
        game.add(game_settings);
        gameFeldAction();

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
    
    
    
    private void gameFeldAction(){
        for (Component component : game_field.getComponents()) {
            JButton button = (JButton) component;
            button.addActionListener(new Fields());
        }
        
    }

    private DefaultListModel mapSizes() {
        DefaultListModel listmodel = new DefaultListModel();

        for (int i = 0; i < mapsize.length; i++) {
            listmodel.addElement(mapsize[i] + "*" + mapsize[i]);
            
        }

        return listmodel;
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
            nums.add(i);
        }
        nums.set(nums.size() - 1, 0);
        if (selected) {
            Collections.shuffle(nums);
        }
        return nums;
    }
    
    

    private void setNumbers(ArrayList<Integer> a) {
        for (int i = 0; i < jbtns.length; i++) {
            JButton jbtn = (JButton) jbtns[i];
            jbtn.setText("" + a.get(i));
            jbtn.setBackground(btncolor);
            pin_field.setText("");
        }
    }

    private void clickExit() {
        int v = JOptionPane.showConfirmDialog(null, "Biztosan kilépsz?", "KILÉPÉS", JOptionPane.YES_NO_OPTION);
        if (v == 0) {
            System.exit(0);
        }
    }

    private void placeGameField(int size) {
        game_field.setLayout(new GridLayout(size, size));
        for (int i = 0; i < size * size; i++) {
            game_field.add(new JButton("_"));
        }

    }
    
    
    class SizeChanged implements ListSelectionListener{

        @Override
        public void valueChanged(ListSelectionEvent e) {
            int selected;

            if (e.getValueIsAdjusting()) {
                String a = size_option.getSelectedValue().charAt(0)+"";
                selected = Integer.parseInt(a);
                System.out.println(selected);
                game_field.removeAll();
                currentPlayer = p_X.isSelected();
                placeGameField(selected);
                gameFeldAction();
                game_field.updateUI();
                
            }
        }


    }
        
//    class PlacementChanged implements ActionListener{
//
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            JRadioButton rdb = (JRadioButton) e.getSource();
// 
//        }
//        
//    }
    
    class Fields implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton button = (JButton) e.getSource();
            if (currentPlayer) {
                button.setText("X");
            }else if (!currentPlayer) {
                button.setText("O");
            }
            button.setEnabled(false);
            currentPlayer = !currentPlayer; 
        }
        
    }

    class Restart implements ActionListener {

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

    class Quit implements ActionListener {

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

        private void click(ActionEvent event) {
            JButton button = (JButton) event.getSource();
            button.setBackground(Color.GRAY);
            String text = pin_field.getText();
            pin_field.setText(text + button.getText());
        }
    }

}
