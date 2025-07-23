package UserAuthentication;

/**Stores user data from data source and writes data to data source.
 *
 */
public interface UserDataHandler {

    /**Checks if a username is already taken
     *
     * @param username
     * @return
     */
    public boolean usernameExists(String username);

    public void registerUser(String username, String hashedPassword);


}
