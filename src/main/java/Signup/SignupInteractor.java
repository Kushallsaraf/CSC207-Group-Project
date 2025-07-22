package Signup;

import UserAuthentication.UserDataHandler;

import java.io.FileNotFoundException;
import java.io.IOException;

public class SignupInteractor {
    private SignupUseCase useCase;


    public SignupInteractor(UserDataHandler handler) throws FileNotFoundException {

        useCase = new SignupUseCase(handler);



    }

    public String handleSignUp(String username, String password) throws IOException {
        return useCase.signUp(username, password);
    }
}
