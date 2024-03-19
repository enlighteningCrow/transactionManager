package transactionmanager.Presentation.AccountCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.AccountCommandDecorator;
import transactionmanager.App.AccountManager;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class BalanceCommand extends CommandHandler {

    public BalanceCommand() {
        super("ViewAccountBalance");
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
                data.put("balance", account.getBalance());
                node.set("data", data);
            } catch (Exception e) {
                node = getErrorNode("invalid arguments");
            }
            return node;
        }
    }
}
