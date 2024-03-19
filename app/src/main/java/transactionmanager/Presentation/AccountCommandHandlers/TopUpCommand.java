package transactionmanager.Presentation.AccountCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.Account;
import transactionmanager.App.AccountCommandDecorator;
import transactionmanager.App.AccountManager;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class TopUpCommand extends CommandHandler {

    public TopUpCommand() {
        super("TopUp");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        AccountManager accountManager = AccountManager.getInstance();
        int accountId = commandDto.arguments().get("id").intValue();
        double amount = commandDto.arguments().get("amount").doubleValue();
        AccountCommandDecorator account = accountManager.getAccount(accountId);
        if (account == null) {
            return getErrorNode("account with given id " + accountId + " does not exist\"");
        } else {
            account.deposit(amount);
            return getSuccessNode();
        }
    }
}
