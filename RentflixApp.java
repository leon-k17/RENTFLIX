
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
    private LocationService locationService;
    private String currencySymbol = "$";

    public RentflixApp() {
        setTitle("RENTFLIX - Student Rental Platform");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1280, 800);
        setLocationRelativeTo(null);

        properties = new ArrayList<>();
        favorites = new ArrayList<>();
        locationService = new LocationService();

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set up location and properties before UI
        initLocationAndProperties();

        // Now set up UI components
        initComponents();
    }

    private void initLocationAndProperties() {
        // Ask for location
        boolean allowed = locationService.requestLocationPermission();
        currencySymbol = locationService.getCurrencySymbol();
        if (!allowed) {
            System.out.println("❌ Location permission denied, defaulting to $ and generic data.");
        }
        properties.addAll(generateSampleProperties());
        System.out.println("✅ Sample properties loaded: " + properties.size());
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(ThemeColors.BACKGROUND);

        navigationPanel = new NavigationPanel(this);
        mainPanel.add(navigationPanel, BorderLayout.WEST);

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(ThemeColors.BACKGROUND);

        contentPanel.add(new IndexPanel(this), "INDEX");
        contentPanel.add(new BrowsePanel(this), "BROWSE");
        contentPanel.add(new FavoritesPanel(this), "FAVORITES");
        contentPanel.add(new CommunityPanel(this), "COMMUNITY");
        contentPanel.add(new StudentProfilePanel(this), "STUDENT_PROFILE");
        contentPanel.add(new LandlordProfilePanel(this), "LANDLORD_PROFILE");
        contentPanel.add(new AddListingPanel(this), "ADD_LISTING");

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
    }

    // Navigation
    public void showPanel(String panelName) {
        cardLayout.show(contentPanel, panelName);
        if (navigationPanel != null) {
            try {
                java.lang.reflect.Method m = navigationPanel.getClass().getMethod("updateSelectedButton", String.class);
                m.invoke(navigationPanel, panelName);
            } catch (Exception e) {
                // Method not present or invocation failed; ignore or log if needed
            }
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

    // Simple property generator
    private List<Property> generateSampleProperties() {
    List<Property> sampleProps = new ArrayList<>();
    String city = locationService.getUserCity();
    if (city == null || city.isEmpty()) city = "Your City";
    boolean isIndia = locationService.getUserCountry().equalsIgnoreCase("India");
    
    // Real apartment/hostel/PG images from free stock photos
    String[] imageUrls = {
        "https://images.unsplash.com/photo-1522708323590-d24dbb6b0267?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1502672260066-6bc35f0af07e?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1493809842364-78817add7ffb?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1484154218962-a197022b5858?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1560185007-cde436f6a4d0?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1502672023488-70e25813eb80?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1536376072261-38c75010e6c9?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1512917774080-9991f1c4c750?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1524758631624-e2822e304c36?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1615874959474-d609969a20ed?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1556020685-ae41abfc9365?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1595526114035-0d45ed16cfbf?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1522771739844-6a9f6d5f14af?w=400&h=300&fit=crop",
        "https://images.unsplash.com/photo-1515263487990-61b07816b324?w=400&h=300&fit=crop"
    };
    
    String[][] pd = isIndia ? new String[][] {
        {"Modern 1BHK near " + city + " University", "MG Road, " + city, "8000", "1", "1", "Fully furnished with WiFi and 24/7 security. Perfect for students."},
        {"Shared 2BHK Apartment", "College Street, " + city, "5000", "2", "1", "Spacious apartment with common kitchen. Near metro station."},
        {"Budget PG for Students", "University Avenue, " + city, "4500", "1", "1", "Affordable accommodation with meals included. Study room available."},
        {"Luxury 3BHK Student Housing", "Park Lane, " + city, "15000", "3", "2", "Premium accommodation with gym and pool. All utilities included."},
        {"Cozy Studio Apartment", "Station Road, " + city, "6500", "1", "1", "Quiet neighborhood. Attached bathroom and small kitchen."},
        {"Shared 3BHK near Campus", "Academic Circle, " + city, "4000", "3", "2", "Walking distance to university. Laundry facilities available."},
        {"Modern PG with AC", "Scholar Way, " + city, "7000", "1", "1", "AC rooms with WiFi. 24/7 CCTV and security."},
        {"2BHK Apartment", "Main Street, " + city, "9000", "2", "1", "Furnished apartment. Pet-friendly. Near cafes and library."},
        {"Budget Room in Shared House", "Campus Drive, " + city, "3500", "1", "1", "Great student community. Bills included in rent."},
        {"Premium Studio", "University Road, " + city, "12000", "1", "1", "Brand new studio with modern amenities. 5 min walk to campus."},
        {"Affordable 1BHK", "College Road, " + city, "5500", "1", "1", "Clean and well-maintained. Near public transport."},
        {"Shared 4BHK House", "Student Street, " + city, "3800", "4", "2", "Large house perfect for group of friends. Garden included."},
        {"Modern Hostel Room", "Education Lane, " + city, "4200", "1", "1", "Hostel-style accommodation. Common areas and kitchen."},
        {"Luxury PG with Food", "University Circle, " + city, "10000", "1", "1", "Premium PG with 3 meals daily. Housekeeping included."},
        {"2BHK Independent Floor", "Academic Avenue, " + city, "11000", "2", "1", "Separate entrance. Semi-furnished. Pet-friendly."}
    } : new String[][] {
        {"Modern Studio near Campus", "123 University Ave, " + city, "800", "1", "1", "Beautiful modern studio apartment just 5 minutes walk from campus."},
        {"Shared 3BR Apartment", "456 College St, " + city, "450", "3", "2", "Spacious 3-bedroom apartment perfect for students."},
        {"Cozy 2BR near Library", "789 Academic Rd, " + city, "650", "2", "1", "Quiet neighborhood, perfect for studying. Pet-friendly."},
        {"Luxury Student Housing", "321 Scholar Lane, " + city, "950", "2", "2", "Brand new luxury apartment with gym and pool."},
        {"Budget-Friendly Room", "654 Student Dr, " + city, "400", "1", "1", "Affordable single room in shared house. Bills included."},
        {"Downtown Apartment", "100 Main St, " + city, "700", "2", "1", "Central location. Walking distance to campus and shops."},
        {"Shared House Room", "200 Park Ave, " + city, "500", "1", "1", "Friendly student community. Furnished room."},
        {"Modern 2BR Condo", "300 Campus Way, " + city, "850", "2", "2", "Recently renovated. All amenities included."},
        {"Studio with Kitchen", "400 College Blvd, " + city, "600", "1", "1", "Private studio with full kitchen. Quiet building."},
        {"3BR Shared Apartment", "500 University Pl, " + city, "550", "3", "2", "Large apartment. Individual bedrooms. Common areas."},
        {"Furnished Studio", "600 Student Ln, " + city, "750", "1", "1", "Fully furnished. High-speed internet. Utilities included."},
        {"Budget Room", "700 Campus Rd, " + city, "350", "1", "1", "Basic room in shared accommodation. Great for budget students."},
        {"Modern 1BR", "800 College Ave, " + city, "680", "1", "1", "Modern amenities. Near bus stop. Parking available."},
        {"Shared 4BR House", "900 University St, " + city, "420", "4", "2", "Spacious house. Large common areas. Backyard."},
        {"Premium Studio", "1000 Academic Dr, " + city, "900", "1", "1", "Premium location. Top floor. Great city views."}
    };
    
    String[] names = {"Rajesh Kumar", "Priya Singh", "Amit Sharma", "Anita Desai", "Vikram Patel",
                      "John Smith", "Sarah Johnson", "Mike Davis", "Emma Wilson", "Tom Brown",
                      "Lisa Chen", "David Lee", "Maria Garcia", "James Wilson", "Anna Patel"};
    
    for (int i = 0; i < pd.length; i++) {
        String[] d = pd[i];
        Property p = new Property(i + 1, d[0], d[1], Double.parseDouble(d[2]),
                Integer.parseInt(d[3]), Integer.parseInt(d[4]), d[5]);
        p.setLandlordName(names[i % names.length]);
        p.setImageUrl(imageUrls[i % imageUrls.length]); // Add real image URLs
        sampleProps.add(p);
    }
    
    return sampleProps;
}



    // Getters
    public List<Property> getProperties() { return properties; }
    public List<Property> getFavorites() { return favorites; }
    public void addProperty(Property p) { properties.add(p); }
    public void toggleFavorite(Property p) {
        if (favorites.contains(p)) {
            favorites.remove(p);
            p.setFavorite(false);
        } else {
            favorites.add(p);
            p.setFavorite(true);
        }
    }
    public String getCurrentUser() { return currentUser; }
    public String getCurrencySymbol() { return currencySymbol; }
    public LocationService getLocationService() { return locationService; }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RentflixApp app = new RentflixApp();
            app.setVisible(true);
        });
    }
}
