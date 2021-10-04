import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JCheckBox;

public class Controller implements MouseWheelListener, MouseListener, MouseMotionListener{
    private Gui gui;
    private ImagePanel image;
    private Terrain terrain;
    private PlantLayer undergrowth;
    private PlantLayer canopy;
    private FileController files;
    private boolean fireMode;
    private int numSpecies;
    private Fire fire;
    private Timer timer;
    private int delay;
    private boolean running;
    private Plant selected;
    private TimerTask task;
    private boolean deaf;

    public Controller(Gui gui, Terrain terrain, PlantLayer undergrowth, PlantLayer canopy){
        this.gui = gui;
        this.image = gui.getImage();
        this.terrain = terrain;
        this.undergrowth = undergrowth;
        this.canopy = canopy;
        this.files = new FileController();    
        running = false;
        selected = null;
        delay = 100;//default
        deaf = false;
    }

    public void initController(){
        gui.getRenderBtn().addActionListener(e -> renderFireSim());
        gui.getFireBtn().addActionListener(e -> openFireSim());
        gui.getBackBtn().addActionListener(e -> closeFireSim());
        gui.getPauseBtn().addActionListener(e -> pauseFireSim());
        gui.getLoadBtn().addActionListener(e -> loadFiles());
        gui.getResetBtn().addActionListener(e ->resetFireSim());
        gui.getMenu1().addActionListener(e -> loadFiles());
        gui.getMenu2().addActionListener(e -> gui.exportView());
        gui.getMenu3().addActionListener(e -> goodbye());
        gui.getMenu4().addActionListener(e -> gui.changeTheme(0));
        gui.getMenu5().addActionListener(e -> gui.changeTheme(1));
        gui.getMenu6().addActionListener(e -> gui.changeTheme(2));
        gui.getMenu7().addActionListener(e -> gui.changeTheme(3));
        gui.getChkCanopy().addItemListener(e -> filterLayers());
        gui.getChkUndergrowth().addItemListener(e -> filterLayers());
        gui.getSpeciesToggle().addItemListener(e -> speciesDetails());
        gui.getHiHeight().addPropertyChangeListener(e -> filterHeightCanopy());
        gui.getLoHeight().addPropertyChangeListener(e -> filterHeightCanopy());
        gui.getHiRadius().addPropertyChangeListener(e -> filterHeightCanopy());
        gui.getLoRadius().addPropertyChangeListener(e -> filterHeightCanopy());
        //gui.getRadSlider().addChangeListener(/**/);
        gui.getChkMetric().addItemListener(e -> changeUnits());
        image.addMouseListener(this);
        image.addMouseMotionListener(this);
        image.addMouseWheelListener(this);
        //gui.changeTheme(0); //###
        initView();
    }

    public void resetFireSim(){
        fire.clearGrid();
        fire.deriveFireImage();
        BufferedImage updatedFireImage = fire.getImage();
        image.setFire(updatedFireImage);

        BufferedImage updatedBurntImage = fire.getImage();
        image.setBurnt(updatedBurntImage);
        gui.repaint();
        
        //timer.cancel();
    }
    


    public void openFireSim(){

        gui.getFireBtn().setVisible(false);
        gui.getPauseBtn().setVisible(true);
        gui.getBackBtn().setVisible(true);
        gui.getResetBtn().setVisible(true);
        gui.getRenderBtn().setVisible(true);
        fireMode=true;
        gui.getPauseBtn().setEnabled(false);
        //Setup fire:
        fire = new Fire(Terrain.getDimX(), Terrain.getDimY(),undergrowth.getLocations(),canopy.getLocations());

    }

    public void closeFireSim(){
        gui.getFireBtn().setVisible(true);
        gui.getBackBtn().setVisible(false);
        gui.getResetBtn().setVisible(false);
        gui.getRenderBtn().setVisible(false);
        gui.getPauseBtn().setVisible(false);

        resetFireSim();
        fireMode=false;
        running=false;
        task.cancel();
        timer.cancel();
        timer.purge();
        gui.getRenderBtn().setEnabled(true);
        gui.getPauseBtn().setEnabled(false);
        image.repaint();


    }
    
    public void pauseFireSim(){
        if (running==false){
            running=true;
            gui.getPauseBtn().setText("Pause");
        }else{
            running=false;
            gui.getPauseBtn().setText("Play");

        }
    }

    public void renderFireSim(){    //Single use
        
        System.out.println("Running the Fire Simulation");
        gui.getPauseBtn().setEnabled(true);
        running=true;
        timer = new Timer();
        task = new TimerTask(){

            @Override
            public void run() {
            
            if (running){
            //delay = gui.getDelay();
            fire.simulate(0,(Terrain.getDimX()*Terrain.getDimY()));    //Run simulation on all
            fire.deriveFireImage();
            BufferedImage updatedFireImage = fire.getImage();
            image.setFire(updatedFireImage);

            BufferedImage updatedBurnImage = fire.getBurntImage();
            image.setBurnt(updatedBurnImage);

            image.repaint();
            }
            } 
        };
        timer.schedule(task, 0, delay);
        gui.getRenderBtn().setEnabled(false);
    }

    public void initView(){
        gui.getLoadFrame().setVisible(true);
        //gui.getMain().setVisible(false);
    }

    public void goodbye(){
        System.exit(0);
    }

    public void loadFiles(){
        //gui.getChooser().setMultiSelectionEnabled(true);
        if(gui.showChooser()){
            File[] list = gui.getChooser().getSelectedFiles();
            String[] filenames = new String[4];
            if(list.length != 4){
                System.out.println("Incorrect number of files selected!\nPlease select:\n   > 1 '.elv' file\n   > 2 '.pdb' files\n  > 1 '.spc.txt' file.");
                loadFiles();
            }else{
                //System.out.println(list[0].toString()  + " " + list[1].toString()  + " " + list[2].toString()  + " " + list[3].toString());
                if(!files.validateFiles(list, filenames)){
                    System.out.println("The selected files could not be loaded, please try again.");
                    loadFiles();
                }else{
                    try{
                        files.readElevation(terrain, filenames[0]);
                        numSpecies = files.readSpecies(filenames[1]);
                        FileController fileRun = new FileController(filenames[2], undergrowth, false); 
                        Thread fileThread = new Thread(fileRun);
                        fileThread.start();
                        files.readLayer(canopy, filenames[3], true); //true = canopy
                        //files.readLayer(canopy, filenames[3], true);
                        try{
                            fileThread.join();
                        }catch(Exception e){
                            System.out.println("Premature thread exit.");
                        }
                        Collections.sort(PlantLayer.getPlantList());
                        System.out.println("All files read in successfully.");
                        refreshView();
                        //image.repaint();
                        //gui.getMini().repaint();
                    }catch(FileNotFoundException e){
                        System.out.println("Could not complete file input.");
                    }
                }
            }       
        }else{
            System.out.println("Cancelled by the user.");
        }
        //System.out.println(FileController.getMaxHeight() + " " + FileController.getMaxRadius());
    }

    private boolean insideCanopy(Point point, Plant plant){
        int x = (int)Math.round(point.x/image.getZoomMult() + image.getTLX());
        int y = (int)Math.round(point.y/image.getZoomMult() + image.getTLY());
        return (Math.pow((x - plant.getX()),2) + Math.pow((y - plant.getY()),2) ) <= Math.pow(plant.getCanopy(),2);
    }

    public void changeSpeciesColour(int id){
        Species[] specieslist = PlantLayer.getAllSpecies();
        Color c = new Color(0,0,0,1.0f);
        Color prev = specieslist[id].getColour();
        specieslist[id].setColour(c);
        image.deriveImage();
        image.repaint();
        //while(!image.getPainted()){}
        //image.setPainted(false);
        specieslist[id].setColour(prev);
    }

    public void refreshView(){
        gui.getLoadFrame().setVisible(false);
        gui.getEast().setPreferredSize(new Dimension(200,Terrain.getDimY()));
        this.deaf = true;
        gui.getHiHeight().setValue(Math.ceil(FileController.getMaxHeight()));
        gui.getHiRadius().setValue(Math.ceil(FileController.getMaxRadius()));
        gui.getLoHeight().setValue(0.00f);
        gui.getLoRadius().setValue(0.00f);
        image.setFilterLimits(0.00f, (float)Math.ceil(FileController.getMaxHeight()), 0.00f, (float)Math.ceil(FileController.getMaxRadius()));
        this.deaf = false;
        
        image.setPlantsInView(files.getTotalSpecies());
        image.deriveImg(terrain);
        //image.setZoom(true);
        image.reset();
        image.deriveImage();
        addSpeciesFilters();
        resetLayerFilters();
        updateFilterSpeciesCounts();      
        image.resetDetails();
        resetDesc();
        
        image.setPreferredSize(new Dimension(Terrain.getDimX(),Terrain.getDimY()));
        
        gui.getMini().setZone(0, 0, Terrain.getDimX(), Terrain.getDimY());
        gui.getMain().setPreferredSize(new Dimension(Terrain.getDimX()+220,Terrain.getDimY()+100));
        gui.getMain().pack();
        gui.getMain().setLocationRelativeTo(null);      
        gui.getMain().setVisible(true);
    }

    public void addSpeciesFilters(){
        gui.clearFilters();
        Species[] list = PlantLayer.getAllSpecies();
        JCheckBox[] filters = new JCheckBox[numSpecies];
        for(int x = 0; x < numSpecies; ++x){
            JCheckBox chk = gui.addFilter(list[x].getCommon());
            chk.addItemListener(e -> filterSpecies());
            filters[x] = chk;
        }
        gui.setFilterList(filters);
    }

    public void filterSpecies(){
        Species[] list = PlantLayer.getAllSpecies();
        JCheckBox[] filters = gui.getFilterList();
        for(int idx = 0; idx < numSpecies; ++idx){
            if(filters[idx].isSelected()){
                list[idx].setFilter(true); 
            }else{
                list[idx].setFilter(false); 
            }
        }
        image.deriveImage();
        updateFilterSpeciesCounts();
        image.repaint();

    }
    
    public void filterLayers(){
        if (gui.getChkCanopy().isSelected()) image.setShowCanopy(true); 
        else image.setShowCanopy(false);

        if (gui.getChkUndergrowth().isSelected()) image.setShowUnderGrowth(true); 
        else image.setShowUnderGrowth(false);
        image.deriveImage();
        updateFilterSpeciesCounts();
        image.repaint();
    }

    public void resetLayerFilters(){
        gui.getChkCanopy().setSelected(true);
        gui.getChkUndergrowth().setSelected(true);
    }

    public void changeUnits(){
        gui.changeMetric(); 
    }

    public void filterHeightCanopy(){
        if(deaf) return;
        deaf = true;
        float loHeight = Float.valueOf(gui.getLoHeight().getText());
        float hiHeight = Float.valueOf(gui.getHiHeight().getText());
        float loRadius = Float.valueOf(gui.getLoRadius().getText());
        float hiRadius = Float.valueOf(gui.getHiRadius().getText());
        if((loHeight > hiHeight) || (loHeight < 0)){
            loHeight = 0.00f;
            gui.getLoHeight().setValue(loHeight);
        }
        if((loRadius > hiRadius) || (loRadius < 0)){
            loRadius = 0.00f;
            gui.getLoRadius().setValue(loRadius);
        }
        if(hiHeight > Math.ceil(FileController.getMaxHeight())){
            hiHeight = (float)Math.ceil(FileController.getMaxHeight());
            gui.getHiHeight().setValue(hiHeight);         
        }
        if(hiRadius > Math.ceil(FileController.getMaxRadius())){
            hiRadius =  (float)Math.ceil(FileController.getMaxRadius());
            gui.getHiRadius().setValue(hiRadius);         
        }
        //Need to add check for negative max 
        System.out.println(loHeight + " " + hiHeight + " " + loRadius + " " + hiRadius);
        image.setFilterLimits(loHeight,hiHeight,loRadius,hiRadius);
        deaf = false;
        image.deriveImage();
        updateFilterSpeciesCounts();
        image.repaint();
        
    }

    public void updateFilterSpeciesCounts(){
        int[] speciesCounts = image.getPlantsInView();
        for(int i = 0; i < gui.getFilterList().length; ++i){
            String current = gui.getFilterList()[i].getText();
            int bracket = current.indexOf("(");
            if(bracket != -1) current = current.substring(0,bracket-1);
            gui.getFilterList()[i].setText(current + " (" + speciesCounts[i] + ")");
        }
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        
        image.setZoom(true);
        double multiplier = image.getZoomMult();
		if (e.getWheelRotation() < 0) {	// Zoom in
			multiplier *=1.1;	//Adjust for smoothness
            image.setZoomMult(multiplier);
		}
		if (e.getWheelRotation() > 0) {	// Zoom out
			multiplier /=1.1;	//Adjust for smoothness
            if(multiplier < 1) multiplier = 1;
            image.setZoomMult(multiplier);
            
		}
        image.deriveImage();
        updateFilterSpeciesCounts();
        if(selected != null) image.displayPlant(selected);
        image.repaint();
        gui.getMini().setZone(image.getTLX(), image.getTLY(), image.getNewDimX(), image.getNewDimY());
               
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        
        Point cursor = e.getLocationOnScreen();
		image.setXDiff(cursor.x - image.getStartX());
		image.setYDiff(cursor.y - image.getStartY());

		image.setDragger(true);        
        image.deriveImage();
        updateFilterSpeciesCounts();
        if(selected != null) image.displayPlant(selected);
		image.repaint();
        gui.getMini().setZone(image.getTLX(), image.getTLY(), image.getNewDimX(), image.getNewDimY());
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        gui.getSpeciesToggle().setEnabled(true);
        Point click = e.getPoint();

        //Fire Placement:
        if (fireMode){
            fire.addFire(click.x, click.y);
            BufferedImage updatedFireImage = fire.getImage();
            BufferedImage burntImage = fire.getBurntImage();

            image.setFire(updatedFireImage);
            image.setBurnt(burntImage);
            //image.deriveImage();
            image.repaint();
            System.out.println("Fire Added");
        }else{
            gui.getSpeciesToggle().setSelected(false);
            int id = -1;
            Species[] list = PlantLayer.getAllSpecies();
            for(Plant plant: PlantLayer.getPlantList()){
                //Check if species is not currently filtered
                if(list[plant.getSpeciesID()].getFilter()){
                    if(insideCanopy(click, plant)){
                        //Will be lowest plant
                        selected = plant;
                        id = plant.getSpeciesID();
                        image.displayPlant(selected);
                        image.repaint();
                        break;
                    }
                }

            }
            if(id > -1){
                //Species[] specieslist = PlantLayer.getAllSpecies();
                //gui.setSpeciesDetails(specieslist[id].toString());
                setPlantDesc();
                //gui.setSpeciesDetails(specieslist[id].toString());
                gui.getTabPane().setSelectedIndex(0);
                //System.out.println("why");
            }else{
                image.resetDetails();
                resetDesc();
                selected = null;
                gui.getSpeciesToggle().setEnabled(false);
                //gui.setSpeciesDetails("Select any plant to \n view details!");
                image.deriveImage();
                image.repaint();
            }
        }
    }

    public void speciesDetails(){
        if(selected != null){
            int id = selected.getSpeciesID();
            changeSpeciesColour(id);
            setSpeciesDesc(id);
        }
    }

    public void setSpeciesDesc(int id){
        if(gui.getSpeciesToggle().isSelected()){
                gui.getShortTitle().setText("Shortest plant:");
                gui.getTallTitle().setText("Tallest plant:");
                gui.getAvTitle().setText("Avg. Radius-Height ratio:");
                Species[] specieslist = PlantLayer.getAllSpecies();
                gui.setCommon(specieslist[id].getCommon());
                gui.setLatin(specieslist[id].getLatin());
                gui.setTallest(Float.toString(specieslist[id].getMaxHeight()));
                gui.setShortest(Float.toString(specieslist[id].getMinHeight()));
                gui.setAvg(Float.toString(specieslist[id].getAvgRatio()));
                gui.setNumber(Integer.toString(specieslist[id].getNumPlants()));
        }else{
            setPlantDesc();
            image.deriveImage();
            image.repaint();
        }
    }

    public void setPlantDesc(){
        gui.getShortTitle().setText("Canopy radius:");
        gui.getTallTitle().setText("Height:");
        gui.getAvTitle().setText("Radius-Height ratio:");
        Species[] specieslist = PlantLayer.getAllSpecies();
        gui.setCommon(specieslist[selected.getSpeciesID()].getCommon());
        gui.setLatin(specieslist[selected.getSpeciesID()].getLatin());
        gui.setTallest(Double.toString(selected.getHeight()));
        gui.setShortest(Double.toString(selected.getCanopy()));
        gui.setAvg(Double.toString(selected.getHeight()/selected.getCanopy()));
        gui.setNumber("1");
    }

    public void resetDesc(){
        gui.getShortTitle().setText("Canopy radius:");
        gui.getTallTitle().setText("Height:");
        gui.getAvTitle().setText("Radius-Height ratio:");
        gui.setCommon("No plant selected. \nClick on a plant to view details");
        gui.setLatin("No plant selected. \nClick on a plant to view details");
        gui.setTallest("0.0");
        gui.setShortest("0.0");
        gui.setAvg("0.0");
        gui.setNumber("0");    
    }

    @Override
	public void mousePressed(MouseEvent e) {
        image.setReleased(false);
        image.setStartPoint( MouseInfo.getPointerInfo().getLocation() );
		
	}

    @Override
	public void mouseReleased(MouseEvent e) {
		image.setReleased(true);        
        image.deriveImage();
        updateFilterSpeciesCounts();
        if(selected != null) image.displayPlant(selected);
        image.repaint();		
	}


    @Override
    public void mouseMoved(MouseEvent e) {
        //
    }


    @Override
    public void mouseEntered(MouseEvent e) {
        //
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        //       
    }
}
