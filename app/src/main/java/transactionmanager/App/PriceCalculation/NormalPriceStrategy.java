package transactionmanager.App.PriceCalculation;

import transactionmanager.App.Products.ProductsD;
import transactionmanager.App.Products.ProductsD.ProductRecord;

public class NormalPriceStrategy implements PriceCalculateStrategy {

    @Override
    public double calculateCost(ProductRecord product) {
        return product.price();
    }

}
