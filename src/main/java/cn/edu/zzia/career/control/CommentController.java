package cn.edu.zzia.career.control;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.edu.zzia.career.pojo.CmUser;
import cn.edu.zzia.career.pojo.lt.Comment;
import cn.edu.zzia.career.query.BaseQuery;
import cn.edu.zzia.career.query.Query;
import cn.edu.zzia.career.rest.RestResult;
import cn.edu.zzia.career.service.CommentService;
import cn.edu.zzia.career.service.UserService;


@RestController
@RequestMapping("/comment")
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/add")
	public RestResult addComment(Comment comment){
		
		CmUser user = userService.findUserByUid(Integer.parseInt(comment.getUserId()));
		comment.setUserName(user.getUname());
		comment.setUserIcon(user.getUface());
		
		int result = commentService.addComment(comment);
		if(result <= 0){
			return RestResult.error("添加评论失败！");
		}
		
		return RestResult.success();
	}
	
	@RequestMapping("/{topicId}")
	public RestResult commentList(@PathVariable("topicId") String topicId, BaseQuery query){
		
		Map<String, Object> q = query.toMap();
		q.put("topicId", topicId);
		return RestResult.success().setResponse(commentService.getCommentList(q));
		
	}

}
