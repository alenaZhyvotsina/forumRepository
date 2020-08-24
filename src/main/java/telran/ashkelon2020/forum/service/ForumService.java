package telran.ashkelon2020.forum.service;

import java.util.List;

import telran.ashkelon2020.forum.dto.CommentDto;
import telran.ashkelon2020.forum.dto.PostDto;
import telran.ashkelon2020.forum.dto.PostResponseDto;

public interface ForumService {
	
	PostResponseDto addPost(String author, PostDto postDto);
	
	PostResponseDto findPostById(String id);
	
	PostResponseDto deletePost(String id);
	
	PostResponseDto updatePost(String id, PostDto postDto);
	
	boolean addLikeToPost(String id);
	
	PostResponseDto addCommentToPost(String id, String author, CommentDto commentDto);
	
	//PostResponseDto[] findPostsByAuthor(String author);
	List<PostResponseDto> findPostsByAuthor(String author);

}
