package UserAuthentication.controllers;

import UserAuthentication.LoginInteractor;
import UserAuthentication.SignupInteractor;
import UserAuthentication.Swing.MainApp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class SwingUserAuthenticationController {

    private SwingUserAuthenticationView view;
    private SignupInteractor signupInteractor;
    private LoginInteractor loginInteractor;


    public SwingUserAuthenticationController(SwingUserAuthenticationView view, LoginInteractor loginInteractor,
                                             SignupInteractor signupInteractor){
        this.loginInteractor = loginInteractor;
        this.signupInteractor = signupInteractor;
        this.view = view;
        connectLoginButton();
        connectSignupButton();



    }


    private void connectLoginButton(){
        this.view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLogin();

            }


        });



    }
    private void handleLogin(){
        String username = view.getUsernameInput();
        if (loginInteractor.handleLogin(view.getUsernameInput(), view.getPasswordInput())){

            this.view.close();
            MainApp mainApp = new MainApp(loginInteractor.getUser(username));
            mainApp.launch();


        };
    }

    private void connectSignupButton(){
        this.view.getSignupButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    signupInteractor.handleSignup(view.getUsernameInput(), view.getPasswordInput());
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }


}
