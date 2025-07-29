package UserAuthentication.JavaFX;

import UserAuthentication.LoginInteractor;
import UserAuthentication.SignupInteractor;
import com.csc207.group.View.JavaFXUserAuthenticationView;
import com.csc207.group.View.ViewManager;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.io.IOException;

public class JavaFXUserAuthenticationController {

    private JavaFXUserAuthenticationView view;
    private LoginInteractor loginInteractor;
    private SignupInteractor signupInteractor;
    private ViewManager viewManager;

    public JavaFXUserAuthenticationController(JavaFXUserAuthenticationView view, LoginInteractor loginInteractor,
                                              SignupInteractor signupInteractor, ViewManager viewManager){
        this.view = view;
        this.loginInteractor = loginInteractor;
        this.signupInteractor = signupInteractor;
        this.viewManager = viewManager;
        connectLoginButton();
        connectSignupButton();
    }

    private void connectLoginButton(){
        this.view.getLoginButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                handleLogin();
            }
        });

    }

    private void handleLogin(){
        String username = this.view.getUsernameInput();
        String password = this.view.getPasswordInput();
        if (this.loginInteractor.handleLogin(username, password)){

            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    view.close();
                    viewManager.showHomepage(loginInteractor.getUser(username)); // Replace with your actual navigation method
                }
            });
            delay.play();

        }
    }

    private void connectSignupButton(){
        this.view.getSignupButton().setOnAction(new javafx.event.EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                handleSignup();
            }
        });

    }

    private void handleSignup(){
        String username = this.view.getUsernameInput();
        String password = this.view.getPasswordInput();
        try {
            this.signupInteractor.handleSignup(username, password);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
