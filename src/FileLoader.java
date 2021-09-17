import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class FileLoader{

    private JFrame loadIn;
    private JFileChooser fChooser;
    private JButton load;
    private FileController fileController;

    //========================================================================
    //      File Selection
    //========================================================================
    private JFrame frame;
    
    File[] orderedFiles = new File[4];
    public void loadFiles(){
      fChooser = new JFileChooser();
      fChooser.setMultiSelectionEnabled(true);
      fChooser.showOpenDialog(frame);
      File[] files = fChooser.getSelectedFiles();
      int numFiles = files.length;
      boolean bElevation = false;
      boolean bSpecies = false;
      boolean bCanopy = false;
      boolean bUndergrowth = false;

      if (numFiles != 4){
          System.out.println(files.length);
          for (int x = 0; x < files.length; ++x)//###
              System.out.println(files[x]);//###
          System.out.println("Not enough files selected. Please select 1 '.elv' file, 2 '.pdb' files and 1 '.spc.txt' file.");
          loadFiles();
          numFiles = files.length;
      }
      if (numFiles == 4){
        numFiles = 0;
        for (int x = 0; x < 4; ++x){
          if (files[x].toString().contains(".elv")){
            orderedFiles[0] = files[x];
            bElevation = true;
          }
          if (files[x].toString().contains(".spc.txt")){
            orderedFiles[1] = files[x];
            bSpecies = true;
          }
          if (files[x].toString().contains("canopy.pdb")){
            orderedFiles[2] = files[x];
            bCanopy = true;
          }
          if (files[x].toString().contains("undergrowth.pdb")){
            orderedFiles[3] = files[x];
            bUndergrowth = true;
          }
        }
        if (!(bElevation && bSpecies && bCanopy && bUndergrowth)){
          System.out.println("The selected files could not be loaded, please try again.");
          loadFiles();
        }
      }
    }

    public File[] getFiles(){
      return orderedFiles;
    }

    public void loader(){
        //======================================================================
        //      Load in Files Frame:
        //======================================================================
        loadIn = new JFrame("Initialising");
        loadIn.setSize(400,400);
        loadIn.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        



        JLabel loading = new JLabel();
        ImageIcon path = new ImageIcon("resources/ECOVIZ.gif");
        loading.setIcon(path);

        //Add button for loading in files:
        load = new JButton();
        load.setText("Load Files");
        //load.addActionListener(fileController);

        loadIn.add(loading);
        loadIn.getContentPane().add(BorderLayout.SOUTH, load);

        loadIn.setVisible(true);

    }

}
