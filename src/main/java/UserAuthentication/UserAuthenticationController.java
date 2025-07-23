package UserAuthentication;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserAuthenticationController {

    private SwingUserAuthenticationView view;
    private SignupInteractor signupInteractor;
    private LoginInteractor loginInteractor;

    public UserAuthenticationController(SwingUserAuthenticationView view, LoginInteractor loginInteractor,
                                        SignupInteractor signupInteractor){
        this.loginInteractor = loginInteractor;
        this.signupInteractor = signupInteractor;
        this.view = view;

    }

    private void connectLoginButton(){
        this.view.getLoginButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loginInteractor.handleLogin(view.getUsernameInput(), view.getPasswordInput());
            }
        });



    }

    private void connectSignupButton(){
        this.view.getSignupButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                signupInteractor.handleSignup(view.getUsernameInput(), view.getPasswordInput());
            }
        });
    }


}
