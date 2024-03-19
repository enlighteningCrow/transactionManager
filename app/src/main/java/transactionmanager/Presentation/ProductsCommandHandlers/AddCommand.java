package transactionmanager.Presentation.ProductsCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.Accounts.AccountCommandDecorator;
import transactionmanager.App.Accounts.AccountManager;
import transactionmanager.App.Products.ProductsD;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class AddCommand extends CommandHandler {

    public AddCommand() {
        super("AddItem");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        ProductsD productsD = ProductsD.getInstance();
        String product = commandDto.arguments().get("name").textValue();
        double price = commandDto.arguments().get("price").doubleValue();
        if (productsD.containsProduct(product)) {
            return getErrorNode("product with given name \"" + product + "\" already exists\"");
        } else {
            productsD.addProduct(product, price);
            return getSuccessNode();
        }
    }
}
