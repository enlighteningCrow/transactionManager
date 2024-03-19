package transactionmanager.App.Products;

public class ProductError extends Error {

    private String message;

    public ProductError(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message;
    }

}