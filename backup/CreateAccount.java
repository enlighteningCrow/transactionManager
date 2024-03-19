package transactionmanager.Presentation;

import transactionmanager.App.Account;
import transactionmanager.App.AccountCommandDecorator;
import transactionmanager.App.AccountManager;

import javax.swing.*;
import java.awt.event.*;

public class CreateAccount extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JSpinner spinner1;
    private JSpinner spinner2;

    public CreateAccount() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        int id = Integer.parseInt((String) spinner1.getValue());
                        double balance = Double.parseDouble((String) spinner2.getValue());
                        Account account = new Account(id, balance);

                        AccountCommandDecorator accountCommand = new AccountCommandDecorator(account);

                        // Add the Account instance to the AccountManager
                        AccountManager accountManager = AccountManager.getInstance();
                        accountManager.addAccount(accountCommand);
                    }
                };
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        CreateAccount dialog = new CreateAccount();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
