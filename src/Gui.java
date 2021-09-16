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
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

import javax.swing.event.ChangeListener;
import java.awt.event.ItemListener;
import javax.swing.event.ChangeEvent;
import java.io.File;

public class Gui extends JPanel implements ActionListener,ChangeListener,ItemListener{

    private JButton load;
    private JFileChooser fChooser;
    private JFrame frame;
    private JFrame loadIn;
    private JMenuItem i1,i2,i3;
    private JLabel pointerLbl;
    private JSlider wDirSlider;
    private imgPanel mainPanel;
    private JButton btnFilter;

    PlantLayer canopy,undergrowth;

    //East Panel:
    private JPanel pnlEast = new JPanel();
    private JLabel heading;
    private JTextArea plantDescription;
    private JLabel config;
    private miniMap mini;
    private JCheckBox ChkUnderGrowth,ChkCanopy;

    //Filter Section:
    private JButton btnBack = new JButton("Back");
    private JPanel fSelection = new JPanel();
    
    public Gui(Terrain terrain, PlantLayer c, PlantLayer u) {
        canopy=c;
        undergrowth=u;

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
            btnFilter = new JButton("Filter");
            btnFilter.addActionListener(this);

            

            //Add Components
            pnlNorth.add(lblSearch);
            pnlNorth.add(search);
            pnlNorth.add(btnFilter);


    //======================================================================
    //      West Panel (MAIN PANEL) : 
    //======================================================================
            mainPanel = new imgPanel(this);
                mainPanel.deriveImg(terrain);
                mainPanel.deriveImg(canopy, true);
                mainPanel.deriveImg(undergrowth, false);
                mainPanel.setPreferredSize(new Dimension(Terrain.getDimX(),Terrain.getDimY()));
                mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
                mainPanel.setBorder(BorderFactory.createRaisedBevelBorder());


            JPanel pnlWest = new JPanel();
                pnlWest.add(mainPanel);

                
    //======================================================================
    //      East Panel: 
    //======================================================================
    pnlEast.setPreferredSize(new Dimension(200,Terrain.getDimY()));
    pnlEast.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
    pnlEast.setBorder(BorderFactory.createRaisedBevelBorder());
    pnlEast.setLayout(new BoxLayout(pnlEast,BoxLayout.PAGE_AXIS));

    heading = new JLabel("Plant Description");
    Font f = heading.getFont();
    heading.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
    plantDescription = new JTextArea("  Common Name:\n  Latin Name:\n  Height:\n  Canopy Radius:");
    plantDescription.setOpaque(false);
    
    //SLIDER FOR WIND DIRECTION:
    wDirSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        wDirSlider.addChangeListener(this);

    //Direction Image:
    //ImageIcon pointer = new ImageIcon("src/resources/Direction.png");
    pointerLbl = new JLabel("Wind Direction: 0 Degrees");

    //Add Components

    pnlEast.add(heading,BorderLayout.NORTH);
    pnlEast.add(Box.createRigidArea(new Dimension(0,5)));
    pnlEast.add(plantDescription,BorderLayout.NORTH);
    pnlEast.add(Box.createRigidArea(new Dimension(0,5)));

    //Configurations
    config = new JLabel("Configurations:");
    config.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        
        
        //Filter gui setup:
        btnBack.setVisible(false);
        btnBack.addActionListener(this);
        fSelection.setVisible(false);
        fSelection.setLayout(new BoxLayout(fSelection,BoxLayout.PAGE_AXIS));

        fSelection.setPreferredSize(new Dimension(200,235));

        fSelection.setBackground(new Color(85,193,219));


        fSelection.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        ChkUnderGrowth = new JCheckBox("Show Undergrowth",true);
        ChkUnderGrowth.addItemListener(this);
        ChkUnderGrowth.setBounds(100,100,50,50);
        fSelection.add(Box.createRigidArea(new Dimension(0,10)));
        ChkCanopy = new JCheckBox("Show Canopy",true);
        ChkCanopy.addItemListener(this);
        ChkCanopy.setBounds(100,100,50,50);
        fSelection.add(Box.createRigidArea(new Dimension(0,10)));

        fSelection.add(ChkUnderGrowth,BorderLayout.CENTER);
        fSelection.add(ChkCanopy,BorderLayout.CENTER);
        
        pnlEast.add(fSelection,BorderLayout.CENTER);
        pnlEast.add(btnBack,BorderLayout.CENTER);

        //////////
    
    pnlEast.add(Box.createRigidArea(new Dimension(0,10)));
    pnlEast.add(pointerLbl);
    pnlEast.add(Box.createRigidArea(new Dimension(0,10)));

    pnlEast.add(wDirSlider);
    pnlEast.add(Box.createRigidArea(new Dimension(0,10)));

    //DRAW MINIMAP
    mini = new miniMap(mainPanel.getTerrain(), mainPanel.getCanopy(), mainPanel.getUndergrowth());
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

    public void setSpeciesDetails(String s){
        this.plantDescription.setText(s);
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

        if (e.getSource() == btnFilter){

            //Hide:
            plantDescription.setVisible(false);
            config.setVisible(false);
            wDirSlider.setVisible(false);
            pointerLbl.setVisible(false);
            btnFilter.setEnabled(false);


            //Show:
            heading.setText("   Select Your Filter:");
            btnBack.setVisible(true);
            fSelection.setVisible(true);

        }

        if (e.getSource() == btnBack){
            //Show:
            heading.setText("Plant Description:");
            plantDescription.setVisible(true);
            config.setVisible(true);
            wDirSlider.setVisible(true);
            pointerLbl.setVisible(true);
            mini.setVisible(true);
            btnFilter.setEnabled(true);

            //Hide:
            btnBack.setVisible(false);
            fSelection.setVisible(false);
        }


    }

    @Override
    public void stateChanged(ChangeEvent e) {

        pointerLbl.setText("Wind Direction: "+Integer.toString(wDirSlider.getValue())+" Degrees");
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        
        if (ChkCanopy.isSelected()){ mainPanel.setShowCanopy(true);; }else{mainPanel.setShowCanopy(false);}

        if (ChkUnderGrowth.isSelected()){ mainPanel.setSHowUnderGrowth(true); }else{mainPanel.setSHowUnderGrowth(false);}
        
    }
}
