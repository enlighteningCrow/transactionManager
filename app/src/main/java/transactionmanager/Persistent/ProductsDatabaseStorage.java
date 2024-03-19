package transactionmanager.Persistent;

import transactionmanager.App.Products.ProductMemento;
import transactionmanager.App.Products.ProductsHistory;

import java.util.List;
import java.util.ArrayList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductsDatabaseStorage {
    private final String url;

    public ProductsDatabaseStorage(String filename) throws SQLException {
        this.url = "jdbc:h2:file:" + filename;
        String query = "CREATE TABLE IF NOT EXISTS products(mementoID int NOT NULL AUTO_INCREMENT, memento JAVA_OBJECT);";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public void saveProductsHistory(List<ProductMemento> productMementos) throws SQLException {
        String query = "INSERT INTO products (memento) VALUES (?);";

        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            for (ProductMemento memento : productMementos) {
                statement.setObject(1, memento);
                statement.executeUpdate();
            }
        }
    }

    public List<ProductMemento> loadProductsHistory() throws SQLException {
        List<ProductMemento> productMementos = new ArrayList<>();
        String query = "SELECT memento FROM products ORDER BY mementoID;";

        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                ProductMemento memento = (ProductMemento) resultSet.getObject("memento");
                productMementos.add(memento);
            }
        }
        return productMementos;
    }
}