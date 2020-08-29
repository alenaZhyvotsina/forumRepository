package telran.ashkelon2020.accounting.service.security;

import java.time.LocalDateTime;
import java.util.Base64;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.ashkelon2020.accounting.dao.UserRepositoryMongoDB;
import telran.ashkelon2020.accounting.dto.UserLoginDto;
import telran.ashkelon2020.accounting.exception.ForbiddenException;
import telran.ashkelon2020.accounting.exception.NotAdminException;
import telran.ashkelon2020.accounting.exception.UnAuthorizedException;
import telran.ashkelon2020.accounting.exception.UserNotFoundException;
import telran.ashkelon2020.accounting.model.User;

@Service
public class AccountSecurityImpl implements AccountingSecurity {

	@Autowired
	UserRepositoryMongoDB userRepository;
	
	@Override
	public String getLogin(String token) {
		UserLoginDto userLoginDto = tokenDecode(token);
		User user = 
				userRepository.findById(userLoginDto.getLogin()).orElseThrow(() -> new UserNotFoundException(userLoginDto.getLogin()));		
		
		if(!BCrypt.checkpw(userLoginDto.getPassword(), user.getPassword())) {
			throw new UnAuthorizedException();
		}
		/*
		if(user.getExpDate().isBefore(LocalDateTime.now())) {
			throw new ForbiddenException();
		}
		*/
		if(user.getRoles().isEmpty()) {
			throw new ForbiddenException();
		}
		return user.getLogin();
	}
	
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

	@Override
	public boolean checkExpDate(String login) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));		
		
		if(user.getExpDate().isBefore(LocalDateTime.now())) {
			throw new ForbiddenException();
		}
		return true;
	}
	
	@Override
	public boolean checkAdmin(String login) {
		User user = userRepository.findById(login).orElseThrow(() -> new UserNotFoundException(login));
		
		if(user.getRoles().contains("ADMIN")) {
			return true;
		}
		else {
			throw new NotAdminException(login);
		}		
	}

}
