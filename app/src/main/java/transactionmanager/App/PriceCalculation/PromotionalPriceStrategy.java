package transactionmanager.App.PriceCalculation;

import transactionmanager.App.Products.ProductsD;
import transactionmanager.App.Products.ProductsD.ProductRecord;

public class PromotionalPriceStrategy implements PriceCalculateStrategy {

    @Override
    public double calculateCost(ProductRecord product) {
        return product.price() * (1.0 - product.discount());
    }

}
