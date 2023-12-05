package com.openclassrooms.mddapi.service.topic;

import java.util.List;

import com.openclassrooms.mddapi.model.Topic;

public interface TopicService {

	List<Topic> getTopics();

    public Topic create(Topic topic) throws Exception;

    public Topic findById(Long id) throws Exception;

    public String delete(Long id) throws Exception;

    public List<Topic> findAll() throws Exception;

	public void subscribe(Long id, Long userId) throws Exception;

	public void unSubscribe(Long id, Long userId) throws Exception;
}
