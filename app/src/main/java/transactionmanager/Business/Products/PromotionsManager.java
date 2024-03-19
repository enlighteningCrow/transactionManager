package transactionmanager.Business.Products;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import transactionmanager.Persistence.AccountDatabaseStorage;
import transactionmanager.Persistence.PromotionDatabaseStorage;

import java.util.Arrays;
import java.util.HashMap;

public class PromotionsManager {
  private static PromotionsManager instance;
  // private AccountStorage accountStorage;
  private PromotionDatabaseStorage promotionFileStorage;

  public static final String accountsFile = "./data/accounts.dtb";

  private PromotionsManager(PromotionDatabaseStorage promotionFileStorage) {
    // this.accountStorage = accountStorage;
    this.promotionFileStorage = promotionFileStorage;
  }

  public static PromotionsManager getInstance() throws SQLException {
    if (instance == null) {
      // AccountDatabaseStorage accountFileStorage = new
      // AccountDatabaseStorage("accounts.txt");
      PromotionDatabaseStorage promotionFileStorage = new PromotionDatabaseStorage(accountsFile);
      // AccountStorage accountStorage = new AccountStorage();
      // try {
      // accountStorage.setAccounts(accountFileStorage.load());
      // } catch (SQLException e) {
      // accountStorage = new AccountStorage();
      // }
      instance = new PromotionsManager(promotionFileStorage);
    }
    return instance;
  }

  public void addPromotion(String product, double discount) throws IOException, SQLException {
    // accountStorage.addAccount(new AccountCommandDecorator(account));
    // try {
    promotionFileStorage.addPromotion(product, discount);
    // } catch (SQLException e) {
    // System.out.println("{\"error\": \"Failed to add account\"}");
    // System.err.println(e);
    // System.exit(-1);
    // }
  }

  public void updatePromotion(String product, double discount) throws IOException, SQLException {
    // accountStorage.addAccount(new AccountCommandDecorator(account));
    // try {
    promotionFileStorage.updatePromotion(product, discount);
    // } catch (SQLException e) {
    // System.out.println("{\"error\": \"Failed to add account\"}");
    // System.err.println(e);
    // System.exit(-1);
    // }
  }

  public void removePromotion(String product) throws IOException, SQLException {
    // accountStorage.removeAccount(account);
    // try {
    // promotionFileStorage.remove(Arrays.asList(account.getAccountId()));
    promotionFileStorage.removePromotion(product);
    // } catch (SQLException e) {
    // System.out.println("{\"error\": \"Failed to remove account\"}");
    // System.exit(-1);
    // }
  }

  public Double getPromotion(String product) throws SQLException {
    // for (AccountCommandDecorator acc : accountStorage.getAllAccounts()) {
    // if (acc.getAccountId() == id) {
    // return acc;
    // }
    // }
    // return null;
    // return promotionFileStorage.get(id);
    return promotionFileStorage.getPromotion(product);
  }

  public HashMap<String, Double> getPromotions() throws ClassNotFoundException, SQLException, IOException {
    return promotionFileStorage.getAllPromotions();
  }

  public boolean hasPromotion(String productName) throws SQLException {
    return promotionFileStorage.promotionExists(productName);
  }

  @Override
  public String toString() {
    return "AccountManager [accountFileStorage=" + promotionFileStorage + "]";
  }
}
