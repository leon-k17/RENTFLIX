import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NavigationPanel extends JPanel {
    private RentflixApp app;
    private JButton selectedButton;
    private Map<String, JButton> buttonMap;

    public NavigationPanel(RentflixApp app) {
        this.app = app;
        this.buttonMap = new HashMap<>();
        setPreferredSize(new Dimension(250, 0));
        setBackground(ThemeColors.CARD_BG);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(20, 0, 20, 0));

        initComponents();
    }

    private void initComponents() {
        // Top panel: logo & subtitle
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(ThemeColors.CARD_BG);
        topPanel.setBorder(new EmptyBorder(0, 20, 30, 20));

        JLabel logoLabel = new JLabel("🏠 RENTFLIX");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        logoLabel.setForeground(ThemeColors.PRIMARY_RED);
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Find Your Home");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(ThemeColors.TEXT_SECONDARY);
        subtitleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        topPanel.add(logoLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        topPanel.add(subtitleLabel);

        // Menu panel: vertical nav buttons
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(ThemeColors.CARD_BG);
        menuPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        addMenuItem(menuPanel, "🏠 Home", "INDEX", true);
        addMenuItem(menuPanel, "🔍 Browse Properties", "BROWSE", false);
        addMenuItem(menuPanel, "❤️ My Favorites", "FAVORITES", false);
        addMenuItem(menuPanel, "👥 Community", "COMMUNITY", false);

        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JSeparator separator = new JSeparator();
        separator.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        separator.setForeground(ThemeColors.BORDER);
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        menuPanel.add(separator);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        addMenuItem(menuPanel, "➕ Add Listing", "ADD_LISTING", false);
        addMenuItem(menuPanel, "👤 Profile", 
            app.getCurrentUser().equals("Landlord") ? "LANDLORD_PROFILE" : "STUDENT_PROFILE", false);

        // Bottom panel: user info
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(ThemeColors.CARD_BG);
        bottomPanel.setBorder(new EmptyBorder(20, 20, 0, 20));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        JLabel userLabel = new JLabel(app.getCurrentUser());
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 13));
        userLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel emailLabel = new JLabel("student@college.edu");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        emailLabel.setForeground(ThemeColors.TEXT_SECONDARY);
        emailLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        bottomPanel.add(userLabel);
        bottomPanel.add(emailLabel);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(menuPanel), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void addMenuItem(JPanel panel, String text, String panelName, boolean selected) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(selected ? Color.WHITE : ThemeColors.TEXT_PRIMARY);
        button.setBackground(selected ? ThemeColors.PRIMARY_RED : ThemeColors.CARD_BG);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setHorizontalAlignment(SwingConstants.LEFT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        buttonMap.put(panelName, button);

        if (selected) selectedButton = button;

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button != selectedButton) {
                    button.setBackground(new Color(40, 40, 40));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button != selectedButton) {
                    button.setBackground(ThemeColors.CARD_BG);
                }
            }
        });

        button.addActionListener(e -> {
            if (selectedButton != null) {
                selectedButton.setBackground(ThemeColors.CARD_BG);
                selectedButton.setForeground(ThemeColors.TEXT_PRIMARY);
            }
            button.setBackground(ThemeColors.PRIMARY_RED);
            button.setForeground(Color.WHITE);
            selectedButton = button;
            app.showPanel(panelName);
        });

        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    public void updateSelectedButton(String panelName) {
        JButton button = buttonMap.get(panelName);
        if (button != null && button != selectedButton) {
            if (selectedButton != null) {
                selectedButton.setBackground(ThemeColors.CARD_BG);
                selectedButton.setForeground(ThemeColors.TEXT_PRIMARY);
            }
            button.setBackground(ThemeColors.PRIMARY_RED);
            button.setForeground(Color.WHITE);
            selectedButton = button;
        }
    }
}
