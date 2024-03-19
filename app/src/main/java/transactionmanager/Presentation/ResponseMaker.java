package transactionmanager.Presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class ResponseMaker {
    protected ObjectMapper objectMapper = new ObjectMapper();

    public ObjectNode createSuccessResponse(String transactionType) {
        return objectMapper.createObjectNode()
                .put("status", "success")
                .put("message", String.format("Transaction of type \"%s\" completed successfully", transactionType))
                .set("data", objectMapper.createObjectNode());
    }

    public ObjectNode createSuccessNDResponse(String transactionType) {
        return objectMapper.createObjectNode()
                .put("status", "success")
                .put("message", String.format("Transaction of type \"%s\" completed successfully", transactionType));
    }

    public ObjectNode createErrorResponse(String transactionType, String errMesg) {
        return objectMapper.createObjectNode()
                .put("status", "error")
                .put("message", String.format("Transaction of type \"%s\" failed. %s", transactionType, errMesg))
                .set("data", objectMapper.createObjectNode());
    }

    public ObjectNode createErrorGenericResponse(String message) {
        return objectMapper.createObjectNode()
                .put("status", "error")
                .put("message", message)
                .set("data", objectMapper.createObjectNode());
    }
}
