package com.openclassrooms.mddapi.service.post;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.PostDto;
import com.openclassrooms.mddapi.mapper.PostMapper;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.PostRepository;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.service.topic.TopicServiceImpl;
import com.openclassrooms.mddapi.service.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class PostServiceImpl implements PostService {

    @Autowired
	private final PostRepository postRepository;
    @Autowired
    private final PostMapper postMapper;
    @Autowired
    private final UserServiceImpl userService;
    @Autowired
    private final TopicServiceImpl topicService;
	
    /** 
     * @return List<Post>
     */
    @Override
	public List<Post> getPosts() {
		return postRepository.findAll();
	}
	
    /** 
     * @return List<Post>
     * @throws Exception
     */
    @Override
	public List<Post> findAll() throws Exception {
		return postRepository.findAll();
	}
    
    /** 
     * @param id
     * @return List<Post>
     * @throws Exception
     */
    @Override
    public List<Post> findAllByTopicId(Long id) throws Exception {
        Topic topic = topicService.findById(id);
        return postRepository.findAllByTopic(topic);
    }
	
    /** 
     * @param post
     * @return Post
     * @throws Exception
     */
    @Override
	public Post create(Post post) throws Exception {
		return postRepository.save(post);
	}
	
    /** 
     * @param id
     * @return Post
     * @throws Exception
     */
    @Override
	public Post findById(Long id) throws Exception {
		return postRepository.findById(id).orElse(null);
	}
	
    /** 
     * @param id
     * @return String
     * @throws Exception
     */
    @Override
	public String delete(Long id) throws Exception {
		postRepository.deleteById(id);
		return "L'article a bien été supprimé.";
	}
	
    /** 
     * @return ResponseEntity<?>
     */
    public ResponseEntity<?> find_all() {
		
        List<Post> posts;
        try {
            posts = findAll();
            return ResponseEntity.ok().body(this.postMapper.toDto(posts));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}
	
    /** 
     * @param postId
     * @return ResponseEntity<?>
     */
    public ResponseEntity<?> find_all_by_topic_id(String postId) {

        List<Post> posts;
        try {
            posts = findAllByTopicId(Long.valueOf(postId));
            return ResponseEntity.ok().body(this.postMapper.toDto(posts));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}	
	
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
    public ResponseEntity<?> find_by_id(String id) {

        try {
            Post post = findById(Long.valueOf(id));

            if (post == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(this.postMapper.toDto(post));

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}
	
    /** 
     * @param postDto
     * @param userDetails
     * @return ResponseEntity<?>
     */
    public ResponseEntity<?> create_post(PostDto postDto, UserDetailsImpl userDetails) {
		
        postDto.setCreatedAt(LocalDateTime.now());
        log.info(postDto);

        Post post;
        User userApp = new User();
        try {
            userApp = userService.findByEmail(userDetails.getUsername());
            postDto.setUser_name(userApp.getUserName()); 

            post = create(this.postMapper.toEntity(postDto));

            log.info(post);
            return ResponseEntity.ok().body(this.postMapper.toDto(post));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}
	
    /** 
     * @param id
     * @return ResponseEntity<?>
     */
    public ResponseEntity<?> delete_post(String id) {
		
        try {
            Post post = findById(Long.valueOf(id));

            if (post == null) {
                return ResponseEntity.notFound().build();
            }
			delete(Long.parseLong(id));
            return ResponseEntity.ok().build();

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}
}
