package cn.edu.zzia.career.service;

import java.util.List;
import java.util.Map;

import cn.edu.zzia.career.pojo.lt.Comment;


public interface CommentService {

	List<Comment> getCommentList(Map<String, Object> query);
	
	int addComment(Comment comment);
	
		
}
