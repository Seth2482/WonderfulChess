package view;

import view.Panels.GradientPanel;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Locale;

public class ArchiveFrame extends JFrame {
    private JPanel MainPanel;
    private JTextField archivePath;
    private JButton chooseDirectory;
    private JPanel titlePanel;
    private JButton confirm;
    private JButton createNew;
    private JFileChooser chooser;
    private ArchiveFrame instance;

    public ArchiveFrame() {
        setContentPane(MainPanel);
        setSize(400, 600);
        setLocationRelativeTo(null);

        instance = this;
        chooseDirectory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // code from https://stackoverflow.com/questions/10083447/selecting-folder-destination-in-java
                chooser = new JFileChooser();
                chooser.setCurrentDirectory(new java.io.File("."));
                chooser.setDialogTitle("Choose your archive directory");
                chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                //
                // disable the "All files" option.
                //
                chooser.setAcceptAllFileFilterUsed(false);
                chooser.setFileFilter(new FileFilter() {
                    @Override
                    public boolean accept(File f) {
                        if(f.getName().toLowerCase(Locale.ROOT).endsWith(".wdfc.json")){
                            return true;
                        }else {
                            return false;
                        }
                    }

                    @Override
                    public String getDescription() {
                        return "Wonderful Chess Archive (*.wdfc.json)";
                    }
                });

                if (chooser.showOpenDialog(instance) == JFileChooser.APPROVE_OPTION) {
                    archivePath.setText(chooser.getSelectedFile().getAbsolutePath());
                }
                else {
                    System.out.println("No Selection ");
                }
            }
        });


        createNew.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                ChessGameFrame mainFrame = new ChessGameFrame(1000, 760);
                mainFrame.setVisible(true);
            }
        });
    }

    private void createUIComponents() {
        titlePanel = new GradientPanel("#83a4d4", "#b6fbff");
    }
}
