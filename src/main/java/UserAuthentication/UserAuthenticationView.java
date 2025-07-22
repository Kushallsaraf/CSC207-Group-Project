package UserAuthentication;


import javax.swing.*;

public class UserAuthenticationView {
    private final JTextField usernameField = new JTextField(10);
    private final JPasswordField passwordField = new JPasswordField(10);
    private final JButton loginButton = new JButton("Login");
    private final JButton signUpButton = new JButton("Sign Up");
    private final JPanel panel;

    public UserAuthenticationView(){


        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);

        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(signUpButton);



    }

    public JPasswordField getPasswordField(){
        return passwordField;
    }

    public JTextField getUsernameField(){
        return usernameField;
    }

    public JButton getLoginButton(){
        return loginButton;
    }

    public JButton getSignUpButton(){
        return signUpButton;
    }

    public JPanel getPanel(){
        return panel;
    }

}
