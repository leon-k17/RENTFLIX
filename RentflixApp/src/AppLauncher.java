import javax.swing.*;

public class AppLauncher {
    public static void main(String[] args) {
        System.out.println("Starting RENTFLIX Application...");
        
        // Set system look and feel for better appearance
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            System.out.println("Look and feel set successfully");
        } catch (Exception e) {
            System.err.println("Error setting look and feel:");
            e.printStackTrace();
        }

        // Launch the authentication window
        SwingUtilities.invokeLater(() -> {
            try {
                System.out.println("Creating RentflixAuth window...");
                RentflixAuth authFrame = new RentflixAuth();
                authFrame.setVisible(true);
                System.out.println("RentflixAuth window created and set to visible");
            } catch (Exception e) {
                System.err.println("Error creating RentflixAuth:");
                e.printStackTrace();
            }
        });
    }
}

