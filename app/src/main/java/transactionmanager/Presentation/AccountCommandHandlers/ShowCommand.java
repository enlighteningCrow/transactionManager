package transactionmanager.Presentation.AccountCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.Accounts.Account;
import transactionmanager.App.Accounts.AccountCommandDecorator;
import transactionmanager.App.Accounts.AccountManager;
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
        // double balanceToAdd = commandDto.arguments().get("balance").doubleValue();
        // TODO: maybe have to check for exception if already exists.
        AccountCommandDecorator account = accountManager.getAccount(accountId);
        if (account == null) {
            // return ("{\"error\": \"account with given id " + accountId + " does not
            // exist\"}");
            return getErrorNode("account with given id " + accountId + " does not exist\"");
        } else {
            // System.out.println("Balance: " + account.getBalance());
            // new ObjectMapper()
            // var mapper = new ObjectMapper()
            // .enable(SerializationFeature.INDENT_OUTPUT);

            // String json;
            ObjectNode node;
            try {
                // account.getCommandHistory();
                // json = mapper.writeValueAsString(account.getCommandHistory());
                node = getSuccessNode();
                ObjectNode data = objectMapper.createObjectNode();
                data.putPOJO("history", account.getCommandHistory());
                node.set("data", data);
                // .set("history", account.getCommandHistory());
            } catch (Exception e) {
                // json = "{\"error\": Invalid arguments}";
                node = getErrorNode("invalid arguments");
            }
            return node;
        }
    }
}
