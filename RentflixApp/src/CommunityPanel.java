import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;

public class CommunityPanel extends JPanel {
    private RentflixApp app;
    private JPanel postsContainer;

    public CommunityPanel(RentflixApp app) {
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

        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(ThemeColors.BACKGROUND);
        headerPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 45));
        headerPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel title = new JLabel("Community");
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(ThemeColors.TEXT_PRIMARY);

        JButton createPostBtn = new JButton("+ Create Post");
        createPostBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        createPostBtn.setForeground(Color.WHITE);
        createPostBtn.setBackground(ThemeColors.PRIMARY_RED);
        createPostBtn.setFocusPainted(false);
        createPostBtn.setBorderPainted(false);
        createPostBtn.setPreferredSize(new Dimension(140, 38));
        createPostBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createPostBtn.addActionListener(e -> showCreatePostDialog());

        headerPanel.add(title, BorderLayout.WEST);
        headerPanel.add(createPostBtn, BorderLayout.EAST);

        mainPanel.add(headerPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 25)));

        // Posts container
        postsContainer = new JPanel();
        postsContainer.setLayout(new BoxLayout(postsContainer, BoxLayout.Y_AXIS));
        postsContainer.setBackground(ThemeColors.BACKGROUND);

        refreshPosts();
        mainPanel.add(postsContainer);

        JScrollPane scroll = new JScrollPane(mainPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);
    }

    public void refreshPosts() {
        postsContainer.removeAll();
        // Get posts from DB
        List<CommunityPost> posts = app.getDB().getAllPosts();
        for (CommunityPost post : posts) {
            postsContainer.add(createPostPanel(post));
            postsContainer.add(Box.createRigidArea(new Dimension(0, 15)));
        }
        postsContainer.revalidate();
        postsContainer.repaint();
    }

    private void showCreatePostDialog() {
        JDialog dialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), "Create New Post", true);
        dialog.setSize(520, 420);
        dialog.setLocationRelativeTo(this);

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new BoxLayout(dialogPanel, BoxLayout.Y_AXIS));
        dialogPanel.setBackground(ThemeColors.CARD_BG);
        dialogPanel.setBorder(new EmptyBorder(25, 25, 25, 25));

        JLabel titleLabel = new JLabel("Post Title");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextField titleField = new JTextField();
        titleField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        titleField.setBackground(ThemeColors.INPUT_BG);
        titleField.setForeground(ThemeColors.TEXT_PRIMARY);
        titleField.setCaretColor(ThemeColors.TEXT_PRIMARY);
        titleField.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, ThemeColors.BORDER),
            new EmptyBorder(10, 12, 10, 12)
        ));
        titleField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        titleField.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel contentLabel = new JLabel("Post Content");
        contentLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        contentLabel.setForeground(ThemeColors.TEXT_PRIMARY);
        contentLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JTextArea contentArea = new JTextArea(8, 40);
        contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        contentArea.setBackground(ThemeColors.INPUT_BG);
        contentArea.setForeground(ThemeColors.TEXT_PRIMARY);
        contentArea.setCaretColor(ThemeColors.TEXT_PRIMARY);
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);
        contentArea.setBorder(BorderFactory.createCompoundBorder(
            new RoundedBorder(8, ThemeColors.BORDER),
            new EmptyBorder(10, 12, 10, 12)
        ));

        JScrollPane contentScroll = new JScrollPane(contentArea);
        contentScroll.setBorder(null);
        contentScroll.setMaximumSize(new Dimension(Integer.MAX_VALUE, 160));
        contentScroll.setAlignmentX(Component.LEFT_ALIGNMENT);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        buttonPanel.setBackground(ThemeColors.CARD_BG);
        buttonPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        cancelBtn.setBackground(ThemeColors.INPUT_BG);
        cancelBtn.setForeground(ThemeColors.TEXT_PRIMARY);
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorderPainted(false);
        cancelBtn.setPreferredSize(new Dimension(100, 38));
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelBtn.addActionListener(e -> dialog.dispose());

        JButton postBtn = new JButton("Post");
        postBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        postBtn.setForeground(Color.WHITE);
        postBtn.setBackground(ThemeColors.PRIMARY_RED);
        postBtn.setFocusPainted(false);
        postBtn.setBorderPainted(false);
        postBtn.setPreferredSize(new Dimension(100, 38));
        postBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        postBtn.addActionListener(e -> {
            String postTitle = titleField.getText().trim();
            String postContent = contentArea.getText().trim();

            if (postTitle.isEmpty() || postContent.isEmpty()) {
                JOptionPane.showMessageDialog(dialog,
                    "Please fill in both title and content",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean success = app.getDB().createPost(postTitle, postContent, app.getCurrentUserId());
            if (success) {
                refreshPosts();
                JOptionPane.showMessageDialog(dialog,
                    "Post created successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
            } else {
                JOptionPane.showMessageDialog(dialog,
                    "Error creating post. Please try again.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        buttonPanel.add(cancelBtn);
        buttonPanel.add(postBtn);

        dialogPanel.add(titleLabel);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        dialogPanel.add(titleField);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 18)));
        dialogPanel.add(contentLabel);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 8)));
        dialogPanel.add(contentScroll);
        dialogPanel.add(Box.createRigidArea(new Dimension(0, 22)));
        dialogPanel.add(buttonPanel);

        dialog.add(dialogPanel);
        dialog.setVisible(true);
    }

    private JPanel createPostPanel(CommunityPost post) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
    panel.setBackground(ThemeColors.CARD_BG);
    panel.setBorder(BorderFactory.createCompoundBorder(
        new RoundedBorder(12, ThemeColors.BORDER),
        new EmptyBorder(20, 20, 20, 20)
    ));
    panel.setPreferredSize(new Dimension(900, 280));
    panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 280));
    panel.setAlignmentX(Component.LEFT_ALIGNMENT);

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
    content.setRows(2);

    JLabel meta = new JLabel(post.getAuthor() + " • " + post.getTime());
    meta.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    meta.setForeground(ThemeColors.TEXT_SECONDARY);
    meta.setAlignmentX(Component.LEFT_ALIGNMENT);

    panel.add(titleLabel);
    panel.add(Box.createRigidArea(new Dimension(0, 8)));
    panel.add(content);
    panel.add(Box.createRigidArea(new Dimension(0, 8)));
    panel.add(meta);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    JSeparator sep = new JSeparator();
    sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
    panel.add(sep);
    panel.add(Box.createRigidArea(new Dimension(0, 10)));

    // Reply button aligned right
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
    buttonPanel.setBackground(ThemeColors.CARD_BG);

    JButton replyButton = new JButton("💬 Reply");
    replyButton.setFont(new Font("Segoe UI", Font.BOLD, 13));
    replyButton.setForeground(Color.WHITE);
    replyButton.setBackground(ThemeColors.PRIMARY_RED);
    replyButton.setFocusPainted(false);
    replyButton.setBorderPainted(false);
    replyButton.setPreferredSize(new Dimension(100, 34));
    replyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
    replyButton.addActionListener(e -> showReplyDialog(post));

    buttonPanel.add(replyButton);
    panel.add(buttonPanel);

    return panel;
}

private void showReplyDialog(CommunityPost post) {
    JDialog replyDialog = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), 
                                      "Reply to: " + post.getTitle(), true);
    replyDialog.setSize(480, 320);
    replyDialog.setLocationRelativeTo(this);
    replyDialog.setLayout(new BorderLayout(10, 10));
    replyDialog.getContentPane().setBackground(ThemeColors.CARD_BG);

    JPanel topPanel = new JPanel(new BorderLayout());
    topPanel.setBackground(ThemeColors.CARD_BG);
    topPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 8, 15));

    JLabel infoLabel = new JLabel("Replying to " + post.getAuthor());
    infoLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    infoLabel.setForeground(ThemeColors.TEXT_SECONDARY);
    topPanel.add(infoLabel, BorderLayout.WEST);

    JTextArea replyArea = new JTextArea();
    replyArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
    replyArea.setBackground(ThemeColors.INPUT_BG);
    replyArea.setForeground(ThemeColors.TEXT_PRIMARY);
    replyArea.setCaretColor(ThemeColors.TEXT_PRIMARY);
    replyArea.setLineWrap(true);
    replyArea.setWrapStyleWord(true);
    replyArea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ThemeColors.BORDER, 1),
            new EmptyBorder(10, 10, 10, 10)
    ));

    JScrollPane scrollPane = new JScrollPane(replyArea);
    scrollPane.setBorder(BorderFactory.createEmptyBorder(0, 15, 8, 15));

    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    buttonPanel.setBackground(ThemeColors.CARD_BG);
    buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 15, 15, 15));

    JButton cancelButton = new JButton("Cancel");
    cancelButton.setFont(new Font("Segoe UI", Font.PLAIN, 12));
    cancelButton.setBackground(ThemeColors.CARD_BG);
    cancelButton.setForeground(ThemeColors.TEXT_PRIMARY);
    cancelButton.setFocusPainted(false);
    cancelButton.addActionListener(e -> replyDialog.dispose());

    JButton sendButton = new JButton("Send Reply");
    sendButton.setFont(new Font("Segoe UI", Font.BOLD, 12));
    sendButton.setBackground(ThemeColors.PRIMARY_RED);
    sendButton.setForeground(Color.WHITE);
    sendButton.setFocusPainted(false);
    sendButton.addActionListener(e -> {
        String replyText = replyArea.getText().trim();
        if (!replyText.isEmpty()) {
            JOptionPane.showMessageDialog(replyDialog, 
                "Reply sent successfully! (Not stored - implement DB for persistence)", 
                "Success", 
                JOptionPane.INFORMATION_MESSAGE);
            replyDialog.dispose();
            // Optional: add data persistence code here
        } else {
            JOptionPane.showMessageDialog(replyDialog, 
                "Please enter a reply!", 
                "Empty Reply", 
                JOptionPane.WARNING_MESSAGE);
        }
    });

    buttonPanel.add(cancelButton);
    buttonPanel.add(Box.createRigidArea(new Dimension(10, 0)));
    buttonPanel.add(sendButton);

    replyDialog.add(topPanel, BorderLayout.NORTH);
    replyDialog.add(scrollPane, BorderLayout.CENTER);
    replyDialog.add(buttonPanel, BorderLayout.SOUTH);

    replyDialog.setVisible(true);
}


}
