import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class InstagramLogin extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel instagramLogo;
    private JLabel forgotPasswordLabel;
    private JLabel signUpLabel;

    // Storing user credentials
    private Map<String, String> userCredentials = new HashMap<>();

    public InstagramLogin() {
        super("Instagram Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(8, 1, 10, 10)); // Adjusted GridLayout to 8 rows

        // Add default user
        userCredentials.put("anastasia20@upi.edu", "michaelj");

        // Load Billabong font
        Font billabongFont = loadBillabongFont();

        // Title "Instagram" with Billabong font
        instagramLogo = new JLabel("Instagram", JLabel.CENTER);
        if (billabongFont != null) {
            instagramLogo.setFont(billabongFont.deriveFont(36f)); // Set font size to 36
        }
        add(instagramLogo);

        // Username Field
        usernameField = new JTextField("Phone number, email or username");
        usernameField.setHorizontalAlignment(JTextField.CENTER);
        usernameField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (usernameField.getText().equals("Phone number, email or username")) {
                    usernameField.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (usernameField.getText().isEmpty()) {
                    usernameField.setText("Phone number, email or username");
                }
            }
        });
        add(usernameField);

        // Password Field
        passwordField = new JPasswordField("Password");
        passwordField.setHorizontalAlignment(JTextField.CENTER);
        passwordField.setEchoChar((char) 0);
        passwordField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).equals("Password")) {
                    passwordField.setText("");
                    passwordField.setEchoChar('•');
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(passwordField.getPassword()).isEmpty()) {
                    passwordField.setText("Password");
                    passwordField.setEchoChar((char) 0);
                }
            }
        });
        add(passwordField);

        // Login Button
        loginButton = new JButton("Log In");
        loginButton.addActionListener(this);
        add(loginButton);

        // Forgot Password Label
        forgotPasswordLabel = new JLabel("<html><a href=\"#\">Forgot your password?</a></html>", JLabel.CENTER);
        forgotPasswordLabel.setForeground(Color.BLUE.darker());
        forgotPasswordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        forgotPasswordLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open a new window to reset the password
                openResetPasswordWindow();
            }
        });
        add(forgotPasswordLabel);

        // Sign Up Label
        signUpLabel = new JLabel("<html><a href=\"#\">Don't have an account? Sign up.</a></html>", JLabel.CENTER);
        signUpLabel.setForeground(Color.BLUE.darker());
        signUpLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        signUpLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Open a new window to create a new account
                openSignUpWindow();
            }
        });
        add(signUpLabel);

        setVisible(true);
    }

    private Font loadBillabongFont() {
        try {
            // Load the Billabong font from the file
            Font font = Font.createFont(Font.TRUETYPE_FONT, new File("Billabong.ttf"));
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(font);
            return font;
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Validate username and password
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (username.isEmpty() || password.isEmpty() || username.equals("Phone number, email or username") || password.equals("Password")) {
                JOptionPane.showMessageDialog(this, "Please enter your username and password.");
            } else {
                // Check if username and password match the stored values
                if (userCredentials.containsKey(username) && userCredentials.get(username).equals(password)) {
                    JOptionPane.showMessageDialog(this, "Anda berhasil login.");
                    // Open the main window after successful login
                    openMainWindow();
                } else {
                    JOptionPane.showMessageDialog(this, "Email atau password salah. Silakan buat akun baru.");
                }
            }
        }
    }

    private void openResetPasswordWindow() {
        JFrame resetPasswordWindow = new JFrame("Reset Password");
        resetPasswordWindow.setSize(300, 150);
        resetPasswordWindow.setLocationRelativeTo(null);
        resetPasswordWindow.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel instructionLabel = new JLabel("Enter your new password:", JLabel.CENTER);
        resetPasswordWindow.add(instructionLabel);

        JPasswordField newPasswordField = new JPasswordField();
        resetPasswordWindow.add(newPasswordField);

        JButton confirmButton = new JButton("Confirm");
        confirmButton.addActionListener(e -> {
            String newPassword = new String(newPasswordField.getPassword());
            if (!newPassword.isEmpty()) {
                String username = usernameField.getText();
                if (userCredentials.containsKey(username)) {
                    userCredentials.put(username, newPassword);
                    JOptionPane.showMessageDialog(this, "Password has been reset successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Username not found.");
                }
                resetPasswordWindow.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Password cannot be empty.");
            }
        });
        resetPasswordWindow.add(confirmButton);

        resetPasswordWindow.setVisible(true);
    }

    private void openSignUpWindow() {
        JFrame signUpWindow = new JFrame("Sign Up");
        signUpWindow.setSize(300, 200);
        signUpWindow.setLocationRelativeTo(null);
        signUpWindow.setLayout(new GridLayout(4, 1, 10, 10));

        JLabel instructionLabel = new JLabel("Enter your email and password:", JLabel.CENTER);
        signUpWindow.add(instructionLabel);

        JTextField emailField = new JTextField();
        emailField.setHorizontalAlignment(JTextField.CENTER);
        signUpWindow.add(emailField);

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setHorizontalAlignment(JPasswordField.CENTER);
        signUpWindow.add(newPasswordField);

        JButton signUpButton = new JButton("Sign Up");
        signUpButton.addActionListener(e -> {
            String email = emailField.getText();
            String newPassword = new String(newPasswordField.getPassword());
            if (!email.isEmpty() && !newPassword.isEmpty()) {
                if (!userCredentials.containsKey(email)) {
                    userCredentials.put(email, newPassword);
                    JOptionPane.showMessageDialog(this, "Account created successfully. You can now log in.");
                    signUpWindow.dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Email is already registered.");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Email and password cannot be empty.");
            }
        });
        signUpWindow.add(signUpButton);

        signUpWindow.setVisible(true);
    }

    private void openMainWindow() {
        dispose(); // Close the current login window
        JFrame mainWindow = new JFrame("Instagram Main Window");
        mainWindow.setSize(600, 600);
        mainWindow.setLocationRelativeTo(null);
        mainWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Adding a label with an image
        JLabel mainImage = new JLabel(new ImageIcon("main_image.png"), JLabel.CENTER);
        mainWindow.add(mainImage, BorderLayout.CENTER);

        // Adding a logout button
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            mainWindow.dispose();
            new InstagramLogin();
        });
        mainWindow.add(logoutButton, BorderLayout.SOUTH);

        mainWindow.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new InstagramLogin());
    }
}
