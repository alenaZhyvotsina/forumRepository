package telran.ashkelon2020.forum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.ashkelon2020.forum.dto.user.UserDto;
import telran.ashkelon2020.forum.dto.user.UserNewDto;
import telran.ashkelon2020.forum.dto.user.UserUpdDto;
import telran.ashkelon2020.forum.service.UserService;

@RestController
@RequestMapping("/forum/user")
public class AccountingController {
	
	@Autowired
	UserService userService;
	
	@PostMapping("")
	public UserDto addUser(@RequestBody UserNewDto userNewDto) {
		return userService.addUser(userNewDto);
	}
	
	@GetMapping("/{login}")
	public UserDto findUser(@PathVariable String login) {
		return userService.findUser(login);
	}
	
	@DeleteMapping("/{login}")
	public UserDto deleteUser(@PathVariable String login) {
		return userService.deleteUser(login);
	}
	
	@PutMapping("/{login}")
	public UserDto updateUser(@PathVariable String login, @RequestBody UserUpdDto userUpdDto) {
		return userService.updateUser(login, userUpdDto);
	}
	
	@PutMapping("/{login}/role/{role}")
	public UserDto addRole(@PathVariable String login, @PathVariable String role) {
		return userService.addRole(login, role);
	}
	
	@DeleteMapping("/{login}/role/{role}")
	public UserDto deleteRole(@PathVariable String login, @PathVariable String role) {
		return userService.deleteRole(login, role);
	}

}
