/*
* File: Gui.java
* MVC: View
* Author(s): BRNJAM019, FRNOWE001, VJRJAC003
* Last edited: 06/10/2021
* Status: Complete
*/

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Locale;
import javax.swing.text.NumberFormatter;

import com.formdev.flatlaf.*;
//import com.jidesoft.swing.*;

public class Gui extends JPanel{

    private JButton load;
    private JFileChooser fChooser;
    private JFrame frame;
    private JFrame loadIn;
    private JMenuItem i1,i2,i3,a1,a2,h1;
    private JLabel wDirLbl, wSpdLbl;
    private JSlider wDirSlider, wSpdSlider, spdSlider;
    private ImagePanel mainPanel;
    private JButton btnFire;
    private JButton btnBack;
    private int delay;

    // FireSim Controls:
    private JButton btnRender;
    private JButton btnPause;
    private JButton btnReset;
    private JCheckBox chkPath,chkBurnt;
    private JButton btnEndSession;
    PlantLayer canopy, undergrowth;

    // East Panel:
    private JPanel pnlEast;
    private JLabel fontExample;
    private JTextArea plantDescription;
    private JLabel config;
    private MiniMap mini;
    private JCheckBox chkUnderGrowth,chkCanopy,chkMetric;
    private JLabel lblSpeed, compass;
    private ImageIcon path3;
    private JPanel pnlFilters,pnlConfig;
    private JCheckBox[] filterlist;
    private JTabbedPane tabbedPane;
    private JCheckBox speciesToggle;
    private JCheckBox chkRecord;
    private JPanel pnlHeight,pnlStats;
    private JButton playR;
    private String common;
    private String latin;
    private String shortest;
    private String tallest;
    private String avgRat;
    private String totNum;
    private JLabel lblCommon;
    private JLabel lblLatin;
    private JLabel lblTall;
    private JLabel lblShort;
    private JLabel lblAvg;
    private JLabel lblNum;
    private JPanel pnlSouth;
    private JButton btnCloseRender;
    private JPanel pnlWest;
    private JButton btnEnlarge;
    private JPanel pnlName;
    //Filter Section:
    private JLabel shTitle;
    private JLabel taTitle;
    private JLabel avTitle;
    private JFormattedTextField tHiHeight, tLoHeight, tHiRadius, tLoRadius;
    private JSlider radSlider;
    private JCheckBox chkSelectRadius;
    private JSlider scrubber;
    private JLabel mousex, mousey;
    private JButton btnCCSpecies;


    //private JSlider sdrViewRadius; 
    
    private JLabel stamp;
    //Filter Section:
    private JButton btnUndo;
    private JCheckBox chkFirebreak;
    private JLabel lblSearch;
    private JTextField search;
    private JPanel pnlDetails;

    public JButton getEndSession(){
        return this.btnEndSession;
    }

    public JButton getPlayR(){
        return this.playR;
    }

    public JButton getEnlarge(){
        return this.btnEnlarge;
    }

    public JMenuItem getHelp(){
        return this.h1;
    }

    public JLabel getStamp(){
        return this.stamp;
    }

    public JFrame getMain() {
        return this.frame;
    }

    public JFrame getLoadFrame() {
        return this.loadIn;
    }

    public int getDelay() {
        return delay;
    }

    public JButton getFireBtn() {
        return this.btnFire;
    }

    public JButton getBackBtn() {
        return this.btnBack;
    }

    public JButton getRenderBtn() {
        return this.btnRender;
    }

    public JButton getResetBtn() {
        return this.btnReset;
    }

    public JButton getPauseBtn() {
        return this.btnPause;
    }

    public JButton getLoadBtn() {
        return this.load;
    }

    public JButton getUndoBtn() {
        return this.btnUndo;
    }

    public JCheckBox getChkFirebreak() {
        return this.chkFirebreak;
    }

    public JFileChooser getChooser() {
        return this.fChooser;
    }

    public JMenuItem getMenu1() {
        return this.i1;
    }

    public JMenuItem getMenu2() {
        return this.i2;
    }

    public JMenuItem getMenu3() {
        return this.i3;
    }

    public JMenuItem getMenu4() {
        return this.a1;
    }

    public JMenuItem getMenu5() {
        return this.a2;
    }

    public JButton getCloseRender(){
        return this.btnCloseRender;
    }

    public JCheckBox getChkShowPath() {
        return this.chkPath;
    }
    public JCheckBox getChkShowBurnt() {
        return this.chkBurnt;
    }

    public JCheckBox getChkUndergrowth() {
        return this.chkUnderGrowth;
    }

    public JCheckBox getChkMetric(){
        return this.chkMetric;
    }

    public JCheckBox getChkCanopy(){
        return this.chkCanopy;
    }

    public ImagePanel getImage() {
        return this.mainPanel;
    }

    public MiniMap getMini() {
        return this.mini;
    }

    public JPanel getEast() {
        return this.pnlEast;
    }

    public JCheckBox getChkRecord(){
        return this.chkRecord;
    }

    public JPanel getFilterPanel() {
        return this.pnlFilters;
    }

    public JCheckBox[] getFilterList() {
        return this.filterlist;
    }

    public JTabbedPane getTabPane() {
        return this.tabbedPane;
    }

    public JSlider getRadSlider() {
        return this.radSlider;
    }

    public JCheckBox getSpeciesToggle() {
        return this.speciesToggle;
    }

    public JLabel getShortTitle() {
        return this.shTitle;
    }

    public JLabel getTallTitle() {
        return this.taTitle;
    }

    public JSlider getScrubber(){
        return this.scrubber;
    }

    public JLabel getAvTitle() {
        return this.avTitle;
    }

    public JFormattedTextField getHiHeight(){
        return this.tHiHeight;
    }

    public JFormattedTextField getLoHeight(){
        return this.tLoHeight;
    }

    public JFormattedTextField getHiRadius(){
        return this.tHiRadius;
    }

    public JFormattedTextField getLoRadius(){
        return this.tLoRadius;
    }

    public JCheckBox getChkSelectRadius(){
        return this.chkSelectRadius;
    }

    public JButton getCCSpecies(){
        return this.btnCCSpecies;
    }

    public ImagePanel getMainPanel(){
        return mainPanel;
    }

    public int getWindDir(){
        return this.wDirSlider.getValue();
    }

    public ImageIcon getCompassPath(){
        return this.path3;
    }

    public int getWindSpd(){
        return this.wSpdSlider.getValue();
    }
    
    public JSlider getScrubSpeed(){
        return spdSlider;
    }

    public JSlider[] getSliderList(){
        JSlider[] sliderList = new JSlider[3];
        sliderList[0] = wDirSlider;
        sliderList[1] = wSpdSlider;
        sliderList[2] = spdSlider;
        return sliderList;
    }

    //Mutator methods
    public void setFilterList(JCheckBox[] list){
        this.filterlist = list;
    }
    
    public void setChkMetric() {
        this.chkMetric.setSelected(true);
    }
    public void setChkBurnt() {
        this.chkBurnt.setSelected(true);
    }
    public void setChkPath() {
        this.chkPath.setSelected(true);
    }
    
    public void setWindDirLbl(String label){
        this.wDirLbl.setText(label);
    }

    public void setCompassPath(String path){
        this.path3 = new ImageIcon(path);
    }
    
    public void setCompassIcon(){
        this.compass.setIcon(getCompassPath());
    }

    public void setWindSpdLbl(String label){
        this.wSpdLbl.setText(label);
    }
    public void setWindSpdMax(int max){
        this.wSpdSlider.setMaximum(max);
    }
    public void setWindSpd(int speed){
        this.wSpdSlider.setValue(speed);
    }

    public void setSpeedLbl(String label){
        this.lblSpeed.setText(label);
    }

    public void setMousePositions(int x, int y){
        this.mousex.setText(" x: " + x);
        this.mousey.setText(" y: " + y);
    }

    public Gui() {
        delay = 75; // default
        // canopy=c;
        // undergrowth=u;
        // ======================================================================
        // Load in Files Frame:
        // ======================================================================
        loadIn = new JFrame("Initialising");
        loadIn.setSize(400, 400);
        loadIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel loading = new JLabel();
        ImageIcon path = new ImageIcon("resources/ECOVIZ.gif");
        loading.setIcon(path);
        fChooser = new JFileChooser();
        fChooser.setMultiSelectionEnabled(true);

        // Add button for loading in files:
        load = new JButton();
        load.setText("Load Files");
        // load.addActionListener(fileController);

        loadIn.add(loading);
        loadIn.getContentPane().add(BorderLayout.SOUTH, load);

        loadIn.setLocationRelativeTo(null);
        loadIn.setVisible(true);

        // ======================================================================
        // Frame:
        // ======================================================================
        frame = new JFrame("EcoViz");

        //try{
        //Image frameIcon = Toolkit.getDefaultToolkit().getImage("resources/frameIcon.png");
        //frame.setIconImage(frameIcon);
        //loadIn.setIconImage(frameIcon);
        //}catch(Exception e){}
        //frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // ======================================================================
        // West Panel (MAIN PANEL) :
        // ======================================================================
        mainPanel = new ImagePanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.setBorder(BorderFactory.createRaisedBevelBorder());

        
        pnlWest = new JPanel();
        pnlWest.add(mainPanel);



        // ======================================================================
        // South Panel:
        // ======================================================================
        pnlSouth = new JPanel();
        pnlSouth.setLayout(new GridLayout(0, 1));

        btnFire = new JButton("Simulate Fire");
        btnBack = new JButton("Leave Fire Simulator");

        btnRender = new JButton("Run");
        btnRender.setVisible(false);

        btnPause = new JButton("Pause");
        btnPause.setVisible(false);

        btnReset = new JButton("Reset");
        btnReset.setVisible(false);

        // Add Components
        stamp = new JLabel();
        ImageIcon path2 = new ImageIcon("resources/stamp.gif");
        stamp.setIcon(path2);

        btnCloseRender = new JButton("Record");

        btnCloseRender.setVisible(false);
        btnCloseRender.setBackground(new Color(44, 105, 122));

        btnBack.setVisible(false);
        btnBack.setBackground(Color.RED);

        chkRecord = new JCheckBox("Record at Start?");
        chkRecord.setVisible(false);

        pnlSouth.add(stamp);
        pnlSouth.add(chkRecord);
        pnlSouth.add(btnFire);
        //
        JPanel pnlFirebreak = new JPanel();
        pnlFirebreak.setOpaque(false);
        pnlFirebreak.setLayout(new GridLayout(0,2));
        chkFirebreak = new JCheckBox("Add Firebreak");
        //chkFirebreak.setEnabled(false);
        chkFirebreak.setVisible(false);
        btnUndo = new JButton("Undo");
        btnUndo.setVisible(false);
        btnUndo.setEnabled(false);
        pnlFirebreak.add(chkFirebreak);
        pnlFirebreak.add(btnUndo);
        pnlSouth.add(pnlFirebreak);
        
        //
        pnlSouth.add(btnRender);
        pnlSouth.add(btnPause);
        pnlSouth.add(btnReset);
        pnlSouth.add(btnCloseRender);
        pnlSouth.add(btnBack);
        
        btnEndSession = new JButton("End Session");
        btnEndSession.setVisible(false);
        
        playR = new JButton("Play");
        playR.setVisible(false);

        scrubber = new JSlider();
        //scrubber.setMaximum(1000);
        scrubber.setEnabled(false);
        scrubber.setVisible(false);

        pnlSouth.add(playR);
        pnlSouth.add(btnEndSession);
        pnlSouth.add(scrubber);
        

        // pnlSouth.setPreferredSize(new Dimension(100,100));
        Border bFire = BorderFactory.createTitledBorder("Fire Simulation");
        pnlSouth.setBackground(new Color(31, 36, 43));
        pnlSouth.setBorder(bFire);

        // ======================================================================
        // East Panel:
        // ======================================================================

        pnlEast = new JPanel(new BorderLayout());
        pnlEast.setPreferredSize(new Dimension(200, Terrain.getDimY()));
        pnlEast.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        pnlEast.setBorder(BorderFactory.createRaisedBevelBorder());
        pnlEast.setLayout(new BorderLayout());

        fontExample = new JLabel();
        Font f = fontExample.getFont();
        fontExample.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        plantDescription = new JTextArea("  Common Name:\n  Latin Name:\n  Height:\n  Canopy Radius:");
        plantDescription.setOpaque(false);

        // Configurations
        config = new JLabel("Configurations:");
        config.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        // fSelection.setBackground(new Color(85,193,219));
        // ###
        // SLIDER FOR WIND DIRECTION:
        wDirSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        wDirSlider.setMaximum(8);
        wDirSlider.setMinimum(1);
        wDirLbl = new JLabel("Wind Direction: North");

        //SLIDER FOR WIND SPEED:
        wSpdSlider = new JSlider(JSlider.HORIZONTAL, 0, 160, 0); //Wind limit in KPH
        wSpdLbl = new JLabel("Wind Speed: 0 KPH");

        // ###
        spdSlider = new JSlider(JSlider.HORIZONTAL, 0, 360, 0);
        spdSlider.setValue(2);
        spdSlider.setEnabled(false);
        spdSlider.setMaximum(2);
        spdSlider.setPaintLabels(true);
        spdSlider.setPaintTicks(true);
        spdSlider.setMinimum(1);
        lblSpeed = new JLabel("Scrubbing Speed: FAST");

        search = new JTextField(20);
        search.setMaximumSize(search.getPreferredSize());
        lblSearch = new JLabel("Search: ");

        chkUnderGrowth = new JCheckBox("Show Undergrowth", true);
        // chkUnderGrowth.addItemListener(this);
        chkUnderGrowth.setBounds(150, 150, 50, 50);
        chkCanopy = new JCheckBox("Show Canopy", true);
        // chkCanopy.addItemListener(this);
        chkCanopy.setBounds(150, 150, 50, 50);

        chkMetric = new JCheckBox("Metric Units", true);
        chkMetric.setBounds(150, 150, 50, 50);

        //// ********************************************************** */
        tabbedPane = new JTabbedPane();
        tabbedPane.setPreferredSize(new Dimension(200, 490));

        // ImageIcon icon = createImageIcon("");

        // =================================================================
        // Details on Demand:
        // =================================================================
        pnlDetails = new JPanel();
        pnlDetails.setLayout(new GridLayout(0, 1));

        JLabel comTitle = new JLabel("Common Name:");
        comTitle.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        JLabel latTitle = new JLabel("Latin Name:");
        latTitle.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        // private variables
        shTitle = new JLabel("Canopy radius:");
        shTitle.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        taTitle = new JLabel("Height:");
        taTitle.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        avTitle = new JLabel("Radius-Height ratio:");
        avTitle.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        JLabel numTitle = new JLabel("No. of Plants:");
        numTitle.setFont(f.deriveFont(f.getStyle() | Font.BOLD));

        lblCommon = new JLabel("\n");
        lblLatin = new JLabel("\n");
        lblAvg = new JLabel("\n");
        lblShort = new JLabel("\n");
        lblTall = new JLabel("\n");
        lblNum = new JLabel("\n");

        pnlName = new JPanel();
        pnlName.setBackground(new Color(31, 36, 43));
        pnlName.setLayout(new BoxLayout(pnlName, BoxLayout.PAGE_AXIS));
        Border b1 = BorderFactory.createTitledBorder("Species");
        pnlName.add(comTitle);
        pnlName.add(lblCommon);
        pnlName.add(latTitle);
        pnlName.add(lblLatin);
        pnlName.setBorder(b1);

        pnlHeight = new JPanel();
        pnlHeight.setBackground(new Color(31, 36, 43));
        pnlHeight.setLayout(new BoxLayout(pnlHeight, BoxLayout.PAGE_AXIS));
        Border b2 = BorderFactory.createTitledBorder("Measurements"); // private
        pnlHeight.add(shTitle);
        pnlHeight.add(lblShort);
        pnlHeight.add(taTitle);
        pnlHeight.add(lblTall);
        pnlHeight.setBorder(b2);

        pnlStats = new JPanel();
        pnlStats.setBackground(new Color(31, 36, 43));
        pnlStats.setLayout(new BoxLayout(pnlStats, BoxLayout.PAGE_AXIS));
        Border b3 = BorderFactory.createTitledBorder("Stats"); // private
        pnlStats.add(avTitle);
        pnlStats.add(lblAvg);
        pnlStats.add(numTitle);
        pnlStats.add(lblNum);
        pnlStats.setBorder(b3);

        pnlDetails.add(pnlName);
        pnlDetails.add(pnlHeight);
        pnlDetails.add(pnlStats);

        chkPath = new JCheckBox("Show Path of Fire",true);
        chkBurnt = new JCheckBox("Show Burnt Trees",true);
        
        JPanel pnlAll = new JPanel();
        pnlAll.setLayout(new GridLayout(0,2));
        speciesToggle = new JCheckBox("Full Details");
        speciesToggle.setEnabled(false);
        speciesToggle.setSelected(false);
        btnCCSpecies = new JButton("Color Fill");
        JPanel pnlleft = new JPanel();
        pnlleft.setOpaque(false);
        pnlleft.setLayout(new GridLayout(0,1));

        JPanel pnlImage = new JPanel();

        btnEnlarge = new JButton();
        btnEnlarge.setVisible(false);
        //plantPath = new ImageIcon("resources/North.png");
        //compass.setIcon(path3);
        pnlleft.add(btnCCSpecies);
        pnlleft.add(speciesToggle);
        pnlAll.add(pnlleft);
        pnlImage.add(btnEnlarge);
        pnlAll.add(pnlImage);

        pnlDetails.add(pnlAll);

        // pnlDetails.add(plantDescription);

        tabbedPane.addTab("Details", null, pnlDetails, "Shows Details on Demand");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1);

        //Configurations:
        compass = new JLabel();
        path3 = new ImageIcon("resources/compass/North.png");
        compass.setIcon(path3);
        //
        pnlConfig = new JPanel();
        JLabel lblConfig = new JLabel("Configurations");
        lblConfig.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        // Add components to Config Panel
        //pnlConfig.add(lblConfig);
        pnlConfig.add(wDirLbl);
        pnlConfig.add(wDirSlider);
        pnlConfig.add(wSpdLbl);
        pnlConfig.add(wSpdSlider);
        pnlConfig.add(lblSpeed);
        pnlConfig.add(spdSlider);
        pnlConfig.add(chkMetric); 
        pnlConfig.add(compass);
        pnlConfig.add(chkPath);
        pnlConfig.add(chkBurnt);

        tabbedPane.addTab("Config", null, pnlConfig, "Change Simulation Settings");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2);

        // Filters:
        pnlFilters = new JPanel();
        pnlFilters.setLayout(new BoxLayout(pnlFilters, BoxLayout.PAGE_AXIS));
        JLabel lblFilters = new JLabel("Filters");
        lblFilters.setFont(f.deriveFont(f.getStyle() | Font.BOLD));
        // Add components to Filter Panel
        pnlFilters.add(lblFilters);
        //pnlFilters.add(lblSearch);
        //pnlFilters.add(search);
        pnlFilters.add(chkUnderGrowth);
        pnlFilters.add(chkCanopy);

        tabbedPane.addTab("Species", null, pnlFilters, "Filter by species");
        tabbedPane.setMnemonicAt(2, KeyEvent.VK_3);

        JPanel pnlFilter2 = new JPanel();
        pnlFilter2.setLayout(new GridLayout(4,1));
        JLabel lblFilter2 = new JLabel("Filter by Height/Canopy:");
        //Height filter
        JPanel heightFilters = new JPanel();
        heightFilters.setLayout(new GridLayout(0,2));
        heightFilters.setSize(new Dimension(200, 50));
        NumberFormat format = DecimalFormat.getInstance(Locale.UK);
        format.setMinimumFractionDigits(2);
        format.setMaximumFractionDigits(2);
        format.setMinimumIntegerDigits(2);
        format.setMaximumIntegerDigits(2);
        NumberFormatter nf = new NumberFormatter(format);
        nf.setOverwriteMode(true);
        nf.setAllowsInvalid(false);

        tHiHeight = new JFormattedTextField(nf);
        tHiHeight.setValue(0.00f);
        JLabel lblHiHeight = new JLabel("Max Height:");
        tLoHeight = new JFormattedTextField(nf);
        tLoHeight.setValue(0.00f);
        JLabel lblLoHeight = new JLabel("Min Height:");

        //Canopy filter
        JPanel canopyFilters = new JPanel();
        canopyFilters.setLayout(new GridLayout(0,2));
        canopyFilters.setSize(new Dimension(200, 50));

        tHiRadius = new JFormattedTextField(nf);
        tHiRadius.setValue(0.00f);
        JLabel lblHiRadius = new JLabel("Max Radius:");
        tLoRadius = new JFormattedTextField(nf);
        tLoRadius.setValue(0.00f);
        JLabel lblLoRadius = new JLabel("Min Radius:");

        heightFilters.add(lblLoHeight);
        heightFilters.add(lblHiHeight);
        heightFilters.add(tLoHeight);
        heightFilters.add(tHiHeight);

        canopyFilters.add(lblLoRadius);
        canopyFilters.add(lblHiRadius);
        canopyFilters.add(tLoRadius);
        canopyFilters.add(tHiRadius);
        
        //Select in radius
        JLabel lblSelectRad = new JLabel("Filter surrounding plants:");

        radSlider = new JSlider(0, 1024);
        radSlider.setEnabled(false);
        radSlider.setValue(1024);
        Hashtable<Integer, JLabel> table = new Hashtable<Integer, JLabel>();
        table.put(0,new JLabel("0"));
        table.put(1024,new JLabel("1024"));
        radSlider.setLabelTable(table);
        radSlider.setPaintLabels(true);

        chkSelectRadius = new JCheckBox("Filter outliers");
        JPanel pnlRadius = new JPanel();
        pnlRadius.add(lblSelectRad);
        pnlRadius.add(radSlider);
        pnlRadius.add(chkSelectRadius);

        pnlFilter2.add(lblFilter2);
        pnlFilter2.add(heightFilters);
        pnlFilter2.add(canopyFilters);
        pnlFilter2.add(pnlRadius);

        tabbedPane.addTab("Filter",null,pnlFilter2,"Visualisation filters");
        tabbedPane.setMnemonicAt(3, KeyEvent.VK_4);
        ////********************************************************** */
            
        //DRAW MINIMAP
        mini = new MiniMap(mainPanel);
        mini.setPreferredSize(new Dimension(200, 200));
        mini.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mini.setOpaque(false);

        pnlEast.add(tabbedPane, BorderLayout.NORTH);
        pnlEast.add(pnlSouth, BorderLayout.CENTER);
        pnlEast.add(mini, BorderLayout.SOUTH);

        // ======================================================================
        // MenuBar:
        // ======================================================================
        JMenuBar mb = new JMenuBar();

        JMenu m1 = new JMenu("File");

            i1 = new JMenuItem("Load Files");
            i2 = new JMenuItem("Export as PNG");
            i3 = new JMenuItem("Exit");

                m1.add(i1);
                m1.add(i2);
                m1.add(i3);
    
        JMenu m3 = new JMenu("Appearance");

        a1 = new JMenuItem("Dark Mode");
        a2 = new JMenuItem("Light Mode");

        JMenu m2 = new JMenu("Help");
        h1 = new JMenuItem("Manual");
        m2.add(h1);

        m3.add(a1);
        m3.add(a2);

        mb.add(m1);
        mb.add(m2);
        mb.add(m3);

        mousex = new JLabel(" x:   ");
        mousey = new JLabel(" y:   ");
        JPanel pnlPos = new JPanel();
        pnlPos.setOpaque(false);
        pnlPos.setLayout(new GridLayout(0,2));
        pnlPos.add(mousex);
        pnlPos.add(mousey);
        mb.add(pnlPos);

        // ======================================================================
        // Adding all to the Frame (ECOVIZ):
        // ======================================================================
        frame.setJMenuBar(mb);
        // frame.getContentPane().add(BorderLayout.SOUTH, pnlSouth);
        frame.getContentPane().add(BorderLayout.WEST, pnlWest);
        frame.getContentPane().add(BorderLayout.EAST, pnlEast);

        // Show
        frame.pack();
        frame.setLocationRelativeTo(null);
        // frame.setVisible(true);
        // loadIn.setVisible(true);
        
    }
    // END OF CONSTRUCTOR

    public JCheckBox addFilter(String name) {
        JCheckBox chk = new JCheckBox(name, true);
        chk.setBounds(150, 150, 50, 50);
        pnlFilters.add(chk);
        return chk;
    }

    public void clearFilters() {
        if (filterlist == null)
            return;
        for (JCheckBox chk : this.filterlist) {
            pnlFilters.remove(chk);
        }
    }

    public void setSpeciesDetails(String s) {
        this.plantDescription.setText(s);
    }

    //////
    public void setCommon(String c) {
        common = c;
        lblCommon.setText(common);
    }

    public void setLatin(String l) {
        latin = l;
        lblLatin.setText(latin);
    }

    public void setShortest(String s) {
        shortest = s;
        lblShort.setText(shortest + "m");
    }

    public void setTallest(String t) {
        tallest = t;
        lblTall.setText(tallest + "m");
    }

    public void setAvg(String a) {
        avgRat = a;
        lblAvg.setText(avgRat);
    }

    public void setNumber(String n) {
        totNum = n;
        lblNum.setText(totNum);
    }

    //////
    public void exportView() {
        JFrame popup = new JFrame();
       // try{
        //Image frameIcon = Toolkit.getDefaultToolkit().getImage("resources/frameIcon.png");
        //popup.setIconImage(frameIcon);
       // }catch(Exception e){}
        String name = JOptionPane.showInputDialog(popup, "Save As:");
        if(name.length() == 0) return;
        mainPanel.exportImage(name);
    }

    public void changeTheme(int theme) {
        LookAndFeel obj = new FlatDarculaLaf();
        
        pnlName.setOpaque(true);
        pnlHeight.setOpaque(true);
        pnlStats.setOpaque(true);  
        pnlSouth.setBackground(new Color(31, 36, 43));


        String message = "";
        switch (theme) {

            case 0:
                message = "Dark Mode Enabled";
                break;
            case 1:
                obj = new FlatLightLaf();
                message = "Light Mode Enabled";
                pnlSouth.setBackground(new Color(175, 186, 204));
                pnlName.setOpaque(false);  
                pnlHeight.setOpaque(false);
                pnlStats.setOpaque(false);              
                break;
            case 2:
                // obj = new FlatDarculaLaf();
                message = "Dark Mode default";
                break;
            case 3:
                // obj = new FlatDarculaLaf();
                message = "Dark Mode default";
                break;
        }
        try {
            UIManager.setLookAndFeel(obj);
            System.out.println(message);
            SwingUtilities.updateComponentTreeUI(frame);
        } catch (Exception e) {
            System.out.println("Caught exception with laf library");
        }
    }

    public boolean showChooser() {
        JFrame fr = new JFrame();

       // try{
            //Image frameIcon = Toolkit.getDefaultToolkit().getImage("resources/frameIcon.png");
           // fr.setIconImage(frameIcon);
          //  }catch(Exception e){}
        
        return fChooser.showOpenDialog(fr) == JFileChooser.APPROVE_OPTION;
    }
}
