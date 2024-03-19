package transactionmanager.Business.PriceCalculation;

import transactionmanager.Business.Products.ProductsD;
import transactionmanager.Business.Products.ProductsD.ProductRecord;

public interface PriceCalculateStrategy {
    public double calculateCost(ProductRecord product);
}
