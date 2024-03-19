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

public class ShowCommand extends CommandHandler {

    public ShowCommand() {
        super("ViewTransactionHistory");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        AccountManager accountManager = AccountManager.getInstance();
        int accountId = commandDto.arguments().get("id").intValue();
        AccountCommandDecorator account = accountManager.getAccount(accountId);
        if (account == null) {
            return getErrorNode("account with given id " + accountId + " does not exist\"");
        } else {

            ObjectNode node;
            try {
                node = getSuccessNode();
                ObjectNode data = objectMapper.createObjectNode();
                data.putPOJO("history", account.getCommandHistory());
                node.set("data", data);
            } catch (Exception e) {
                node = getErrorNode("invalid arguments");
            }
            return node;
        }
    }
}
