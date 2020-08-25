package telran.ashkelon2020.forum.exceptions;

public class UserNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException(String login) {
		super("User with login = " + login + " not found");
	}
}
