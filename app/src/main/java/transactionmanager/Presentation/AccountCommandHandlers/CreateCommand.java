package transactionmanager.Presentation.AccountCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.Account;
import transactionmanager.App.AccountCommandDecorator;
import transactionmanager.App.AccountManager;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class CreateCommand extends CommandHandler {

    public CreateCommand() {
        super("CreateAccount");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        AccountManager accountManager = AccountManager.getInstance();
        int accountIdToAdd = commandDto.arguments().get("id").intValue();
        double balanceToAdd = commandDto.arguments().get("balance").doubleValue();
        accountManager.addAccount(new AccountCommandDecorator(new Account(accountIdToAdd, balanceToAdd)));
        // TODO: maybe have to check for exception if already exists.

        // return objectMapper.createObjectNode();
        return getSuccessNode();
    }

}
