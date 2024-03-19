package transactionmanager.App;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;

public interface Transaction extends Serializable {
    void execute() throws ClassNotFoundException, SQLException, IOException;

    String getType();
}
// int getAccountId();

// double getAmount();
