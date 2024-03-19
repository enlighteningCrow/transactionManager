package transactionmanager.App;

import java.io.IOException;
import java.sql.SQLException;

// public class TopUpTransaction implements Transaction {
//     private int accountId;
//     private double amount;

//     public TopUpTransaction(int accountId, double amount) {
//         this.accountId = accountId;
//         this.amount = amount;
//     }

//     @Override
//     public int getAccountId() {
//         return accountId;
//     }

//     @Override
//     public double getAmount() {
//         return amount;
//     }

//     @Override
//     public void execute(AccountCommandDecorator account) {
//         account.updateBalance(amount);
//     }

//     @Override
//     public String toString() {
//         return "TopUpTransaction [accountId=" + accountId + ", amount=" + amount + "]";
//     }

// }

record TopUpTransaction(int accountId, double amount) implements Transaction {
    public String getType() {
        return "TopUp";
    }

    @Override
    public void execute() throws ClassNotFoundException, SQLException, IOException {
        AccountManager.getInstance().getAccount(accountId).updateBalance(amount);
    }
}