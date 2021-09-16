/*
* Previously: PlantLayer.java
* File: Gui.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Status: In progress
*/

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.color.*;

//import java.io.File;
import java.io.File;
public class Gui extends JPanel implements ActionListener{
    private imgPanel mainPanel;
    private JButton load;
    private JFileChooser fChooser;
    private JFrame frame;
    private JFrame loadIn;
    private JMenuItem i1,i2,i3;
    public Gui(Terrain land, PlantLayer c, PlantLayer u) {
        
    //======================================================================
    //      Load in Files Frame:
    //======================================================================
    loadIn = new JFrame("Initialising");

    //File Chooser:
            fChooser = new JFileChooser();
            fChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);


            loadIn.setSize(400,400);
            loadIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JLabel loading = new JLabel();
                ImageIcon path = new ImageIcon("src/resources/ECOVIZ.gif");
                loading.setIcon(path);
            
            //Add button for loading in files:
            load = new JButton();
                load.setText("Load Files");
                load.addActionListener(this);


            //splash.getContentPane().add(loading, BorderLayout.CENTER);
            loadIn.add(loading);
            loadIn.getContentPane().add(BorderLayout.SOUTH, load);

    //======================================================================
    //      Frame:
    //======================================================================
            frame = new JFrame("EcoViz");
            //frame.getContentPane().setBackground(Color.BLACK);
            frame.setSize(500,500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    //======================================================================   
    //      North Panel:
    //======================================================================
            JPanel pnlNorth = new JPanel();
            pnlNorth.setBackground(new Color(16,120,173));
            JLabel lblSearch = new JLabel("Search: ");
            JTextField search = new JTextField(20);
            JLabel lblFilter = new JLabel("Filter: ");
            JComboBox<String> filter = new JComboBox<String>();
                filter.addItem("basic filter 1");
                filter.addItem("basic filter+ 2");
                filter.addItem("basic filter 3");
                filter.addItem("Custom...");

            

            //Add Components
            pnlNorth.add(lblSearch);
            pnlNorth.add(search);
            pnlNorth.add(lblFilter);
            pnlNorth.add(filter);


    //======================================================================
    //      West Panel (MAIN PANEL) : 
    //======================================================================
            mainPanel = new imgPanel(land.deriveImg(), c.deriveImg(), u.deriveImg());
                mainPanel.setPreferredSize(new Dimension(Terrain.getDimX(),Terrain.getDimY()));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                mainPanel.setBorder(BorderFactory.createRaisedBevelBorder());


            JPanel pnlWest = new JPanel();
                pnlWest.add(mainPanel);

                
    //======================================================================
    //      East Panel: 
    //======================================================================
    JPanel pnlEast = new JPanel();
    pnlEast.setPreferredSize(new Dimension(200,Terrain.getDimY()));
    pnlEast.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    pnlEast.setBorder(BorderFactory.createRaisedBevelBorder());
    pnlEast.setLayout(new BoxLayout(pnlEast,BoxLayout.PAGE_AXIS));

    JLabel heading = new JLabel("Plant Description");
    Font f = heading.getFont();
    heading.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
    JTextArea plantDescription = new JTextArea("  Common Name:\n  Latin Name:\n  Height:\n  Canopy Radius:");
    plantDescription.setOpaque(false);

    //Add Components
    pnlEast.add(heading,BorderLayout.NORTH);
    pnlEast.add(Box.createRigidArea(new Dimension(0,5)));
    pnlEast.add(plantDescription,BorderLayout.NORTH);
    pnlEast.add(Box.createRigidArea(new Dimension(0,5)));

    //Configurations
    JLabel config = new JLabel("Configurations:");
    config.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        JCheckBox ChkUnderGrowth = new JCheckBox("Show Undergrowth",true);
        ChkUnderGrowth.setBounds(100,100,50,50);
        pnlEast.add(Box.createRigidArea(new Dimension(0,5)));
        JCheckBox ChkOverGrowth = new JCheckBox("Show Overgrowth",true);
        ChkOverGrowth.setBounds(100,100,50,50);
        

    pnlEast.add(ChkUnderGrowth,BorderLayout.CENTER);
    pnlEast.add(ChkOverGrowth,BorderLayout.CENTER);

    //DRAW MINIMAP
    miniMap mini = new miniMap(land.deriveImg(), c.deriveImg(), u.deriveImg());
    mini.setPreferredSize(new Dimension(200,200));
    mini.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    mini.setOpaque(false);

    pnlEast.setBackground(new Color(85,193,219));
    pnlEast.add(mini,BorderLayout.SOUTH);


    //======================================================================
    //      South Panel:
    //======================================================================
            JPanel pnlSouth = new JPanel();
                JLabel lblZoom = new JLabel("Scroll to Zoom                       ");
                lblZoom.setForeground(Color.white);
                JButton btnFire = new JButton("Simulate Fire");
                pnlSouth.setBackground(new Color(8,78,137));

                //Add Components
                pnlSouth.add(lblZoom);
                pnlSouth.add(btnFire);

    //====================================================================== 
    //      MenuBar:
    //======================================================================
                JMenuBar mb = new JMenuBar();

                JMenu m1 = new JMenu("File");

                    i1 = new JMenuItem("Load Files");
                        i1.addActionListener(this);
                    i2 = new JMenuItem("Export as PNG");
                        i2.addActionListener(this);
                    i3 = new JMenuItem("Exit");
                        i3.addActionListener(this);

                        m1.add(i1);
                        m1.add(i2);
                        m1.add(i3);
            
                JMenu m2 = new JMenu("Help");
                mb.add(m1);
                mb.add(m2);

    //====================================================================== 
    //      Adding all to the Frame (ECOVIZ):
    //======================================================================
            frame.setJMenuBar(mb);
            frame.getContentPane().add(BorderLayout.SOUTH, pnlSouth);
            frame.getContentPane().add(BorderLayout.NORTH, pnlNorth);
            frame.getContentPane().add(BorderLayout.WEST, pnlWest);
            frame.getContentPane().add(BorderLayout.EAST, pnlEast);

        
        // Show
        frame.pack();
        frame.setVisible(true);
        //loadIn.setVisible(true);

    }

    public void actionPerformed( ActionEvent e ){
        
        //Load Files:
        if ((e.getSource() == load) | e.getSource() == i1){
            int returnVal = fChooser.showOpenDialog(Gui.this);
            if (returnVal == JFileChooser.APPROVE_OPTION){
                File directory = fChooser.getSelectedFile();

                //Add a check, to see if files are valid*********
                loadIn.setVisible(false);
                frame.setVisible(true);
                System.out.println("Opening the file");
            }else{System.out.println("Cancelled by the user");}
        }

        //Terminate Application:
        if (e.getSource() == i3){
            System.exit(0);}

        //Export PNG:
        if (e.getSource() == i2){
            JFrame popup = new JFrame();
            String nm = JOptionPane.showInputDialog(popup, "Save As:");
            mainPanel.exportImage(nm);
        }

    }
}
