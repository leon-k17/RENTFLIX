import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// IMPORTANT: Set your MySQL password below!
public class DatabaseManager {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/rentflix_db?useSSL=false";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "K@ush1kk"; // CHANGE this to your actual MySQL password

    private Connection connection;

    public DatabaseManager() {
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
        
        // Add this print to verify the URL:
        System.out.println("Connecting to database with URL: " + DB_URL);
        
        System.out.println("✅ Database connected!");
    } catch (Exception e) {
        e.printStackTrace();
    }
}


    // ========== USER AUTHENTICATION ==========

    public int loginUser(String email, String password) {
    try {
        email = email.trim();
        password = password.trim();
                System.out.println("INPUT EMAIL: [" + email + "]");
        System.out.println("INPUT PASS: [" + password + "] (" + password.length() + ")");


        String sql = "SELECT id, email, password FROM users WHERE email = ?";

        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, email);
        ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
            String dbPassword = rs.getString("password");
            int id = rs.getInt("id");

            System.out.println("DB EMAIL: [" + rs.getString("email") + "]");
            System.out.println("DB PASSWORD: [" + dbPassword + "] (" + dbPassword.length() + ")");


            if (password.equals(dbPassword)) {
                rs.close();
                stmt.close();
                return id;
            }
        }
        rs.close();
        stmt.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return -1;
}



    public boolean registerUser(String email, String password, String fullName, String phone, String type) {
    try {
        String sql = "INSERT INTO users (email, password, full_name, phone, user_type) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setString(2, password);
        stmt.setString(3, fullName);
        stmt.setString(4, phone);
        stmt.setString(5, type);
        stmt.executeUpdate();
        stmt.close();
        return true;
    } catch (SQLException e) {
        System.err.println("Registration failed: " + e.getMessage());
        return false;
    }
}


    public String getUserName(int id) {
        try {
            String sql = "SELECT full_name FROM users WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String name = rs.getString("full_name");
                rs.close();
                stmt.close();
                return name;
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return "User";
    }

    // ========== PROPERTY METHODS ==========

    public List<Property> getAllProperties() {
        List<Property> props = new ArrayList<>();
        try {
            String sql = "SELECT * FROM properties";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Property p = new Property(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("address"),
                    rs.getDouble("price"),
                    rs.getInt("bedrooms"),
                    rs.getInt("bathrooms"),
                    rs.getString("description")
                );
                p.setImageUrl(rs.getString("image_url"));
                props.add(p);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) { e.printStackTrace(); }
        return props;
    }

    public boolean addProperty(Property p, int landlordId) {
        try {
            String sql = "INSERT INTO properties (title, address, city, price, bedrooms, bathrooms, description, image_url, landlord_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, p.getTitle());
            stmt.setString(2, p.getAddress());
            stmt.setString(3, p.getCity());
            stmt.setDouble(4, p.getPrice());
            stmt.setInt(5, p.getBedrooms());
            stmt.setInt(6, p.getBathrooms());
            stmt.setString(7, p.getDescription());
            stmt.setString(8, p.getImageUrl());
            stmt.setInt(9, landlordId);
            stmt.executeUpdate();
            stmt.close();
            return true;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }

    // ========== FAVORITES METHODS ==========

    public boolean addFavorite(int userId, int propertyId) {
        try {
            String sql = "INSERT INTO favorites (user_id, property_id) VALUES (?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, propertyId);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding favorite: " + e.getMessage());
            return false;
        }
    }

    public boolean removeFavorite(int userId, int propertyId) {
        try {
            String sql = "DELETE FROM favorites WHERE user_id = ? AND property_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, propertyId);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error removing favorite: " + e.getMessage());
            return false;
        }
    }

    public List<Property> getUserFavorites(int userId) {
        List<Property> favorites = new ArrayList<>();
        try {
            String sql = "SELECT p.* FROM properties p " +
                    "JOIN favorites f ON p.id = f.property_id " +
                    "WHERE f.user_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, userId);

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Property p = new Property(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getString("address"),
                    rs.getDouble("price"),
                    rs.getInt("bedrooms"),
                    rs.getInt("bathrooms"),
                    rs.getString("description")
                );
                p.setImageUrl(rs.getString("image_url"));
                favorites.add(p);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error fetching favorites: " + e.getMessage());
        }
        return favorites;
    }

    // ========== COMMUNITY (POSTS & REPLIES) ==========

    public boolean createPost(String title, String content, int authorId) {
        try {
            String sql = "INSERT INTO community_posts (title, content, author_id) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, title);
            pstmt.setString(2, content);
            pstmt.setInt(3, authorId);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error creating post: " + e.getMessage());
            return false;
        }
    }

    public List<CommunityPost> getAllPosts() {
        List<CommunityPost> posts = new ArrayList<>();
        try {
            String sql = "SELECT cp.*, u.full_name FROM community_posts cp " +
                    "JOIN users u ON cp.author_id = u.id " +
                    "ORDER BY cp.created_at DESC";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                CommunityPost post = new CommunityPost(
                    rs.getString("title"),
                    rs.getString("content"),
                    rs.getString("full_name"),
                    rs.getTimestamp("created_at").toString() // can format to "Just now" if needed
                );
                posts.add(post);
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.err.println("Error fetching posts: " + e.getMessage());
        }
        return posts;
    }

    public boolean addReply(int postId, int userId, String text) {
        try {
            String sql = "INSERT INTO post_replies (post_id, user_id, reply_text) VALUES (?, ?, ?)";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, postId);
            pstmt.setInt(2, userId);
            pstmt.setString(3, text);
            pstmt.executeUpdate();
            pstmt.close();
            return true;
        } catch (SQLException e) {
            System.err.println("Error adding reply: " + e.getMessage());
            return false;
        }
    }

    public List<String> getReplies(int postId) {
        List<String> replies = new ArrayList<>();
        try {
            String sql = "SELECT pr.reply_text, u.full_name FROM post_replies pr " +
                    "JOIN users u ON pr.user_id = u.id WHERE pr.post_id = ?";
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, postId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                String reply = rs.getString("full_name") + ": " + rs.getString("reply_text");
                replies.add(reply);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.err.println("Error fetching replies: " + e.getMessage());
        }
        return replies;
    }

    public void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("✅ Connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing DB: " + e.getMessage());
        }
    }
}
