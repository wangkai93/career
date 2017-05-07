package cn.edu.zzia.career.service;

import java.util.List;
import java.util.Map;

import cn.edu.zzia.career.pojo.lt.Topic;


public interface TopicService {

	List<Topic> getTopicList(Map<String, Object> query);
	
	Topic getTopic(String topicId);
	
	int addTopic(Topic topic);
	
	List<Topic> getJoinTopicList(Map<String, Object> query);
	
	int update(Topic topic);
	
	int remove(String id);
}
