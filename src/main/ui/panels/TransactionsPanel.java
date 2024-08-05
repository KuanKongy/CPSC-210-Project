package ui.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;

import model.Transaction;

import ui.BankManagementAppGUI;

// TransactionsPanel represents sidebar's transactions panel
public class TransactionsPanel extends Panel {
    private JList<Transaction> list;
    private DefaultListModel<Transaction> listModel;
    private JTextArea textArea;

    private JFrame sortWindow;
    private JComboBox<String> comboType;

    private JFrame sortedWindow;
    private DefaultListModel<Transaction> listSortModel;
    private JList<Transaction> listSort;

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // EFFECTS: constructs an instance of a Transactions panel
    public TransactionsPanel(BankManagementAppGUI controller) {
        super(controller);

        placeHistory(controller);
        placeTransactionDetailer();
        placeSortButton(controller);
    }

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // MODIFIES: this
    // EFFECTS: constructs a list that shows history
    private void placeHistory(BankManagementAppGUI controller) {
        this.listModel = new DefaultListModel<>();

        for (Transaction transaction : controller.getManager().getHistory()) {
            listModel.addElement(transaction);
        }

        this.list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setPreferredSize(new Dimension(300, 163));
        
        list.getSelectionModel().addListSelectionListener(e -> {
            Transaction t = list.getSelectedValue();
            textArea.setText("Transaction's id: " + t.getId()
                    + "\nTransaction's amount: " + t.getAmount()
                    + "\nTransaction's receiver: " + t.getReceiver()
                    + "\nTransaction's receiver's number: " + t.getNumber()
                    + "\nTransaction's type: " + t.getType());
        });

        this.add(listScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: constructs a text area that shows selected transaction's details
    private void placeTransactionDetailer() {
        textArea = new JTextArea(10, 25);
        JScrollPane scrollPane = new JScrollPane(textArea); 
        textArea.setEditable(false);

        this.add(scrollPane);
    }

    public DefaultListModel<Transaction> getListModel() {
        return this.listModel;
    }

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // MODIFIES: this
    // EFFECTS: constructs a sort button that sorts transactions by type
    private void placeSortButton(BankManagementAppGUI controller) {
        JButton sortButton = new JButton("Sort by type");

        sortButton.addActionListener(e -> {
            String[] types = { "TS", "TF", "WD" };
            comboType = new JComboBox<>(types);

            JLabel labelType = new JLabel();
            labelType.setText("Enter type you want to sort by:");

            sortWindow = new JFrame("Sorting transactions");
            sortWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            sortWindow.setSize(350, 98);
            sortWindow.setLayout(new FlowLayout());
            sortWindow.add(labelType);
            sortWindow.add(comboType);
            placeSubmitSortButton(controller);
            sortWindow.setVisible(true);
            sortWindow.setResizable(false);
        });

        this.add(sortButton);
    }

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // MODIFIES: this
    // EFFECTS: places a submit button on sortWindow
    private void placeSubmitSortButton(BankManagementAppGUI controller) {
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            String type = (String) comboType.getSelectedItem();

            sortedWindow = new JFrame("Transactions of type " + type);
            sortedWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            sortedWindow.setSize(300, 120);
            sortedWindow.setLayout(new FlowLayout());
            placeSortList();
            
            for (Transaction transaction : controller.getManager().sortType(type)) {
                listSortModel.addElement(transaction);
            }

            JScrollPane listScrollPane = new JScrollPane(listSort);
            listScrollPane.setPreferredSize(new Dimension(250, 80));
            sortedWindow.add(listScrollPane);
            
            sortedWindow.setVisible(true);
            sortedWindow.setResizable(false);
        });

        sortWindow.add(submitButton);
    }

    // MODIFIES: this
    // EFFECTS: places a sort list on sortedWindow
    private void placeSortList() {
        listSortModel = new DefaultListModel<>();
        listSort = new JList<>(listSortModel);
        listSort.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listSort.setSelectedIndex(0);
        listSort.setVisibleRowCount(5);
        listSort.getSelectionModel().addListSelectionListener(event -> {
            Transaction t = listSort.getSelectedValue();
            textArea.setText("Transaction's id: " + t.getId()
                    + "\nTransaction's amount: " + t.getAmount()
                    + "\nTransaction's receiver: " + t.getReceiver()
                    + "\nTransaction's receiver's number: " + t.getNumber()
                    + "\nTransaction's type: " + t.getType());
        });
    }
}
