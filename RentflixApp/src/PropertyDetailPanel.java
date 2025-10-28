import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;


import java.net.URI;
import java.net.URL;

public class PropertyDetailPanel extends JPanel {
    private RentflixApp app;
    private Property property;

    public PropertyDetailPanel(RentflixApp app, Property property) {
        this.app = app;
        this.property = property;
        setBackground(ThemeColors.BACKGROUND);
        setLayout(new BorderLayout());
        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ThemeColors.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        // Back button
        JButton backButton = new JButton("← Back to Browse");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setForeground(ThemeColors.TEXT_PRIMARY);
        backButton.setBackground(ThemeColors.CARD_BG);
        backButton.setFocusPainted(false);
        backButton.setBorderPainted(false);
        backButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        backButton.setAlignmentX(Component.LEFT_ALIGNMENT);
        backButton.addActionListener(e -> {
            app.showPanel("BROWSE");
            System.out.println("✅ Back button clicked - returning to Browse");
        });

        mainPanel.add(backButton);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel contentPanel = new JPanel(new BorderLayout(30, 0));
        contentPanel.setBackground(ThemeColors.CARD_BG);
        contentPanel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15, ThemeColors.BORDER),
            new EmptyBorder(30, 30, 30, 30)
        ));
        contentPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 600));

        // Left side - Image panel with real image loading
        JPanel imagePanel = new JPanel(new BorderLayout());
        imagePanel.setPreferredSize(new Dimension(500, 400));
        imagePanel.setBackground(new Color(60, 60, 60));
        JLabel imageLabel = new JLabel("Loading...", SwingConstants.CENTER);
        imageLabel.setForeground(ThemeColors.TEXT_SECONDARY);
        imagePanel.add(imageLabel, BorderLayout.CENTER);

        Component leftImageComponent;
        if (property.getImageUrl() != null && !property.getImageUrl().isEmpty()) {
            loadPropertyImage(property.getImageUrl(), imageLabel, imagePanel);
            leftImageComponent = imagePanel;
        } else {
            GradientImagePanel gradientPanel = new GradientImagePanel(getPropertyColor(property.getId()));
            gradientPanel.setPreferredSize(new Dimension(500, 400));
            gradientPanel.setLayout(new BorderLayout());
            JLabel fallbackIcon = new JLabel("🏠", SwingConstants.CENTER);
            fallbackIcon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 120));
            fallbackIcon.setForeground(new Color(255, 255, 255, 200));
            gradientPanel.add(fallbackIcon, BorderLayout.CENTER);
            leftImageComponent = gradientPanel;
        }

        // Right side - Details
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        detailsPanel.setBackground(ThemeColors.CARD_BG);

        JLabel titleLabel = new JLabel(property.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel priceLabel = new JLabel(app.getCurrencySymbol() + String.format("%.0f", property.getPrice()) + "/month");
        priceLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        priceLabel.setForeground(ThemeColors.PRIMARY_RED);
        priceLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel addressLabel = new JLabel("📍 " + property.getAddress());
        addressLabel.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        addressLabel.setForeground(ThemeColors.TEXT_SECONDARY);
        addressLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel featuresPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        featuresPanel.setBackground(ThemeColors.CARD_BG);
        featuresPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel bedLabel = new JLabel("🛏️ " + property.getBedrooms() + " Bedrooms");
        bedLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bedLabel.setForeground(ThemeColors.TEXT_PRIMARY);

        JLabel bathLabel = new JLabel("🚿 " + property.getBathrooms() + " Bathrooms");
        bathLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bathLabel.setForeground(ThemeColors.TEXT_PRIMARY);

        featuresPanel.add(bedLabel);
        featuresPanel.add(bathLabel);

        JLabel descLabel = new JLabel("Description");
        descLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        descLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        descLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea descText = new JTextArea(property.getDescription());
        descText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descText.setForeground(ThemeColors.TEXT_SECONDARY);
        descText.setBackground(ThemeColors.CARD_BG);
        descText.setLineWrap(true);
        descText.setWrapStyleWord(true);
        descText.setEditable(false);
        descText.setAlignmentX(Component.LEFT_ALIGNMENT);
        descText.setOpaque(false);

        JLabel landlordLabel = new JLabel("Landlord: " + (property.getLandlordName() != null ? property.getLandlordName() : "Contact for details"));
        landlordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        landlordLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        landlordLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        buttonPanel.setBackground(ThemeColors.CARD_BG);
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JButton favoriteBtn = new JButton(property.isFavorite() ? "❤️ Remove from Favorites" : "🤍 Add to Favorites");
        favoriteBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        favoriteBtn.setForeground(Color.WHITE);
        favoriteBtn.setBackground(property.isFavorite() ? ThemeColors.ACCENT_ORANGE : ThemeColors.PRIMARY_RED);
        favoriteBtn.setFocusPainted(false);
        favoriteBtn.setBorderPainted(false);
        favoriteBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        favoriteBtn.addActionListener(e -> {
            app.toggleFavorite(property);
            favoriteBtn.setText(property.isFavorite() ? "❤️ Remove from Favorites" : "🤍 Add to Favorites");
            favoriteBtn.setBackground(property.isFavorite() ? ThemeColors.ACCENT_ORANGE : ThemeColors.PRIMARY_RED);
        });

        JButton contactBtn = new JButton("📧 Contact Landlord");
        contactBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contactBtn.setForeground(ThemeColors.TEXT_PRIMARY);
        contactBtn.setBackground(ThemeColors.INPUT_BG);
        contactBtn.setFocusPainted(false);
        contactBtn.setBorderPainted(false);
        contactBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        contactBtn.addActionListener(e ->
            JOptionPane.showMessageDialog(this, "Contact feature coming soon!",
                "Contact", JOptionPane.INFORMATION_MESSAGE));

        buttonPanel.add(favoriteBtn);
        buttonPanel.add(contactBtn);

        detailsPanel.add(titleLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(priceLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        detailsPanel.add(addressLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        detailsPanel.add(featuresPanel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        detailsPanel.add(descLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        detailsPanel.add(descText);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        detailsPanel.add(landlordLabel);
        detailsPanel.add(Box.createRigidArea(new Dimension(0, 25)));
        detailsPanel.add(buttonPanel);

        contentPanel.add(leftImageComponent, BorderLayout.WEST);
        contentPanel.add(detailsPanel, BorderLayout.CENTER);

        mainPanel.add(contentPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private Color getPropertyColor(int id) {
        Color[] colors = {
            new Color(229, 62, 48), new Color(255, 102, 51), new Color(52, 152, 219),
            new Color(155, 89, 182), new Color(26, 188, 156), new Color(241, 196, 15),
            new Color(230, 126, 34), new Color(231, 76, 60), new Color(142, 68, 173),
            new Color(39, 174, 96)
        };
        return colors[id % colors.length];
    }

    private class GradientImagePanel extends JPanel {
        private Color color;

        public GradientImagePanel(Color color) {
            this.color = color;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            Color darkColor = new Color(
                Math.max(0, color.getRed() - 40),
                Math.max(0, color.getGreen() - 40),
                Math.max(0, color.getBlue() - 40)
            );

            GradientPaint gradient = new GradientPaint(
                0, 0, darkColor,
                0, getHeight(), color
            );

            g2d.setPaint(gradient);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    private void loadPropertyImage(String imageUrl, JLabel imageLabel, JPanel imagePanel) {
        SwingWorker<ImageIcon, Void> worker = new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground() throws Exception {
                try {
                    URL url = new URI(imageUrl).toURL();

                    BufferedImage img = ImageIO.read(url);
                    if (img != null) {
                        Image scaledImg = img.getScaledInstance(500, 400, Image.SCALE_SMOOTH);
                        return new ImageIcon(scaledImg);
                    }
                } catch (Exception e) {
                    System.err.println("Failed to load detail image: " + imageUrl);
                }
                return null;
            }

            @Override
            protected void done() {
                try {
                    ImageIcon icon = get();
                    if (icon != null) {
                        imageLabel.setText("");
                        imageLabel.setIcon(icon);
                    } else {
                        imageLabel.setText("🏠");
                        imageLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
                    }
                } catch (Exception e) {
                    imageLabel.setText("🏠");
                    imageLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 100));
                }
            }
        };
        worker.execute();
    }
}
