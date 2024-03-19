package transactionmanager.Presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.AccountCommandDecorator;
import transactionmanager.App.AccountManager;
import transactionmanager.Presentation.AccountCommandHandlers.*;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class AccountsCommandHandler extends CommandHandler {
    // private final AccountManager accountManager;

    public AccountsCommandHandler() throws ClassNotFoundException, SQLException, IOException {
        super("accounts");
        // this.accountManager = AccountManager.getInstance();
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws JsonProcessingException {
        // ObjectMapper mapper = new ObjectMapper();
        // CommandDto commandDto = mapper.readValue(commandJson, CommandDto.class);

        // String action = commandDto.command().toLowerCase();
        // String action = commandDto.command().get(0);
        // String action = commandDto.command().remove(0);
        commandDto.command().remove(0);
        List<CommandHandler> handlers = Arrays.asList(new CreateCommand(), new ShowCommand());
        for (int i = 1; i < handlers.size(); ++i) {
            handlers.get(i - 1).setNextHandler(handlers.get(i));
        }

        return handlers.get(0).handleCommand(commandDto);
    }

    @Override
    public boolean canHandle(CommandDto commandDto) {
        return commandDto.command().get(0).equals("accounts");
    }
}