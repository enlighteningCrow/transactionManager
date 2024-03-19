package transactionmanager.Presentation.AccountCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.Accounts.Account;
import transactionmanager.App.Accounts.AccountCommandDecorator;
import transactionmanager.App.Accounts.AccountManager;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class ListCommand extends CommandHandler {

    public ListCommand() {
        super("ListAccounts");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        AccountManager accountManager = AccountManager.getInstance();
        // System.out.println("Balance: " + account.getBalance());
        // new ObjectMapper()
        var mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);

        ObjectNode node;
        try {
            node = getSuccessNode();
            // .json = mapper.writeValueAsString(accountManager.getAccount(0));
            ObjectNode data = objectMapper.createObjectNode();
            // data.putPOJO("accounts", accountManager.getAccounts());
            ArrayNode accounts = data.putArray("accounts");
            for (AccountCommandDecorator account : accountManager.getAccounts()) {
                accounts = accounts.addPOJO(account);
            }
            node.set("data", data);
        } catch (Exception e) {
            // json = "{\"error\": Invalid arguments}";
            node = getErrorNode("invalid arguments.");
        }
        // return json;
        return node;
    }

}
