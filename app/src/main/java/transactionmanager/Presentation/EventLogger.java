package transactionmanager.Presentation;

import java.util.ArrayList;

import transactionmanager.Business.Observer;

public class EventLogger implements Observer {
    static ArrayList<String> events = new ArrayList<>();

    @Override
    public void notify(String message) {
        events.add(message);
    }

    public static ArrayList<String> consumeEvents() {
        ArrayList<String> old = events;
        events = new ArrayList<>();
        return old;
    }
}