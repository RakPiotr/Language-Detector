import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class GUI {

    private JFrame frame;
    private JTextField filePathField;
    private JLabel resultLabel;

    public GUI() {
        // Create the main frame
        frame = new JFrame("Language Detector");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create the file chooser panel
        JPanel fileChooserPanel = new JPanel(new BorderLayout());
        filePathField = new JTextField();
        filePathField.setEditable(false); // Make the text field read-only
        JButton browseButton = new JButton("Choose File");
        fileChooserPanel.add(filePathField, BorderLayout.CENTER);
        fileChooserPanel.add(browseButton, BorderLayout.EAST);

        // Create the result label
        resultLabel = new JLabel("Result: ", SwingConstants.CENTER);

        // Create the button panel
        JPanel buttonPanel = new JPanel();
        JButton findLanguageButton = new JButton("Find Language");
        buttonPanel.add(findLanguageButton);

        // Add components to the frame
        frame.add(fileChooserPanel, BorderLayout.NORTH);
        frame.add(resultLabel, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Add action listeners
        browseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chooseFile();
            }
        });

        findLanguageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findLanguage();
            }
        });

        // Show the frame
        frame.setVisible(true);
    }

    /**
     * Opens a file chooser to select a file.
     */
    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Select a File");
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    /**
     * Detects the language of the selected file using the CheckLanguage class.
     */
    private void findLanguage() {
        String filePath = filePathField.getText();
        if (filePath.isEmpty()) {
            resultLabel.setText("Result: Please choose a file first.");
        } else {
            try {
                String language = CheckLanguage.fromFile(filePath);
                resultLabel.setText("Result: Detected Language is " + language);
            } catch (Exception e) {
                resultLabel.setText("Result: Error detecting language.");
            }
        }
    }
}