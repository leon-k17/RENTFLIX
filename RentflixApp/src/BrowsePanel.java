import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class BrowsePanel extends JPanel {
    private RentflixApp app;
    private JPanel propertyGridPanel;

    public BrowsePanel(RentflixApp app) {
        this.app = app;
        setBackground(ThemeColors.BACKGROUND);
        setLayout(new BorderLayout());
        initComponents();
        refreshProperties();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ThemeColors.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(ThemeColors.BACKGROUND);

        JLabel title = new JLabel("Browse Properties");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(ThemeColors.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel location = new JLabel("📍 Near " + app.getLocationService().getUserCity());
        location.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        location.setForeground(ThemeColors.TEXT_SECONDARY);
        location.setAlignmentX(Component.LEFT_ALIGNMENT);

        topPanel.add(title);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        topPanel.add(location);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        propertyGridPanel = new JPanel(new GridLayout(0, 3, 20, 20));
        propertyGridPanel.setBackground(ThemeColors.BACKGROUND);

        JScrollPane scroll = new JScrollPane(propertyGridPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        mainPanel.add(scroll, BorderLayout.CENTER);

        add(mainPanel);
    }

    public void refreshProperties() {
        // Fetch fresh properties from database
        List<Property> properties = app.getDB().getAllProperties();
        updatePropertyGrid(properties);
    }

    private void updatePropertyGrid(List<Property> properties) {
        propertyGridPanel.removeAll();
        for (Property p : properties) {
            propertyGridPanel.add(new PropertyCard(app, p));
        }
        propertyGridPanel.revalidate();
        propertyGridPanel.repaint();
    }
}
