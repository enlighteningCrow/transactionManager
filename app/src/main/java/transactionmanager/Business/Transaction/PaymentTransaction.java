package transactionmanager.Business.Transaction;

import java.io.IOException;
import java.sql.SQLException;

import transactionmanager.Business.Accounts.AccountManager;

public record PaymentTransaction(int accountId, double amount) implements Transaction {
    public String getType() {
        return "Payment";
    }

    @Override
    public void execute() throws ClassNotFoundException, SQLException, IOException {
        AccountManager.getInstance().getAccount(accountId).updateBalance(-amount);
    }
}