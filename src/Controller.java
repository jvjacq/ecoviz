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

    public Controller(Gui gui, Terrain terrain, PlantLayer undergrowth, PlantLayer canopy){
        this.gui = gui;
        this.image = gui.getImage();
        this.terrain = terrain;
        this.undergrowth = undergrowth;
        this.canopy = canopy;
        this.files = new FileController();    
        running = false;
        delay = 25;//default
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
        fire = new Fire(Terrain.getDimX(), Terrain.getDimY(),undergrowth.getPlantGrid());

    }

    public void closeFireSim(){
        gui.getFireBtn().setVisible(true);
        gui.getBackBtn().setVisible(false);
        gui.getResetBtn().setVisible(false);
        gui.getRenderBtn().setVisible(false);
        resetFireSim();
        fireMode=false;
        timer.cancel();
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
        timer.schedule(new TimerTask(){

            @Override
            public void run() {
            
            if (running){
            //delay = gui.getDelay();
            fire.simulate(0,(Terrain.getDimX()*Terrain.getDimY()));    //Run simulation on all
            fire.deriveFireImage();
            BufferedImage updatedFireImage = fire.getImage();
            image.setFire(updatedFireImage);
            image.repaint();
            }
            }
            
        }, 0, delay);
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
                        files.readLayer(undergrowth, filenames[2], false);
                        files.readLayer(canopy, filenames[3], true); //true = canopy
                        Collections.sort(PlantLayer.getPlantList());
                        System.out.println("All files read in successfully.");
                        refreshView();
                    }catch(FileNotFoundException e){
                        System.out.println("Could not complete file input.");
                    }
                }
            }       
        }else{
            System.out.println("Cancelled by the user.");
        }
    }

    private boolean insideCanopy(Point point, Plant plant){
        return (Math.pow((point.x - plant.getX()),2) + Math.pow((point.y - plant.getY()),2) ) <= Math.pow(plant.getCanopy(),2);
    }

    public void changeSpeciesColour(int id){
        Species[] specieslist = PlantLayer.getAllSpecies();
        Color c = new Color(0,0,0,1.0f);
        Color prev = specieslist[id].getColour();
        specieslist[id].setColour(c);
        image.derivePlants();
        image.repaint();
        specieslist[id].setColour(prev);
    }

    public void refreshView(){
        gui.getLoadFrame().setVisible(false);
        gui.getEast().setPreferredSize(new Dimension(200,Terrain.getDimY()));
        image.deriveImg(terrain);
        image.derivePlants();
        image.setPreferredSize(new Dimension(Terrain.getDimX(),Terrain.getDimY()));
        addSpeciesFilters();
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
        image.derivePlants();
        image.repaint();

    }
    
    public void filterLayers(){
        if (gui.getChkCanopy().isSelected()) image.setShowCanopy(true); 
        else image.setShowCanopy(false);

        if (gui.getChkUndergrowth().isSelected()) image.setShowUnderGrowth(true); 
        else image.setShowUnderGrowth(false);
        image.derivePlants();
        image.repaint();
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        image.setZoom(true);
        double multiplier = image.getZoomMult();
		if (e.getWheelRotation() < 0) {	// Zoom in
			multiplier *=1.1;	//Adjust for smoothness
            image.setZoomMult(multiplier);
			image.repaint();
		}
		if (e.getWheelRotation() > 0) {	// Zoom out
			multiplier /=1.1;	//Adjust for smoothness
            if(multiplier < 1) multiplier = 1;
            image.setZoomMult(multiplier);
			image.repaint();
		}        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Point cursor = e.getLocationOnScreen();
		image.setXDiff(cursor.x - image.getStartX());
		image.setYDiff(cursor.y - image.getStartY());

		image.setDragger(true);
		image.repaint();
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point click = e.getPoint();

        //Fire Placement:
        if (fireMode){
            fire.addFire(click.x, click.y);
            BufferedImage updatedFireImage = fire.getImage();
            image.setFire(updatedFireImage);
            image.repaint();
            System.out.println("Fire Added");
        }else{
            int id = -1;
            Species[] list = PlantLayer.getAllSpecies();
            for(Plant plant: PlantLayer.getPlantList()){
                if(list[plant.getSpeciesID()].getFilter()){
                    if(insideCanopy(click, plant)){
                        //Will be lowest plant
                        id = plant.getSpeciesID();
                        changeSpeciesColour(id);
                        break;
                    }
                }

            }
            if(id > -1){
                Species[] specieslist = PlantLayer.getAllSpecies();
                gui.setSpeciesDetails(specieslist[id].toString());
            }else{
                gui.setSpeciesDetails("Select any plant to \n view details!");
                image.repaint();
            }
        }
    }

    @Override
	public void mousePressed(MouseEvent e) {
        image.setReleased(false);
        image.setStartPoint( MouseInfo.getPointerInfo().getLocation() );
		
	}

    @Override
	public void mouseReleased(MouseEvent e) {
		image.setReleased(true);
		//image.repaint();
		
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
