package transactionmanager.Presentation.ProductsCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.Business.Accounts.AccountCommandDecorator;
import transactionmanager.Business.Accounts.AccountManager;
import transactionmanager.Business.Products.ProductsD;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class RemoveCommand extends CommandHandler {

    public RemoveCommand() {
        super("RemoveItem");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        ProductsD productsD = ProductsD.getInstance();
        String product = commandDto.arguments().get("name").textValue();
        if (!productsD.containsProduct(product)) {
            return getErrorNode("product with given name \"" + product + "\" does not exist\"");
        } else {
            productsD.removeProduct(product);
            return getSuccessNode();
        }
    }
}
