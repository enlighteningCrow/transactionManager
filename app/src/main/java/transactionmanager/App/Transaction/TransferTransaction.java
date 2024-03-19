package transactionmanager.App.Transaction;

import java.io.IOException;
import java.sql.SQLException;

import transactionmanager.App.Accounts.AccountManager;

public record TransferTransaction(int fromId, int toId, double amount) implements Transaction {
    public String getType() {
        return "Transfer";
    }

    @Override
    public void execute() throws ClassNotFoundException, SQLException, IOException {
        AccountManager.getInstance().getAccount(fromId).updateBalance(-amount);
        AccountManager.getInstance().getAccount(toId).updateBalance(+amount);
    }
}