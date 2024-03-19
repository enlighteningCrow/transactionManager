package transactionmanager.Persistent;

import java.io.*;

import transactionmanager.App.ProductsHistory;
import transactionmanager.App.ProductMemento;
import java.util.List;

public class ProductsFileStorage {
    private String filePath;

    public ProductsFileStorage(String filePath) {
        this.filePath = filePath;
    }

    public void saveProductsHistory(ProductsHistory productsHistory) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filePath))) {
            outputStream.writeObject(productsHistory.getProductMementoList());
        } catch (IOException e) {
            System.err.println("Error saving products history: " + e.getMessage());
        }
    }

    public List<ProductMemento> loadProductsHistory() {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<ProductMemento>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading products history: " + e.getMessage());
        }
        return null;
    }
}
