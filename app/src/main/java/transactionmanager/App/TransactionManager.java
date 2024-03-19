package transactionmanager.App;

import java.io.IOException;
import java.sql.SQLException;

import transactionmanager.App.ProductsD.ProductRecord;

public class TransactionManager {
    private PriceCalculateStrategy normalStrategy = new NormalPriceStrategy();
    private PriceCalculateStrategy promotionalStrategy = new PromotionalPriceStrategy();

    public void topUp(AccountCommandDecorator account, double amount)
            throws ClassNotFoundException, SQLException, IOException {
        account.deposit(amount);
    }

    public void pay(AccountCommandDecorator account, String productName)
            throws InsufficientBalanceError, ClassNotFoundException, SQLException, IOException {
        ProductsD productsD = ProductsD.getInstance();
        if (productsD.containsProduct(productName)) {
            ProductRecord product = productsD.getProduct(productName);
            assert (product != null);
            if (product.isOnPromotion()) {
                account.purchase(promotionalStrategy.calculateCost(product.getPrice()));
            } else {
                account.purchase(normalStrategy.calculateCost(product.getPrice()));
            }
        } else {
            throw new ProductError(productName + " is not in the database.");
        }
    }
}