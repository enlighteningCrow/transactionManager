package transactionmanager.Business.Products;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import transactionmanager.Persistence.PromotionDatabaseStorage;

import java.io.IOException;
import java.io.Serializable;

import java.sql.SQLException;

public class ProductsD {
    private static ProductsD instance = null;
    private PromotionDatabaseStorage promotionStorage = null;

    public static record ProductRecord(String name, double price, boolean onDiscount, double discount)
            implements Serializable {
    }

    public static ProductsD getInstance() throws SQLException {
        if (instance == null)
            instance = new ProductsD();
        return instance;
    }

    private HashMap<String, Double> products;

    private ProductsD() throws SQLException {
        this.products = new HashMap<>();
        this.promotionStorage = new PromotionDatabaseStorage("./data/promotions.dtb");
    }

    public ProductRecord getProduct(String productName) throws SQLException {
        PromotionsManager promotionsManager = PromotionsManager.getInstance();
        double price = products.get(productName);
        boolean onDiscount = promotionsManager.hasPromotion(productName);
        double discount = 0;
        if (onDiscount) {
            discount = promotionsManager.getPromotion(productName);
        }
        return new ProductRecord(productName, price, onDiscount, discount);
    }

    public void addProduct(String productName, double price) throws SQLException {
        products.put(productName, price);
        ProductsHistory.getInstance().addProductMemento(new ProductMemento(products));
    }

    public void updateProduct(String productName, double price) throws SQLException {
        products.put(productName, price);
        ProductsHistory.getInstance().addProductMemento(new ProductMemento(products));
    }

    public void removeProduct(String productName) throws SQLException {
        products.remove(productName);
        ProductsHistory.getInstance().addProductMemento(new ProductMemento(products));
    }

    public boolean containsProduct(String productName) {
        return products.containsKey(productName);
    }

    public HashMap<String, Double> getAllProducts() {
        return products;
    }

    public List<ProductRecord> getAllProductRecords() throws SQLException {
        // double price = products.get(productName);
        // boolean onDiscount = promotionStorage.promotionExists(productName);
        // double discount = 0;
        // if (onDiscount) {
        // discount = promotionStorage.getPromotion(productName);
        // }
        ArrayList<ProductRecord> productRecords = new ArrayList<>();
        for (var product : getAllProducts().entrySet()) {
            productRecords.add(getRecordFromProduct(product.getKey(), product.getValue()));
        }
        return productRecords;
        // return .stream().map(i -> {
        // try {
        // return getRecordFromProduct(i.getKey(), i.getValue());
        // } catch (SQLException e) {
        // System.exit(-1);
        // }
        // });
        // return ;
    }

    private ProductRecord getRecordFromProduct(String productName, double price) throws SQLException {
        // double price = products.get(productName);
        PromotionsManager promotionsManager = PromotionsManager.getInstance();
        boolean onDiscount = promotionsManager.hasPromotion(productName);
        // boolean onDiscount = promotionStorage.promotionExists(productName);
        double discount = 0;
        if (onDiscount) {
            discount = promotionsManager.getPromotion(productName);
        }
        return new ProductRecord(productName, price, onDiscount, discount);
    }

    public ProductMemento saveToMemento() {
        return new ProductMemento(products);
    }

    public void restoreFromMemento(ProductMemento memento) {
        products = new HashMap<>(memento.getProductsState());
    }

    public void addPromotion(String product, double discount) throws SQLException {
        promotionStorage.addPromotion(product, discount);
    }

    public void updatePromotion(String product, double discount) throws SQLException {
        promotionStorage.addPromotion(product, discount);
    }

    @Override
    public String toString() {
        return "ProductsD [products=" + products + "]";
    }
}