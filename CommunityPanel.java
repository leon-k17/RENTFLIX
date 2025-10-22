import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CommunityPanel extends JPanel {
    private RentflixApp app;
    private List<CommunityPost> posts;

    public CommunityPanel(RentflixApp app) {
        this.app = app;
        setBackground(ThemeColors.BACKGROUND);
        setLayout(new BorderLayout());
        createSamplePosts();
        initComponents();
    }

    private void createSamplePosts() {
        posts = new ArrayList<>();
        posts.add(new CommunityPost("Looking for roommate", 
            "2nd year CS student seeking roommate for 2BHK near campus. Budget ₹12k/month.", 
            "Sarah J.", "2 hours ago"));
        posts.add(new CommunityPost("Best areas for students?", 
            "Moving soon. Which neighborhoods are safe and close to university?", 
            "Mike T.", "5 hours ago"));
        posts.add(new CommunityPost("Furniture sale - Moving out!", 
            "Selling desk, chair, bed. Great condition. DM for details!", 
            "Emma W.", "1 day ago"));
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ThemeColors.BACKGROUND);
        mainPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        JLabel title = new JLabel("Community");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(ThemeColors.TEXT_PRIMARY);
        title.setAlignmentX(Component.LEFT_ALIGNMENT);

        mainPanel.add(title);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        for (CommunityPost post : posts) {
            mainPanel.add(createPostPanel(post));
            mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        }

        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll);
    }

    private JPanel createPostPanel(CommunityPost post) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(ThemeColors.CARD_BG);
        panel.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(12, ThemeColors.BORDER),
            new EmptyBorder(20, 20, 20, 20)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 300));

        JLabel titleLabel = new JLabel(post.getTitle());
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea content = new JTextArea(post.getContent());
        content.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        content.setForeground(ThemeColors.TEXT_SECONDARY);
        content.setBackground(ThemeColors.CARD_BG);
        content.setLineWrap(true);
        content.setWrapStyleWord(true);
        content.setEditable(false);
        content.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel meta = new JLabel(post.getAuthor() + " • " + post.getTime());
        meta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        meta.setForeground(ThemeColors.TEXT_SECONDARY);
        meta.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(titleLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(content);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(meta);
        panel.add(Box.createRigidArea(new Dimension(0, 12)));

        // Replies section
        JPanel repliesPanel = new JPanel();
        repliesPanel.setLayout(new BoxLayout(repliesPanel, BoxLayout.Y_AXIS));
        repliesPanel.setBackground(ThemeColors.CARD_BG);
        repliesPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        updateReplies(repliesPanel, post);
        panel.add(repliesPanel);

        // Reply input
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.X_AXIS));
        inputPanel.setBackground(ThemeColors.CARD_BG);
        inputPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField replyField = new JTextField();
        replyField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        replyField.setBackground(ThemeColors.INPUT_BG);
        replyField.setForeground(ThemeColors.TEXT_PRIMARY);
        replyField.setCaretColor(ThemeColors.TEXT_PRIMARY);
        replyField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, ThemeColors.BORDER),
            new EmptyBorder(8, 10, 8, 10)
        ));

        JButton replyBtn = new JButton("Reply");
        replyBtn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        replyBtn.setForeground(Color.WHITE);
        replyBtn.setBackground(ThemeColors.PRIMARY_RED);
        replyBtn.setFocusPainted(false);
        replyBtn.setBorderPainted(false);
        replyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        replyBtn.addActionListener(e -> {
            String text = replyField.getText().trim();
            if (!text.isEmpty()) {
                post.addReply(app.getCurrentUser() + ": " + text);
                replyField.setText("");
                updateReplies(repliesPanel, post);
                repliesPanel.revalidate();
                repliesPanel.repaint();
            }
        });

        inputPanel.add(replyField);
        inputPanel.add(Box.createRigidArea(new Dimension(8, 0)));
        inputPanel.add(replyBtn);

        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(inputPanel);

        return panel;
    }

    private void updateReplies(JPanel panel, CommunityPost post) {
        panel.removeAll();
        if (post.getReplies().isEmpty()) {
            JLabel noReplies = new JLabel("No replies yet. Be first to reply!");
            noReplies.setFont(new Font("Segoe UI", Font.ITALIC, 12));
            noReplies.setForeground(new Color(140, 140, 140));
            noReplies.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(noReplies);
        } else {
            for (String reply : post.getReplies()) {
                JPanel replyPanel = new JPanel();
                replyPanel.setLayout(new BoxLayout(replyPanel, BoxLayout.X_AXIS));
                replyPanel.setBackground(new Color(40, 40, 40));
                replyPanel.setBorder(BorderFactory.createCompoundBorder(
                    new RoundedBorder(8, new Color(50, 50, 50)),
                    new EmptyBorder(8, 10, 8, 10)
                ));
                replyPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

                JLabel icon = new JLabel("💬 ");
                icon.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 12));

                JLabel text = new JLabel(reply);
                text.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                text.setForeground(new Color(200, 200, 200));

                replyPanel.add(icon);
                replyPanel.add(text);

                panel.add(replyPanel);
                panel.add(Box.createRigidArea(new Dimension(0, 6)));
            }
        }
    }
}
