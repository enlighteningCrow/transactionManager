package transactionmanager.Presentation.AccountCommandHandlers;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.Business.Accounts.Account;
import transactionmanager.Business.Accounts.AccountCommandDecorator;
import transactionmanager.Business.Accounts.AccountManager;
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

        return getSuccessNode();
    }

}
