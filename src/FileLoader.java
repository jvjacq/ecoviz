import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class FileLoader{

    private JFrame loadIn;
    private JFileChooser fChooser;
    private JButton load;
    private FileController filecontroller;

    public FileLoader(){
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
                    ImageIcon path = new ImageIcon("resources/ECOVIZ.gif");
                    loading.setIcon(path);
                
                //Add button for loading in files:
                load = new JButton();
                    load.setText("Load Files");
                    //load.addActionListener(filecontroller);


                //splash.getContentPane().add(loading, BorderLayout.CENTER);
                loadIn.add(loading);
                loadIn.getContentPane().add(BorderLayout.SOUTH, load);

                loadIn.setVisible(true);
    }

    public JFileChooser getChooser(){
        return this.fChooser;
    }

}
