/*
* Previously: PlantLayer.java
* File: Gui.java
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Status: In progress
*/

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import com.formdev.flatlaf.*;

public class Gui extends JPanel implements ChangeListener{

    private JButton load;
    private JFileChooser fChooser;
    private JFrame frame;
    private JFrame loadIn;
    private JMenuItem i1,i2,i3,a1,a2,a3,a4;
    private JLabel pointerLbl;
    private JSlider wDirSlider,spdSlider;
    private ImagePanel mainPanel;
    private JButton btnFire;
    private JButton btnBack;

    //FireSim Controls:
    private JButton btnRender;
    private JButton btnPause;
    private JButton btnReset;

    PlantLayer canopy,undergrowth;

    //East Panel:
    private JPanel pnlEast;
    private JLabel fontExample;
    private JTextArea plantDescription;
    private JLabel config;
    private miniMap mini;
    private JCheckBox ChkUnderGrowth,ChkCanopy;
    private JLabel lblSpeed;
    private JPanel pnlFilters;
    private JCheckBox[] filterlist;
    private JTabbedPane tabbedPane;

    //Filter Section:
    private JLabel lblSearch;
    private JTextField search;

    public JFrame getMain(){
        return this.frame;
    }

    public JFrame getLoadFrame(){
        return this.loadIn;
    }

    public JButton getFireBtn(){
        return this.btnFire;
    }

    public JButton getBackBtn(){
        return this.btnBack;
    }
    public JButton getRenderBtn(){
        return this.btnRender;
    }

    public JButton getResetBtn(){
        return this.btnReset;
    }

    public JButton getPauseBtn(){
        return this.btnPause;
    }

    public JButton getLoadBtn(){
        return this.load;
    }

    public JFileChooser getChooser(){
        return this.fChooser;
    }

    public JMenuItem getMenu1(){
        return this.i1;
    }
    public JMenuItem getMenu2(){
        return this.i2;
    }
    public JMenuItem getMenu3(){
        return this.i3;
    }
    public JMenuItem getMenu4(){
        return this.a1;
    }
    public JMenuItem getMenu5(){
        return this.a2;
    }
    public JMenuItem getMenu6(){
        return this.a3;
    }

    public JMenuItem getMenu7(){
        return this.a4;
    }

    public JCheckBox getChkUndergrowth(){
        return this.ChkUnderGrowth;
    }

    public JCheckBox getChkCanopy(){
        return this.ChkCanopy;
    }

    public ImagePanel getImage(){
        return this.mainPanel;
    }

    public miniMap getMini(){
        return this.mini;
    }

    public JPanel getEast(){
        return this.pnlEast;
    }

    public JPanel getFilterPanel(){
        return this.pnlFilters;
    }

    public JCheckBox[] getFilterList(){
        return this.filterlist;
    }

    public JTabbedPane getTabPane(){
        return this.tabbedPane;
    }

    public void setFilterList(JCheckBox[] list){
        this.filterlist = list;
    }

    public Gui() {
        //canopy=c;
        //undergrowth=u;
        //======================================================================
        //      Load in Files Frame:
        //======================================================================
        loadIn = new JFrame("Initialising");
        loadIn.setSize(400,400);
        loadIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel loading = new JLabel();
        ImageIcon path = new ImageIcon("resources/ECOVIZ.gif");
        loading.setIcon(path);
        fChooser = new JFileChooser();
        fChooser.setMultiSelectionEnabled(true);

        //Add button for loading in files:
        load = new JButton();
        load.setText("Load Files");
        //load.addActionListener(fileController);

        loadIn.add(loading);
        loadIn.getContentPane().add(BorderLayout.SOUTH, load);

        loadIn.setLocationRelativeTo(null);
        loadIn.setVisible(true);

        //======================================================================
        //      Frame:
        //======================================================================
        frame = new JFrame("EcoViz");
        //frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



        //======================================================================
        //      West Panel (MAIN PANEL) : 
        //======================================================================
        mainPanel = new ImagePanel();
            /*mainPanel.deriveImg(terrain);
            mainPanel.deriveImg(canopy, true);
            mainPanel.deriveImg(undergrowth, false);
            mainPanel.setPreferredSize(new Dimension(Terrain.getDimX(),Terrain.getDimY()));*/
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            mainPanel.setBorder(BorderFactory.createRaisedBevelBorder());


        JPanel pnlWest = new JPanel();
            pnlWest.add(mainPanel);

                    
        //======================================================================
        //      East Panel: 
        //======================================================================
        pnlEast = new JPanel(new BorderLayout());
        pnlEast.setPreferredSize(new Dimension(200,Terrain.getDimY()));
        pnlEast.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        pnlEast.setBorder(BorderFactory.createRaisedBevelBorder());
        pnlEast.setLayout(new BoxLayout(pnlEast,BoxLayout.PAGE_AXIS));

        fontExample = new JLabel(); 
        Font f = fontExample.getFont();
        fontExample.setFont(f.deriveFont(f.getStyle() | Font.BOLD));



        plantDescription = new JTextArea("  Common Name:\n  Latin Name:\n  Height:\n  Canopy Radius:");
        plantDescription.setOpaque(false);

        //Configurations
        config = new JLabel("Configurations:");
        config.setFont(f.deriveFont(f.getStyle() | Font.BOLD));



            //fSelection.setBackground(new Color(85,193,219));

        //SLIDER FOR WIND DIRECTION:
        wDirSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        wDirSlider.addChangeListener(this);
        pointerLbl = new JLabel("Wind Direction: 0 Degrees");

        spdSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        spdSlider.setMaximum(5);
        spdSlider.setMinimum(1);
        spdSlider.addChangeListener(this);
        lblSpeed = new JLabel("Simulation Speed: x1");

        search = new JTextField(20);
        search.setMaximumSize(search.getPreferredSize());
        lblSearch = new JLabel("Search: ");
            
        ChkUnderGrowth = new JCheckBox("Show Undergrowth",true);
        //ChkUnderGrowth.addItemListener(this);
        ChkUnderGrowth.setBounds(150,150,50,50);
        ChkCanopy = new JCheckBox("Show Canopy",true);
        //ChkCanopy.addItemListener(this);
        ChkCanopy.setBounds(150,150,50,50);



        ////********************************************************** */
        tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(200,250));

        //ImageIcon icon = createImageIcon("");

        //Details on Demand:
        JPanel pnlDetails = new JPanel();
            JLabel lblDetails = new JLabel("Details on Demand");
            lblDetails.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            //Add components to Details on Demand Panel
            pnlDetails.add(lblDetails);
            pnlDetails.add(plantDescription);

        tabbedPane.addTab("Details",null,pnlDetails,"Shows Details on Demand");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        //Configurations:
        JPanel pnlConfig = new JPanel();
            JLabel lblConfig = new JLabel("Configurations");
            lblConfig.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            //Add components to Config Panel
            pnlConfig.add(lblConfig);
            pnlConfig.add(pointerLbl);
            pnlConfig.add(wDirSlider);
            pnlConfig.add(lblSpeed);
            pnlConfig.add(spdSlider);


        tabbedPane.addTab("Config",null,pnlConfig, "Change Simulation Settings");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        //Filters:
        pnlFilters = new JPanel();
            pnlFilters.setLayout(new BoxLayout(pnlFilters, BoxLayout.PAGE_AXIS ));
            JLabel lblFilters = new JLabel("Filters");
            lblFilters.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
            //Add components to Filter Panel
            pnlFilters.add(lblFilters);
            pnlFilters.add(lblSearch);
            pnlFilters.add(search);
            pnlFilters.add(ChkUnderGrowth);
            pnlFilters.add(ChkCanopy);

        tabbedPane.addTab("Filter",null,pnlFilters,"Edit Filters");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        ////********************************************************** */

        //DRAW MINIMAP
        //mini = new miniMap(mainPanel.getTerrain(), mainPanel.getCanopy(), mainPanel.getUndergrowth());
        mini = new miniMap(mainPanel);
        mini.setPreferredSize(new Dimension(200,200));
        mini.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        mini.setOpaque(false);

        //pnlEast.setBackground(new Color(85,193,219));
        pnlEast.add(tabbedPane);
        pnlEast.add(mini,BorderLayout.SOUTH);


        //======================================================================
        //      South Panel:
        //======================================================================
        JPanel pnlSouth = new JPanel();
            pnlSouth.setLayout(new BoxLayout(pnlSouth,BoxLayout.X_AXIS));

            btnFire = new JButton("Simulate Fire");
            btnBack = new JButton("Leave Fire Simulator");

            btnRender = new JButton("Render (Temp Run)");
            btnRender.setVisible(false);

            btnPause = new JButton("Pause");
            btnPause.setVisible(false);

            btnReset = new JButton("Reset");
            btnReset.setVisible(false);

            //Add Components
            
            btnBack.setVisible(false);
            btnBack.setBackground(Color.RED);


            pnlSouth.add(btnFire);
            pnlSouth.add(btnRender);
            pnlSouth.add(btnPause);
            pnlSouth.add(btnReset);
            pnlSouth.add(btnBack);

            


        //====================================================================== 
        //      MenuBar:
        //======================================================================
        JMenuBar mb = new JMenuBar();

        JMenu m1 = new JMenu("File");

            i1 = new JMenuItem("Load Files");
                //i1.addActionListener(this);
            i2 = new JMenuItem("Export as PNG");
                //i2.addActionListener(this);
            i3 = new JMenuItem("Exit");
                //i3.addActionListener(this);

                m1.add(i1);
                m1.add(i2);
                m1.add(i3);
    
        JMenu m2 = new JMenu("Help");
        JMenu m3 = new JMenu("Appearance");

        a1 = new JMenuItem("Dark Mode");
        a2 = new JMenuItem("Light Mode");
        a3 = new JMenuItem("Cosmo");
        a4 = new JMenuItem("Forest");
        m3.add(a1);
        //a1.addActionListener(this);

        m3.add(a2);
        //a2.addActionListener(this);

        m3.add(a3);
        //a3.addActionListener(this);

        m3.add(a4);
        //a4.addActionListener(this);



        mb.add(m1);
        mb.add(m2);
        mb.add(m3);

    //====================================================================== 
    //      Adding all to the Frame (ECOVIZ):
    //======================================================================
        frame.setJMenuBar(mb);
        frame.getContentPane().add(BorderLayout.SOUTH, pnlSouth);
        frame.getContentPane().add(BorderLayout.WEST, pnlWest);
        frame.getContentPane().add(BorderLayout.EAST, pnlEast);

        
        // Show
        frame.pack();
        frame.setLocationRelativeTo(null);
        //frame.setVisible(true);
        //loadIn.setVisible(true);

    }
    //END OF CONSTRUCTOR

    public JCheckBox addFilter(String name){
        JCheckBox chk = new JCheckBox(name, true);
        chk.setBounds(150,150,50,50);
        pnlFilters.add(chk);
        return chk;
    }

    public void clearFilters(){
        if(filterlist == null) return;
        for(JCheckBox chk: this.filterlist){
            pnlFilters.remove(chk);
        }
    }

    public void setSpeciesDetails(String s){
        this.plantDescription.setText(s);
    }

    public void exportView(){
        JFrame popup = new JFrame();
        String name = JOptionPane.showInputDialog(popup, "Save As:");
        mainPanel.exportImage(name);
    }

    public void changeTheme(int theme){
        LookAndFeel obj = new FlatDarculaLaf();
        String message = "";
        switch(theme){
            
            case 0:
                message = "Dark Mode Enabled";
                break;
            case 1: 
                obj = new FlatLightLaf();
                message = "Light Mode Enabled";
                break;
            case 2: 
                //obj = new FlatDarculaLaf();
                message = "Dark Mode default";
                break;
            case 3: 
                //obj = new FlatDarculaLaf();
                message = "Dark Mode default";
                break;
        }
        try{
            UIManager.setLookAndFeel(obj);
            System.out.println(message);
            SwingUtilities.updateComponentTreeUI(frame);
        }catch (Exception e){
            System.out.println("Caught exception with laf library");
        }
    }

    public boolean showChooser(){
        JFrame fr = new JFrame();
        return fChooser.showOpenDialog(fr) == JFileChooser.APPROVE_OPTION;
    }

    @Override
    public void stateChanged(ChangeEvent e) {

        pointerLbl.setText("Wind Direction: "+Integer.toString(wDirSlider.getValue())+" Degrees");
        lblSpeed.setText("Simulation Speed: x"+Integer.toString(spdSlider.getValue()));
    }

    /*@Override
    public void itemStateChanged(ItemEvent e) {
        
        if (ChkCanopy.isSelected()){ mainPanel.setShowCanopy(true);; }else{mainPanel.setShowCanopy(false);}

        if (ChkUnderGrowth.isSelected()){ mainPanel.setSHowUnderGrowth(true); }else{mainPanel.setSHowUnderGrowth(false);}
        
    }*/

    protected JComponent makeTextPanel(String text){//Taken from tabbedPane.java demo (Oracle)
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1,1));
        panel.add(filler);
        return panel;
    }

}
