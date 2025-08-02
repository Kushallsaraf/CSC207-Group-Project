package UserAuthentication.JavaFX;

import Cache.FirebaseRestClient;
import Firebase.FirebaseUserDataHandler;
import UserAuthentication.*;
import com.csc207.group.View.JavaFXUserAuthenticationView;
import com.csc207.group.View.ViewManager;
import javafx.stage.Stage;

public class JavaFXUserAuthenticator{

    private JavaFXUserAuthenticationView view;
    private JavaFXUserAuthenticationController controller;
    private UserDataHandler dataHandler;
    private LoginPresenter loginPresenter;
    private LoginInteractor loginInteractor;
    private SignupInteractor signupInteractor;
    private SignupPresenter signupPresenter;
    private ViewManager viewManager;


    public JavaFXUserAuthenticator(Stage stage, ViewManager viewManager){
        view = new JavaFXUserAuthenticationView(stage);
        this.viewManager = viewManager;
        viewManager.setUserAuthenticationView(view);


        FirebaseRestClient firebaseRestClient = new FirebaseRestClient();
        dataHandler = new FirebaseUserDataHandler(firebaseRestClient);
        loginPresenter = new LoginPresenter(view);
        signupPresenter = new SignupPresenter(view);
        loginInteractor = new LoginInteractor(dataHandler, loginPresenter);
        signupInteractor = new SignupInteractor(dataHandler, signupPresenter);
        controller = new JavaFXUserAuthenticationController(view, loginInteractor,
                signupInteractor, viewManager);
    }




    public void run() {
        viewManager.showUserAuthenticationView();
    }

}
