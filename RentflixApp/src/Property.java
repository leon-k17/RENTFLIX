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
    private boolean favorite;
    private String city;

    // Constructor (as used in AddListing, Browse, etc.)
    public Property(int id, String title, String address, double price, int bedrooms, int bathrooms, String description) {
        this.id = id;
        this.title = title;
        this.address = address;
        this.price = price;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.description = description;
        this.city = "chennai";
    }

    // Getters and setters
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAddress() { return address; }
    public double getPrice() { return price; }
    public int getBedrooms() { return bedrooms; }
    public int getBathrooms() { return bathrooms; }
    public String getDescription() { return description; }
    public String getImageUrl() { return imageUrl; }
    public String getLandlordName() { return landlordName; }
    public boolean isFavorite() { 
        return favorite;
    }
    public String getCity() { 
        return city; 
    }

    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    public void setLandlordName(String landlordName) { this.landlordName = landlordName; }
    public void setFavorite(boolean favorite) { this.favorite = favorite; }
    public void setCity(String city) { this.city = city; }
}
