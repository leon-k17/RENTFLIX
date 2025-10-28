
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class LandlordProfilePanel extends JPanel {
    private RentflixApp app;

    public LandlordProfilePanel(RentflixApp app) {
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

        JLabel titleLabel = new JLabel("Landlord Dashboard");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel statsPanel = new JPanel(new GridLayout(1, 3, 20, 0));
        statsPanel.setBackground(ThemeColors.BACKGROUND);
        statsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        statsPanel.add(createStatCard("Total Listings", "5", "🏠"));
        statsPanel.add(createStatCard("Active Views", "127", "👁️"));
        statsPanel.add(createStatCard("Total Inquiries", "23", "📧"));

        mainPanel.add(statsPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JLabel listingsLabel = new JLabel("My Listings");
        listingsLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        listingsLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        listingsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(listingsLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel propertyGrid = new JPanel(new GridLayout(0, 3, 20, 20));
        propertyGrid.setBackground(ThemeColors.BACKGROUND);
        
        for (Property property : app.getProperties()) {
            propertyGrid.add(new PropertyCard(app, property));
        }

        mainPanel.add(propertyGrid);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createStatCard(String label, String value, String icon) {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBackground(ThemeColors.CARD_BG);
        card.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(12, ThemeColors.BORDER),
            new EmptyBorder(20, 20, 20, 20)
        ));

        JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 40));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel valueLabel = new JLabel(value);
        valueLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        valueLabel.setForeground(ThemeColors.PRIMARY_RED);
        valueLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        labelComp.setForeground(ThemeColors.TEXT_SECONDARY);
        labelComp.setAlignmentX(Component.CENTER_ALIGNMENT);

        card.add(iconLabel);
        card.add(Box.createRigidArea(new Dimension(0, 10)));
        card.add(valueLabel);
        card.add(Box.createRigidArea(new Dimension(0, 5)));
        card.add(labelComp);

        return card;
    }
}
