package transactionmanager.Presentation;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.IOException;
import java.lang.NullPointerException;
import java.sql.SQLException;

import java.util.HashMap;

public class Main {
    public static void main(String[] args) {
        EventLogger logger = new EventLogger();
        CommandHandler accountsHandler = null;
        accountsHandler = new AccountsCommandHandler();
        CommandHandler productsHandler = new ProductsCommandHandler();

        accountsHandler.setNextHandler(productsHandler);
        CommandHandler promotionsHandler = new PromotionsCommandHandler();
        productsHandler.setNextHandler(promotionsHandler);

        CommandHandler exitHandler = new ExitCommandHandler();
        promotionsHandler.setNextHandler(exitHandler);

        JsonFactory fJsonFactory = new JsonFactory();
        fJsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);

        while (true) {

            ObjectMapper mapper = new ObjectMapper(fJsonFactory);
            ObjectNode response = null;
            try {
                CommandDto commandDto = mapper.readValue(System.in, CommandDto.class);
                response = accountsHandler.handleCommand(commandDto);
            } catch (NullPointerException e) {
                System.out.println("{\"error\": \"Incomplete arguments passed\"}");
            } catch (JsonProcessingException e) {
                System.err.println("JsonProcessingException: " + e.getMessage());
                System.exit(0);
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
                System.exit(0);
            }
            System.out.println(OutputMaker.build(response));
        }
    }
}