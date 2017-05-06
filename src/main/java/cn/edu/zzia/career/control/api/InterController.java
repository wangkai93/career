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

import cn.edu.zzia.career.pojo.CmInter;
import cn.edu.zzia.career.pojo.Message;
import cn.edu.zzia.career.service.InterService;

@Controller
@RequestMapping("/api/inter")
public class InterController {

	@Autowired
	private InterService interService;

	@ModelAttribute
	public void loadInterInfo(@RequestParam(value = "interId",required = false) Integer interId, Map<String, Object> map) {

		if (null != interId) {
			CmInter inter = interService.findByIid(interId);
			if (null != inter) {
				map.put("inter", inter);
			}
		}
	}

	/**
	 * 查询企业相关的所有面试，即学生投简历
	 * 
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/getInterviews", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public List<CmInter> getCompanyInters(@RequestParam("companyId") Integer companyId) {

		List<CmInter> inters = interService.findInterByCompanyId(companyId);
		return inters;
	}

	/**
	 * 根据面试id,查询一个面试信息
	 * 
	 * @param interId
	 * @return
	 */
	@RequestMapping(value = "/getOne", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public CmInter getCmInterById(@RequestParam("interId") Integer interId) {
		return interService.findByIid(interId);
	}

	/**
	 * 企业更新面试信息
	 * 
	 * @param inter
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Message verifyInterview(@ModelAttribute("inter") CmInter inter) {

		Message msg = null;
		try {
			interService.updateInter(inter);
			msg = new Message(200, "verify inter success");
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			msg = new Message(218, "verify inter failure");
			return msg;
		}
	}

}
