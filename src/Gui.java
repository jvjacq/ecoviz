import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import java.awt.GridLayout;

import java.awt.BorderLayout;
import java.awt.Dimension;

public class Gui {

//======================================================================
//      Constructor:
//======================================================================
    public Gui(int fX, int fY, Terrain land) {
        




//======================================================================
//      SplashScreen:
//======================================================================
JFrame splash = new JFrame("EcoViz - Initialising");

        splash.setSize(400,400);
        splash.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JLabel loading = new JLabel();
            ImageIcon path = new ImageIcon("src/resources/ECOVIZ.gif");
            loading.setIcon(path);
        
        //splash.getContentPane().add(loading, BorderLayout.CENTER);
        splash.add(loading);



//======================================================================
//      Frame:
//======================================================================
        JFrame frame = new JFrame("EcoViz");

        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);



//======================================================================   
//      North Panel:
//======================================================================
        JPanel pnlNorth = new JPanel();

        JLabel lblSearch = new JLabel("Search: ");
        JTextField search = new JTextField(20);
        JLabel lblFilter = new JLabel("Filter: ");
        JComboBox filter = new JComboBox();
            filter.addItem("basic filter 1");
            filter.addItem("basic filter 2");
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
        imgPanel mainPanel = new imgPanel(land);
            mainPanel.setPreferredSize(new Dimension(land.getDimX(),land.getDimY()));
            mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            mainPanel.setBorder(BorderFactory.createRaisedBevelBorder());


        JPanel pnlWest = new JPanel();
            pnlWest.add(mainPanel);

            
//======================================================================
//      East Panel: 
//======================================================================
        JPanel pnlEast = new JPanel();
            pnlEast.setPreferredSize(new Dimension(land.getDimX()-50,land.getDimY()));
            pnlEast.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            pnlEast.setBorder(BorderFactory.createRaisedBevelBorder());

            JTextArea plantDescription = new JTextArea();
            pnlEast.add(plantDescription);
        
            //Add Components
            pnlEast.add(plantDescription);

//======================================================================
//      South Panel:
//======================================================================
        JPanel pnlSouth = new JPanel();
            JLabel lblZoom = new JLabel("ZOOM: ");
                JButton btnZoomIn = new JButton("+");
                JButton btnZoomOut = new JButton("-");

            JButton btnFire = new JButton("Simulate Fire");
            
            //Add Components
            pnlSouth.add(lblZoom);
            pnlSouth.add(btnZoomIn);
            pnlSouth.add(btnZoomOut);
            pnlSouth.add(btnFire);

//====================================================================== 
//      MenuBar:
//======================================================================
            JMenuBar mb = new JMenuBar();

            JMenu m1 = new JMenu("File");
                JMenuItem i1 = new JMenuItem("Load Files");
                JMenuItem i2 = new JMenuItem("Export as PNG");
                JMenuItem i3 = new JMenuItem("Exit");
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
        //frame.pack();
        frame.setVisible(true);
        splash.setVisible(true);

    }
}
