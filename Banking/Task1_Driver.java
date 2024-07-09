/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package banking;

/**
 *
 * @author abhin
 */
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;

class Account implements Serializable {
    String name;
    int account_number;
    String pin;
    double Amount;

    Account() {
        name = null;
        account_number = 0;
        pin = null;
        Amount = 0;
    }

    Account(String n, int acc, String pi, double amount) {
        name = n;
        account_number = acc;
        pin = pi;
        Amount = amount;
    }

    public void setName(String n) {
        name = n;
    }

    public void setAccountNumber(int n) {
        account_number = n;
    }

    public void setPIN(String p) {
        pin = p;
    }

    public void setAmount(double a) {
        Amount = a;
    }

    public String getName() {
        return name;
    }

    public int getAccountNumber() {
        return account_number;
    }

    public String getPIN() {
        return pin;
    }

    public double getAmount() {
        return Amount;
    }
}

class Bank {
    ArrayList<Account> AL = new ArrayList<Account>();

    public void deleteAccount(int accountNumber) {
        int indexToDelete = -1;
        for (int i = 0; i < AL.size(); i++) {
            if (AL.get(i).getAccountNumber() == accountNumber) {
                indexToDelete = i;
                break;
            }
        }
        if (indexToDelete != -1) {
            AL.remove(indexToDelete);
            JOptionPane.showMessageDialog(null, "Account Deleted Successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Account not Found");
        }
    }

    public void updateAccount(int accountNumber, String PIN, String newName, String newPIN) {
        for (Account account : AL) {
            if (account.getAccountNumber() == accountNumber && account.getPIN().equalsIgnoreCase(PIN)) {
                account.setName(newName);
                account.setPIN(newPIN);
                JOptionPane.showMessageDialog(null, "Account Updated Successfully");
                return;
            }
        }
        JOptionPane.showMessageDialog(null, "Account not Found or PIN incorrect");
    }

    public void addNewRecord(String name, int acc, String pin, double amount) {
        if (amount < 0) {
            JOptionPane.showMessageDialog(null, "Amount cannot be negative");
            return;
        }
        for (Account account : AL) {
            if (account.getAccountNumber() == acc) {
                JOptionPane.showMessageDialog(null, "Account already exists");
                return;
            }
        }

        Account ac = new Account(name, acc, pin, amount);
        AL.add(ac);
        JOptionPane.showMessageDialog(null, "Account Created Successfully");
    }

    public void transfer(int senderAcc, String senderPin, int receiverAcc, double amount) {
        int sender_index = -1;
        int receiver_index = -1;
        if (amount < 0) {
            JOptionPane.showMessageDialog(null, "Amount cannot be negative");
            return;
        }

        for (int i = 0; i < AL.size(); i++) {
            if (AL.get(i).getAccountNumber() == senderAcc && AL.get(i).getPIN().equals(senderPin)) {
                sender_index = i;
            }
            if (AL.get(i).getAccountNumber() == receiverAcc) {
                receiver_index = i;
            }
        }

        if (sender_index == -1 || receiver_index == -1) {
            JOptionPane.showMessageDialog(null, "Account not Found");
            return;
        }

        if (AL.get(sender_index).getAmount() >= amount) {
            AL.get(receiver_index).setAmount(AL.get(receiver_index).getAmount() + amount);
            AL.get(sender_index).setAmount(AL.get(sender_index).getAmount() - amount);
            JOptionPane.showMessageDialog(null, "Transfer Successful");
        } else {
            JOptionPane.showMessageDialog(null, "Sender does not have enough balance in the account");
        }
    }

    public void withdraw(int accountNumber, double amount, String pin) {
        int person_index = -1;
        if (amount < 0) {
            JOptionPane.showMessageDialog(null, "Amount cannot be negative");
            return;
        }

        for (int i = 0; i < AL.size(); i++) {
            if (AL.get(i).getAccountNumber() == accountNumber && AL.get(i).getPIN().equals(pin)) {
                person_index = i;
                break;
            }
        }

        if (person_index == -1) {
            JOptionPane.showMessageDialog(null, "Account not Found or PIN incorrect");
            return;
        }

        if (AL.get(person_index).getAmount() >= amount) {
            AL.get(person_index).setAmount(AL.get(person_index).getAmount() - amount);
            JOptionPane.showMessageDialog(null, "Withdrawal Successful");
        } else {
            JOptionPane.showMessageDialog(null, "This person does not have enough balance in the account");
        }
    }

    public ArrayList<Account> getAccounts() {
        return AL;
    }

    public void save() {
        try {
            FileOutputStream fos = new FileOutputStream("BankRecord.txt");
            ObjectOutputStream out = new ObjectOutputStream(fos);
            for (int i = 0; i < AL.size(); i++)
                out.writeObject(AL.get(i));
            fos.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error Saving Data to File");
        }
    }

    public void load() {
        try {
            FileInputStream fis = new FileInputStream("BankRecord.txt");
            ObjectInputStream in = new ObjectInputStream(fis);
            while (true) {
                try {
                    Account temp = (Account) in.readObject();
                    if (temp == null)
                        break;
                    AL.add(temp);
                } catch (EOFException e) {
                    break; // Reached end of file
                }
            }
            fis.close();
        } catch (Exception e) {
            e.printStackTrace(); // Log or handle the exception appropriately
            JOptionPane.showMessageDialog(null, "Error loading data from file");
        }
    }
}

public class Task1_Driver extends JFrame {
    private Bank bank;

    private JLabel nameLabel, accNumberLabel, pinLabel, amountLabel, senderAccLabel, senderPinLabel, receiverAccLabel, transferAmountLabel, withdrawAccLabel, withdrawAmountLabel, withdrawPinLabel;
    private JTextField nameField, accNumberField, pinField, amountField, senderAccField, senderPinField, receiverAccField, transferAmountField, withdrawAccField, withdrawAmountField, withdrawPinField;
    private JButton createAccountBtn, transferBtn, withdrawBtn, printBtn, deleteAccountBtn, updateAccountBtn, refreshBtn;

    public Task1_Driver() {
        super("Bank Application");
        bank = new Bank();
        bank.load(); // Load existing records

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 600);
        setLayout(null);

        initComponents();
        addListeners();

        setVisible(true);
    }

    private void initComponents() {
        nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 20, 100, 25);
        add(nameLabel);

        nameField = new JTextField();
        nameField.setBounds(200, 20, 200, 25);
        add(nameField);

        accNumberLabel = new JLabel("Account Number:");
        accNumberLabel.setBounds(50, 50, 150, 25);
        add(accNumberLabel);

        accNumberField = new JTextField();
        accNumberField.setBounds(200, 50, 200, 25);
        add(accNumberField);

        pinLabel = new JLabel("PIN:");
        pinLabel.setBounds(50, 80, 100, 25);
        add(pinLabel);

        pinField = new JTextField();
        pinField.setBounds(200, 80, 200, 25);
        add(pinField);

        amountLabel = new JLabel("Initial Amount:");
        amountLabel.setBounds(50, 110, 150, 25);
        add(amountLabel);

        amountField = new JTextField();
        amountField.setBounds(200, 110, 200, 25);
        add(amountField);

        createAccountBtn = new JButton("Create Account");
        createAccountBtn.setBounds(200, 140, 200, 25);
        add(createAccountBtn);

        senderAccLabel = new JLabel("Sender's Account Number:");
        senderAccLabel.setBounds(50, 170, 200, 25);
        add(senderAccLabel);

        senderAccField = new JTextField();
        senderAccField.setBounds(250, 170, 200, 25);
        add(senderAccField);

        senderPinLabel = new JLabel("Sender's PIN:");
        senderPinLabel.setBounds(50, 200, 150, 25);
        add(senderPinLabel);

        senderPinField = new JTextField();
        senderPinField.setBounds(250, 200, 200, 25);
        add(senderPinField);

        receiverAccLabel = new JLabel("Receiver's Account Number:");
        receiverAccLabel.setBounds(50, 230, 200, 25);
        add(receiverAccLabel);

        receiverAccField = new JTextField();
        receiverAccField.setBounds(250, 230, 200, 25);
        add(receiverAccField);

        transferAmountLabel = new JLabel("Transfer Amount:");
        transferAmountLabel.setBounds(50, 260, 150, 25);
        add(transferAmountLabel);

        transferAmountField = new JTextField();
        transferAmountField.setBounds(250, 260, 200, 25);
        add(transferAmountField);

        transferBtn = new JButton("Transfer");
        transferBtn.setBounds(250, 290, 200, 25);
        add(transferBtn);

        withdrawAccLabel = new JLabel("Account Number:");
        withdrawAccLabel.setBounds(50, 320, 150, 25);
        add(withdrawAccLabel);

        withdrawAccField = new JTextField();
        withdrawAccField.setBounds(200, 320, 200, 25);
        add(withdrawAccField);

        withdrawAmountLabel = new JLabel("Withdraw Amount:");
        withdrawAmountLabel.setBounds(50, 350, 150, 25);
        add(withdrawAmountLabel);

        withdrawAmountField = new JTextField();
        withdrawAmountField.setBounds(200, 350, 200, 25);
        add(withdrawAmountField);

        withdrawPinLabel = new JLabel("Withdraw PIN:");
        withdrawPinLabel.setBounds(50, 380, 100, 25);
        add(withdrawPinLabel);

        withdrawPinField = new JTextField();
        withdrawPinField.setBounds(200, 380, 200, 25);
        add(withdrawPinField);

        withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setBounds(200, 410, 200, 25);
        add(withdrawBtn);

        printBtn = new JButton("Print Accounts");
        printBtn.setBounds(200, 440, 200, 25);
        add(printBtn);

        deleteAccountBtn = new JButton("Delete Account");
        deleteAccountBtn.setBounds(200, 470, 200, 25);
        add(deleteAccountBtn);

        updateAccountBtn = new JButton("Update Account");
        updateAccountBtn.setBounds(200, 500, 200, 25);
        add(updateAccountBtn);

        refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(200, 530, 200, 25);
        add(refreshBtn);
    }

    private void addListeners() {
        createAccountBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int acc = Integer.parseInt(accNumberField.getText());
                String pin = pinField.getText();
                double amount = Double.parseDouble(amountField.getText());
                bank.addNewRecord(name, acc, pin, amount);
            }
        });

        transferBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int senderAcc = Integer.parseInt(senderAccField.getText());
                String senderPin = senderPinField.getText();
                int receiverAcc = Integer.parseInt(receiverAccField.getText());
                double amount = Double.parseDouble(transferAmountField.getText());
                bank.transfer(senderAcc, senderPin, receiverAcc, amount);
            }
        });

        withdrawBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int withdrawAcc = Integer.parseInt(withdrawAccField.getText());
                double amount = Double.parseDouble(withdrawAmountField.getText());
                String pin = withdrawPinField.getText();
                bank.withdraw(withdrawAcc, amount, pin);
            }
        });

        printBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                StringBuilder accountsInfo = new StringBuilder();
                for (Account account : bank.getAccounts()) {
                    accountsInfo.append("Name: ").append(account.getName()).append(", ");
                    accountsInfo.append("Account Number: ").append(account.getAccountNumber()).append(", ");
                    accountsInfo.append("Balance: ").append(account.getAmount()).append("\n");
                }
                JOptionPane.showMessageDialog(null, accountsInfo.toString());
            }
        });

        updateAccountBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFrame updateAccountFrame = new JFrame("Update Account");
                updateAccountFrame.setSize(400, 300);
                updateAccountFrame.setLayout(null);

                JLabel accountNumberLabel = new JLabel("Account Number:");
                accountNumberLabel.setBounds(50, 30, 150, 25);
                updateAccountFrame.add(accountNumberLabel);

                JTextField accountNumberField = new JTextField();
                accountNumberField.setBounds(200, 30, 150, 25);
                updateAccountFrame.add(accountNumberField);

                JLabel currentPinLabel = new JLabel("Current PIN:");
                currentPinLabel.setBounds(50, 70, 150, 25);
                updateAccountFrame.add(currentPinLabel);

                JTextField currentPinField = new JTextField();
                currentPinField.setBounds(200, 70, 150, 25);
                updateAccountFrame.add(currentPinField);

                JLabel newNameLabel = new JLabel("New Name:");
                newNameLabel.setBounds(50, 110, 150, 25);
                updateAccountFrame.add(newNameLabel);

                JTextField newNameField = new JTextField();
                newNameField.setBounds(200, 110, 150, 25);
                updateAccountFrame.add(newNameField);

                JLabel newPinLabel = new JLabel("New PIN:");
                newPinLabel.setBounds(50, 150, 150, 25);
                updateAccountFrame.add(newPinLabel);

                JTextField newPinField = new JTextField();
                newPinField.setBounds(200, 150, 150, 25);
                updateAccountFrame.add(newPinField);

                JButton updateBtn = new JButton("Update");
                updateBtn.setBounds(150, 200, 100, 25);
                updateAccountFrame.add(updateBtn);

                updateBtn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        int accountNumber = Integer.parseInt(accountNumberField.getText());
                        String currentPin = currentPinField.getText();
                        String newName = newNameField.getText();
                        String newPin = newPinField.getText();
                        bank.updateAccount(accountNumber, currentPin, newName, newPin);
                        updateAccountFrame.dispose();
                    }
                });

                updateAccountFrame.setVisible(true);
            }
        });

        deleteAccountBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int accountNumber = Integer.parseInt(JOptionPane.showInputDialog("Enter Account Number to Delete:"));
                bank.deleteAccount(accountNumber);
            }
        });

        refreshBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Clear all text fields
                nameField.setText("");
                accNumberField.setText("");
                pinField.setText("");
                amountField.setText("");
                senderAccField.setText("");
                senderPinField.setText("");
                receiverAccField.setText("");
                transferAmountField.setText("");
                withdrawAccField.setText("");
                withdrawAmountField.setText("");
                withdrawPinField.setText("");
            }
        });
    }

    public static void main(String[] args) {
        new Task1_Driver();
    }
}




