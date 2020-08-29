package telran.ashkelon2020.accounting.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2020.accounting.model.User;

public interface UserRepositoryMongoDB extends MongoRepository<User, String> {

	//Optional<User> findByIdAndPassword(String login, String password);

	//User findByLogin(String login);
	
	//void deleteByLogin(String login);
}
