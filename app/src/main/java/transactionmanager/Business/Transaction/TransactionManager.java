package transactionmanager.Business.Transaction;

import java.io.IOException;
import java.sql.SQLException;

import transactionmanager.Business.Accounts.AccountCommandDecorator;
import transactionmanager.Business.PriceCalculation.NormalPriceStrategy;
import transactionmanager.Business.PriceCalculation.PriceCalculateStrategy;
import transactionmanager.Business.PriceCalculation.PromotionalPriceStrategy;
import transactionmanager.Business.Products.ProductError;
import transactionmanager.Business.Products.ProductsD;
import transactionmanager.Business.Products.ProductsD.ProductRecord;

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