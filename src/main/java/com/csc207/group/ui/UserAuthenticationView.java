package ui;

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

    /**Update the message label
     *
     */
    public void updateMessageView(String message);


    /**Closes the view
     *
     */
    public void close();


    public void clearUsernameAndPasswordFields();

    public void clearPasswordField();
}
