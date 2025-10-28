import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RentflixApp extends JFrame {
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private NavigationPanel navigationPanel;
    private List<Property> properties;
    private List<Property> favorites;
    private String currentUser = "Student";
    private int currentUserId = -1;
    private String currencySymbol = "₹";
    private LocationService locationService;
    private DatabaseManager dbManager;

    public RentflixApp(int userId, String username) {
        System.out.println("✅ RentflixApp launched with userId: " + userId + ", username: " + username);
        this.currentUserId = userId;
        this.currentUser = username;
        
        setTitle("RENTFLIX - Student Rental Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);

        dbManager = new DatabaseManager();
        favorites = new ArrayList<>();
        locationService = new LocationService();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up location and load data from DB
        initLocationAndProperties();

        // UI
        initComponents();
        
        System.out.println("✅ RentflixApp fully initialized, showing window");
        setVisible(true);
    }

    private void initLocationAndProperties() {
        boolean allowed = locationService.requestLocationPermission();
        currencySymbol = locationService.getCurrencySymbol();
        if (!allowed) {
            System.out.println("❌ Location permission denied, defaulting to ₹ and generic data.");
        }
        // Load all actual properties from DB
        properties = dbManager.getAllProperties();
        System.out.println("✅ Properties loaded from DB: " + properties.size());
        // Load favorites too
        favorites = dbManager.getUserFavorites(currentUserId);
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ThemeColors.BACKGROUND);

        navigationPanel = new NavigationPanel(this);
        mainPanel.add(navigationPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(ThemeColors.BACKGROUND);

        // Add all panels with their proper names
        contentPanel.add(new IndexPanel(this), "INDEX");
        contentPanel.add(new BrowsePanel(this), "BROWSE");
        contentPanel.add(new FavoritesPanel(this), "FAVORITES");
        contentPanel.add(new CommunityPanel(this), "COMMUNITY");
        contentPanel.add(new StudentProfilePanel(this), "STUDENT_PROFILE");
        contentPanel.add(new LandlordProfilePanel(this), "LANDLORD_PROFILE");
        contentPanel.add(new AddListingPanel(this), "ADD_LISTING");

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        
        // Show the INDEX panel by default (home page)
        SwingUtilities.invokeLater(() -> {
            showPanel("INDEX");
        });
    }

    // Navigation
    public void showPanel(String panelName) {
        System.out.println("Showing panel: " + panelName);
        cardLayout.show(contentPanel, panelName);
        if (navigationPanel != null) {
            navigationPanel.updateSelectedButton(panelName);
        }
    }

    public void showPropertyDetail(Property property) {
        Component[] comps = contentPanel.getComponents();
        for (Component c : comps) {
            if (c instanceof PropertyDetailPanel) contentPanel.remove(c);
        }
        PropertyDetailPanel detail = new PropertyDetailPanel(this, property);
        contentPanel.add(detail, "PROPERTY_DETAIL");
        cardLayout.show(contentPanel, "PROPERTY_DETAIL");
    }

    // Getters
    public List<Property> getProperties() { return properties; }
    public List<Property> getFavorites() { return favorites; }
    
    public void addProperty(Property p) {
        boolean success = dbManager.addProperty(p, currentUserId);
        if (success) {
            properties = dbManager.getAllProperties();
        }
    }
    
    public void toggleFavorite(Property p) {
        if (favorites.contains(p)) {
            favorites.remove(p);
            dbManager.removeFavorite(currentUserId, p.getId());
            p.setFavorite(false);
        } else {
            favorites.add(p);
            dbManager.addFavorite(currentUserId, p.getId());
            p.setFavorite(true);
        }
    }
    
    public String getCurrentUser() { return currentUser; }
    public int getCurrentUserId() { return currentUserId; }
    public String getCurrencySymbol() { return currencySymbol; }
    public LocationService getLocationService() { return locationService; }
    public DatabaseManager getDB() { return dbManager; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RentflixApp(1, "Test Student");
        });
    }
}
