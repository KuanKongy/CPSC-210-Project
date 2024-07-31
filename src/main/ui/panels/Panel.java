package ui.panels;

import javax.swing.*;

import ui.BankManagementAppGUI;

import java.awt.*;

public abstract class Panel extends JPanel {
    protected final BankManagementAppGUI controller;

    //REQUIRES: BankManagementAppGUI controller that holds this tab
    public Panel (BankManagementAppGUI controller) {
        this.controller = controller;
    }

    //EFFECTS: creates and returns row with button included
    public JPanel formatButtonRow(JButton b) {
        JPanel p = new JPanel();
        p.setLayout(new FlowLayout());
        p.add(b);

        return p;
    }

    //EFFECTS: returns the SmartHomeUI controller for this tab
    public BankManagementAppGUI getController() {
        return controller;
    }

}
