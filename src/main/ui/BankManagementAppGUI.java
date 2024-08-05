package ui;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import model.Manager;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.panels.AccountsPanel;
import ui.panels.HomePanel;
import ui.panels.TransactionsPanel;

// BankManagementAppGUI represents a Bank Management application with GUI
public class BankManagementAppGUI extends JFrame {
    private static final String JSON_STORE = "./data/manager.json";
    public static final int HOME_PANEL_INDEX = 0;
    public static final int ACCOUNTS_PANEL_INDEX = 1;
    public static final int TRANSACTIONS_PANEL_INDEX = 2;
    public static final int WIDTH = 700;
    public static final int HEIGHT = 500;
    private JTabbedPane sidebar;
    private Manager manager;
    private JsonWriter jsonWriter;
    private JsonReader jsonReader;
    private HomePanel homePanel;
    private AccountsPanel accountsPanel;
    private TransactionsPanel transactionsPanel;

    // EFFECTS: constructs an instance of a BankManagementAppGUI
    public BankManagementAppGUI() {
        super("Bank Management");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        init();

        this.sidebar.setTabPlacement(JTabbedPane.LEFT);

        loadPanels();
        add(this.sidebar);

        setVisible(true);
    }

    // MODIFIES: this
    // EFFECTS: instantiates manager, writer, reader and sidebar
    public void init() {
        this.manager = new Manager();
        this.jsonWriter = new JsonWriter(JSON_STORE);
        this.jsonReader = new JsonReader(JSON_STORE);
        this.sidebar = new JTabbedPane();
    }

    //MODIFIES: this
    //EFFECTS: adds panels to this GUI
    private void loadPanels() {
        homePanel = new HomePanel(this);
        accountsPanel = new AccountsPanel(this);
        transactionsPanel = new TransactionsPanel(this);

        sidebar.add(homePanel, HOME_PANEL_INDEX);
        sidebar.setTitleAt(HOME_PANEL_INDEX, "Home");
        sidebar.add(accountsPanel, ACCOUNTS_PANEL_INDEX);
        sidebar.setTitleAt(ACCOUNTS_PANEL_INDEX, "Accounts");
        sidebar.add(transactionsPanel, TRANSACTIONS_PANEL_INDEX);
        sidebar.setTitleAt(TRANSACTIONS_PANEL_INDEX, "Transactions");
    }

    //EFFECTS: returns Manager object controlled by this UI
    public Manager getManager() {
        return this.manager;
    }

    //EFFECTS: returns sidebar of this UI
    public JTabbedPane getTabbedPane() {
        return this.sidebar;
    }

    //EFFECTS: returns transactions panel of this UI
    public TransactionsPanel getTransactionsPanel() {
        return this.transactionsPanel;
    }

    // EFFECTS: saves manager to file
    public void saveData() {
        try {
            jsonWriter.open();
            jsonWriter.write(this.manager);
            jsonWriter.close();
        } catch (FileNotFoundException e) {
            // do nothing
        }
    }

    // MODIFIES: this
    // EFFECTS: loads manager from file
    public void loadData() {
        try {
            this.manager = jsonReader.read();
            accountsPanel = new AccountsPanel(this);
            transactionsPanel = new TransactionsPanel(this);
            sidebar.remove(ACCOUNTS_PANEL_INDEX);
            sidebar.add(accountsPanel, ACCOUNTS_PANEL_INDEX);
            sidebar.setTitleAt(ACCOUNTS_PANEL_INDEX, "Accounts");
            sidebar.remove(TRANSACTIONS_PANEL_INDEX);
            sidebar.add(transactionsPanel, TRANSACTIONS_PANEL_INDEX);
            sidebar.setTitleAt(TRANSACTIONS_PANEL_INDEX, "Transactions");
        } catch (IOException e) {
            // do nothing
        }
    }
}