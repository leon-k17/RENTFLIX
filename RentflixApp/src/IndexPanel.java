import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class IndexPanel extends JPanel {
    private RentflixApp app;

    public IndexPanel(RentflixApp app) {
        this.app = app;
        setBackground(ThemeColors.BACKGROUND);
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ThemeColors.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(40, 40, 40, 40));

        JPanel heroPanel = createHeroPanel();
        mainPanel.add(heroPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 50)));

        JLabel featuredLabel = new JLabel("Featured Properties");
        featuredLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        featuredLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        featuredLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        mainPanel.add(featuredLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel propertyGrid = createPropertyGrid();
        mainPanel.add(propertyGrid);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private JPanel createHeroPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ThemeColors.CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15, ThemeColors.PRIMARY_RED, 2),
            new EmptyBorder(60, 50, 60, 50)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        JLabel titleLabel = new JLabel("Find Your Perfect Student Home");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 42));
        titleLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Discover affordable, student-friendly accommodations near your campus");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        subtitleLabel.setForeground(ThemeColors.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

       JButton browseButton = new JButton("Browse Properties");
        browseButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        browseButton.setForeground(Color.WHITE);
        browseButton.setBackground(ThemeColors.PRIMARY_RED);
        browseButton.setFocusPainted(false);
        browseButton.setBorderPainted(false);
        browseButton.setPreferredSize(new Dimension(200, 50));
        browseButton.setMaximumSize(new Dimension(200, 50));
        browseButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        browseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        browseButton.addActionListener(e -> {
        System.out.println("✅ Browse button clicked from IndexPanel");
        app.showPanel("BROWSE");  // This will trigger navigation update
});


        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 15)));
        panel.add(subtitleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 30)));
        panel.add(browseButton);

        return panel;
    }

    private JPanel createPropertyGrid() {
        JPanel grid = new JPanel(new GridLayout(0, 3, 20, 20));
        grid.setBackground(ThemeColors.BACKGROUND);
        grid.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));

        int count = 0;
        for (Property property : app.getProperties()) {
            if (count >= 6) break;
            grid.add(new PropertyCard(app, property));
            count++;
        }

        return grid;
    }
}
