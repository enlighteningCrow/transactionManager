package transactionmanager.Presentation.PromotionsCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.Business.Accounts.AccountCommandDecorator;
import transactionmanager.Business.Accounts.AccountManager;
import transactionmanager.Business.Products.ProductsD;
import transactionmanager.Business.Products.PromotionsManager;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class RemoveCommand extends CommandHandler {

    public RemoveCommand() {
        super("RemovePromotion");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        PromotionsManager promotionsManager = PromotionsManager.getInstance();
        String product = commandDto.arguments().get("name").textValue();
        if (!promotionsManager.hasPromotion(product)) {
            return getErrorNode("promotion on product with given name \"" + product + "\" does not exist\"");
        } else {
            PromotionsManager.getInstance().removePromotion(product);
            return getSuccessNode();
        }
    }
}
