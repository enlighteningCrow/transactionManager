package transactionmanager.App.Accounts;

import java.io.IOException;
import java.sql.SQLException;

import transactionmanager.App.Transaction.InsufficientBalanceError;

public class Account {
    protected int accountId;
    protected String name;
    protected double balance;

    public Account() {
        accountId = 0;
        balance = 0.0;
    }

    public Account(int accountId, double balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    public int getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) throws ClassNotFoundException, SQLException, IOException {
        balance += amount;
    }

    public void purchase(double amount)
            throws InsufficientBalanceError, ClassNotFoundException, SQLException, IOException {
        if (amount > balance) {
            throw new InsufficientBalanceError();
        }
        balance -= amount;
    }

    public void transfer(int accountId, double amount)
            throws InsufficientBalanceError, ClassNotFoundException, SQLException, IOException {
        if (amount > balance) {
            throw new InsufficientBalanceError();
        }
        balance -= amount;
        AccountManager.getInstance().getAccount(accountId).balance += amount;
    }

    public void updateBalance(double amount) {
        balance += amount;
    }

    @Override
    public String toString() {
        return "Account [accountId=" + accountId + ", balance=" + balance + "]";
    }
}