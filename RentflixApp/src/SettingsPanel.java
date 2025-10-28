import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SettingsPanel extends JPanel {
    public SettingsPanel(RentflixApp app) {
        setBackground(ThemeColors.BACKGROUND);
        setLayout(new BorderLayout());
        init(app);
    }

    private void init(RentflixApp app) {
        JLabel header = new JLabel("Settings");
        header.setFont(new Font("Segoe UI", Font.BOLD, 22));
        header.setForeground(ThemeColors.TEXT_PRIMARY);
        header.setBorder(new EmptyBorder(12,12,12,12));
        add(header, BorderLayout.NORTH);

        JPanel body = new JPanel();
        body.setBackground(ThemeColors.CARD_BG);
        body.setBorder(new EmptyBorder(20,20,20,20));
        body.setLayout(new BoxLayout(body, BoxLayout.Y_AXIS));

        JLabel theme = new JLabel("Theme");
        theme.setForeground(ThemeColors.TEXT_PRIMARY);
        String[] options = {"System Default (Dark-friendly)", "Light (not fully styled)"};
        JComboBox<String> themeBox = new JComboBox<>(options);

        JLabel currency = new JLabel("Currency (app uses auto-detected)");
        currency.setForeground(ThemeColors.TEXT_PRIMARY);

        body.add(theme);
        body.add(themeBox);
        body.add(Box.createRigidArea(new Dimension(0,12)));
        body.add(currency);

        add(body, BorderLayout.CENTER);
    }
}
