package transactionmanager.App;

import java.util.List;
import java.util.ArrayList;

public class AccountStorage {
    private List<AccountCommandDecorator> accounts = new ArrayList<>();

    public void addAccount(AccountCommandDecorator account) {
        accounts.add(account);
    }

    public void removeAccount(AccountCommandDecorator account) {
        accounts.remove(account);
    }

    public void removeIndex(int i) {
        accounts.remove(i);
    }

    public List<AccountCommandDecorator> getAllAccounts() {
        return accounts;
    }

    public void setAccounts(List<AccountCommandDecorator> list) {
        this.accounts = list;
    }

    @Override
    public String toString() {
        return "AccountStorage [accounts=" + accounts + "]";
    }
}