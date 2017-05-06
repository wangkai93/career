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

import cn.edu.zzia.career.pojo.CmArea;
import cn.edu.zzia.career.pojo.CmEmp;
import cn.edu.zzia.career.pojo.CmInter;
import cn.edu.zzia.career.pojo.CmJob;
import cn.edu.zzia.career.pojo.CmRecruit;
import cn.edu.zzia.career.pojo.CmStudent;
import cn.edu.zzia.career.pojo.CmUnemp;
import cn.edu.zzia.career.pojo.Message;
import cn.edu.zzia.career.service.EmpService;
import cn.edu.zzia.career.service.InterService;
import cn.edu.zzia.career.service.StudentService;
import cn.edu.zzia.career.service.UnempService;
import cn.edu.zzia.career.tools.DateUtil;

@Controller
@RequestMapping("/api/student")
public class StudentController {

	@Autowired
	private InterService interService = null;

	@Autowired
	private EmpService empService = null;

	@Autowired
	private UnempService unempService = null;

	@Autowired
	private StudentService studentService = null;

	@ModelAttribute
	public void loadStudentIfn(@RequestParam(value = "studentId", required = false) Integer studentId,
			Map<String, Object> map) {

		if (null != studentId) {
			CmStudent student = studentService.findBySid(studentId);
			if (null != student) {
				map.put("student", student);
			}
		}
	}

	@RequestMapping(value = "/updateStudentInfo", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public Message updateStudentInfo(@ModelAttribute("student") CmStudent student) {

		Message msg = null;
		try {
			studentService.updateStudent(student);
			msg = new Message(433, "update student info success");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg = new Message(434, "update student info failure");
			return msg;
		}
	}

	/**
	 * 修改就业信息
	 * 
	 * @param studentId
	 * @param jobId
	 * @param salary
	 * @param info
	 * @param wangqian
	 * @return
	 */
	@RequestMapping(value = "/changeGraduateInfo", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public Message changeGraduateInfo(CmEmp emp, @RequestParam(value = "studentId") Integer studentId,
			@RequestParam(value = "jobId", required = false) Integer jobId) {

		Message msg = null;
		try {
			CmStudent student = new CmStudent();
			student.setSid(studentId);
			if (null != jobId) {
				CmJob job = new CmJob();
				job.setJid(jobId);
				emp.setCmJobByJid(job);
			}
			emp.setCmStudentBySid(student);
			emp.setEstate(0);
			empService.saveCmEmp(emp);
			unempService.deleleteUnEmp(studentId);
			CmStudent stu = studentService.findBySid(studentId);
			stu.setSstate(5);
			studentService.updateStudent(stu);
			msg = new Message(200, "change graduate success");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg = new Message(202, "change graduate failure");
			return msg;
		}
	}

	/**
	 * 更新学生的未就业信息
	 * 
	 * @param unemp
	 * @param studentId
	 * @param jobId
	 * @return
	 */
	@RequestMapping(value = "/updateUnempInfo", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public Message changeUmempInfo(CmUnemp unemp, @RequestParam(value = "studentId") Integer studentId) {

		Message msg = null;
		try {
			CmStudent student = new CmStudent();
			student.setSid(studentId);
			unemp.setCmStudentBySid(student);
			unemp.setUestate(0);
			unempService.addUnEmp(unemp);
			empService.deleteEmp(studentId);
			CmStudent stu = studentService.findBySid(studentId);
			stu.setSstate(4);
			studentService.updateStudent(stu);
			msg = new Message(200, "change graduate success");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg = new Message(202, "change graduate failure");
			return msg;
		}
	}

	/**
	 * 学生投简历，相当于添加面试信息
	 * 
	 * @param studentId
	 * @param companyId
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value = "/deliverResume", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public Message deliverResume(@RequestParam("studentId") Integer studentId,
			@RequestParam("recruitId") Integer recruitId,
			@RequestParam(value = "areaId", required = false) Integer areaId) {

		Message msg = null;
		try {
			CmInter inter = new CmInter();
			CmStudent student = new CmStudent();
			student.setSid(studentId);

			CmRecruit recruit = new CmRecruit();
			recruit.setRid(recruitId);

			if (null != areaId) {
				CmArea area = new CmArea();
				area.setAid(areaId);
				inter.setCmAreaByAid(area);
			}

			inter.setCmStudentBySid(student);
			inter.setCmRecruitByRid(recruit);
			inter.setIsuccess(0);
			inter.setItime(new DateUtil().SysDate());
			inter.setItype("0");

			interService.saveInter(inter);
			msg = new Message(200, "add inter success");
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = new Message(201, "add inter failure");
			return msg;
		}
	}

	/**
	 * 根据学生id查找该学生所有的面试
	 * 
	 * @param studentId
	 * @return
	 */
	@RequestMapping(value = "/getStudentRecruit", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public List<CmInter> getStudentInter(@RequestParam("studentId") Integer studentId) {

		List<CmInter> inters = interService.findAllInterByStudentId(studentId);

		return inters;
	}

	/**
	 * 根据学生id和就业状态查找就业状态
	 * 
	 * @param studentId
	 * @param state
	 *            4：未就业，5就业
	 * @return
	 */
	@RequestMapping(value = "/getEmpState", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object getStudentStatus(@RequestParam("studentId") Integer studentId, @RequestParam("state") Integer state) {

		if (state == 4) {
			return unempService.findBySid(studentId);
		} else if (state == 5) {
			return empService.findEmpBySid(studentId);
		}

		return null;
	}
	
	@RequestMapping(value = "/getAllStudent",method = RequestMethod.GET,produces = {"application/json;charset=UTF-8"})
	@ResponseBody
	public List<CmStudent> getAllStudent(){
		return studentService.findAllStudent();
	}
}
