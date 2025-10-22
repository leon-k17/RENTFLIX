import javax.swing.*;

public class LocationService {
    private String userCountry = "India";
    private String userCity = "chennai";
    private double latitude = 19.0760;
    private double longitude = 72.8777;

    public boolean requestLocationPermission() {
        // Auto-detect location using IP (simulated for offline version)
        // In real version, you'd call IP geolocation API here
        
        // For now, auto-detect based on system locale
        String systemCountry = System.getProperty("user.country");
        if (systemCountry != null) {
            userCountry = systemCountry;
        }
        
        System.out.println("📍 Auto-detected location: " + userCity + ", " + userCountry);
        return true;
    }

    public String getCurrencySymbol() {
        if (userCountry.equalsIgnoreCase("India") || userCountry.equalsIgnoreCase("IN")) {
            return "₹";
        } else if (userCountry.equalsIgnoreCase("United States") || userCountry.equalsIgnoreCase("US") ||
                   userCountry.equalsIgnoreCase("Canada") || userCountry.equalsIgnoreCase("CA") ||
                   userCountry.equalsIgnoreCase("Australia") || userCountry.equalsIgnoreCase("AU")) {
            return "$";
        } else if (userCountry.equalsIgnoreCase("United Kingdom") || userCountry.equalsIgnoreCase("GB")) {
            return "£";
        } else {
            return "$";
        }
    }

    public String getUserCity() { return userCity; }
    public String getUserCountry() { return userCountry; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
}
