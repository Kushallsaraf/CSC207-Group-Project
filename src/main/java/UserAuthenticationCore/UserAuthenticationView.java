package UserAuthenticationCore;

/**
 * Has methods to provide the controller with all relevant
 * information pertaining to user input.
 */
public interface UserAuthenticationView {

    /**
     *
     * @return a string of the username field input
     */
    public String getUsernameInput();


    /**
     *
     * @return a string of the password field input
     *
     */
    public String getPasswordInput();

    /** Lets the controller know the signup button has been pressed
     *
     */
    public void pressSignUp();

    /** Lets the controller know the login button has been pressed
     *
     */
    public void pressLogIn();

}
