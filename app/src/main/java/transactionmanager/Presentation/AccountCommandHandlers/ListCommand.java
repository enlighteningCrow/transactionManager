package transactionmanager.Presentation.AccountCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.Business.Accounts.Account;
import transactionmanager.Business.Accounts.AccountCommandDecorator;
import transactionmanager.Business.Accounts.AccountManager;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class ListCommand extends CommandHandler {

    public ListCommand() {
        super("ListAccounts");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto) throws ClassNotFoundException, SQLException, IOException {
        AccountManager accountManager = AccountManager.getInstance();
        var mapper = new ObjectMapper()
                .enable(SerializationFeature.INDENT_OUTPUT);

        ObjectNode node;
        try {
            node = getSuccessNode();
            ObjectNode data = objectMapper.createObjectNode();
            ArrayNode accounts = data.putArray("accounts");
            for (AccountCommandDecorator account : accountManager.getAccounts()) {
                accounts = accounts.addPOJO(account);
            }
            node.set("data", data);
        } catch (Exception e) {
            node = getErrorNode("invalid arguments.");
        }
        return node;
    }

}
