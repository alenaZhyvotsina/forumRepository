package telran.ashkelon2020.forum.dto.user;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter 
@NoArgsConstructor
@AllArgsConstructor
public class UserNewDto {
	
	String login;
	String password;
	String firstName;
	String lastName;
	Set<String> roles; // ADMIN, MODERATOR, USER (default)

}
