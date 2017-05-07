package cn.edu.zzia.career.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.zzia.career.dao.lt.CommentMapper;
import cn.edu.zzia.career.dao.lt.TopicMapper;
import cn.edu.zzia.career.pojo.lt.Comment;
import cn.edu.zzia.career.pojo.lt.Topic;
import cn.edu.zzia.career.utils.DateUtils;
import cn.edu.zzia.career.utils.IdUtils;


@Service
public class CommentServiceImpl implements CommentService {
	
	@Autowired
	private CommentMapper commentMapper;
	
	@Autowired
	private TopicMapper topicMapper;

	@Override
	public List<Comment> getCommentList(Map<String, Object> query) {
		return commentMapper.selectList(query);
	}

	@Override
	@Transactional()
	public int addComment(Comment comment) {
		
		comment.setCommentId(IdUtils.id());
		comment.setStatus(1);
		comment.setCreateTime(DateUtils.getNowDateTime());
		
		Topic topic = topicMapper.selectById(comment.getTopicId());
		topic.setCommentNum(topic.getCommentNum()+1);
		
		return commentMapper.insert(comment) & topicMapper.update(topic);
	}
}
