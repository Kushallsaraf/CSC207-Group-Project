package UserAuthentication;

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


    /** Makes the view visible
     *
     */
    public void display();

    /**Update the message label to let a user know the username
     * is already taken
     *
     */
    public void usernameAlreadyTakenError();

    /**Update the message label to let a user know the username
     * they entered is invalid
     *
     */
    public void invalidUsernameError();

    /**Update the message label to let a user know the password they
     * entered is invalid
     *
     */
    public void invalidPasswordError();


    /**Update the message label to let a user know both the password they
     * entered is invalid as well as the username.
     *
     */
    public void invalidInputError();


}
