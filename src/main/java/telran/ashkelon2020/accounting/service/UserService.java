package telran.ashkelon2020.accounting.service;

import telran.ashkelon2020.accounting.dto.UserDto;
import telran.ashkelon2020.accounting.dto.UserNewDto;
import telran.ashkelon2020.accounting.dto.UserUpdDto;

public interface UserService {
	
	UserDto addUser(UserNewDto userDto);
	
	UserDto findUser(String login/*, String password*/);
	
	UserDto deleteUser(String login);
	
	UserDto updateUser(String login, UserUpdDto userUpdDto);
	
	UserDto addRole(String login, String role);
	
	UserDto deleteRole(String login, String role);
	
	UserDto changePassword(String login, /*String password,*/ String newPassword);

}
