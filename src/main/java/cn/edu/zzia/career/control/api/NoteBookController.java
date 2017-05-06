package cn.edu.zzia.career.control.api;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zzia.career.pojo.CmStudent;
import cn.edu.zzia.career.pojo.Message;
import cn.edu.zzia.career.pojo.NoteBook;
import cn.edu.zzia.career.service.NoteBookService;

@Controller
@RequestMapping("/api/note")
public class NoteBookController {

	@Autowired
	private NoteBookService noteBookService;

	/**
	 * 用于在更新时从数据库查询笔记
	 * 
	 * @param noteId
	 * @param map
	 */
	@ModelAttribute
	public void loadNoteBook(@RequestParam(value = "noteId", required = false) Integer noteId,
			Map<String, Object> map) {

		if (null != noteId) {
			NoteBook note = noteBookService.findById(noteId);
			if (null != note) {
				map.put("noteBook", note);
			}
		}

	}

	/**
	 * 新增一条笔记，并返回新增的消息
	 * 
	 * @param notebook
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Message save(NoteBook notebook,@RequestParam("studentId")Integer studentId) {
		Message msg = null;
		try {
			CmStudent student = new CmStudent();
			student.setSid(studentId);
			notebook.setStudent(student);
			noteBookService.save(notebook);
			msg = new Message(200, "add note success");
			return msg;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msg = new Message(204, "add note failure");
			return msg;
		}

	}

	/**
	 * 更新笔记的内容，并返回更新的消息
	 * 
	 * @param notebook
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Message update(@ModelAttribute("noteBook") NoteBook notebook) {
		Message msg = null;
		try {
			noteBookService.update(notebook);
			msg = new Message(200, "update note success");
			return msg;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msg = new Message(205, "update note failure");
			return msg;
		}
	}

	/**
	 * 根据笔记id删除一个笔记信息，并返回删除的消息
	 * 
	 * @param noteId
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Message deleteById(@RequestParam(value = "noteId") Integer noteId) {

		Message msg = null;
		try {
			noteBookService.deleteById(noteId);
			msg = new Message(200, "delete note success");
			return msg;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			msg = new Message(206, "delete note failure");
			return msg;
		}

	}

	/**
	 * 根据笔记id查询一个笔记信息，并返回json数据
	 * 
	 * @param noteId
	 * @return
	 */
	@RequestMapping(value = "/findOne", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public NoteBook findById(@RequestParam(value = "noteId") Integer noteId) {

		return noteBookService.findById(noteId);
	}

	/**
	 * 根据学生id查询该学生所有的笔记信息，并返回json数据
	 * 
	 * @param studentId
	 * @return
	 */
	@RequestMapping(value = "/findByStudentId", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public List<NoteBook> findAllNoteBookByStudentId(Integer studentId) {

		return noteBookService.findAllNoteBookByStudentId(studentId);
	}

}
