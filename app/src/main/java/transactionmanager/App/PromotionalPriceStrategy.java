package transactionmanager.App;

public class PromotionalPriceStrategy implements PriceCalculateStrategy {

    @Override
    public double calculateCost(double fullPrice) {
        return fullPrice * 0.8;
    }

}
