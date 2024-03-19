package transactionmanager.App.PriceCalculation;

import transactionmanager.App.Products.ProductsD;
import transactionmanager.App.Products.ProductsD.ProductRecord;

public interface PriceCalculateStrategy {
    public double calculateCost(ProductRecord product);
}
