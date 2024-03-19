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
import transactionmanager.App.InsufficientBalanceError;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class TransferCommand extends CommandHandler {

    public TransferCommand() {
        super("Transfer");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto)
            throws ClassNotFoundException, SQLException, IOException, InsufficientBalanceError {
        AccountManager accountManager = AccountManager.getInstance();
        int fromID = commandDto.arguments().get("fromID").intValue();
        int toID = commandDto.arguments().get("toID").intValue();
        double amount = commandDto.arguments().get("amount").doubleValue();
        AccountCommandDecorator accountFrom = accountManager.getAccount(fromID);
        AccountCommandDecorator accountTo = accountManager.getAccount(toID);
        if (accountFrom == null) {
            return getErrorNode("account with given id " + fromID + " does not exist\"");
        } else if (accountTo == null) {
            return getErrorNode("account with given id " + toID + " does not exist\"");
        } else {
            accountFrom.transfer(accountTo.getAccountId(), amount);
            return getSuccessNode();
        }
    }
}
