package cn.edu.zzia.career.service;

import java.util.Date;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.edu.zzia.career.dao.INoteBookDao;
import cn.edu.zzia.career.pojo.NoteBook;

@Service
public class NoteBookService {

	@Autowired
	private INoteBookDao noteBookDao;

	/**
	 * 增加一个笔记 信息
	 * 
	 * @param notebook
	 */
	public void save(NoteBook notebook) {

		if (null != notebook){
			
			Date date = new Date();
			notebook.setCreateAt(date);
			notebook.setUpdateAt(date);
			noteBookDao.save(notebook);
		}
	}

	/**
	 * 更新笔记信息
	 * 
	 * @param notebook
	 */
	public void update(NoteBook notebook) {
		if (null != notebook){
			notebook.setUpdateAt(new Date());
			noteBookDao.update(notebook);
		}
	}

	/**
	 * 根据笔记的id删除一个笔记
	 * 
	 * @param noteId
	 */
	public void deleteById(Integer noteId) {
		if (null != noteId) {
			noteBookDao.deleteByIds(noteId);
		}
	}

	/**
	 * 根据笔记的id查找一个笔记
	 * 
	 * @param noteId
	 * @return
	 */
	public NoteBook findById(Integer noteId) {

		if (null != noteId)
			return noteBookDao.findObjectById(noteId);

		return null;

	}

	/**
	 * 根据学生id查找该学生所有的笔记
	 * 
	 * @param studentId
	 * @return
	 */
	public List<NoteBook> findAllNoteBookByStudentId(Integer studentId) {

		if (null != studentId) {

			String whereHql = " and o.student.sid = ? ";
			Object[] params = { studentId };
			LinkedHashMap<String,String> orderby = new LinkedHashMap<>();
			orderby.put("o.updateAt","desc");
			return noteBookDao.findObjectsByConditionWithNoPage(whereHql, params,orderby);
		}

		return null;
	}

}
