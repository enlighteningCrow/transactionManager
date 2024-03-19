package transactionmanager.Business.Accounts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import transactionmanager.Persistence.AccountDatabaseStorage;

import java.util.Arrays;

public class AccountManager {
  private static AccountManager instance;
  private AccountDatabaseStorage accountFileStorage;

  public static final String accountsFile = "./data/accounts.dtb";

  private AccountManager(AccountDatabaseStorage accountFileStorage) {
    this.accountFileStorage = accountFileStorage;
  }

  public static AccountManager getInstance() throws SQLException, ClassNotFoundException, IOException {
    if (instance == null) {
      AccountDatabaseStorage accountFileStorage = new AccountDatabaseStorage(accountsFile);
      instance = new AccountManager(accountFileStorage);
    }
    return instance;
  }

  public void addAccount(AccountCommandDecorator account) throws IOException, SQLException {
    accountFileStorage.save(Arrays.asList(new AccountCommandDecorator(account)));
  }

  public void removeAccount(AccountCommandDecorator account) throws IOException, SQLException {
    accountFileStorage.remove(Arrays.asList(account.getAccountId()));
  }

  public void removeAccountId(int id) throws IOException, SQLException {
    accountFileStorage.remove(Arrays.asList(id));
  }

  public AccountCommandDecorator getAccount(int id) throws ClassNotFoundException, SQLException, IOException {
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
