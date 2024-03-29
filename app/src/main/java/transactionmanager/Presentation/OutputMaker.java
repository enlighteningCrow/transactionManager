package transactionmanager.Presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class OutputMaker {
    public static ObjectMapper objectMapper = new ObjectMapper();

    public static ObjectNode build(ObjectNode node) {
        ObjectNode root = objectMapper.createObjectNode();
        root.set("response", node);
        ArrayNode events = root.putArray("events");
        for (String event : EventLogger.consumeEvents()) {
            events.add(event);
        }
        return root;
    }
}