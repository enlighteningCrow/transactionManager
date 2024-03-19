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
  private PromotionDatabaseStorage promotionFileStorage;

  public static final String accountsFile = "./data/accounts.dtb";

  private PromotionsManager(PromotionDatabaseStorage promotionFileStorage) {
    this.promotionFileStorage = promotionFileStorage;
  }

  public static PromotionsManager getInstance() throws SQLException {
    if (instance == null) {
      PromotionDatabaseStorage promotionFileStorage = new PromotionDatabaseStorage(accountsFile);
      instance = new PromotionsManager(promotionFileStorage);
    }
    return instance;
  }

  public void addPromotion(String product, double discount) throws IOException, SQLException {
    promotionFileStorage.addPromotion(product, discount);
  }

  public void updatePromotion(String product, double discount) throws IOException, SQLException {
    promotionFileStorage.updatePromotion(product, discount);
  }

  public void removePromotion(String product) throws IOException, SQLException {
    promotionFileStorage.removePromotion(product);
  }

  public Double getPromotion(String product) throws SQLException {
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
