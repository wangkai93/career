package cn.edu.zzia.career.control.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zzia.career.pojo.CmCompany;
import cn.edu.zzia.career.pojo.CmStudent;
import cn.edu.zzia.career.pojo.CmUser;
import cn.edu.zzia.career.service.CompanyService;
import cn.edu.zzia.career.service.StudentService;
import cn.edu.zzia.career.service.UserService;

@Controller
@RequestMapping("/api/login")
public class LoginController {

	private static final Integer ADMIN_USER = 1;
	private static final Integer STUDENT = 2;
	private static final Integer COMPANY = 3;

	@Autowired
	private UserService userService = null;

	@Autowired
	private StudentService stuService = null;

	@Autowired
	private CompanyService comService = null;

	/**
	 * 分别处理 客户端 管理员、学生和企业的登陆
	 * 
	 * @param username
	 * @param password
	 * @param type
	 *            1:管理员、2:学生、3:企业
	 * @return
	 */
	/**
	 * @param username
	 * @param password
	 * @param type
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Object login(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("type") Integer type) {

		if (type == ADMIN_USER) {
			CmUser user = userService.findlogin(username, password);
			return user;
		} else if (type == STUDENT) {
			CmStudent stu = stuService.findStudentByNameAndPassword(username, password);
			return stu;
		} else if (type == COMPANY) {
			CmCompany company = comService.findCompanyByNameAndPassword(username, password);
			return company;
		}
		return null;
	}

}
