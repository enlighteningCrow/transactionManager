package transactionmanager.Presentation;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.Business.Transaction.InsufficientBalanceError;

public class ExitCommandHandler extends CommandHandler {

    public ExitCommandHandler() {
        super("exit");
    }

    @Override
    public ObjectNode cHandleCommand(CommandDto commandJson) throws JsonProcessingException, ClassNotFoundException,
            SQLException, IOException, InsufficientBalanceError {
        System.exit(0);
        return null;
    }

}
