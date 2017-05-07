package cn.edu.zzia.career.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.zzia.career.dao.lt.TopicMapper;
import cn.edu.zzia.career.pojo.lt.Topic;
import cn.edu.zzia.career.utils.DateUtils;
import cn.edu.zzia.career.utils.IdUtils;

@Service
public class TopicServiceImpl implements TopicService {

	@Autowired
	private TopicMapper topicMapper;
	
	@Override
	public List<Topic> getTopicList(Map<String, Object> query) {
		return topicMapper.selectList(query);
	}

	@Override
	public Topic getTopic(String topicId) {
		return topicMapper.selectById(topicId);
	}

	@Override
	public int addTopic(Topic topic) {
		
		topic.setTopicId(IdUtils.id());
		topic.setCreateTime(DateUtils.getNowDateTime());
		topic.setStatus(0);
		topic.setCommentNum(0);
		
		return topicMapper.insert(topic);
	}

	@Override
	public List<Topic> getJoinTopicList(Map<String, Object> query) {
		return topicMapper.selectJoinList(query);
	}

	@Override
	public int update(Topic topic) {
		return topicMapper.update(topic);
	}
	
	@Override
	public int remove(String id) {
		return topicMapper.delete(id);
	}
	
}
