package transactionmanager.Presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.List;
import java.util.Arrays;

public class ProductsCommandHandler extends CommandHandler {
    public ProductsCommandHandler() {
        super("products");

    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandJson) throws JsonProcessingException {
        commandJson.command().remove(0);
        List<CommandHandler> handlers = Arrays.asList();
        for (int i = 1; i < handlers.size(); ++i) {
            handlers.get(i - 1).setNextHandler(handlers.get(i));
        }

        return handlers.get(0).handleCommand(commandJson);
    }

    @Override
    public boolean canHandle(CommandDto commandDto) {
        return commandDto.command().get(0).equals("products");
    }
}