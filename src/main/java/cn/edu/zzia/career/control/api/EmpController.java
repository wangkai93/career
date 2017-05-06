package cn.edu.zzia.career.control.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.edu.zzia.career.pojo.CmEmp;
import cn.edu.zzia.career.pojo.CmUnemp;
import cn.edu.zzia.career.service.EmpService;
import cn.edu.zzia.career.service.UnempService;

@Controller
@RequestMapping("/api/emp")
public class EmpController {
	

	@Autowired
	private EmpService empService;

	@Autowired
	private UnempService unempService;


	@RequestMapping(value = "/findAllEmp", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public List<CmEmp> findAllEmp() {
		
		List<CmEmp> list = empService.findAllEmp();
		return list;
	}

	@RequestMapping(value = "/findAllUnemp", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public List<CmUnemp> findAllUnEmp() {

		return unempService.findAllUnemp();
	}

}
