package transactionmanager.App;

import java.io.IOException;
import java.sql.SQLException;

// public class PaymentTransaction implements Transaction {
//     private int accountId;
//     private double amount;

//     public PaymentTransaction(int accountId, double amount) {
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
//         account.updateBalance(-amount);
//     }

//     @Override
//     public String toString() {
//         return "PaymentTransaction [accountId=" + accountId + ", amount=" + amount + "]";
//     }
// }

record PaymentTransaction(int accountId, double amount) implements Transaction {
    public String getType() {
        return "Payment";
    }

    @Override
    public void execute() throws ClassNotFoundException, SQLException, IOException {
        AccountManager.getInstance().getAccount(accountId).updateBalance(-amount);
    }
}