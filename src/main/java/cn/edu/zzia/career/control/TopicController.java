package cn.edu.zzia.career.control;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.zzia.career.pojo.CmUser;
import cn.edu.zzia.career.pojo.lt.Topic;
import cn.edu.zzia.career.query.BaseQuery;
import cn.edu.zzia.career.query.Query;
import cn.edu.zzia.career.query.TopicQuery;
import cn.edu.zzia.career.rest.RestResult;
import cn.edu.zzia.career.service.CommentService;
import cn.edu.zzia.career.service.TopicService;
import cn.edu.zzia.career.service.UserService;


@RestController
@RequestMapping("/topic")
public class TopicController {
	
	@Autowired
	private TopicService topicService;
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/list")
	public RestResult getList(TopicQuery query){
		return RestResult.success().setResponse(topicService.getTopicList(query.toMap()));
	}
	
	@RequestMapping("/{topicId}")
	public RestResult getTopic(@PathVariable("topicId") String topicId, BaseQuery query){
		
		Topic topic = topicService.getTopic(topicId);
		Map<String, Object> q = query.toMap();
		q.put("topicId", topicId);
		topic.setCommentList(commentService.getCommentList(q));
		
		return RestResult.success().setResponse(topic);
	}
	
	@RequestMapping("/add")
	public RestResult addTopic(Topic topic){
		
		CmUser user = userService.findUserByUid(Integer.parseInt(topic.getUserId()));
		topic.setUserName(user.getUname());
		topic.setIcon(user.getUface());
		
		int result = topicService.addTopic(topic);
		if(result <= 0){
			return RestResult.error("添加话题失败！");
		}
		
		return RestResult.success();
	}
	
	@RequestMapping("/join")
	public RestResult join(TopicQuery query){
		return RestResult.success().setResponse(topicService.getJoinTopicList(query.toMap()));
	}
	
	@RequestMapping("/update")
	public RestResult update(Topic topic){
		int result = topicService.update(topic);
		if(result > 0){
			return RestResult.success();
		}
		return RestResult.error("失败");
	}
	
	@RequestMapping("/rm/{id}")
	public RestResult remove(@PathVariable("id") String id){
		int result = topicService.remove(id);
		if(result > 0){
			return RestResult.success();
		}
		return RestResult.error("失败");
	}
	
}
