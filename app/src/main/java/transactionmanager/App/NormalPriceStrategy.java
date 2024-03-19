package transactionmanager.App;

public class NormalPriceStrategy implements PriceCalculateStrategy {

    @Override
    public double calculateCost(double fullPrice) {
        return fullPrice;
    }

}
