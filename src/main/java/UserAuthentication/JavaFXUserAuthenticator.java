package UserAuthentication;

import UserAuthentication.controllers.JavaFXUserAuthenticationController;
import UserAuthentication.controllers.JavaFXUserAuthenticationView;
import com.csc207.group.View.ViewManager;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class JavaFXUserAuthenticator implements Runnable{

    private JavaFXUserAuthenticationView view;
    private JavaFXUserAuthenticationController controller;
    private UserDataHandler dataHandler;
    private LoginPresenter loginPresenter;
    private LoginInteractor loginInteractor;
    private SignupInteractor signupInteractor;
    private SignupPresenter signupPresenter;
    private ViewManager viewManager;


    public JavaFXUserAuthenticator(Stage stage){
        view = new JavaFXUserAuthenticationView(stage);
        viewManager = new ViewManager(stage, view);


        dataHandler = null;
        try {
            dataHandler = new JSONUserDataHandler(Constants.FILE_PATH);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        loginPresenter = new LoginPresenter(view);
        signupPresenter = new SignupPresenter(view);
        loginInteractor = new LoginInteractor(dataHandler, loginPresenter);
        signupInteractor = new SignupInteractor(dataHandler, signupPresenter);
        controller = new JavaFXUserAuthenticationController(view, loginInteractor,
                signupInteractor, viewManager);
    }



    @Override
    public void run() {
        viewManager.showLoginView();
    }
}
