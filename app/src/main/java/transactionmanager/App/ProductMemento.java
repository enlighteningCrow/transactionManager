package transactionmanager.App;

import java.util.HashMap;

import java.io.Serializable;

import transactionmanager.App.ProductsD.ProductRecord;

public class ProductMemento implements Serializable {
    private HashMap<String, ProductRecord> productsState;

    public ProductMemento(HashMap<String, ProductRecord> productsState) {
        this.productsState = new HashMap<>(productsState);
    }

    public HashMap<String, ProductRecord> getProductsState() {
        return productsState;
    }
}