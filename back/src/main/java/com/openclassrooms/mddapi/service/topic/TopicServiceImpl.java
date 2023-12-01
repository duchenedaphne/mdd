package com.openclassrooms.mddapi.service.topic;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.openclassrooms.mddapi.dto.TopicDto;
import com.openclassrooms.mddapi.exception.BadRequestException;
import com.openclassrooms.mddapi.exception.NotFoundException;
import com.openclassrooms.mddapi.mapper.TopicMapper;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import com.openclassrooms.mddapi.repository.TopicRepository;
import com.openclassrooms.mddapi.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class TopicServiceImpl implements TopicService {

	private TopicRepository topicRepository;
	private final TopicMapper topicMapper;
	private UserRepository userRepository;

	@Override
	public List<Topic> getTopics() {
		return topicRepository.findAll();
	}

	@Override
	public Topic create(Topic topic) throws Exception {
		return topicRepository.save(topic);
	}

	@Override
	public Topic findById(Long id) throws Exception {
		return topicRepository.findById(id).orElse(null);
	}

	@Override
	public Topic update(Long id, Topic topic) throws Exception {
		topic.setId(id);
		return topicRepository.save(topic);
	}

	@Override
	public String delete(Long id) throws Exception {
		topicRepository.deleteById(id);
		return "Le thème a bien été supprimé.";
	}

	@Override
	public List<Topic> findAll() throws Exception {
		return topicRepository.findAll();
	}

	@Override
	public void subscribe(Long id, Long userId) throws Exception {
		
		Topic topic = topicRepository.findById(id).orElse(null);

		User user = userRepository.findById(userId).orElse(null);

		if (topic == null || user == null) {
			throw new NotFoundException();
		}

		boolean alreadySubscribe = topic.getUsers().stream().anyMatch(subscriber -> subscriber.getId().equals(userId));
        
        if(alreadySubscribe) {
            throw new BadRequestException();
        }

		topic.getUsers().add(user);

		topicRepository.save(topic);
	}

	@Override
	public void unSubscribe(Long id, Long userId) throws Exception {
		
		Topic topic = topicRepository.findById(id).orElse(null);

		if (topic == null) {
			throw new NotFoundException();
		}

		boolean alreadySubscribe = topic.getUsers().stream().anyMatch(subscriber -> subscriber.getId().equals(userId));
        
        if(!alreadySubscribe) {
            throw new BadRequestException();
        }

		topic.setUsers(topic.getUsers().stream().filter(subscriber -> !subscriber.getId().equals(userId)).collect(Collectors.toList()));

		topicRepository.save(topic);
	}
	
	public ResponseEntity<?> find_by_id(String id) {

        try {
            Topic topic = findById(Long.valueOf(id));

            if (topic == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok().body(this.topicMapper.toDto(topic));

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}

	public ResponseEntity<?> find_all() {
		
        List<Topic> topics;
        try {
            topics = findAll();
            return ResponseEntity.ok().body(this.topicMapper.toDto(topics));
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}

	public ResponseEntity<?> create_topic(TopicDto topicDto) {
		
        log.info(topicDto);

        Topic topic;
        try {
            topic = create(this.topicMapper.toEntity(topicDto));

            log.info(topic);
            return ResponseEntity.ok().body(this.topicMapper.toDto(topic));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}

	public ResponseEntity<?> update_topic(String id, TopicDto topicDto) {
		
        try {
            Topic topic = update(Long.parseLong(id), this.topicMapper.toEntity(topicDto));

            return ResponseEntity.ok().body(this.topicMapper.toDto(topic));

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
	}

	public ResponseEntity<?> delete_topic(String id) {
		
        try {
            Topic topic = findById(Long.valueOf(id));

            if (topic == null) {
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
	
	public ResponseEntity<?> subscription(String id, String userId) {
		
        try {
            subscribe(Long.parseLong(id), Long.parseLong(userId));

            return ResponseEntity.ok().build();

        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
			e.printStackTrace();
            return ResponseEntity.badRequest().build();
		}
	}

	public ResponseEntity<?> subscription_cancelling(String id, String userId) {
		
        try {
            unSubscribe(Long.parseLong(id), Long.parseLong(userId));

            return ResponseEntity.ok().build();
			
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
			e.printStackTrace();
            return ResponseEntity.badRequest().build();
		}
	}
}
