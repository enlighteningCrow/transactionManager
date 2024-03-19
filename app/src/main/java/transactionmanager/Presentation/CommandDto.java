package transactionmanager.Presentation;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;

public record CommandDto(ArrayList<String> command, JsonNode arguments) {
}