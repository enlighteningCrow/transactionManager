
package transactionmanager.Business.Products;

import java.util.ArrayList;
import java.util.List;

import transactionmanager.Persistence.ProductsDatabaseStorage;

import java.io.Serializable;

import java.util.Arrays;
import java.sql.SQLException;

public class ProductsHistory implements Serializable {
  private static ProductsHistory instance;
  private ProductsDatabaseStorage fileStorage;

  public static final String productsFile = "./data/products.dtb";

  private ProductsHistory() throws SQLException {
    fileStorage = new ProductsDatabaseStorage(productsFile);
  }

  public static ProductsHistory getInstance() throws SQLException {
    if (instance == null) {
      instance = new ProductsHistory();
    }
    return instance;
  }

  public void addProductMemento(ProductMemento productMemento) throws SQLException {
    fileStorage.saveProductsHistory(Arrays.asList(productMemento));
  }

  public List<ProductMemento> getProductMementoList() throws SQLException {
    return fileStorage.loadProductsHistory();
  }

}
