package transactionmanager.Presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.Presentation.ProductsCommandHandlers.AddCommand;
import transactionmanager.Presentation.ProductsCommandHandlers.ListCommand;
import transactionmanager.Presentation.ProductsCommandHandlers.RemoveCommand;
import transactionmanager.Presentation.ProductsCommandHandlers.UpdateCommand;

import java.util.List;
import java.util.Arrays;

public class ProductsCommandHandler extends CommandHandler {
    public ProductsCommandHandler() {
        super("products");

        List<CommandHandler> handlers = Arrays.asList(new AddCommand(), new ListCommand(), new RemoveCommand(),
                new UpdateCommand());
        for (int i = 1; i < handlers.size(); ++i) {
            handlers.get(i - 1).setNextHandler(handlers.get(i));
        }
        setDelegateHandler(handlers.get(0));
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandJson) throws JsonProcessingException {
        // commandJson.command().remove(0);
        // return handlers.get(0).handleCommand(commandJson);
        throw new RuntimeException("Reached unreachable products handler");
    }
}