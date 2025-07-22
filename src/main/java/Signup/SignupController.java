package Signup;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Controller class for the sign-up screen.
 * <p>
 * Handles user input from the GUI and triggers the sign-up logic via the SignUpInteractor.
 * Displays success or error messages based on the result of the registration attempt.
 */
public class SignupController {
    private final UserAuthenticationView view;
    private final SignupInteractor interactor;


    public SignupController(SignupInteractor interactor){
        this.view = new UserAuthenticationView();
        this.interactor = interactor;
        connectSignupButton();
        launchUI();

    }

    private void connectSignupButton(){
        view.getSignUpButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = view.getUsernameField().getText();
                String password = new String(view.getPasswordField().getPassword());
                String resultMessage = interactor.handleSignUp(username, password);
            }
        });

    }
    private void launchUI() {
        JFrame frame = new JFrame("Sign Up");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(view.getPanel());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }


}
