
public class Property {
    private int id;
    private String title;
    private String address;
    private double price;
    private int bedrooms;
    private int bathrooms;
    private String description;
    private String imageUrl;
    private String landlordName;
    private String landlordEmail;
    private boolean isFavorite;

    public Property(int id, String title, String address, double price, int bedrooms, int bathrooms, String description) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.price = price;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.description = description;
        this.isFavorite = false;
    }

    // Getters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAddress() { return address; }
    public double getPrice() { return price; }
    public int getBedrooms() { return bedrooms; }
    public int getBathrooms() { return bathrooms; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getLandlordName() { return landlordName; }
    public String getLandlordEmail() { return landlordEmail; }
    public boolean isFavorite() { return isFavorite; }

    // Setters
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setLandlordName(String name) { this.landlordName = name; }
    public void setLandlordEmail(String email) { this.landlordEmail = email; }
    public void setFavorite(boolean favorite) { this.isFavorite = favorite; }
    public void setTitle(String title) { this.title = title; }
    public void setAddress(String address) { this.address = address; }
    public void setPrice(double price) { this.price = price; }
    public void setDescription(String description) { this.description = description; }
}
