package transactionmanager.Persistent;

import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

import transactionmanager.App.Account;
import transactionmanager.App.AccountCommandDecorator;

public class AccountFileStorage {
  private final String filename;

  public AccountFileStorage(String filename) {
    this.filename = filename;
  }

  public List<AccountCommandDecorator> load() throws IOException {
    List<AccountCommandDecorator> accounts = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        String name = parts[0];
        int id = Integer.parseInt(name);
        double balance = Double.parseDouble(parts[1]);

        AccountCommandDecorator account = new AccountCommandDecorator(new Account(id, balance));
        accounts.add(account);
      }
    }
    return accounts;
  }

  public void save(List<AccountCommandDecorator> accounts) {
    try (FileWriter fileWriter = new FileWriter(filename)) {
      for (AccountCommandDecorator account : accounts) {
        fileWriter.write(account.getAccountId() + "," + account.getBalance());
        fileWriter.write("\n");
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String toString() {
    return "AccountFileStorage [filename=" + filename + "]";
  }
}
