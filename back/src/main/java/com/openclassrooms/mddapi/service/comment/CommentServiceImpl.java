package com.openclassrooms.mddapi.service.comment;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.CommentDto;
import com.openclassrooms.mddapi.mapper.CommentMapper;
import com.openclassrooms.mddapi.model.Comment;
import com.openclassrooms.mddapi.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class CommentServiceImpl implements CommentService {
    
	private CommentRepository commentRepository;
    private final CommentMapper commentMapper;

	@Override
	public List<Comment> getComments() {
		return commentRepository.findAll();
	}

	@Override
	public Comment findById(Long id) throws Exception {
		return commentRepository.findById(id).orElse(null);
	}

	@Override
	public List<Comment> findAll() throws Exception {
		return commentRepository.findAll();
	}

	@Override
	public Comment create(Comment comment) throws Exception {
		return commentRepository.save(comment);
	}

	@Override
	public Comment update(Long id, Comment comment) throws Exception {
		comment.setId(id);
		return commentRepository.save(comment);
	}

	@Override
	public String delete(Long id) throws Exception {
		commentRepository.deleteById(id);
		return "L'article a bien été supprimé.";
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

	public ResponseEntity<?> find_all() {
		
        List<Comment> comments;
        try {
            comments = findAll();
            return ResponseEntity.ok().body(this.commentMapper.toDto(comments));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}

	public ResponseEntity<?> create_comment(CommentDto commentDto) {
		
        log.info(commentDto);

        Comment comment;
        try {
            comment = create(this.commentMapper.toEntity(commentDto));

            log.info(comment);
            return ResponseEntity.ok().body(this.commentMapper.toDto(comment));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}

	public ResponseEntity<?> update_comment(String id, CommentDto commentDto) {
		
        try {
            Comment comment = update(Long.parseLong(id), this.commentMapper.toEntity(commentDto));

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
