package telran.ashkelon2020.forum.service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import telran.ashkelon2020.forum.dao.ForumRepositoryMongoDB;
import telran.ashkelon2020.forum.dto.CommentDto;
import telran.ashkelon2020.forum.dto.CommentResponceDto;
import telran.ashkelon2020.forum.dto.DatePeriodDto;
import telran.ashkelon2020.forum.dto.PostDto;
import telran.ashkelon2020.forum.dto.PostResponseDto;
import telran.ashkelon2020.forum.exceptions.PostNotFoundException;
import telran.ashkelon2020.forum.model.Comment;
import telran.ashkelon2020.forum.model.Post;

@Component
public class ForumServiceImpl implements ForumService {
	
	@Autowired
	ForumRepositoryMongoDB forumRepository;
	
	@Autowired
	ModelMapper modelMapper;

	@Override
	public PostResponseDto addPost(String author, PostDto postDto) {		
		/*
		Set<String> tags = new HashSet<>();		
		String[] tagsFromDto = postDto.getTags();
		if(tagsFromDto != null && tagsFromDto.length > 0) {
			for(String tag : tagsFromDto) {
				tags.add(tag);
			}
		}		
		Post post = new Post(postDto.getTitle(), postDto.getContent(), author, tags);
		*/
		Post post = new Post(postDto.getTitle(), postDto.getContent(), author, postDto.getTags());
		Post postSaved = forumRepository.save(post);
		
		return modelMapper.map(postSaved, PostResponseDto.class);
	}

	@Override
	public PostResponseDto findPostById(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

		return modelMapper.map(post, PostResponseDto.class);
	}

	@Override
	public PostResponseDto deletePost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

		forumRepository.delete(post);
		
		return modelMapper.map(post, PostResponseDto.class);
	}

	@Override
	public PostResponseDto updatePost(String id, PostDto postDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));

		String title = postDto.getTitle();
		if(title != null && !title.isEmpty()) {
			post.setTitle(title);
		}
		String content = postDto.getContent();
		if(content != null && !content.isEmpty()) {
			post.setContent(content);
		}
		/*
		String[] tags = postDto.getTags();
		if(tags != null && tags.length > 0) {
			for(String tag : tags) {
				post.addTag(tag);
			}			
		}
		*/
		Set<String> tags = postDto.getTags();
		if(tags != null) {
			tags.forEach(t -> post.addTag(t));
		}
		
		Post postSaved = forumRepository.save(post);
				
		return modelMapper.map(postSaved, PostResponseDto.class);
	}

	@Override
	public boolean addLikeToPost(String id) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		
		post.addLike();
		forumRepository.save(post);
		
		return true;
	}

	@Override
	public PostResponseDto addCommentToPost(String id, String author, CommentDto commentDto) {
		Post post = forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		
		post.addComment(new Comment(author, commentDto.getMessage()));
		Post postSaved = forumRepository.save(post);
		
		return modelMapper.map(postSaved, PostResponseDto.class);
	}

	@Override
	public List<PostResponseDto> findPostsByAuthor(String author) {
		return forumRepository.findByAuthor(author)
				.map(p -> modelMapper.map(p, PostResponseDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PostResponseDto> findPostsByTags(List<String> tags) {
		return forumRepository.findByTagsIn(tags)
				.map(p -> modelMapper.map(p, PostResponseDto.class))
				.collect(Collectors.toList());
				
	}

	@Override
	public Iterable<PostResponseDto> findPostsByDates(DatePeriodDto datePeriodDto) {
		
		// это делается по умолчанию
		LocalDateTime dateFrom = LocalDateTime.of(datePeriodDto.getDateFrom(), LocalTime.MIDNIGHT);
		LocalDateTime dateTo = LocalDateTime.of(datePeriodDto.getDateTo(), LocalTime.MIDNIGHT);
		
		//return forumRepository.findByDateCreatedBetween(dateFrom, dateTo)
		return forumRepository.findByDateCreatedBetween(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo())		
				.map(p -> modelMapper.map(p, PostResponseDto.class))
				.collect(Collectors.toList());
	}
	/*
	 ISODate("2020-08-25T00:00:00Z")
	{ $dateFromString: {
	     dateString: "2017-02-08"
	} }*/

	@Override
	public Iterable<CommentResponceDto> findAllPostComments(String id) {
		/*
		forumRepository.findAllPostComments(id).forEach(c -> System.out.println(c));
		
		return forumRepository.findAllPostComments(id)
				.map(c -> modelMapper.map(c, CommentResponceDto.class))
				.collect(Collectors.toList());
		*/
		
		return forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id))
				.getComments().stream()
				.map(c -> modelMapper.map(c, CommentResponceDto.class))
				.collect(Collectors.toList());

				
	}

	@Override
	public Iterable<CommentResponceDto> findAllPostCommentsByAuthor(String id, String user) {
		/*
		forumRepository.findAllPostCommentsByAuthor(id, author).forEach(c -> System.out.println(c));
		
		return forumRepository.findAllPostCommentsByAuthor(id, author)
				.map(c -> modelMapper.map(c, CommentResponceDto.class))
				.collect(Collectors.toList());
		*/
		
		return forumRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id))
				.getComments().stream()
				.filter(c -> user.equalsIgnoreCase(c.getUser()))
				.map(c -> modelMapper.map(c, CommentResponceDto.class))
				.collect(Collectors.toList());
	}

}