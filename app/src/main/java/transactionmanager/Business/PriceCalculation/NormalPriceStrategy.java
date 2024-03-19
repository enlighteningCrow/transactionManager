package transactionmanager.Business.PriceCalculation;

import transactionmanager.Business.Products.ProductsD;
import transactionmanager.Business.Products.ProductsD.ProductRecord;

public class NormalPriceStrategy implements PriceCalculateStrategy {

    @Override
    public double calculateCost(ProductRecord product) {
        return product.price();
    }

}
