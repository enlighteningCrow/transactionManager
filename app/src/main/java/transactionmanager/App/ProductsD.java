package transactionmanager.App;

import java.util.HashMap;
import java.io.Serializable;

import java.sql.SQLException;

public class ProductsD {
    private static ProductsD instance = null;

    public static ProductsD getInstance() {
        if (instance == null)
            instance = new ProductsD();
        return instance;
    }

    public static class ProductRecord implements Serializable {
        private double price;

        public ProductRecord(double price) {
            this.price = price;
        }

        public double getPrice() {
            return price;
        }

        @Override
        public String toString() {
            return "ProductRecord [price=" + price + "]";
        }
    }

    private HashMap<String, ProductRecord> products;

    private ProductsD() {
        this.products = new HashMap<>();
    }

    public ProductRecord getProduct(String productName) {
        return products.get(productName);
    }

    public void addProduct(String productName, double price) throws SQLException {
        products.put(productName, new ProductRecord(price));
        ProductsHistory.getInstance().addProductMemento(new ProductMemento(products));
    }

    public void removeProduct(String productName) throws SQLException {
        products.remove(productName);
        ProductsHistory.getInstance().addProductMemento(new ProductMemento(products));
    }

    public boolean containsProduct(String productName) {
        return products.containsKey(productName);
    }

    public HashMap<String, ProductRecord> getAllProducts() {
        return products;
    }

    public ProductMemento saveToMemento() {
        return new ProductMemento(products);
    }

    public void restoreFromMemento(ProductMemento memento) {
        this.products = new HashMap<>(memento.getProductsState());
    }

    @Override
    public String toString() {
        return "ProductsD [products=" + products + "]";
    }
}