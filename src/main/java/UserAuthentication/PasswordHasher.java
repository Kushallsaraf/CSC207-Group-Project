package UserAuthentication;

/**Will hash passwords and check if user input matches hashed password
 *
 */
public interface PasswordHasher {

    public void hashPassword(String password);

    public boolean checkPassword(String password, String hashedPassword);






}
