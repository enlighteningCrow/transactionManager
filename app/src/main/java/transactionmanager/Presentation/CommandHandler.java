package transactionmanager.Presentation;

import java.io.IOException;
import java.sql.SQLException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import transactionmanager.App.InsufficientBalanceError;

public abstract class CommandHandler {
    protected CommandHandler nextHandler = null;
    protected CommandHandler delegateHandler = null;
    protected static ObjectMapper objectMapper = new ObjectMapper();
    protected static ResponseFactory responseFactory = new ResponseFactory();
    protected String commandName;

    public void setNextHandler(CommandHandler handler) {
        this.nextHandler = handler;
    }

    public abstract ObjectNode cHandleCommand(CommandDto commandJson)
            throws JsonProcessingException, ClassNotFoundException, SQLException, IOException, InsufficientBalanceError;

    public boolean canHandle(CommandDto commandDto) {
        return commandDto.command().get(0).equals(commandName);
    }

    public CommandHandler(String commandName) {
        this.commandName = commandName;
    }

    // public final
    public final ObjectNode handleCommand(CommandDto commandDto) throws JsonProcessingException {
        if (commandDto.command().size() > 0) {
            if (canHandle(commandDto)) {
                try {
                    commandDto.command().remove(0);
                    return cHandleCommand(commandDto);
                } catch (ClassNotFoundException | SQLException | IOException | InsufficientBalanceError e) {
                    System.err.println("Error: Cannot handle the command.");
                    System.err.println(e);
                }
            } else if (nextHandler != null) {
                return nextHandler.handleCommand(commandDto);
            }
        }
        return responseFactory.createErrorGenericResponse("Invalid Command");
    }

    public void setDelegateHandler(CommandHandler delegateHandler) {
        this.delegateHandler = delegateHandler;
    }

    public ObjectNode getSuccessNode() {
        return responseFactory.createSuccessResponse(commandName);
    }

    public ObjectNode getErrorNode(String errMesg) {
        return responseFactory.createErrorResponse(commandName, errMesg);
    }

    public ObjectNode getErrorGenericNode(String errMesg) {
        return responseFactory.createErrorGenericResponse(errMesg);
    }

}