package transactionmanager.Persistent;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import transactionmanager.App.Transaction;

public class TransactionDatabaseStorage {
    private final String url;

    public TransactionDatabaseStorage(String filename) throws SQLException {
        this.url = "jdbc:h2:file:" + filename;
        String query = "CREATE TABLE IF NOT EXISTS transactions(transactionID INT AUTO_INCREMENT PRIMARY KEY, transactionType VARCHAR(255));";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public void save(Transaction transaction) throws SQLException {
        String query = "INSERT INTO transactions(transactionType) VALUES(?);";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, transaction.getType());
            statement.executeUpdate();
        }
    }

    public List<Transaction> getTransactionsForAccount(int accountId) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        String query = "SELECT transactionType FROM transactions JOIN account_transactions ON transactions.transactionID = account_transactions.transactionID WHERE account_transactions.accountID = ? ORDER BY ;";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, accountId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String transactionType = resultSet.getString("transactionType");
                    switch  (transactionType) {
                        case "TopUp":
                        
                }
            }
        }
        return transactions;
    }
}