package transactionmanager.App.Transaction;

import java.io.IOException;
import java.sql.SQLException;

import transactionmanager.App.Accounts.AccountCommandDecorator;
import transactionmanager.App.PriceCalculation.NormalPriceStrategy;
import transactionmanager.App.PriceCalculation.PriceCalculateStrategy;
import transactionmanager.App.PriceCalculation.PromotionalPriceStrategy;
import transactionmanager.App.Products.ProductError;
import transactionmanager.App.Products.ProductsD;
import transactionmanager.App.Products.ProductsD.ProductRecord;

// import transactionmanager.App.ProductsD.ProductRecord;

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
            // ProductRecord product = productsD.getProduct(productName);
            ProductRecord product = productsD.getProduct(productName);
            assert (product != null);
            if (product.onDiscount()) {
                account.purchase(promotionalStrategy.calculateCost(product));
            } else {
                account.purchase(normalStrategy.calculateCost(product));
            }
        } else {
            throw new ProductError(productName + " is not in the database.");
        }
    }
}