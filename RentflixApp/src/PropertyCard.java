import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.net.URI;

import javax.imageio.ImageIO;

public class PropertyCard extends JPanel {
    private RentflixApp app;
    private Property property;

    public PropertyCard(RentflixApp app, Property property) {
        this.app = app;
        this.property = property;
        setLayout(new BorderLayout());
        setBackground(ThemeColors.CARD_BG);
        setBorder(new RoundedBorder(12, ThemeColors.BORDER));
        setPreferredSize(new Dimension(300, 340));
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        initComponents();
        addHoverEffect();
    }

    private void initComponents() {
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(300, 180));
        imagePanel.setBackground(new Color(60, 60, 60));

        JLabel imageLabel = new JLabel("Loading...", SwingConstants.CENTER);
        imageLabel.setForeground(ThemeColors.TEXT_SECONDARY);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        if (property.getImageUrl() != null && !property.getImageUrl().isEmpty()) {
            loadImageAsync(property.getImageUrl(), imageLabel);
        }

        JButton favBtn = new JButton(property.isFavorite() ? "❤️" : "🤍");
        favBtn.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 18));
        favBtn.setBackground(new Color(0, 0, 0, 180));
        favBtn.setFocusPainted(false);
        favBtn.setBorderPainted(false);
        favBtn.setPreferredSize(new Dimension(40, 40));
        favBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        favBtn.addActionListener(e -> {
            app.toggleFavorite(property);
            favBtn.setText(property.isFavorite() ? "❤️" : "🤍");
        });

        JPanel favPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        favPanel.setOpaque(false);
        favPanel.add(favBtn);
        imagePanel.add(favPanel, BorderLayout.NORTH);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(ThemeColors.CARD_BG);
        content.setBorder(new EmptyBorder(15, 15, 15, 15));

        JLabel titleLabel = new JLabel(truncate(property.getTitle(), 30));
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 15));
        titleLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel addrLabel = new JLabel(truncate(property.getAddress(), 32));
        addrLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        addrLabel.setForeground(ThemeColors.TEXT_SECONDARY);
        addrLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel details = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        details.setBackground(ThemeColors.CARD_BG);
        details.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel bed = new JLabel("🛏️ " + property.getBedrooms());
        bed.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bed.setForeground(ThemeColors.TEXT_SECONDARY);
        
        JLabel bath = new JLabel("🚿 " + property.getBathrooms());
        bath.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        bath.setForeground(ThemeColors.TEXT_SECONDARY);
        
        details.add(bed);
        details.add(bath);

        JLabel price = new JLabel(app.getCurrencySymbol() + String.format("%.0f", property.getPrice()) + "/mo");
        price.setFont(new Font("Segoe UI", Font.BOLD, 17));
        price.setForeground(ThemeColors.PRIMARY_RED);
        price.setAlignmentX(Component.LEFT_ALIGNMENT);

        content.add(titleLabel);
        content.add(Box.createRigidArea(new Dimension(0, 5)));
        content.add(addrLabel);
        content.add(Box.createRigidArea(new Dimension(0, 8)));
        content.add(details);
        content.add(Box.createRigidArea(new Dimension(0, 8)));
        content.add(price);

        add(imagePanel, BorderLayout.NORTH);
        add(content, BorderLayout.CENTER);
    }

    private void loadImageAsync(String url, JLabel label) {
        new SwingWorker<ImageIcon, Void>() {
            protected ImageIcon doInBackground() throws Exception {
                BufferedImage img = ImageIO.read(new URI(url).toURL());

                return new ImageIcon(img.getScaledInstance(300, 180, Image.SCALE_SMOOTH));
            }
            protected void done() {
                try {
                    label.setText("");
                    label.setIcon(get());
                } catch (Exception e) {
                    label.setText("🏠");
                    label.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
                }
            }
        }.execute();
    }

    private String truncate(String text, int max) {
        return text.length() <= max ? text : text.substring(0, max - 3) + "...";
    }

    private void addHoverEffect() {
        addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                setBorder(new RoundedBorder(12, ThemeColors.PRIMARY_RED, 2));
            }
            public void mouseExited(MouseEvent e) {
                setBorder(new RoundedBorder(12, ThemeColors.BORDER));
            }
            public void mouseClicked(MouseEvent e) {
                app.showPropertyDetail(property);
            }
        });
    }
}
