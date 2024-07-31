package ui.panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import model.Transaction;
import ui.BankManagementAppGUI;

public class HomePanel extends Panel implements ListSelectionListener {
    private JList<Transaction> list;
    private DefaultListModel<Transaction> listModel;
    private JButton removeButton;

    public HomePanel(BankManagementAppGUI controller) {
        super(controller);

        listModel = new DefaultListModel<>();

        list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        //list.addListSelectionListener(this);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setPreferredSize(new Dimension(250, 80));

        placeSaveButton();
    }

    //EFFECTS: constructs a status button that switches to the report tab on the console
    private void placeSaveButton() {
        JPanel saveBlock = new JPanel();
        JButton saveButton = new JButton("Save");
        saveBlock.add(formatButtonRow(saveButton));

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String buttonPressed = e.getActionCommand();
                if (buttonPressed.equals("Save")) {
                    controller.saveData();
                }
            }
        });

        this.add(saveBlock);
    }

    //This method is required by ListSelectionListener.
    public void valueChanged(ListSelectionEvent e) {
        if (e.getValueIsAdjusting() == false) {

            if (list.getSelectedIndex() == -1) {
            //No selection, disable fire button.
                removeButton.setEnabled(false);

            } else {
            //Selection, enable the fire button.
                removeButton.setEnabled(true);
            }
        }
    }

}
