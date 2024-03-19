package transactionmanager.App;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import transactionmanager.Persistent.AccountDatabaseStorage;

import java.util.Arrays;

public class AccountManager {
  private static AccountManager instance;
  // private AccountStorage accountStorage;
  private AccountDatabaseStorage accountFileStorage;

  public static final String accountsFile = "./data/accounts.dtb";

  private AccountManager(AccountDatabaseStorage accountFileStorage) {
    // this.accountStorage = accountStorage;
    this.accountFileStorage = accountFileStorage;
  }

  public static AccountManager getInstance() throws SQLException, ClassNotFoundException, IOException {
    if (instance == null) {
      // AccountDatabaseStorage accountFileStorage = new
      // AccountDatabaseStorage("accounts.txt");
      AccountDatabaseStorage accountFileStorage = new AccountDatabaseStorage(accountsFile);
      // AccountStorage accountStorage = new AccountStorage();
      // try {
      // accountStorage.setAccounts(accountFileStorage.load());
      // } catch (SQLException e) {
      // accountStorage = new AccountStorage();
      // }
      instance = new AccountManager(accountFileStorage);
    }
    return instance;
  }

  public void addAccount(AccountCommandDecorator account) throws IOException, SQLException {
    // accountStorage.addAccount(new AccountCommandDecorator(account));
    // try {
    accountFileStorage.save(Arrays.asList(new AccountCommandDecorator(account)));
    // } catch (SQLException e) {
    // System.out.println("{\"error\": \"Failed to add account\"}");
    // System.err.println(e);
    // System.exit(-1);
    // }
  }

  public void removeAccount(AccountCommandDecorator account) throws IOException, SQLException {
    // accountStorage.removeAccount(account);
    // try {
    accountFileStorage.remove(Arrays.asList(account.getAccountId()));
    // } catch (SQLException e) {
    // System.out.println("{\"error\": \"Failed to remove account\"}");
    // System.exit(-1);
    // }
  }

  public void removeAccountId(int id) throws IOException, SQLException {
    // List<AccountCommandDecorator> accounts = accountStorage.getAllAccounts();
    // for (int i = accounts.size() - 1; i >= 0; --i) {
    // if (accounts.get(i).getAccountId() == id) {
    // accountStorage.removeIndex(i);
    // }
    // }
    accountFileStorage.remove(Arrays.asList(id));
    // try {
    // accountFileStorage.save(accountStorage.getAllAccounts());
    // } catch (SQLException e) {
    // System.out.println("{\"error\": \"Failed to remove account\"}");
    // System.exit(-1);
    // }
  }

  public AccountCommandDecorator getAccount(int id) throws ClassNotFoundException, SQLException, IOException {
    // for (AccountCommandDecorator acc : accountStorage.getAllAccounts()) {
    // if (acc.getAccountId() == id) {
    // return acc;
    // }
    // }
    // return null;
    return accountFileStorage.get(id);
  }

  public List<AccountCommandDecorator> getAccounts() throws ClassNotFoundException, SQLException, IOException {
    return accountFileStorage.load();
  }

  @Override
  public String toString() {
    return "AccountManager [accountFileStorage=" + accountFileStorage + "]";
  }
}
