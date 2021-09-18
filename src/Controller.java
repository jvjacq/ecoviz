public class Controller {
    private Gui gui;
    private Terrain terrain;
    private PlantLayer undergrowth;
    private PlantLayer canopy;

    public Controller(Gui gui, Terrain terrain, PlantLayer undergrowth, PlantLayer canopy){
        this.gui = gui;
        this.terrain = terrain;
        this.undergrowth = undergrowth;
        this.canopy = canopy;
        
    }

    public void initController(){
        gui.getMenu2().addActionListener(e -> gui.exportView());
        gui.getMenu3().addActionListener(e -> goodbye());
        gui.getMenu4().addActionListener(e -> gui.changeTheme(0));
        gui.getMenu5().addActionListener(e -> gui.changeTheme(1));
        gui.getMenu6().addActionListener(e -> gui.changeTheme(2));
        gui.getMenu7().addActionListener(e -> gui.changeTheme(3));
    }

    public void goodbye(){
        System.exit(0);
    }
}
