package ui.panels;

import java.awt.Image;

import javax.swing.*;

import ui.BankManagementAppGUI;

// HomePanel represents sidebar's home panel
public class HomePanel extends Panel {
    private static final String IMAGE_STORE = "./images/Logo_Sberbank.png";
    
    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // EFFECTS: constructs an instance of a Home panel
    public HomePanel(BankManagementAppGUI controller) {
        super(controller);

        placeVisualComponent();
        placeSaveButton();
        placeLoadButton();
    }

    //EFFECTS: constructs a save button that saves data
    private void placeSaveButton() {
        JPanel saveBlock = new JPanel();
        JButton saveButton = new JButton("Save data");
        saveBlock.add(formatButtonRow(saveButton));

        // saveButton.addActionListener(new ActionListener() {
        //     @Override
        //     public void actionPerformed(ActionEvent e) {
        //         String buttonPressed = e.getActionCommand();
        //         if (buttonPressed.equals("Save")) {
        //             controller.saveData();
        //         }
        //     }
        // });

        saveButton.addActionListener(e -> {
            getController().saveData();
        });

        this.add(saveBlock);
    }

    //EFFECTS: constructs a load button that loads data
    private void placeLoadButton() {
        JPanel loadBlock = new JPanel();
        JButton loadButton = new JButton("Load data");
        loadBlock.add(formatButtonRow(loadButton));

        loadButton.addActionListener(e -> {
            getController().loadData();
        });

        this.add(loadBlock);
    }

    //EFFECTS: constructs an image label
    private void placeVisualComponent() {
        ImageIcon image = new ImageIcon(IMAGE_STORE);
        Image imageIcon = image.getImage();
        Image icon = imageIcon.getScaledInstance(660, 150, Image.SCALE_SMOOTH);
        image = new ImageIcon(icon);

        JLabel imageLabel = new JLabel(image);
        imageLabel.setText("Welcome to the Bank Management app!");
        imageLabel.setHorizontalTextPosition(JLabel.CENTER);
        imageLabel.setVerticalTextPosition(JLabel.BOTTOM);

        this.add(imageLabel);
    }
}
