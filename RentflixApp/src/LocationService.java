public class LocationService {

    // Always assume permission granted (for PC apps)
    public boolean requestLocationPermission() {
        return true;
    }

    // Set a static city for demo (can extend later to detect via API)
    public String getUserCity() {
        return "chennai";
    }

    public String getUserCountry() {
        return "India";
    }

    public String getCurrencySymbol() {
        return "₹";
    }
}
