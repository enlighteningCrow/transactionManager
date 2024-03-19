package transactionmanager.Business.Products;

import java.util.HashMap;

import java.io.Serializable;

public class ProductMemento implements Serializable {
    private HashMap<String, Double> productsState;

    public ProductMemento(HashMap<String, Double> productsState) {
        this.productsState = new HashMap<>(productsState);
    }

    public HashMap<String, Double> getProductsState() {
        return productsState;
    }
}