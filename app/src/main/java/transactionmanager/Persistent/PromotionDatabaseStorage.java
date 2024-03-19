package transactionmanager.Persistent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PromotionDatabaseStorage {
    private final String url;

    public PromotionDatabaseStorage(String filename) throws SQLException {
        this.url = "jdbc:h2:file:" + filename;
        String query = "CREATE TABLE IF NOT EXISTS promotions(productName VARCHAR(255) PRIMARY KEY, promotion DOUBLE PRECISION CHECK (promotion >= 0.0 AND promotion < 1.0));";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public double getPromotion(String productName) throws SQLException {
        double promotion = 0.0;
        String query = "SELECT promotion FROM promotions WHERE productName = ?;";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    promotion = resultSet.getDouble("promotion");
                }
            }
        }
        return promotion;
    }

    public HashMap<String, Double> getAllPromotions() throws SQLException {
        HashMap<String, Double> map = new HashMap<>();
        // double promotion = 0.0;
        String query = "SELECT (productName, promotion) FROM promotions;";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            // statement.setString(1, productName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    Double promotion = resultSet.getDouble("promotion");
                    String product = resultSet.getString("productName");
                    map.put(product, promotion);
                }
            }
        }
        return map;
    }

    public void addPromotion(String productName, double promotion) throws SQLException {
        String query = "INSERT INTO promotions(productName, promotion) VALUES(?, ?)";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productName);
            statement.setDouble(2, promotion);
            statement.executeUpdate();
        }
    }

    public void removePromotion(String productName) throws SQLException {
        String query = "DELETE FROM promotions WHERE productName = ?";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productName);
            statement.executeUpdate();
        }
    }

    public boolean promotionExists(String productName) throws SQLException {
        String query = "SELECT COUNT(*) AS count FROM promotions WHERE productName = ?";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, productName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt("count");
                    return count > 0;
                }
            }
        }
        return false;
    }

    public void updatePromotion(String productName, double newPromotion) throws SQLException {
        String query = "UPDATE promotions SET promotion = ? WHERE productName = ?";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setDouble(1, newPromotion);
            statement.setString(2, productName);
            statement.executeUpdate();
        }
    }
}