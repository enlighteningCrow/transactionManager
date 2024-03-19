package transactionmanager.Business.Accounts;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import transactionmanager.Business.Observable;
import transactionmanager.Business.Transaction.InsufficientBalanceError;
import transactionmanager.Business.Transaction.PaymentTransaction;
import transactionmanager.Business.Transaction.TopUpTransaction;
import transactionmanager.Business.Transaction.Transaction;
import transactionmanager.Business.Transaction.TransferTransaction;

import transactionmanager.Presentation.EventLogger;

public class AccountCommandDecorator extends Account implements Observable {
    private Account account;
    private List<Transaction> commandHistory;

    public void setCommandHistory(List<Transaction> commandHistory) {
        this.commandHistory = commandHistory;
    }

    public AccountCommandDecorator(Account account) {
        this.account = account;
        this.commandHistory = new ArrayList<>();
        addObserver(new EventLogger());
    }

    @Override
    public int getAccountId() {
        return account.getAccountId();
    }

    @Override
    public double getBalance() {
        return account.getBalance();
    }

    @Override
    public void deposit(double amount) throws ClassNotFoundException, SQLException, IOException {
        notifyObservers("Depositing " + amount + " into account " + getAccountId());
        Transaction transaction = new TopUpTransaction(account.getAccountId(), amount);
        transaction.execute();
        commandHistory.add(transaction);
    }

    @Override
    public void purchase(double amount)
            throws InsufficientBalanceError, ClassNotFoundException, SQLException, IOException {
        if (amount > account.balance) {
            throw new InsufficientBalanceError();
        }
        notifyObservers("Purchasing with price " + amount + " from account " + getAccountId());
        Transaction transaction = new PaymentTransaction(account.getAccountId(), amount);
        transaction.execute();
        commandHistory.add(transaction);
    }

    @Override
    public void transfer(int recipientId, double amount)
            throws InsufficientBalanceError, ClassNotFoundException, SQLException, IOException {
        if (amount > account.balance) {
            throw new InsufficientBalanceError();
        }
        notifyObservers("Transferring " + amount + " from account " + getAccountId() + " to account " + recipientId);
        Transaction transaction = new TransferTransaction(account.getAccountId(), recipientId, amount);
        transaction.execute();
        commandHistory.add(transaction);
    }

    public List<Transaction> getCommandHistory() {
        return commandHistory;
    }

    @Override
    public String toString() {
        return "AccountCommandDecorator [account=" + account + ", commandHistory=" + commandHistory + "]";
    }
}