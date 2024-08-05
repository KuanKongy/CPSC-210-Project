package ui.panels;

import javax.swing.*;

import ui.BankManagementAppGUI;

import java.awt.*;

// Panel represent usual GUI panels
public abstract class Panel extends JPanel {
    private final BankManagementAppGUI controller;

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    public Panel(BankManagementAppGUI controller) {
        this.controller = controller;
    }

    // EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    // EFFECTS: returns the controller for this tab
    public BankManagementAppGUI getController() {
        return controller;
    }

}
