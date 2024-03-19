package transactionmanager.Presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.Presentation.PromotionsCommandHandlers.AddCommand;
import transactionmanager.Presentation.PromotionsCommandHandlers.ListCommand;
import transactionmanager.Presentation.PromotionsCommandHandlers.RemoveCommand;
import transactionmanager.Presentation.PromotionsCommandHandlers.UpdateCommand;

import java.util.List;
import java.util.Arrays;

public class PromotionsCommandHandler extends CommandHandler {
    public PromotionsCommandHandler() {
        super("promotions");

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