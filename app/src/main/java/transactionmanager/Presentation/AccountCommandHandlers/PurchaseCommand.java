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
import transactionmanager.App.Products.ProductsD;
import transactionmanager.App.Products.ProductsD.ProductRecord;
import transactionmanager.App.Transaction.InsufficientBalanceError;
import transactionmanager.App.Transaction.TransactionManager;
import transactionmanager.Presentation.CommandDto;
import transactionmanager.Presentation.CommandHandler;

public class PurchaseCommand extends CommandHandler {

    public PurchaseCommand() {
        super("Purchase");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandDto)
            throws ClassNotFoundException, SQLException, IOException, InsufficientBalanceError {
        AccountManager accountManager = AccountManager.getInstance();
        int accountID = commandDto.arguments().get("accountId").intValue();
        String productName = commandDto.arguments().get("productName").textValue();
        AccountCommandDecorator account = accountManager.getAccount(accountID);
        ProductsD productsD = ProductsD.getInstance();
        ProductRecord productRecord = productsD.getProduct(productName);
        if (account == null) {
            return getErrorNode("account with given id " + accountID + " does not exist\"");
        } else {
            // account.purchase(productRecord.price());
            new TransactionManager().pay(account, productName);
            return getSuccessNode();
        }
    }
}
