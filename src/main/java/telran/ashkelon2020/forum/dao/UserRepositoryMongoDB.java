package telran.ashkelon2020.forum.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2020.forum.model.User;

public interface UserRepositoryMongoDB extends MongoRepository<User, String> {

	//User findByLogin(String login);
	
	//void deleteByLogin(String login);
}
