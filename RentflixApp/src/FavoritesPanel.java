import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class FavoritesPanel extends JPanel {
    private RentflixApp app;

    public FavoritesPanel(RentflixApp app) {
        this.app = app;
        setBackground(ThemeColors.BACKGROUND);
        setLayout(new BorderLayout());
    }

    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        if (visible) {
            refreshFavorites();
        }
    }

    private void refreshFavorites() {
        removeAll();

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ThemeColors.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel titleLabel = new JLabel("My Favorites");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Fetch favorites live from database via DatabaseManager
        List<Property> favoriteProperties = app.getDB().getUserFavorites(app.getCurrentUserId());

        if (favoriteProperties.isEmpty()) {
            JLabel emptyLabel = new JLabel("No favorites yet. Start browsing to add some!");
            emptyLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            emptyLabel.setForeground(ThemeColors.TEXT_SECONDARY);
            emptyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
            mainPanel.add(emptyLabel);
        } else {
            JPanel propertyGrid = new JPanel(new GridLayout(0, 3, 20, 20));
            propertyGrid.setBackground(ThemeColors.BACKGROUND);

            for (Property property : favoriteProperties) {
                propertyGrid.add(new PropertyCard(app, property));
            }

            mainPanel.add(propertyGrid);
        }

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        revalidate();
        repaint();
    }
}
