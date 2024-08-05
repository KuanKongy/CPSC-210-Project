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
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

import model.Account;
import model.Transaction;
import ui.BankManagementAppGUI;

// AccountsPanel represents sidebar's accounts panel
public class AccountsPanel extends Panel {
    private static final String[] CURRENCIES = { "CAD", "USD" };
    private JList<Account> list;
    private DefaultListModel<Account> listModel;
    private JTextArea textArea;

    private JFrame addWindow;
    private JTextField fieldName;
    private JTextField fieldNumber;
    private JComboBox<String> comboCurrency;
    private JLabel labelName;
    private JLabel labelNumber;
    private JLabel labelCurrency;

    private JFrame changeWindow;
    private JComboBox<String> comboChangeCurrency;
    private JComboBox<String> comboFees;

    private JFrame makeWindow;
    private JTextField fieldReceiver;
    private JTextField fieldRecNumber;
    private JTextField fieldAmount;
    private JTextField fieldId;
    private JComboBox<String> comboType;
    private JLabel labelReceiver;
    private JLabel labelRecNumber;
    private JLabel labelAmount;
    private JLabel labelType;
    private JLabel labelId;

    private JFrame depositWindow;
    private JTextField fieldDeposit;

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // EFFECTS: constructs an instance of an Accounts panel
    public AccountsPanel(BankManagementAppGUI controller) {
        super(controller);

        placeAccounts(controller);
        placeAccountDetailer();
        placeAddButton(controller);
        placeChangeButton();
        placeMakeButton(controller);
        placeDepositButton();
        placeRemoveButton(controller);
    }

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // MODIFIES: this
    // EFFECTS: constructs a list that shows accounts
    private void placeAccounts(BankManagementAppGUI controller) {
        this.listModel = new DefaultListModel<>();

        for (Account account : controller.getManager().getAccounts()) {
            listModel.addElement(account);
        }

        this.list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setSelectedIndex(0);
        list.setVisibleRowCount(5);
        JScrollPane listScrollPane = new JScrollPane(list);
        listScrollPane.setPreferredSize(new Dimension(300, 163));

        list.getSelectionModel().addListSelectionListener(e -> {
            Account a = list.getSelectedValue();
            if (a != null) {
                textArea.setText("Account's number: " + a.getNumber()
                        + "\nAccount's owner: " + a.getName()
                        + "\nAccount's currency: " + a.checkCurrency()
                        + "\nAccount's balance: " + a.checkBalance()
                        + "\nIs account closed? " + a.isClosed()
                        + "\nDoes account pay fees?: " + a.haveFees());
            }
        });

        this.add(listScrollPane);
    }

    // MODIFIES: this
    // EFFECTS: constructs a text area that shows selected account's details
    private void placeAccountDetailer() {
        textArea = new JTextArea(10, 25);
        JScrollPane scrollPane = new JScrollPane(textArea);
        textArea.setEditable(false);

        this.add(scrollPane);
    }

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // MODIFIES: this
    // EFFECTS: constructs an add button that adds an account
    private void placeAddButton(BankManagementAppGUI controller) {
        JButton addButton = new JButton("Add account");

        addButton.addActionListener(e -> {
            addWindow = new JFrame("Adding account");
            initForAdd();

            addWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            addWindow.setSize(370, 160);
            addWindow.setLayout(new FlowLayout());
            addWindow.add(labelName);
            addWindow.add(fieldName);
            addWindow.add(labelNumber);
            addWindow.add(fieldNumber);
            addWindow.add(labelCurrency);
            addWindow.add(comboCurrency);
            placeSubmitAddButton(controller);
            addWindow.setVisible(true);
            addWindow.setResizable(false);
        });

        this.add(addButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes required fields and labels for addWindow
    private void initForAdd() {
        fieldName = new JTextField(5);
        fieldNumber = new JTextField(5);
        comboCurrency = new JComboBox<>(CURRENCIES);

        labelName = new JLabel();
        labelName.setText("Enter accounts's owner's name:");
        labelNumber = new JLabel();
        labelNumber.setText("Enter preferred accounts's number (5 digits):");
        labelCurrency = new JLabel();
        labelCurrency.setText("Choose preferred accounts's currency:");
    }

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // MODIFIES: this, controller
    // EFFECTS: places a submit button on addWindow
    private void placeSubmitAddButton(BankManagementAppGUI controller) {
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            String name = fieldName.getText();
            int number = Integer.parseInt(fieldNumber.getText());
            String currency = (String) comboCurrency.getSelectedItem();

            Account account = new Account(name, number, currency);
            listModel.addElement(account);
            controller.getManager().addAccount(name, number, currency);
        });

        addWindow.add(submitButton);
    }

    // MODIFIES: this
    // EFFECTS: constructs a change button that changes details for a selected account
    private void placeChangeButton() {
        JButton changeButton = new JButton("Change details");

        changeButton.addActionListener(e -> {
            comboChangeCurrency = new JComboBox<>(CURRENCIES);
            String[] fees = { "Yes", "No" };
            comboFees = new JComboBox<>(fees);

            JLabel labelChangeCurrency = new JLabel();
            labelChangeCurrency.setText("Choose preferred accounts's currency:");
            JLabel labelFees = new JLabel();
            labelFees.setText("Do you want to pay fees? (Yes or No):");

            changeWindow = new JFrame("Changing details");

            changeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            changeWindow.setSize(350, 130);
            changeWindow.setLayout(new FlowLayout());
            changeWindow.add(labelChangeCurrency);
            changeWindow.add(comboChangeCurrency);
            changeWindow.add(labelFees);
            changeWindow.add(comboFees);
            placeSubmitChangeButton();
            changeWindow.setVisible(true);
            changeWindow.setResizable(false);
        });

        this.add(changeButton);
    }

    // MODIFIES: a
    // EFFECTS: places a submit button on changeWindow
    private void placeSubmitChangeButton() {
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            Account a = list.getSelectedValue();
            if (a != null) {
                String currency = (String) comboChangeCurrency.getSelectedItem();
                String hasFees = (String) comboFees.getSelectedItem();
                boolean payFees = false;
                if (hasFees.equals("Yes")) {
                    payFees = true;
                }

                a.changeCurrency(currency);
                a.changeFees(payFees);
            }
        });

        changeWindow.add(submitButton);
    }

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // MODIFIES: this
    // EFFECTS: constructs a make button that makes a transaction for a selected account
    private void placeMakeButton(BankManagementAppGUI controller) {
        JButton makeButton = new JButton("Make transaction");

        makeButton.addActionListener(e -> {
            makeWindow = new JFrame("Making transaction");
            initForMake();

            makeWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            makeWindow.setSize(370, 222);
            makeWindow.setLayout(new FlowLayout());
            makeWindow.add(labelReceiver);
            makeWindow.add(fieldReceiver);
            makeWindow.add(labelRecNumber);
            makeWindow.add(fieldRecNumber);
            makeWindow.add(labelAmount);
            makeWindow.add(fieldAmount);
            makeWindow.add(labelType);
            makeWindow.add(comboType);
            makeWindow.add(labelId);
            makeWindow.add(fieldId);
            placeSubmitMakeButton(controller);
            makeWindow.setVisible(true);
            makeWindow.setResizable(false);
        });

        this.add(makeButton);
    }

    // MODIFIES: this
    // EFFECTS: initializes required fields and labels for makeWindow
    private void initForMake() {
        fieldReceiver = new JTextField(5);
        fieldRecNumber = new JTextField(5);
        fieldAmount = new JTextField(5);
        String[] types = { "TS", "TF", "WD" };
        comboType = new JComboBox<>(types);
        fieldId = new JTextField(5);

        labelReceiver = new JLabel();
        labelReceiver.setText("Enter receiver's name:");
        labelRecNumber = new JLabel();
        labelRecNumber.setText("Enter receiver's account number (5 digits):");
        labelAmount = new JLabel();
        labelAmount.setText("Enter amount you want to send:");
        labelType = new JLabel();
        labelType.setText("Choose this transaction's type:");
        labelId = new JLabel();
        labelId.setText("Enter preferred transaction's id (5 digits):");
    }

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // MODIFIES: account, controller
    // EFFECTS: places a submit button on makeWindow
    private void placeSubmitMakeButton(BankManagementAppGUI controller) {
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            Account account = list.getSelectedValue();
            int i = list.getSelectedIndex();
            if (account != null) {
                String name = fieldReceiver.getText();
                int number = Integer.parseInt(fieldRecNumber.getText());
                int amount = Integer.parseInt(fieldAmount.getText());
                int id = Integer.parseInt(fieldId.getText());
                String type = (String) comboType.getSelectedItem();

                Transaction transaction = new Transaction(name, number, amount, type, id);
                controller.getTransactionsPanel().getListModel().addElement(transaction);
                controller.getManager().makeTransaction(i, name, number, amount, type, id);
            }
        });

        makeWindow.add(submitButton);
    }

    // MODIFIES: this
    // EFFECTS: constructs a deposit button that deposits money for a selected account
    private void placeDepositButton() {
        JButton depositButton = new JButton("Make deposit");

        depositButton.addActionListener(e -> {
            fieldDeposit = new JTextField(5);

            JLabel labelDeposit = new JLabel();
            labelDeposit.setText("Enter amount you want to deposit:");

            depositWindow = new JFrame("Depositing money");

            depositWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            depositWindow.setSize(350, 97);
            depositWindow.setLayout(new FlowLayout());
            depositWindow.add(labelDeposit);
            depositWindow.add(fieldDeposit);
            placeSubmitDepositButton();
            depositWindow.setVisible(true);
            depositWindow.setResizable(false);
        });

        this.add(depositButton);
    }

    // MODIFIES: a
    // EFFECTS: places a submit button on depositWindow
    private void placeSubmitDepositButton() {
        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(e -> {
            Account a = list.getSelectedValue();
            if (a != null) {
                int deposit = Integer.parseInt(fieldDeposit.getText());
                a.makeDeposit(deposit);
            }
        });

        depositWindow.add(submitButton);
    }

    // REQUIRES: BankManagementAppGUI controller that holds this panel
    // MODIFIES: a, this, controller
    // EFFECTS: constructs a remove button that removes selected account
    private void placeRemoveButton(BankManagementAppGUI controller) {
        JButton removeButton = new JButton("Remove account");

        removeButton.addActionListener(e -> {
            Account a = list.getSelectedValue();
            int i = list.getSelectedIndex();
            if (a != null) {
                listModel.removeElement(a);
                controller.getManager().remove(i);
            }
        });

        this.add(removeButton);
    }
}
