package View.Dialog;

import Archive.Archive;
import View.Chessboard;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import java.awt.event.*;
import java.io.File;
import java.util.Locale;

public class ChoosePathDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField pathTextField;
    private JButton chooseButton;
    private JFileChooser chooser;
    private ChoosePathDialog instance;

    public ChoosePathDialog() {
        setLocationRelativeTo(null);
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setSize(600, 200);
        setTitle("Where do you want to save your archive?");
        chooser = new JFileChooser();
        chooser.setAcceptAllFileFilterUsed(false);
        chooser.setFileFilter(new FileFilter() {
            @Override
            public boolean accept(File f) {
                if (f.getName().toLowerCase(Locale.ROOT).endsWith(".wdfc.json")) {
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public String getDescription() {
                return "Wonderful Chess Archive (*.wdfc.json)";
            }
        });
        chooser.setDialogTitle("Specify a file to save");
        instance = this;


        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        chooseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (chooser.showSaveDialog(instance) == JFileChooser.APPROVE_OPTION) {
                    String path = chooser.getSelectedFile().getAbsolutePath();
                    if (!path.endsWith(".wdfc.json")) {
                        path += ".wdfc.json";
                    }

                    pathTextField.setText(path);
                }
            }
        });

        pathTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                changedUpdate(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                boolean validFile = pathTextField.getText().endsWith(".wdfc.json");
                buttonOK.setEnabled(validFile);
            }
        });

    }

    private void onOK() {
        Archive archive = Chessboard.getInstance().getArchive();
        archive.setPath(this.pathTextField.getText());
        archive.save();
        dispose();

        JOptionPane.showMessageDialog(Chessboard.getInstance(), "Your archive has saved!");

    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        ChoosePathDialog dialog = new ChoosePathDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
