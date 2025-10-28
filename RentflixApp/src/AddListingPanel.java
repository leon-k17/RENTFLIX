import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class AddListingPanel extends JPanel {
    private RentflixApp app;
    private JTextField titleField, addressField, priceField;
    private JComboBox<Integer> bedroomCombo, bathroomCombo;
    private JTextArea descriptionArea;

    public AddListingPanel(RentflixApp app) {
        this.app = app;
        setBackground(ThemeColors.BACKGROUND);
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ThemeColors.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel titleLabel = new JLabel("Add New Listing");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(ThemeColors.CARD_BG);
        formPanel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15, ThemeColors.BORDER),
            new EmptyBorder(30, 30, 30, 30)
        ));
        formPanel.setMaximumSize(new Dimension(800, Integer.MAX_VALUE));

        addFormLabel(formPanel, "Property Title");
        titleField = createTextField();
        formPanel.add(titleField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        addFormLabel(formPanel, "Address");
        addressField = createTextField();
        formPanel.add(addressField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        addFormLabel(formPanel, "Monthly Rent (INR)");
        priceField = createTextField();
        formPanel.add(priceField);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel roomsPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        roomsPanel.setBackground(ThemeColors.CARD_BG);
        roomsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));

        JPanel bedroomPanel = new JPanel();
        bedroomPanel.setLayout(new BoxLayout(bedroomPanel, BoxLayout.Y_AXIS));
        bedroomPanel.setBackground(ThemeColors.CARD_BG);
        addFormLabel(bedroomPanel, "Bedrooms");
        bedroomCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4, 5});
        styleComboBox(bedroomCombo);
        bedroomPanel.add(bedroomCombo);

        JPanel bathroomPanel = new JPanel();
        bathroomPanel.setLayout(new BoxLayout(bathroomPanel, BoxLayout.Y_AXIS));
        bathroomPanel.setBackground(ThemeColors.CARD_BG);
        addFormLabel(bathroomPanel, "Bathrooms");
        bathroomCombo = new JComboBox<>(new Integer[]{1, 2, 3, 4});
        styleComboBox(bathroomCombo);
        bathroomPanel.add(bathroomCombo);

        roomsPanel.add(bedroomPanel);
        roomsPanel.add(bathroomPanel);
        formPanel.add(roomsPanel);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        addFormLabel(formPanel, "Description");
        descriptionArea = new JTextArea(5, 40);
        descriptionArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descriptionArea.setBackground(ThemeColors.INPUT_BG);
        descriptionArea.setForeground(ThemeColors.TEXT_PRIMARY);
        descriptionArea.setCaretColor(ThemeColors.TEXT_PRIMARY);
        descriptionArea.setLineWrap(true);
        descriptionArea.setWrapStyleWord(true);
        descriptionArea.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, ThemeColors.BORDER),
            new EmptyBorder(12, 15, 12, 15)
        ));

        JScrollPane descScrollPane = new JScrollPane(descriptionArea);
        descScrollPane.setBorder(null);
        descScrollPane.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        formPanel.add(descScrollPane);
        formPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        buttonPanel.setBackground(ThemeColors.CARD_BG);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton submitButton = createPrimaryButton("Create Listing");
        submitButton.addActionListener(e -> handleSubmit());

        JButton cancelButton = createSecondaryButton("Cancel");
        cancelButton.addActionListener(e -> clearForm());

        buttonPanel.add(submitButton);
        buttonPanel.add(cancelButton);
        formPanel.add(buttonPanel);

        mainPanel.add(formPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addFormLabel(JPanel panel, String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 14));
        label.setForeground(ThemeColors.TEXT_PRIMARY);
        label.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 8)));
    }

    private JTextField createTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBackground(ThemeColors.INPUT_BG);
        field.setForeground(ThemeColors.TEXT_PRIMARY);
        field.setCaretColor(ThemeColors.TEXT_PRIMARY);
        field.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, ThemeColors.BORDER),
            new EmptyBorder(12, 15, 12, 15)
        ));
        field.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        field.setAlignmentX(Component.LEFT_ALIGNMENT);
        return field;
    }

    private void styleComboBox(JComboBox<?> combo) {
        combo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        combo.setBackground(ThemeColors.INPUT_BG);
        combo.setForeground(ThemeColors.TEXT_PRIMARY);
        combo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        combo.setAlignmentX(Component.LEFT_ALIGNMENT);
    }

    private JButton createPrimaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(ThemeColors.PRIMARY_RED);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(150, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JButton createSecondaryButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(ThemeColors.INPUT_BG);
        button.setForeground(ThemeColors.TEXT_PRIMARY);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(100, 45));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private void handleSubmit() {
        String title = titleField.getText();
        String address = addressField.getText();
        String priceText = priceField.getText();
        String description = descriptionArea.getText();

        if (title.isEmpty() || address.isEmpty() || priceText.isEmpty() || description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        try {
            double price = Double.parseDouble(priceText);
            int bedrooms = (Integer) bedroomCombo.getSelectedItem();
            int bathrooms = (Integer) bathroomCombo.getSelectedItem();

            Property newProperty = new Property(
                0, // id will be auto-assigned by DB
                title, address, price, bedrooms, bathrooms, description
            );
            newProperty.setLandlordName(app.getCurrentUser());

            boolean success = app.getDB().addProperty(newProperty, app.getCurrentUserId());

            if (success) {
                JOptionPane.showMessageDialog(this, "Listing created successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                app.showPanel("LANDLORD_PROFILE");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create listing. Try again.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid price",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void clearForm() {
        titleField.setText("");
        addressField.setText("");
        priceField.setText("");
        descriptionArea.setText("");
        bedroomCombo.setSelectedIndex(0);
        bathroomCombo.setSelectedIndex(0);
    }
}
