package transactionmanager.Business.PriceCalculation;

import transactionmanager.Business.Products.ProductsD;
import transactionmanager.Business.Products.ProductsD.ProductRecord;

public class PromotionalPriceStrategy implements PriceCalculateStrategy {

    @Override
    public double calculateCost(ProductRecord product) {
        return product.price() * (1.0 - product.discount());
    }

}
