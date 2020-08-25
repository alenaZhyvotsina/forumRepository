package telran.ashkelon2020.forum.dto.user;

import java.time.LocalDateTime;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	String login;
	String password;
	String firstName;
	String lastName;
	LocalDateTime expDate;  // now() + 30 days
	Set<String> roles; // ADMIN, MODERATOR, USER (default)

}
