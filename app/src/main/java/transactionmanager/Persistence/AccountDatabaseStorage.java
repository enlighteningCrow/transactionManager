
package transactionmanager.Persistence;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import transactionmanager.Business.Accounts.Account;
import transactionmanager.Business.Accounts.AccountCommandDecorator;
import transactionmanager.Business.Transaction.Transaction;

public class AccountDatabaseStorage {
    private final String url;

    public AccountDatabaseStorage(String filename) throws SQLException {
        this.url = "jdbc:h2:file:" + filename;
        String query = "CREATE TABLE IF NOT EXISTS accounts(id INTEGER PRIMARY KEY, balance DOUBLE PRECISION, command_history BINARY LARGE OBJECT);";
        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            statement.executeUpdate();
        }
    }

    public List<AccountCommandDecorator> load() throws SQLException, ClassNotFoundException, IOException {
        List<AccountCommandDecorator> accounts = new ArrayList<>();
        String query = "SELECT id, balance, command_history FROM accounts;";

        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query);
                ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                double balance = resultSet.getDouble("balance");
                List<Transaction> commandHistory = deserialize(resultSet.getBytes("command_history"));
                AccountCommandDecorator account = new AccountCommandDecorator(new Account(id, balance));
                account.setCommandHistory(commandHistory);
                accounts.add(account);
            }
        }
        return accounts;
    }

    public AccountCommandDecorator get(int id) throws SQLException, ClassNotFoundException, IOException {
        AccountCommandDecorator account = null;
        String query = "SELECT id, balance, command_history FROM accounts WHERE id = ?;";

        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query);) {
            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int idF = resultSet.getInt("id");
                    double balance = resultSet.getDouble("balance");
                    List<Transaction> commandHistory = deserialize(resultSet.getBytes("command_history"));
                    account = new AccountCommandDecorator(new Account(idF, balance));
                    account.setCommandHistory(commandHistory);
                }
            }
        }
        return account;

    }

    public void save(List<AccountCommandDecorator> accounts) throws SQLException, IOException {
        String query = "INSERT INTO accounts(id, balance, command_history) VALUES( ?, ?, ?)";

        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            for (AccountCommandDecorator account : accounts) {
                statement.setInt(1, account.getAccountId());
                statement.setDouble(2, account.getBalance());
                statement.setBytes(3, serialize(account.getCommandHistory()));
                statement.executeUpdate();
            }
        }
    }

    public void remove(List<Integer> accounts) throws SQLException {
        String query = "DELETE FROM accounts WHERE id=?";

        try (Connection connection = DriverManager.getConnection(url);
                PreparedStatement statement = connection.prepareStatement(query)) {
            for (Integer account : accounts) {
                statement.setInt(1, account);
                statement.executeUpdate();
            }
        }
    }

    private byte[] serialize(List<Transaction> transactions) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
        objectOutputStream.writeObject(transactions);
        objectOutputStream.close();
        return byteArrayOutputStream.toByteArray();
    }

    @SuppressWarnings("unchecked")
    private List<Transaction> deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        List<Transaction> transactions = (List<Transaction>) objectInputStream.readObject();
        objectInputStream.close();
        return transactions;
    }
}