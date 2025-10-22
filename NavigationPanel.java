import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

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
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(ThemeColors.CARD_BG);
        topPanel.setBorder(new EmptyBorder(0, 20, 30, 20));

        JLabel logoLabel = new JLabel("🏠 RENTFLIX");
        logoLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        logoLabel.setForeground(ThemeColors.PRIMARY_RED);
        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        topPanel.add(logoLabel);
        topPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(ThemeColors.CARD_BG);
        menuPanel.setBorder(new EmptyBorder(0, 10, 0, 10));

        addMenuItem(menuPanel, "🏠 Home", "INDEX", true);
        addMenuItem(menuPanel, "🔍 Browse", "BROWSE", false);
        addMenuItem(menuPanel, "❤️ Favorites", "FAVORITES", false);
        addMenuItem(menuPanel, "👥 Community", "COMMUNITY", false);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        menuPanel.add(sep);
        menuPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        
        addMenuItem(menuPanel, "👤 Profile", "STUDENT_PROFILE", false);
        addMenuItem(menuPanel, "➕ Add Listing", "ADD_LISTING", false);

        add(topPanel, BorderLayout.NORTH);
        add(new JScrollPane(menuPanel), BorderLayout.CENTER);
    }

    private void addMenuItem(JPanel panel, String text, String panelName, boolean selected) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setForeground(ThemeColors.TEXT_PRIMARY);
        btn.setBackground(selected ? ThemeColors.PRIMARY_RED : ThemeColors.CARD_BG);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        if (selected) selectedButton = btn;
        buttonMap.put(panelName, btn);

        btn.addActionListener(e -> {
            updateSelectedButton(panelName);
            app.showPanel(panelName);
        });

        panel.add(btn);
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
    }

    public void updateSelectedButton(String panelName) {
        if (selectedButton != null) selectedButton.setBackground(ThemeColors.CARD_BG);
        JButton newBtn = buttonMap.get(panelName);
        if (newBtn != null) {
            newBtn.setBackground(ThemeColors.PRIMARY_RED);
            selectedButton = newBtn;
        }
    }
}
