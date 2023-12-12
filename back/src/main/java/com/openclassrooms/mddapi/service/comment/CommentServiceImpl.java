package com.openclassrooms.mddapi.service.comment;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.model.Post;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.CommentRepository;
import com.openclassrooms.mddapi.security.services.UserDetailsImpl;
import com.openclassrooms.mddapi.service.post.PostServiceImpl;
import com.openclassrooms.mddapi.service.user.UserServiceImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentServiceImpl implements CommentService {
    
    @Autowired
	private final CommentRepository commentRepository;
    @Autowired
    private final CommentMapper commentMapper;
    @Autowired
    private final PostServiceImpl postService;
    @Autowired
    private final UserServiceImpl userService;

	@Override
	public List<Comment> getComments() {
		return commentRepository.findAll();
	}

	@Override
	public List<Comment> findAll() throws Exception {
		return commentRepository.findAll();
	}

    @Override
    public List<Comment> findAllByPostId(Long id) throws Exception {
        Post post = postService.findById(id);
        return commentRepository.findAllByPost(post);
    }

	@Override
	public Comment create(Comment comment) throws Exception {
		return commentRepository.save(comment);
	}

	@Override
	public Comment findById(Long id) throws Exception {
		return commentRepository.findById(id).orElse(null);
	}

	@Override
	public String delete(Long id) throws Exception {
		commentRepository.deleteById(id);
		return "L'article a bien été supprimé.";
	}

	public ResponseEntity<?> find_all_by_post_id(String postId) {

        List<Comment> comments;
        try {
            comments = findAllByPostId(Long.valueOf(postId));
            return ResponseEntity.ok().body(this.commentMapper.toDto(comments));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}

	public ResponseEntity<?> create_comment(CommentDto commentDto, UserDetailsImpl userDetails) {
		
        commentDto.setCreatedAt(LocalDateTime.now());
        log.info(commentDto);

        Comment comment;
        User userApp = new User();
        try {
            userApp = userService.findByEmail(userDetails.getUsername());
            commentDto.setUser_id(userApp.getId()); 

            comment = create(this.commentMapper.toEntity(commentDto));

            log.info(comment);
            return ResponseEntity.ok().body(this.commentMapper.toDto(comment));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}
	
	public ResponseEntity<?> find_by_id(String id) {

        try {
            Comment comment = findById(Long.valueOf(id));

            if (comment == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(this.commentMapper.toDto(comment));

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}

	public ResponseEntity<?> delete_comment(String id) {
		
        try {
            Comment comment = findById(Long.valueOf(id));

            if (comment == null) {
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
