package transactionmanager.Presentation.ProductsCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.Accounts.AccountCommandDecorator;
import transactionmanager.App.Accounts.AccountManager;
import transactionmanager.App.Products.ProductsD;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class UpdateCommand extends CommandHandler {

    public UpdateCommand() {
        super("UpdateItem");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        ProductsD productsD = ProductsD.getInstance();
        String product = commandDto.arguments().get("name").textValue();
        double price = commandDto.arguments().get("price").doubleValue();
        if (!productsD.containsProduct(product)) {
            return getErrorNode("product with given name \"" + product + "\" does not exist\"");
        } else {
            productsD.updateProduct(product, price);
            ObjectNode node;
            try {
                node = getSuccessNode();
                ObjectNode data = objectMapper.createObjectNode();
                data.put("price", price);
                node.set("data", data);
            } catch (Exception e) {
                node = getErrorNode("invalid arguments");
            }
            return node;
        }
    }
}
