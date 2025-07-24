package UserAuthentication;

import Login.LoginInteractor;
import Signup.SignupInteractor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import User.User;

/**
 * Controller class for the sign-up screen.
 * <p>
 * Handles user input from the GUI and triggers the sign-up logic via the SignUpInteractor.
 * Displays success or error messages based on the result of the registration attempt.
 */
public class UserAuthenticationController {
    private final UserAuthenticationView view;
    private final SignupInteractor signupInteractor;
    private final LoginInteractor loginInteractor;
    private User user;


    public UserAuthenticationController(SignupInteractor signupInteractor, LoginInteractor loginInteractor){
        this.view = new UserAuthenticationView();
        this.signupInteractor = signupInteractor;
        this.loginInteractor = loginInteractor;
        this.user = new User("","");
        connectSignupButton();
        connectLoginButton();
        launchUI();

    }

    private void connectLoginButton() {
        view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = view.getUsernameField().getText();
                String password = new String(view.getPasswordField().getPassword());
                if(loginInteractor.handleLogin(username, password)){
                    setUser(username);


                }

            }
        });
    }

    private void connectSignupButton(){
        view.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = view.getUsernameField().getText();
                String password = new String(view.getPasswordField().getPassword());
                try {
                    String resultMessage = signupInteractor.handleSignUp(username, password);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

    }

    private void launchUI() {
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(view.getPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private User getUser(String username){
        return loginInteractor.getUser(username);
    }

    private void setUser(String username){
        this.user = getUser(username);
        user.logIn();
    }

    public User sendUser(){
        return this.user;
    }




}
