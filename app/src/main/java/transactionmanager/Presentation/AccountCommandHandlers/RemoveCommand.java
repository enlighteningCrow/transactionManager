package transactionmanager.Presentation.AccountCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.Business.Accounts.Account;
import transactionmanager.Business.Accounts.AccountCommandDecorator;
import transactionmanager.Business.Accounts.AccountManager;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class RemoveCommand extends CommandHandler {

    public RemoveCommand() {
        super("RemoveAccount");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        AccountManager accountManager = AccountManager.getInstance();
        int accountIdToAdd = commandDto.arguments().get("id").intValue();
        accountManager.removeAccountId(accountIdToAdd);
        return getSuccessNode();
    }

}
