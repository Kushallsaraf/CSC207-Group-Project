package Login;

import UserAuthentication.Constants;
import UserAuthentication.PasswordHasher;
import UserAuthentication.UserDataHandler;

import java.io.FileNotFoundException;
import java.util.Map;
import User.User;

public class LoginUseCase {
    private UserDataHandler handler;

    public LoginUseCase(UserDataHandler handler) throws FileNotFoundException {
        this.handler = handler;
    }

    public boolean login(String username, String password){
        Map<String, User> users = handler.getUsers();
        if (users.containsKey(username)){
            User user = users.get(username);
            return PasswordHasher.checkPassword(password, user.getHashedPassword());
        }
        return false;

    }
    public User getUser(String username) {
        Map<String, User> users = handler.getUsers();
        return users.get(username);
    }
}
