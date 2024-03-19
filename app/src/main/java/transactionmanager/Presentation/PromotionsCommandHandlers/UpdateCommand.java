package transactionmanager.Presentation.PromotionsCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.Accounts.AccountCommandDecorator;
import transactionmanager.App.Accounts.AccountManager;
import transactionmanager.App.Products.ProductsD;
import transactionmanager.App.Products.PromotionsManager;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class UpdateCommand extends CommandHandler {

    public UpdateCommand() {
        super("UpdateItem");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        PromotionsManager promotionsManager = PromotionsManager.getInstance();
        String product = commandDto.arguments().get("name").textValue();
        double discount = commandDto.arguments().get("discount").doubleValue();
        if (!promotionsManager.hasPromotion(product)) {
            return getErrorNode("promotion on product with given name \"" + product + "\" does not exist\"");
        } else {
            PromotionsManager.getInstance().addPromotion(product, discount);
            return getSuccessNode();
        }
    }
}
