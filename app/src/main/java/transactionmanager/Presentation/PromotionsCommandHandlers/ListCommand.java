package transactionmanager.Presentation.PromotionsCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.Accounts.AccountCommandDecorator;
import transactionmanager.App.Accounts.AccountManager;
import transactionmanager.App.Products.ProductsD;
import transactionmanager.App.Products.PromotionsManager;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class ListCommand extends CommandHandler {

    public ListCommand() {
        super("ListPromotions");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        PromotionsManager promotionsManager = PromotionsManager.getInstance();
        // String product = commandDto.arguments().get("name").textValue();
        ObjectNode node = objectMapper.createObjectNode();
        try {
            node = getSuccessNode();
            ObjectNode data = objectMapper.createObjectNode();
            ArrayNode array = data.putArray("products");
            for (var promotion : promotionsManager.getPromotions().entrySet()) {
                ObjectNode obj = array.addObject();
                obj.put("name", promotion.getKey()).put("discount", promotion.getValue());
            }
            node.set("data", data);
        } catch (Exception e) {
            node = getErrorNode("invalid arguments");
        }
        // if (!promotionsManager.hasPromotion(product)) {
        // return getErrorNode("promotion on product with given name \"" + product + "\"
        // does not exist\"");
        // } else {
        // PromotionsManager.getInstance().removePromotion(product);
        // return getSuccessNode();
        // }
        return node;
    }
}
