package telran.ashkelon2020.forum.service;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import telran.ashkelon2020.forum.dao.UserRepositoryMongoDB;
import telran.ashkelon2020.forum.dto.user.UserDto;
import telran.ashkelon2020.forum.dto.user.UserNewDto;
import telran.ashkelon2020.forum.dto.user.UserUpdDto;
import telran.ashkelon2020.forum.exceptions.UserNotFoundException;
import telran.ashkelon2020.forum.model.User;

@Component
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepositoryMongoDB userRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public UserDto addUser(UserNewDto userNewDto) {
		Set<String> roles = userNewDto.getRoles();		
		if(roles == null || roles.isEmpty()) {
			roles = new HashSet<>();
		}
		
		User user = 
				new User(userNewDto.getLogin(), userNewDto.getPassword(), 
						 userNewDto.getFirstName(), userNewDto.getLastName(), roles);
		
		User userSaved = userRepository.save(user);
		return modelMapper.map(userSaved, UserDto.class);
	}

	@Override
	public UserDto findUser(String login) {
		/*
		User user = userRepository.findByLogin(login);		
		if(user == null) {
			throw new UserNotFoundException(login);
		}
		*/
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));		
		
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto deleteUser(String login) {
		/*
		User user = userRepository.findByLogin(login);
		if(user == null) {
			throw new UserNotFoundException(login);
		}
		*/
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		userRepository.delete(user);
		
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto updateUser(String login, UserUpdDto userUpdDto) {
		/*
		User user = userRepository.findByLogin(login);
		if(user == null) {
			throw new UserNotFoundException(login);
		}
		*/
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		
		String name = userUpdDto.getFirstName();
		if(name != null && !name.isEmpty()) {
			user.setFirstName(name);
		}
		String surname = userUpdDto.getLastName();
		if(surname != null && !surname.isEmpty()) {
			user.setLastName(surname);
		}
		
		User userSaved = userRepository.save(user);
		
		return modelMapper.map(userSaved, UserDto.class);
	}

	@Override
	public UserDto addRole(String login, String role) {
		/*
		User user = userRepository.findByLogin(login);
		if(user == null) {
			throw new UserNotFoundException(login);
		}
		*/
		
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		
		if(user.addRole(role)) {
			user = userRepository.save(user);
		}
		
		return modelMapper.map(user, UserDto.class);
	}

	@Override
	public UserDto deleteRole(String login, String role) {
		/*
		User user = userRepository.findByLogin(login);
		if(user == null) {
			throw new UserNotFoundException(login);
		}
		*/
		
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		
		if(user.deleteRole(role)) {
			user = userRepository.save(user);
		}
		
		return modelMapper.map(user, UserDto.class);
	}

}
