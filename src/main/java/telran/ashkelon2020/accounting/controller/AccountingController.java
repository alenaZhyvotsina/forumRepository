package telran.ashkelon2020.accounting.controller;

import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.accounting.dao.UserRepositoryMongoDB;
import telran.ashkelon2020.accounting.dto.PasswordDto;
import telran.ashkelon2020.accounting.dto.UserDto;
import telran.ashkelon2020.accounting.dto.UserLoginDto;
import telran.ashkelon2020.accounting.dto.UserNewDto;
import telran.ashkelon2020.accounting.dto.UserUpdDto;
import telran.ashkelon2020.accounting.exception.ForbiddenException;
import telran.ashkelon2020.accounting.exception.UnAuthorizedException;
import telran.ashkelon2020.accounting.model.User;
import telran.ashkelon2020.accounting.service.UserService;
import telran.ashkelon2020.accounting.service.security.AccountingSecurity;

@RestController
@RequestMapping("/forum/user")
public class AccountingController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AccountingSecurity securityService;
	
	@PostMapping("/register")
	public UserDto addUser(@RequestBody UserNewDto userNewDto) {
		return userService.addUser(userNewDto);
	}
	
	@PostMapping("/login")
	//public UserDto findUser(@RequestBody UserLoginDto userLoginDto) {
	public UserDto findUser(@RequestHeader("Authorization") String token) {
		//UserLoginDto userLoginDto = tokenDecode(token);
		String userLogin = securityService.getLogin(token);
		securityService.checkExpDate(userLogin);
		return userService.findUser(userLogin/*, userLoginDto.getPassword()*/);
	}
	
	@DeleteMapping("/{login}")
	public UserDto deleteUser(@PathVariable String login, @RequestHeader("Authorization") String token) {
		String userLogin = securityService.getLogin(token);
		
		if(!userLogin.equals(login)) {
			throw new ForbiddenException();
		}
		//securityService.checkExpDate(userLogin);
		return userService.deleteUser(login);
	}
	
	@PutMapping("/{login}")
	public UserDto updateUser(@PathVariable String login, @RequestBody UserUpdDto userUpdDto,
							  @RequestHeader("Authorization") String token) {
		String userLogin = securityService.getLogin(token);
		securityService.checkExpDate(userLogin);
		if(!userLogin.equals(login)) {
			throw new ForbiddenException();
		}		
		
		return userService.updateUser(login, userUpdDto);
	}
	
	@PutMapping("/{login}/role/{role}")
	public UserDto addRole(@PathVariable String login, @PathVariable String role,
						   @RequestHeader("Authorization") String token) {	
		String userLogin = securityService.getLogin(token);
		securityService.checkExpDate(userLogin);
		securityService.checkAdmin(userLogin);
		
		return userService.addRole(login, role);
		
	}
	
	@DeleteMapping("/{login}/role/{role}")
	public UserDto deleteRole(@PathVariable String login, @PathVariable String role,
							  @RequestHeader("Authorization") String token) {
		String userLogin = securityService.getLogin(token);
		securityService.checkExpDate(userLogin);
		securityService.checkAdmin(userLogin);
		
		return userService.deleteRole(login, role);
	}
	
	@PutMapping("/password")
	public UserDto changePassword(/*@RequestBody PasswordDto passwordDto, */
					@RequestHeader("Authorization") String token,
					@RequestHeader("X-Password") String newPassword) {
		
		String userLogin = securityService.getLogin(token);
				
		return userService.changePassword(userLogin, 
				/*passwordDto.getPassword(), */ newPassword);
	}
	
	/*
	private UserLoginDto tokenDecode(String token) {
		try {
			String[] credentials = token.split(" ");
			String credential = new String(Base64.getDecoder().decode(credentials[1]));
			credentials = credential.split(":");
			return new UserLoginDto(credentials[0], credentials[1]);
		} catch (Exception e) {
			throw new UnAuthorizedException();
		}
	}
	*/

}
