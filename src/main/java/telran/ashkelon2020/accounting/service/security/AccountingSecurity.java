package telran.ashkelon2020.accounting.service.security;

public interface AccountingSecurity {
	
	String getLogin(String token);
	
	boolean checkExpDate(String login);
	
	boolean checkAdmin(String login);

}
