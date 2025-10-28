
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class StudentProfilePanel extends JPanel {
    private RentflixApp app;

    public StudentProfilePanel(RentflixApp app) {
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

        JLabel titleLabel = new JLabel("Student Profile");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        JPanel profileCard = new JPanel();
        profileCard.setLayout(new BoxLayout(profileCard, BoxLayout.Y_AXIS));
        profileCard.setBackground(ThemeColors.CARD_BG);
        profileCard.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(15, ThemeColors.BORDER),
            new EmptyBorder(30, 30, 30, 30)
        ));
        profileCard.setMaximumSize(new Dimension(Integer.MAX_VALUE, 500));

        JLabel iconLabel = new JLabel("👤", SwingConstants.CENTER);
        iconLabel.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel nameLabel = new JLabel("BARADI KAUSHIK");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        nameLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel emailLabel = new JLabel("kb0037@srmist.edu.in");
        emailLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        emailLabel.setForeground(ThemeColors.TEXT_SECONDARY);
        emailLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel statusLabel = new JLabel("✓ Verified Student");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        statusLabel.setForeground(ThemeColors.ACCENT_ORANGE);
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        profileCard.add(iconLabel);
        profileCard.add(Box.createRigidArea(new Dimension(0, 15)));
        profileCard.add(nameLabel);
        profileCard.add(Box.createRigidArea(new Dimension(0, 5)));
        profileCard.add(emailLabel);
        profileCard.add(Box.createRigidArea(new Dimension(0, 10)));
        profileCard.add(statusLabel);
        profileCard.add(Box.createRigidArea(new Dimension(0, 30)));

        addInfoRow(profileCard, "Year of Study", "second year");
        addInfoRow(profileCard, "Phone", "+91 6304023404");
        addInfoRow(profileCard, "Preferred Budget", "INR 10k - 12k/month");
        addInfoRow(profileCard, "Looking For", "1-2 Bedroom near campus");

        mainPanel.add(profileCard);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void addInfoRow(JPanel parent, String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(ThemeColors.CARD_BG);
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        JLabel labelComp = new JLabel(label);
        labelComp.setFont(new Font("Segoe UI", Font.BOLD, 14));
        labelComp.setForeground(ThemeColors.TEXT_PRIMARY);

        JLabel valueComp = new JLabel(value);
        valueComp.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        valueComp.setForeground(ThemeColors.TEXT_SECONDARY);

        row.add(labelComp, BorderLayout.WEST);
        row.add(valueComp, BorderLayout.EAST);

        parent.add(row);
        parent.add(Box.createRigidArea(new Dimension(0, 15)));
    }
}
