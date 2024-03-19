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
    // public static class StdinWrapper extends S
    public static void main(String[] args) {
        // if (true) {
        // JsonFactory fJsonFactory = new JsonFactory();
        // fJsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        // // HashMap<String, Object> maps
        // ObjectMapper mapper = new ObjectMapper();
        // ObjectNode node = mapper.createObjectNode();
        // ObjectNode n2 = mapper.createObjectNode();
        // n2.put("asdjiof", "z09uf");
        // JsonNode node = new JSONInt;
        // return;
        // }
        CommandHandler accountsHandler = null;
        try {
            accountsHandler = new AccountsCommandHandler();
        } catch (ClassNotFoundException | SQLException | IOException e) {
            System.err.println("Error: Cannot register the accounts handler.");
        }
        CommandHandler productsHandler = new ProductsCommandHandler();

        accountsHandler.setNextHandler(productsHandler);

        // JsonFactory
        JsonFactory fJsonFactory = new JsonFactory();
        fJsonFactory.configure(JsonParser.Feature.AUTO_CLOSE_SOURCE, false);
        // fJsonFactory.disable(JsonGenerator.Feature.AUTO_CLOSE_TARGET);

        while (true) {
            // ObjectMapper mapper = new ObjectMapper(fJsonFactory);

            // ObjectMapper mapper = new ObjectMapper();
            ObjectMapper mapper = new ObjectMapper(fJsonFactory);
            String response = "";
            // InputStream stream = System.in;
            try {
                // response = accountsHandler.handleCommand(input);
                // mapper.createParser(System.in);
                CommandDto commandDto = mapper.readValue(System.in, CommandDto.class);
                System.err.println("Processing: " + commandDto);
                response = accountsHandler.handleCommand(commandDto).toString();
            } catch (NullPointerException e) {
                System.out.println("{\"error\": \"Incomplete arguments passed\"}");
            } catch (JsonProcessingException e) {
                // e.printStackTrace();
                System.err.println("JsonProcessingException: " + e.getMessage());
                System.exit(0);
            } catch (IOException e) {
                System.err.println("IOException: " + e.getMessage());
                System.exit(0);
            }
            System.out.println(response);
        }
    }
}