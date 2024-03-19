package transactionmanager.Presentation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.Accounts.AccountCommandDecorator;
import transactionmanager.App.Accounts.AccountManager;
import transactionmanager.Presentation.AccountCommandHandlers.*;

import java.util.List;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;

public class AccountsCommandHandler extends CommandHandler {
    // private final AccountManager accountManager;

    public AccountsCommandHandler() throws ClassNotFoundException, SQLException, IOException {
        super("accounts");
        List<CommandHandler> handlers = Arrays.asList(new BalanceCommand(), new CreateCommand(), new ListCommand(),
                new RemoveCommand(), new ShowCommand(), new TopUpCommand(), new TransferCommand());
        for (int i = 1; i < handlers.size(); ++i) {
            handlers.get(i - 1).setNextHandler(handlers.get(i));
        }
        setDelegateHandler(handlers.get(0));
        // this.accountManager = AccountManager.getInstance();
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws JsonProcessingException {
        throw new RuntimeException("Reached unreachable accounts handler");
        // return handlers.get(0).handleCommand(commandDto);
    }
}