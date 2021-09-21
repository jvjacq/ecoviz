import java.awt.event.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collections;

public class Controller implements MouseWheelListener, MouseListener, MouseMotionListener{
    private Gui gui;
    private ImagePanel image;
    private Terrain terrain;
    private PlantLayer undergrowth;
    private PlantLayer canopy;
    private FileController files;
    private FireController fireController;
    private boolean fireMode;

    public Controller(Gui gui, Terrain terrain, PlantLayer undergrowth, PlantLayer canopy){
        this.gui = gui;
        this.image = gui.getImage();
        this.terrain = terrain;
        this.undergrowth = undergrowth;
        this.canopy = canopy;
        this.files = new FileController();    
        fireController = new FireController(Terrain.getDimX(),Terrain.getDimY(),undergrowth,canopy);

    }

    public void initController(){
        gui.getFireBtn().addActionListener(e -> openFireSim());
        gui.getBackBtn().addActionListener(e -> closeFireSim());
        gui.getLoadBtn().addActionListener(e -> loadFiles());
        gui.getResetBtn().addActionListener(e -> fireController.getFire().clearGrid());
        gui.getMenu1().addActionListener(e -> loadFiles());
        gui.getMenu2().addActionListener(e -> gui.exportView());
        gui.getMenu3().addActionListener(e -> goodbye());
        gui.getMenu4().addActionListener(e -> gui.changeTheme(0));
        gui.getMenu5().addActionListener(e -> gui.changeTheme(1));
        gui.getMenu6().addActionListener(e -> gui.changeTheme(2));
        gui.getMenu7().addActionListener(e -> gui.changeTheme(3));
        image.addMouseListener(this);
        image.addMouseMotionListener(this);
        image.addMouseWheelListener(this);
        //gui.changeTheme(0); //###
        initView();
    }

    public void openFireSim(){
        gui.getFireBtn().setVisible(false);
        gui.getBackBtn().setVisible(true);
        gui.getPauseBtn().setVisible(true);
        gui.getResetBtn().setVisible(true);
        gui.getPlayBtn().setVisible(true);
        fireMode=true;

    }

    public void closeFireSim(){
        gui.getFireBtn().setVisible(true);
        gui.getBackBtn().setVisible(false);
        gui.getPauseBtn().setVisible(false);
        gui.getResetBtn().setVisible(false);
        gui.getPlayBtn().setVisible(false);
        fireMode=false;
    }

    public void runFireSim(){
        System.out.println("Running Fire Simulation");
        
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
                        files.readSpecies(filenames[1]);
                        files.readLayer(undergrowth, filenames[2]);
                        files.readLayer(canopy, filenames[3]);
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
        //int x = Math.round(point.x/image.getScale());
        //int y = Math.round(point.y/image.getScale());
        return (Math.pow((point.x - plant.getX()),2) + Math.pow((point.y - plant.getY()),2) ) <= Math.pow(plant.getCanopy(),2);
    }

    public void changeSpeciesColour(int id, int rgb){
        int[] colours = Species.getCOLOURS();
        Color c = new Color(0,0,0,1.0f);
        colours[id] = c.getRGB();
        image.derivePlants();
        image.repaint();
        //colours[id]
        /*Species[] cSpecies = canopy.getSpeciesList();
        Species[] uSpecies = canopy.getSpeciesList();
        for(Species s: cSpecies){
            if(s.getSpeciesID() == id){
                s.setColour(0);
                break;
            }
        }
        for(Species s: uSpecies){
            if(s.getSpeciesID() == id){
                s.setColour(0);
                break;
            }
        }*/
    }

    public void refreshView(){
        gui.getLoadFrame().setVisible(false);
        gui.getEast().setPreferredSize(new Dimension(200,Terrain.getDimY()));
        //image.setScale(1024/Terrain.getDimX());
        image.deriveImg(terrain);
        image.derivePlants();
        //image.deriveImg(undergrowth, false);
        //image.deriveImg(canopy, true);
        //image.setPreferredSize(new Dimension(Math.round(Terrain.getDimX()*image.getScale()),Math.round(Terrain.getDimY()*image.getScale())));
        //gui.getMain().setPreferredSize(new Dimension(Math.round(Terrain.getDimX()*image.getScale())+220,Math.round(Terrain.getDimY()*image.getScale())+100));
        image.setPreferredSize(new Dimension(Terrain.getDimX(),Terrain.getDimY()));
        gui.getMain().setPreferredSize(new Dimension(Terrain.getDimX()+220,Terrain.getDimY()+100));
        gui.getMain().pack();
        gui.getMain().setLocationRelativeTo(null);      
        gui.getMain().setVisible(true);
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
            fireController.getFire().addFire(click.x, click.y);
            System.out.println("Fire Added");
        }else{
            int id = -1;
            //System.out.println(click.x + " " + click.y);
            for(Plant plant: PlantLayer.getPlantList()){
                if(insideCanopy(click, plant)){
                    //Will be lowest plant
                    id = plant.getSpeciesID();
                    changeSpeciesColour(id, 0);
                    //System.out.println("Plant: " + plant.getX() + " " + plant.getY());
                    break;
                }
            }
            if(id > -1){
                String[][] specieslist = Species.getSPECIES();
                gui.setSpeciesDetails("Common name:\n" + specieslist[id][0] + "\nLatin name:\n" + specieslist[id][1]);
            }
            /*int col = image.getCanopy().getRGB(click.x, click.y);
            int[] speciesColours = Species.getCOLOURS();
            String[][] specieslist = Species.getSPECIES();
            for(int idx = 0; idx < speciesColours.length; ++idx){
                if(speciesColours[idx] == col){
                    gui.setSpeciesDetails("Common name:\n" + specieslist[idx][0] + "\nLatin name:\n" + specieslist[idx][1]);
                    break;
                }
            }*/
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
