package com.mycompany.tipcalculator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class TipCalculator extends JFrame implements ActionListener {
    private JPanel loginPanel, signUpPanel, mainPanel, calculatorPanel, historyPanel;
    private JTextField nameField, emailField, confirmEmailField, billField, peopleField;
    private JPasswordField passwordField, confirmPasswordField;
    private JButton loginButton, signUpButton, calculateButton, historyButton, cancelButton, backButton, newTipButton;
    private JTextArea historyArea;
    private JLabel welcomeLabel;
    private String currentUser;
    private HashMap<String, String> userCredentials = new HashMap<>();
    private ArrayList<TipRecord> tipHistory = new ArrayList<>();
    static NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(); 

    public TipCalculator() {
        setTitle("Tip Calculator");
        setSize(450, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Changing the background color
        getContentPane().setBackground(Color.decode("#E0F7FA"));

        loginPanel = createLoginPanel();
        signUpPanel = createSignUpPanel();
        mainPanel = createMainMenuPanel();
        calculatorPanel = createCalculatorPanel();
        historyPanel = createHistoryPanel();

        showPanel(loginPanel);
        setVisible(true);
    }

    private JPanel createLoginPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#E0F7FA")); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Sign In");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.decode("#00796B")); // Title color
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        nameField = new JTextField(20);  
        gbc.gridy = 2;
        panel.add(nameField, gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridy = 4;
        panel.add(passwordField, gbc);

        // Set the color for the buttons
        Color darkGreen = Color.decode("#004D40"); 

        loginButton = new JButton("Login");
        loginButton.setBackground(darkGreen);
        loginButton.setForeground(Color.WHITE);
        loginButton.addActionListener(this);
        gbc.gridy = 5;
        panel.add(loginButton, gbc);

        JButton toSignUpButton = new JButton("Don't have an account? Sign Up");
        toSignUpButton.setBackground(darkGreen);
        toSignUpButton.setForeground(Color.WHITE);
        toSignUpButton.addActionListener(e -> showPanel(signUpPanel));
        gbc.gridy = 6;
        panel.add(toSignUpButton, gbc);

        return panel;
    }

    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#E0F7FA")); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Sign Up");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.decode("#00796B")); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Username:"), gbc);
        nameField = new JTextField(20);
        gbc.gridy = 2;
        panel.add(nameField, gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Email:"), gbc);
        emailField = new JTextField(20);
        gbc.gridy = 4;
        panel.add(emailField, gbc);

        gbc.gridy = 5;
        panel.add(new JLabel("Confirm Email:"), gbc);
        confirmEmailField = new JTextField(20);
        gbc.gridy = 6;
        panel.add(confirmEmailField, gbc);

        gbc.gridy = 7;
        panel.add(new JLabel("Password:"), gbc);
        passwordField = new JPasswordField(20);
        gbc.gridy = 8;
        panel.add(passwordField, gbc);

        gbc.gridy = 9;
        panel.add(new JLabel("Confirm Password:"), gbc);
        confirmPasswordField = new JPasswordField(20);
        gbc.gridy = 10;
        panel.add(confirmPasswordField, gbc);

        signUpButton = new JButton("Sign Up");
        signUpButton.setBackground(Color.decode("#004D40"));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.addActionListener(this);
        gbc.gridy = 11;
        panel.add(signUpButton, gbc);

        JButton backToLoginButton = new JButton("Back to Login");
        backToLoginButton.setBackground(Color.decode("#004D40"));
        backToLoginButton.setForeground(Color.WHITE);
        backToLoginButton.addActionListener(e -> showPanel(loginPanel));
        gbc.gridy = 12;
        panel.add(backToLoginButton, gbc);

        return panel;
    }

    private JPanel createMainMenuPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.decode("#E0F7FA")); 
        
        welcomeLabel = new JLabel("Welcome!");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        welcomeLabel.setForeground(Color.decode("#00796B"));
        panel.add(welcomeLabel);

        newTipButton = new JButton("Calculate Tip");
        newTipButton.setBackground(Color.decode("#004D40"));
        newTipButton.setForeground(Color.WHITE);
        newTipButton.addActionListener(e -> showPanel(calculatorPanel));
        panel.add(newTipButton);

        historyButton = new JButton("View Tip History");
        historyButton.setBackground(Color.decode("#004D40"));
        historyButton.setForeground(Color.WHITE);
        historyButton.addActionListener(e -> displayTipHistory());
        panel.add(historyButton);

        return panel;
    }

    private JPanel createCalculatorPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.decode("#E0F7FA")); 
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel titleLabel = new JLabel("Tip Calculator");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.decode("#00796B")); 
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(titleLabel, gbc);

        gbc.gridy = 1;
        panel.add(new JLabel("Bill Amount:"), gbc);
        billField = new JTextField(20);
        gbc.gridy = 2;
        panel.add(billField, gbc);

        gbc.gridy = 3;
        panel.add(new JLabel("Number of People:"), gbc);
        peopleField = new JTextField(20);
        gbc.gridy = 4;
        panel.add(peopleField, gbc);

        gbc.gridy = 5;
        panel.add(new JLabel("How was your service:"), gbc);
        String[] serviceOptions = {"Select", "Good", "Average", "Bad"};
        JComboBox<String> serviceCombo = new JComboBox<>(serviceOptions);
        gbc.gridy = 6;
        panel.add(serviceCombo, gbc);

        calculateButton = new JButton("Calculate");
        calculateButton.setBackground(Color.decode("#004D40"));
        calculateButton.setForeground(Color.WHITE);
        calculateButton.addActionListener(this);
        gbc.gridy = 7;
        panel.add(calculateButton, gbc);

        cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.decode("#004D40"));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(this);
        gbc.gridy = 8;
        panel.add(cancelButton, gbc);

        return panel;
    }

    private JPanel createHistoryPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.decode("#E0F7FA")); 

        historyArea = new JTextArea(15, 30);
        historyArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historyArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        backButton = new JButton("Back");
        backButton.setBackground(Color.decode("#004D40"));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(this);
        panel.add(backButton, BorderLayout.SOUTH);

        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            String username = nameField.getText();
            String password = new String(passwordField.getPassword());

            if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
                currentUser = username;
                welcomeLabel.setText("Welcome, " + currentUser + "!");
                showPanel(mainPanel);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == signUpButton) {
            String name = nameField.getText();
            String email = emailField.getText();
            String confirmEmail = confirmEmailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!email.equals(confirmEmail)) {
                JOptionPane.showMessageDialog(this, "Emails do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                userCredentials.put(name, password);
                JOptionPane.showMessageDialog(this, "Account created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                showPanel(loginPanel);
            }
        } else if (e.getSource() == calculateButton) {
            try {
                double bill = Double.parseDouble(billField.getText());
                int people = Integer.parseInt(peopleField.getText());

                //Handling the negative and string values
                if (bill <= 0) {
                    JOptionPane.showMessageDialog(this, "Bill amount must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (people <= 0) {
                    JOptionPane.showMessageDialog(this, "Number of people must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                JComboBox<String> serviceCombo = (JComboBox<String>) ((JButton) e.getSource()).getParent().getComponent(6); 
                String service = serviceCombo.getSelectedItem().toString();

                if (service.equals("Select")) {
                    JOptionPane.showMessageDialog(this, "Please select a service quality.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double tipPercentage = 0.0; 

                // Determine tip percentage based on service quality
                if (service.equals("Good")) {
                    tipPercentage = 0.20; // 20% tip
                } else if (service.equals("Average")) {
                    tipPercentage = 0.15; // 15% tip
                } else if (service.equals("Bad")) {
                    tipPercentage = 0.10; // 10% tip
                }

                // Calculate tip and total bill including tip
                double totalTip = bill * tipPercentage;
                double totalBillWithTip = bill + totalTip;
                double totalPerPerson = totalBillWithTip / people;

                // Display results in a message dialog
                String message = String.format(
                        "Total Bill (with tip): %s\nTotal Tip: %s\nTip Rate: %.0f%%\nTotal per Person: %s\n\nTip has been saved in the history.",
                        currencyFormat.format(totalBillWithTip),
                        currencyFormat.format(totalTip),
                        tipPercentage * 100,
                        currencyFormat.format(totalPerPerson)
                );
                JOptionPane.showMessageDialog(this, message, "Tip Calculation Result", JOptionPane.INFORMATION_MESSAGE);

                // Save to history with all details
                tipHistory.add(new TipRecord(currentUser, totalBillWithTip, totalTip, tipPercentage, totalPerPerson, people, service));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for bill and people.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else if (e.getSource() == cancelButton) {
            showPanel(mainPanel);
        } else if (e.getSource() == backButton) {
            showPanel(mainPanel);
        }
    }

    private void showPanel(JPanel panel) {
        getContentPane().removeAll();
        getContentPane().add(panel);
        revalidate();
        repaint();
    }

    private void displayTipHistory() {
        StringBuilder historyText = new StringBuilder();
        for (TipRecord record : tipHistory) {
            historyText.append(String.format("User: %s | Total Bill (with tip): %s | Total Tip: %s | Tip Rate: %.0f%% | Total per Person: %s | People: %d | Service: %s\n",
                    record.getUser(),
                    currencyFormat.format(record.getTotalBillWithTip()),
                    currencyFormat.format(record.getTotalTip()),
                    record.getTipRate() * 100,
                    currencyFormat.format(record.getTotalPerPerson()),
                    record.getPeople(),
                    record.getService()));
        }
        historyArea.setText(historyText.toString());
        showPanel(historyPanel);
    }

    public static void main(String[] args) {
        new TipCalculator();
    }

    class TipRecord {
        private String user;
        private double totalBillWithTip;
        private double totalTip;
        private double tipRate;
        private double totalPerPerson;
        private int people;
        private String service;

        public TipRecord(String user, double totalBillWithTip, double totalTip, double tipRate, double totalPerPerson, int people, String service) {
            this.user = user;
            this.totalBillWithTip = totalBillWithTip;
            this.totalTip = totalTip;
            this.tipRate = tipRate;
            this.totalPerPerson = totalPerPerson;
            this.people = people;
            this.service = service;
        }

        public String getUser() {
            return user;
        }

        public double getTotalBillWithTip() {
            return totalBillWithTip;
        }

        public double getTotalTip() {
            return totalTip;
        }

        public double getTipRate() {
            return tipRate;
        }

        public double getTotalPerPerson() {
            return totalPerPerson;
        }

        public int getPeople() {
            return people;
        }

        public String getService() {
            return service;
        }
    }
}
