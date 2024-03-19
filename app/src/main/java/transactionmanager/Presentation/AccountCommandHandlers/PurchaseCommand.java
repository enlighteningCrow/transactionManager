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
import transactionmanager.Business.Products.ProductsD;
import transactionmanager.Business.Products.ProductsD.ProductRecord;
import transactionmanager.Business.Transaction.InsufficientBalanceError;
import transactionmanager.Business.Transaction.TransactionManager;
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
        // ProductsD productsD = ProductsD.getInstance();
        // ProductRecord productRecord = productsD.getProduct(productName);
        if (account == null) {
            return getErrorNode("account with given id " + accountID + " does not exist\"");
        } else {
            // account.purchase(productRecord.price());
            new TransactionManager().pay(account, productName);
            return getSuccessNode();
        }
    }
}
