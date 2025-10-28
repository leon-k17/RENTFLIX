import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;

public class RentflixAuth extends JFrame {
    
    private DatabaseManager dbManager;
    private JPanel mainPanel;
    private CardLayout cardLayout;
    private JPanel cardContainer;
    private JComboBox<String> userTypeCombo;
    private String currentUserType = "student";

    // Login fields
    private JTextField loginEmailField;
    private JPasswordField loginPasswordField;
    private JButton loginButton;
    private JButton switchToSignupButton;

    // Signup fields
    private JTextField fullNameField;
    private JTextField signupEmailField;
    private JTextField phoneField;
    private JTextField collegeEmailField;
    private JComboBox<String> yearOfStudyCombo;
    private JTextField aadharField;
    private JTextField addressField;
    private JPasswordField signupPasswordField;
    private JButton signupButton;
    private JButton switchToLoginButton;
    private JPanel studentFieldsPanel;
    private JPanel landlordFieldsPanel;
    private JCheckBox showPasswordCheckBox;
    private JCheckBox showLoginPasswordCheckBox;

    // Color scheme
    private final Color PRIMARY_RED = new Color(229, 62, 48);
    private final Color HOVER_RED = new Color(185, 28, 28);
    private final Color BACKGROUND = new Color(18, 18, 18);
    private final Color CARD_BG = new Color(26, 26, 26);
    private final Color TEXT_PRIMARY = new Color(242, 242, 242);
    private final Color TEXT_SECONDARY = new Color(156, 163, 175);
    private final Color BORDER_COLOR = new Color(46, 46, 46);
    private final Color INPUT_BG = new Color(30, 30, 30);

    public RentflixAuth() {
        setTitle("RENTFLIX - Student Rental Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(550, 750);
        setLocationRelativeTo(null);
        setResizable(true);

        dbManager = new DatabaseManager();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBackground(BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 0, 0);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0;

        JPanel cardPanel = createCardPanel();
        mainPanel.add(cardPanel, gbc);

        add(mainPanel);
    }

   private JPanel createCardPanel() {
    JPanel card = new JPanel();
    card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
    card.setBackground(CARD_BG);
    card.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15, BORDER_COLOR),
            new EmptyBorder(40, 40, 40, 40)
    ));

    // Header
    JPanel headerPanel = new JPanel();
    headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
    headerPanel.setBackground(CARD_BG);
    headerPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel iconLabel = new JLabel("🏠");
    iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
    iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel titleLabel = new JLabel("RENTFLIX");
    titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
    titleLabel.setForeground(PRIMARY_RED);
    titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    JLabel subtitleLabel = new JLabel("Find your perfect student accommodation");
    subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    subtitleLabel.setForeground(TEXT_SECONDARY);
    subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

    headerPanel.add(iconLabel);
    headerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
    headerPanel.add(titleLabel);
    headerPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    headerPanel.add(subtitleLabel);

    card.add(headerPanel);
    card.add(Box.createRigidArea(new Dimension(0, 35)));

    // CardLayout for login/signup switching
    cardLayout = new CardLayout();
    cardContainer = new JPanel(cardLayout);
    cardContainer.setBackground(CARD_BG);
    cardContainer.setAlignmentX(Component.CENTER_ALIGNMENT);

    JPanel loginPanel = createLoginPanel();
    JPanel signupPanel = createSignupPanel();

    // ✅ WRAP SIGNUP PANEL IN SCROLL PANE
    JScrollPane signupScrollPane = new JScrollPane(signupPanel);
    signupScrollPane.setBorder(null);
    signupScrollPane.setBackground(CARD_BG);
    signupScrollPane.getVerticalScrollBar().setUnitIncrement(16);
    signupScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    cardContainer.add(loginPanel, "LOGIN");
    cardContainer.add(signupScrollPane, "SIGNUP");  // ✅ Add scroll pane instead

    card.add(cardContainer);

    return card;
}


    private JPanel createLoginPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(CARD_BG);

        JLabel welcomeLabel = new JLabel("Welcome Back");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        welcomeLabel.setForeground(TEXT_PRIMARY);
        welcomeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel instructionLabel = new JLabel("Sign in to continue to your account");
        instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        instructionLabel.setForeground(TEXT_SECONDARY);
        instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(welcomeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(instructionLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        panel.add(createLabel("Login As"));
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        String[] loginTypes = {"Student", "Landlord (Coming Soon)"};
        JComboBox<String> loginTypeCombo = new JComboBox<>(loginTypes);
        styleComboBox(loginTypeCombo);
        panel.add(loginTypeCombo);
        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        panel.add(createLabel("Email Address"));
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        loginEmailField = createStyledTextField("Enter your email", false);
        panel.add(loginEmailField);
        panel.add(Box.createRigidArea(new Dimension(0, 18)));

        panel.add(createLabel("Password"));
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
        loginPasswordField = createStyledPasswordField("Enter your password");
        panel.add(loginPasswordField);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        showLoginPasswordCheckBox = new JCheckBox("Show password");
        showLoginPasswordCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        showLoginPasswordCheckBox.setBackground(CARD_BG);
        showLoginPasswordCheckBox.setForeground(TEXT_SECONDARY);
        showLoginPasswordCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
        showLoginPasswordCheckBox.addActionListener(e -> {
            if (showLoginPasswordCheckBox.isSelected()) {
                loginPasswordField.setEchoChar((char) 0);
            } else {
                loginPasswordField.setEchoChar('\u2022');
            }
        });
        panel.add(showLoginPasswordCheckBox);
        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        loginButton = createPrimaryButton("Sign In");
        loginButton.addActionListener(e -> handleLogin(loginTypeCombo));
        panel.add(loginButton);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        panel.add(createDividerPanel("or"));
        panel.add(Box.createRigidArea(new Dimension(0, 15)));

        switchToSignupButton = createSecondaryButton("Create New Account");
        switchToSignupButton.addActionListener(e -> cardLayout.show(cardContainer, "SIGNUP"));
        panel.add(switchToSignupButton);

        return panel;
    }

    private JPanel createSignupPanel() {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(CARD_BG);

    JLabel createLabel = new JLabel("Create Account");
    createLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
    createLabel.setForeground(TEXT_PRIMARY);
    createLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    JLabel instructionLabel = new JLabel("Join RENTFLIX to find your perfect home");
    instructionLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
    instructionLabel.setForeground(TEXT_SECONDARY);
    instructionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(createLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 5)));
    panel.add(instructionLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 20)));

    // User Type Selection
    panel.add(createLabel("I am a"));
    panel.add(Box.createRigidArea(new Dimension(0, 8)));
    String[] userTypes = {"Student", "Landlord"};
    userTypeCombo = new JComboBox<>(userTypes);
    styleComboBox(userTypeCombo);
    userTypeCombo.addActionListener(e -> updateUserTypeFields());
    panel.add(userTypeCombo);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    // Full Name
    panel.add(createLabel("Full Name"));
    panel.add(Box.createRigidArea(new Dimension(0, 8)));
    fullNameField = createStyledTextField("John Doe", false);
    panel.add(fullNameField);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    // Email
    panel.add(createLabel("Email Address"));
    panel.add(Box.createRigidArea(new Dimension(0, 8)));
    signupEmailField = createStyledTextField("you@example.com", false);
    panel.add(signupEmailField);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    // Phone
    panel.add(createLabel("Phone Number"));
    panel.add(Box.createRigidArea(new Dimension(0, 8)));
    phoneField = createStyledTextField("+91 XXXXX XXXXX", false);
    panel.add(phoneField);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    // Student specific fields
    studentFieldsPanel = new JPanel();
    studentFieldsPanel.setLayout(new BoxLayout(studentFieldsPanel, BoxLayout.Y_AXIS));
    studentFieldsPanel.setBackground(CARD_BG);
    studentFieldsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

    studentFieldsPanel.add(createLabel("College Email"));
    studentFieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    collegeEmailField = createStyledTextField("student@college.edu", false);
    studentFieldsPanel.add(collegeEmailField);
    studentFieldsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    studentFieldsPanel.add(createLabel("Year of Study"));
    studentFieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    String[] years = {"Select year", "First Year", "Second Year", "Third Year", "Fourth Year"};
    yearOfStudyCombo = new JComboBox<>(years);
    styleComboBox(yearOfStudyCombo);
    studentFieldsPanel.add(yearOfStudyCombo);
    studentFieldsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    // Landlord specific fields
    landlordFieldsPanel = new JPanel();
    landlordFieldsPanel.setLayout(new BoxLayout(landlordFieldsPanel, BoxLayout.Y_AXIS));
    landlordFieldsPanel.setBackground(CARD_BG);
    landlordFieldsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
    landlordFieldsPanel.setVisible(false);

    landlordFieldsPanel.add(createLabel("Aadhar Number"));
    landlordFieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    aadharField = createStyledTextField("XXXX XXXX XXXX", false);
    landlordFieldsPanel.add(aadharField);
    landlordFieldsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    landlordFieldsPanel.add(createLabel("Address"));
    landlordFieldsPanel.add(Box.createRigidArea(new Dimension(0, 8)));
    addressField = createStyledTextField("Enter your full address", false);
    landlordFieldsPanel.add(addressField);
    landlordFieldsPanel.add(Box.createRigidArea(new Dimension(0, 15)));

    panel.add(studentFieldsPanel);
    panel.add(landlordFieldsPanel);

    // Password
    panel.add(createLabel("Password"));
    panel.add(Box.createRigidArea(new Dimension(0, 8)));
    signupPasswordField = createStyledPasswordField("Minimum 6 characters");
    panel.add(signupPasswordField);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    // Show password checkbox
    showPasswordCheckBox = new JCheckBox("Show password");
    showPasswordCheckBox.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    showPasswordCheckBox.setBackground(CARD_BG);
    showPasswordCheckBox.setForeground(TEXT_SECONDARY);
    showPasswordCheckBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    showPasswordCheckBox.addActionListener(e -> {
        if (showPasswordCheckBox.isSelected()) {
            signupPasswordField.setEchoChar((char) 0);
        } else {
            signupPasswordField.setEchoChar('\u2022');
        }
    });
    panel.add(showPasswordCheckBox);
    panel.add(Box.createRigidArea(new Dimension(0, 20)));

    // CREATE ACCOUNT BUTTON (Primary)
    signupButton = createPrimaryButton("Create Account");
    signupButton.addActionListener(e -> handleSignup());
    panel.add(signupButton);
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    // Divider
    panel.add(createDividerPanel("or"));
    panel.add(Box.createRigidArea(new Dimension(0, 15)));

    // BACK TO LOGIN BUTTON (Secondary)
    switchToLoginButton = createSecondaryButton("Already have an account? Sign In");
    switchToLoginButton.addActionListener(e -> cardLayout.show(cardContainer, "LOGIN"));
    panel.add(switchToLoginButton);

    return panel;
}


    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        label.setForeground(TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        return label;
    }

    private JTextField createStyledTextField(String placeholder, boolean isPassword) {
        JTextField field = new JTextField();
        field.setText(placeholder);
        field.setForeground(new Color(156, 163, 175));
        styleTextField(field);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_PRIMARY);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(8, PRIMARY_RED),
                        new EmptyBorder(12, 16, 12, 16)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(156, 163, 175));
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(8, BORDER_COLOR),
                        new EmptyBorder(12, 16, 12, 16)
                ));
            }
        });

        return field;
    }

    private JPasswordField createStyledPasswordField(String placeholder) {
        JPasswordField field = new JPasswordField();
        field.setEchoChar('\u2022');
        field.setText(placeholder);
        field.setForeground(new Color(156, 163, 175));
        styleTextField(field);

        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (new String(field.getPassword()).equals(placeholder)) {
                    field.setText("");
                    field.setForeground(TEXT_PRIMARY);
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(8, PRIMARY_RED),
                        new EmptyBorder(12, 16, 12, 16)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (new String(field.getPassword()).isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(new Color(156, 163, 175));
                }
                field.setBorder(BorderFactory.createCompoundBorder(
                        new RoundedBorder(8, BORDER_COLOR),
                        new EmptyBorder(12, 16, 12, 16)
                ));
            }
        });

        return field;
    }

    private void styleTextField(JTextField field) {
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(INPUT_BG);
        field.setBorder(BorderFactory.createCompoundBorder(
                new RoundedBorder(8, BORDER_COLOR),
                new EmptyBorder(12, 16, 12, 16)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        field.setCaretColor(TEXT_PRIMARY);
    }

    private void styleComboBox(JComboBox<String> comboBox) {
        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(INPUT_BG);
        comboBox.setForeground(TEXT_PRIMARY);
        comboBox.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        comboBox.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(PRIMARY_RED);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_RED);
            }
        });

        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        button.setBackground(INPUT_BG);
        button.setForeground(TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(46, 46, 46));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(INPUT_BG);
            }
        });

        return button;
    }

    private JPanel createDividerPanel(String text) {
        JPanel dividerPanel = new JPanel();
        dividerPanel.setLayout(new BoxLayout(dividerPanel, BoxLayout.X_AXIS));
        dividerPanel.setBackground(CARD_BG);
        dividerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dividerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 20));

        JSeparator leftSep = new JSeparator();
        leftSep.setForeground(BORDER_COLOR);

        JLabel orLabel = new JLabel(" " + text + " ");
        orLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        orLabel.setForeground(TEXT_SECONDARY);

        JSeparator rightSep = new JSeparator();
        rightSep.setForeground(BORDER_COLOR);

        dividerPanel.add(leftSep);
        dividerPanel.add(orLabel);
        dividerPanel.add(rightSep);

        return dividerPanel;
    }

    private void updateUserTypeFields() {
        String selected = (String) userTypeCombo.getSelectedItem();
        currentUserType = selected.toLowerCase();

        if (currentUserType.equals("student")) {
            studentFieldsPanel.setVisible(true);
            landlordFieldsPanel.setVisible(false);
        } else {
            studentFieldsPanel.setVisible(false);
            landlordFieldsPanel.setVisible(true);
        }

        revalidate();
        repaint();
    }

    private boolean isPlaceholder(JTextField field, String placeholder) {
        return field.getText().equals(placeholder) || field.getText().isEmpty();
    }

    private boolean isPlaceholder(JPasswordField field, String placeholder) {
        String password = new String(field.getPassword());
        return password.equals(placeholder) || password.isEmpty();
    }

    private void handleLogin(JComboBox<String> loginTypeCombo) {
        String email = loginEmailField.getText();
        String password = new String(loginPasswordField.getPassword());
        String loginType = (String) loginTypeCombo.getSelectedItem();

        if (isPlaceholder(loginEmailField, "Enter your email") ||
            isPlaceholder(loginPasswordField, "Enter your password") ||
            email.isEmpty() || password.isEmpty()) {
            showErrorDialog("Please fill in all required fields");
            return;
        }

        if (loginType.contains("Landlord")) {
            showErrorDialog("Landlord portal is under development.\nPlease check back soon!");
            return;
        }

        loginButton.setEnabled(false);
        loginButton.setText("Signing in...");

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            int userId = -1;
            String fullName = "";

            @Override
            protected Void doInBackground() throws Exception {
                userId = dbManager.loginUser(email, password);
                if (userId != -1) {
                    fullName = dbManager.getUserName(userId);
                }
                System.out.println("Login attempt for: " + email + ", Result: " + userId);
                return null;
            }

            @Override
            protected void done() {
                loginButton.setEnabled(true);
                loginButton.setText("Sign In");
                if (userId != -1) {
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        new RentflixApp(userId, fullName);
                    });
                } else {
                    showErrorDialog("Invalid email or password!");
                }
            }
        };
        worker.execute();
    }

    private void handleSignup() {
        String fullName = fullNameField.getText();
        String email = signupEmailField.getText();
        String phone = phoneField.getText();
        String password = new String(signupPasswordField.getPassword());

        if (isPlaceholder(fullNameField, "John Doe") ||
            isPlaceholder(signupEmailField, "you@example.com") ||
            isPlaceholder(phoneField, "+91 XXXXX XXXXX") ||
            isPlaceholder(signupPasswordField, "Minimum 6 characters") ||
            fullName.isEmpty() || email.isEmpty() || phone.isEmpty() ||
            password.isEmpty() || password.length() < 6) {
            showErrorDialog("Please enter valid details.\nPassword must be at least 6 characters.");
            return;
        }

        if (currentUserType.equals("student")) {
            if (isPlaceholder(collegeEmailField, "student@college.edu") ||
                yearOfStudyCombo.getSelectedIndex() == 0) {
                showErrorDialog("Please complete all student-specific fields.");
                return;
            }
        } else {
            if (isPlaceholder(aadharField, "XXXX XXXX XXXX") ||
                isPlaceholder(addressField, "Enter your full address")) {
                showErrorDialog("Please complete all landlord-specific fields.");
                return;
            }
        }

        signupButton.setEnabled(false);
        signupButton.setText("Creating account...");

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            boolean success = false;

            @Override
            protected Void doInBackground() throws Exception {
                success = dbManager.registerUser(email, password, fullName, phone, currentUserType);
                return null;
            }

            @Override
            protected void done() {
                signupButton.setEnabled(true);
                signupButton.setText("Create Account");
                if (success) {
                    int userId = dbManager.loginUser(email, password);
                    dispose();
                    SwingUtilities.invokeLater(() -> {
                        new RentflixApp(userId, fullName);
                    });
                } else {
                    showErrorDialog("Registration failed. Email may already exist.");
                }
            }
        };
        worker.execute();
    }

    private void showErrorDialog(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RentflixAuth();
        });
    }
}
