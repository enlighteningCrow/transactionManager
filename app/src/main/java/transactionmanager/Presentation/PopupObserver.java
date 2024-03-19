package transactionmanager.Presentation;

import javax.swing.*;

import transactionmanager.App.Observer;

public class PopupObserver implements Observer {
    @Override
    public void notify(String message) {
        // Create and display a Swing popup with the provided message
        JOptionPane.showMessageDialog(null, message, "Notification", JOptionPane.INFORMATION_MESSAGE);
    }
}