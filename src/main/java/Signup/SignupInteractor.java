package Signup;

public class SignupInteractor {
    private SignupUseCase useCase;
    private String username;
    private String password;

    public SignupInteractor(String username, String password){
        this.username = username;
        this.password = password;
        useCase = new SignupUseCase();



    }

    public String handleSignUp(String username, String password){
        return null;
    }
}
