package telran.ashkelon2020.forum.service;

import telran.ashkelon2020.forum.dto.user.UserDto;
import telran.ashkelon2020.forum.dto.user.UserNewDto;
import telran.ashkelon2020.forum.dto.user.UserUpdDto;

public interface UserService {
	
	UserDto addUser(UserNewDto userDto);
	
	UserDto findUser(String login);
	
	UserDto deleteUser(String login);
	
	UserDto updateUser(String login, UserUpdDto userUpdDto);
	
	UserDto addRole(String login, String role);
	
	UserDto deleteRole(String login, String role);

}
