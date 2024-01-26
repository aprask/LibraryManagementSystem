package org.libmansys.Menu.Login;

import org.libmansys.Menu.LibraryMenu;

import javax.swing.*;

public class LoginMenu extends JFrame {

    public void runLibraryProgram() {
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Enter Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");

        loginButton.addActionListener(e -> {
            char[] passwordChars = passwordField.getPassword();
            String password = new String(passwordChars);
            Account account = new Account("user","root");
                if (verifyUser(account, password)) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    dispose();
                    new LibraryMenu();
                } else {
                    JOptionPane.showMessageDialog(null, "Login failed. Please try again.");
                    passwordField.setText("");
                }
            });

        panel.add(label);
        panel.add(passwordField);
        panel.add(loginButton);

        add(panel);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public boolean verifyUser(Account account, String password) {
        for (int i = 0; i < 5; i++) {
            if (password.equals(account.getPassword())) return true;
        }
        return false;
    }
}
