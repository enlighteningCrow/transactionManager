
package transactionmanager.App;

import java.util.ArrayList;
import java.util.List;

import transactionmanager.Persistent.ProductsDatabaseStorage;

import java.io.Serializable;

import java.util.Arrays;
import java.sql.SQLException;

public class ProductsHistory implements Serializable {
  private static ProductsHistory instance;
  // private List<ProductMemento> productMementoList;
  private ProductsDatabaseStorage fileStorage;

  public static final String productsFile = "./data/products.dtb";

  private ProductsHistory() throws SQLException {
    // productMementoList = new ArrayList<>();
    // fileStorage = new ProductsDatabaseStorage("products_history.ser");
    fileStorage = new ProductsDatabaseStorage(productsFile);
    // loadHistoryFromFile();
  }

  public static ProductsHistory getInstance() throws SQLException {
    if (instance == null) {
      instance = new ProductsHistory();
    }
    return instance;
  }

  public void addProductMemento(ProductMemento productMemento) throws SQLException {
    // productMementoList.add(productMemento);
    fileStorage.saveProductsHistory(Arrays.asList(productMemento));
  }

  public List<ProductMemento> getProductMementoList() throws SQLException {
    // return productMementoList;
    return fileStorage.loadProductsHistory();
  }

  // private void saveHistoryToFile() {
  // fileStorage.saveProductsHistory(this);
  // }

  // private void loadHistoryFromFile() throws SQLException {
  // List<ProductMemento> loadedHistory = fileStorage.loadProductsHistory();
  // if (loadedHistory != null) {
  // this.productMementoList = loadedHistory;
  // }
  // if (productMementoList.size() > 0) {
  // ProductsD.getInstance().restoreFromMemento(productMementoList.get(productMementoList.size()
  // - 1));
  // }
  // }
}
