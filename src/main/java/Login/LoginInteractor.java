package Login;

import java.io.FileNotFoundException;
import User.User;
import UserAuthentication.UserDataHandler;

public class LoginInteractor {
    private LoginUseCase useCase;

    public LoginInteractor(UserDataHandler handler) throws FileNotFoundException {
        this.useCase = new LoginUseCase(handler);

    }

    public boolean handleLogin(String username, String password){
        return useCase.login(username, password);
    }

    public User getUser(String username){
        return useCase.getUser(username);
    }
}
