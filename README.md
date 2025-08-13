# Bank Application

A Java-based desktop banking application built with Swing for a user-friendly GUI, enabling account management, secure fund transfers, withdrawals, and data persistence using serialization. This project demonstrates object-oriented programming, data structures (ArrayList), and robust error handling, simulating a lightweight database system for structured data management.

## Features
- **Account Management:** Create, update, and delete accounts with details like name, account number, PIN, and balance.
- **Fund Transfers:** Securely transfer funds between accounts with PIN validation and balance checks.
- **Withdrawals:** Withdraw funds with account number and PIN verification, ensuring sufficient balance.
- **Data Persistence:** Save and load account data to/from a file (`BankRecord.txt`) using Java serialization.
- **User Interface:** Intuitive Swing-based GUI with input validation and user feedback via JOptionPane.
- **Error Handling:** Robust checks for negative amounts, duplicate accounts, and incorrect PINs.

## Tech Stack
- **Language26**: Java, Swing, Serialization, ArrayList
- **IDE**: NetBeans (or any Java IDE like Eclipse, IntelliJ IDEA)
- **File**: BankRecord.txt (for data persistence)

## Setup Instructions
1. **Clone the Repository:**
   ```bash
   git clone https://github.com/Abhinav2656/BankApplication.git
   ```
2. **Open in IDE:** Import the project into your preferred Java IDE (e.g., NetBeans, Eclipse).
3. **Run the Application:** Execute `Task1_Driver.java` to launch the GUI.
4. **Dependencies:** Requires Java (JDK 8 or higher). No external libraries needed.
5. **Data File:** The application creates/uses `BankRecord.txt` in the project directory for data storage.

## Usage
- **Create Account:** Enter name, account number, PIN, and initial amount to create a new account.
- **Transfer Funds:** Specify sender/receiver account numbers, PIN, and amount for secure transfers.
- **Withdraw:** Input account number, PIN, and amount to withdraw funds.
- **Update/Delete:** Modify account details (name, PIN) or delete accounts via dedicated buttons.
- **Print Accounts:** View all accounts with their details in a dialog box.
- **Refresh:** Clear all input fields for a fresh start.

## Project Structure
- `Account.java`: Defines the `Account` class with attributes (name, account number, PIN, balance) and getters/setters.
- `Bank.java`: Manages account operations (add, delete, update, transfer, withdraw) and file I/O for persistence.
- `Task1_Driver.java`: Implements the Swing GUI and event listeners for user interactions.
- `BankRecord.txt`: Auto-generated file for storing serialized account data.

## Future Improvements
- Add a database (e.g., PostgreSQL) for robust data storage instead of file-based serialization.
- Implement stronger security (e.g., hashed PINs) for production use.
- Enhance GUI with modern frameworks like JavaFX for better aesthetics.
- Add transaction history and logging for auditability.
- Include unit tests for core functionality (e.g., transfer, withdrawal logic).

