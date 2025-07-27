package UserAuthentication.Swing;

import UserAuthentication.*;
import UserAuthentication.JSON.JSONUserDataHandler;

import java.io.FileNotFoundException;

/**This class ties up all the components together of the
 * login/signup feature.
 *
 */
public class SwingUserAuthenticator implements Runnable {
    SwingUserAuthenticationView view;
    JSONUserDataHandler dataHandler;
    LoginPresenter loginPresenter;
    SignupPresenter signupPresenter;
    LoginInteractor loginInteractor;
    SignupInteractor signupInteractor;
    SwingUserAuthenticationController controller;

    public SwingUserAuthenticator(){
        view = new SwingUserAuthenticationView();
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
        controller = new SwingUserAuthenticationController(view, loginInteractor,
                signupInteractor);

    }

    public void run(){
        this.view.display();

    }


}
