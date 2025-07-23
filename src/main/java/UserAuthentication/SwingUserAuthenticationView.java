package UserAuthentication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SwingUserAuthenticationView implements UserAuthenticationView{
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel messageLabel;
    private JButton loginButton;
    private JButton signupButton;
    public SwingUserAuthenticationView(){
        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        messageLabel = new JLabel();


        usernameField = new JTextField();
        passwordField = new JPasswordField();

        JPanel usernamePanel = new JPanel();
        JPanel passwordPanel = new JPanel();
        JPanel buttonPanel = new JPanel();
        JPanel messagePanel = new JPanel();

        signupButton = new JButton("Signup");
        loginButton = new JButton("Login");

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);

        buttonPanel.add(signupButton);
        buttonPanel.add(loginButton);

        messagePanel.add(messageLabel);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(usernamePanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(messagePanel);
        mainPanel.add(buttonPanel);

        frame = new JFrame("GameCentral Login");
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(false);





    }

    public JButton getLoginButton(){
        return this.loginButton;
    }

    public JButton getSignupButton(){
        return this.signupButton;
    }




    @Override
    public String getUsernameInput() {
        return usernameField.getText();
    }

    @Override
    public String getPasswordInput() {
        return new String(passwordField.getPassword());
    }

    @Override
    public void display() {
        frame.setVisible(true);

    }

    @Override
    public void usernameAlreadyTakenError() {
        messageLabel.setText("The username you entered is already taken");

        timedMessage();


    }
    private void timedMessage(){
        Timer timer = new Timer(5000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                messageLabel.setText("");
            }
        });

        timer.setRepeats(false); // Make sure it only runs once
        timer.start();
    }
    @Override
    public void invalidUsernameError() {
        messageLabel.setText("Invalid: username must be between 1-20 characters long");

        timedMessage();

    }

    @Override
    public void invalidPasswordError() {
        messageLabel.setText("Invalid: password must be 10-20 characters long, no spaces in between");

        timedMessage();

    }

    @Override
    public void invalidInputError() {
        messageLabel.setText("Invalid: username must be between 1-20 characters long; password must be 10-20 characters" +
                        " long. No spaces between characters."
                );

        timedMessage();

    }
}
