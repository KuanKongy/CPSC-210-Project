# Bank Management Application

## Project Description
The **Bank Management** application will let users create *bank accounts* and efficiently manage them. Possible features include checking *balance* and making *deposits*; making *transactions* or *transfers*; listing all *bank accounts* and the *history* of *transactions* or *transfers*. International college students or businessmen can use this application to have multiple accounts and effortlessly manage them for different and fast transactions. 

This project is of interest to me because I had trouble trying to open a bank account and transfer money from an international one to pay for my tuition, and I wished there was a more straightforward system to work with.

## User Stories
- As a user, I want to be able to create a bank account and add it to my list of bank accounts.
- As a user, I want to be able to view my history of transactions or transfers and my list of bank accounts.
- As a user, I want to be able to select a bank account or transaction and view its details, such as balance and fees.
- As a user, I want to be able to select a bank account and change or update details, for example, change currency and cancel fees.
- As a user, I want to be able to select a bank account and make a transaction or transfer to other people or accounts.
- As a user, I want to be able to update my history of transactions and sort transactions by types of receivers.
- As a user, I want to be able to close an account and remove an account from my list of bank accounts.
- As a user, I want to be able to save my list of bank accounts and my history of transactions to file (if I so choose).
- As a user, I want to be able to load my list of bank accounts and my history of transactions from file (if I so choose).

## Instructions for Grader
- You can generate the first required action related to the user story "adding multiple Xs to a Y" by opening the "Accounts" panel in the left sidebar and clicking the button labelled "Add account". The button will open a new window, and, after inserting details, it will add the account to the list of accounts.
- You can generate the second required action related to the user story "adding multiple Xs to a Y" by opening the "Accounts" panel in the left sidebar and clicking the button labelled "Remove account". The button will remove the selected account from the list of accounts.
- You can locate my visual component by opening the "Home" panel in the left sidebar, the logo "Sber Bank" is the visual component.
- You can save the state of my application by opening the "Home" panel in the left sidebar and clicking the button labelled "Save data".
- You can reload the state of my application by opening the "Home" panel in the left sidebar and clicking the button labelled "Load data".

## Phase 4: Task 2
Sun Aug 04 22:54:56 PDT 2024  
Added account 11111 to list of accounts  
Sun Aug 04 22:54:56 PDT 2024  
Changed account 11111's fees to true  
Sun Aug 04 22:54:56 PDT 2024  
Closed an account 11111  
Sun Aug 04 22:54:56 PDT 2024  
Made a deposit on account 11111 by 10000.0  
Sun Aug 04 22:54:56 PDT 2024  
Added account 22222 to list of accounts  
Sun Aug 04 22:54:56 PDT 2024  
Changed account 22222's fees to true  
Sun Aug 04 22:54:56 PDT 2024  
Closed an account 22222  
Sun Aug 04 22:54:56 PDT 2024  
Made a deposit on account 22222 by 20000.0  
Sun Aug 04 22:54:56 PDT 2024  
Added account 33333 to list of accounts  
Sun Aug 04 22:54:56 PDT 2024  
Changed account 33333's fees to false  
Sun Aug 04 22:54:56 PDT 2024  
Closed an account 33333  
Sun Aug 04 22:54:56 PDT 2024  
Made a deposit on account 33333 by 30000.0  
Sun Aug 04 22:55:03 PDT 2024  
Added account 12345 to list of accounts  
Sun Aug 04 22:55:15 PDT 2024  
Updated balance and changed account 12345's currency to USD  
Sun Aug 04 22:55:15 PDT 2024  
Changed account 12345's fees to false  
Sun Aug 04 22:55:36 PDT 2024  
Made a transaction on account 33333 by 1234.0  
Sun Aug 04 22:55:36 PDT 2024  
Added transaction 12354 TS to history  
Sun Aug 04 22:55:42 PDT 2024  
Made a deposit on account 33333 by 10000.0  
Sun Aug 04 22:55:49 PDT 2024  
Closed an account 12345  
Sun Aug 04 22:55:49 PDT 2024  
Account 12345 removed from list of accounts  

## Phase 4: Task 3
If I had more time to work on the project, I would refactor the design of the abstract Panel class with its subclasses. I should've improved coupling by abstracting duplicated code into methods and improved the single responsibility principle by splitting subclasses into smaller ones. The subclasses have lots of duplicated code, such as fields, the creation of buttons, and the creation of lists that should've been placed inside the abstract panel class to decrease the duplication. The subclasses have lots of buttons that create new windows; they should've been abstracted into new classes to improve readability and debugging. The subclasses have an indirect bi-directional relationship with the GUI; currently, they make changes to the manager by dependency through passing parameters, which can be confusing and misleading because we have to reflect some changes (if we add an account to the list of accounts, both the manager in the GUI and the JList in the panel should reflect the change).