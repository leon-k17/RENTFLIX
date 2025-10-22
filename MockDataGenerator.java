
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MockDataGenerator {
    private static final Random random = new Random();
    
    public static List<Property> generatePropertiesForLocation(String city, String country, int count) {
        List<Property> properties = new ArrayList<>();
        
        String[] propertyTypes = {"Studio", "1BHK", "2BHK", "3BHK", "Shared Room", "PG"};
        String[] amenities = {
            "Fully furnished with WiFi",
            "Near metro station",
            "24/7 security and CCTV",
            "Attached bathroom",
            "Common kitchen and dining",
            "Gym and recreational area",
            "Pet-friendly accommodation",
            "Study room available",
            "Laundry facilities included",
            "Parking available"
        };
        
        String[] streetPrefixes = {"MG Road", "College Street", "University Avenue", 
                                   "Park Lane", "Main Street", "Station Road", 
                                   "Campus Drive", "Scholar Way", "Academic Circle"};
        
        boolean isIndia = country.equalsIgnoreCase("India");
        
        for (int i = 0; i < count; i++) {
            String propertyType = propertyTypes[random.nextInt(propertyTypes.length)];
            String title = propertyType + " near " + city + " University";
            
            String address = streetPrefixes[random.nextInt(streetPrefixes.length)] + 
                           ", " + city;
            
            double price;
            if (isIndia) {
                price = 4000 + (random.nextInt(16000)); // ₹4000-20000
            } else {
                price = 300 + (random.nextInt(700)); // $300-1000
            }
            
            int bedrooms = propertyType.contains("Studio") ? 0 : 
                          propertyType.contains("1BHK") ? 1 :
                          propertyType.contains("2BHK") ? 2 :
                          propertyType.contains("3BHK") ? 3 : 1;
            
            int bathrooms = bedrooms == 0 ? 1 : bedrooms;
            
            String description = "Perfect student accommodation in " + city + ". " +
                               amenities[random.nextInt(amenities.length)] + ". " +
                               amenities[random.nextInt(amenities.length)] + ".";
            
            Property property = new Property(
                i + 1,
                title,
                address,
                price,
                bedrooms,
                bathrooms,
                description
            );
            
            String[] landlordNames = {"Rajesh Kumar", "Priya Singh", "Amit Sharma", 
                                     "Anita Desai", "Vikram Patel", "Sneha Gupta"};
            property.setLandlordName(landlordNames[random.nextInt(landlordNames.length)]);
            
            properties.add(property);
        }
        
        return properties;
    }
}

