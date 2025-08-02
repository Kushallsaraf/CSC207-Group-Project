package UserAuthentication.JavaFX;

import Cache.FirebaseRestClient;
import Firebase.FirebaseUserDataHandler;
import UserAuthentication.*;
import com.csc207.group.View.JavaFXUserAuthenticationView;
import com.csc207.group.MainApp.GameCentralController;
import javafx.stage.Stage;

public class JavaFXUserAuthenticator{

    private JavaFXUserAuthenticationView view;
    private JavaFXUserAuthenticationController controller;
    private UserDataHandler dataHandler;
    private LoginPresenter loginPresenter;
    private LoginInteractor loginInteractor;
    private SignupInteractor signupInteractor;
    private SignupPresenter signupPresenter;
    private GameCentralController gameCentralController;


    public JavaFXUserAuthenticator(Stage stage, GameCentralController gameCentralController){
        view = new JavaFXUserAuthenticationView(stage);
        this.gameCentralController = gameCentralController;
        gameCentralController.setUserAuthenticationView(view);


        FirebaseRestClient firebaseRestClient = new FirebaseRestClient();
        dataHandler = new FirebaseUserDataHandler(firebaseRestClient);
        loginPresenter = new LoginPresenter(view);
        signupPresenter = new SignupPresenter(view);
        loginInteractor = new LoginInteractor(dataHandler, loginPresenter);
        signupInteractor = new SignupInteractor(dataHandler, signupPresenter);
        controller = new JavaFXUserAuthenticationController(view, loginInteractor,
                signupInteractor, gameCentralController);
    }




    public void run() {
        gameCentralController.showUserAuthenticationView();
    }

}
