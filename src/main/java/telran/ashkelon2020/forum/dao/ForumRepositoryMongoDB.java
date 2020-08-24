package telran.ashkelon2020.forum.dao;

import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.ashkelon2020.forum.model.Post;

public interface ForumRepositoryMongoDB extends MongoRepository<Post, String> {
	
	Stream<Post> findByAuthor(String author);

}
