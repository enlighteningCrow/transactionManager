package transactionmanager.Presentation.ProductsCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.Business.Accounts.AccountCommandDecorator;
import transactionmanager.Business.Accounts.AccountManager;
import transactionmanager.Business.Products.ProductsD;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class ListCommand extends CommandHandler {

    public ListCommand() {
        super("ListItems");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        ProductsD productsD = ProductsD.getInstance();
        productsD.getAllProducts();
        ObjectNode node;
        try {
            node = getSuccessNode();
            ObjectNode data = objectMapper.createObjectNode();
            ArrayNode array = data.putArray("products");
            for (var product : productsD.getAllProductRecords()) {
                array = array.addPOJO(product);
            }
            node.set("data", data);
        } catch (Exception e) {
            node = getErrorNode("invalid arguments");
        }
        return node;
    }
}
