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
import cn.edu.zzia.career.pojo.CmCompany;
import cn.edu.zzia.career.pojo.CmJob;
import cn.edu.zzia.career.pojo.CmRecruit;
import cn.edu.zzia.career.pojo.Message;
import cn.edu.zzia.career.service.RecruitService;

@Controller
@RequestMapping("/api/recruit")
public class RecruitController {

	@Autowired
	private RecruitService recruitService = null;
	
	@ModelAttribute
	public void loadRecruitInfo(@RequestParam(value = "recruitId" , required = false) Integer recruitId, Map<String, Object> map) {

		if (null != recruitId) {
			CmRecruit recruit = recruitService.findByRid(recruitId);
			if (null != recruit) {
				map.put("recruit", recruit);
			}
		}
	}

	/**
	 * 添加招聘信息
	 * 
	 * @param recruit
	 * @param jobId
	 * @param companyId
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public Message addRecruit(CmRecruit recruit, @RequestParam(value = "jobId",required = false) Integer jobId,
			@RequestParam("companyId") Integer companyId,
			@RequestParam(value = "areaId", required = false) Integer areaId) {
		Message msg = null;
		try {
			if(null != jobId){
				CmJob job = new CmJob();
				job.setJid(jobId);
				recruit.setCmJobByJid(job);
			}

			CmCompany company = new CmCompany();
			company.setCid(companyId);

			if (null != areaId) {
				CmArea area = new CmArea();
				area.setAid(areaId);
				recruit.setCmAreaByAid(area);
			}
			
			recruit.setRstate(2);
			recruit.setCmCompanyByCid(company);

			recruitService.addRecruit(recruit);
			msg = new Message(200, "add recruit success");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg = new Message(208, "add recruit failure");
			return msg;
		}
	}

	/**
	 * 根据招聘信息的id查询一个招聘的详细信息
	 * 
	 * @param recruitId
	 * @return
	 */
	@RequestMapping(value = "/getOne", method = RequestMethod.GET, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public CmRecruit getOneRecruitById(@RequestParam("recruitId") Integer recruitId) {

		CmRecruit recruit = recruitService.findRecruitById(recruitId);
		return recruit;
	}
	
	/**
	 * 提供查询招聘信息的接口
	 * 
	 * @param type
	 *            type=1 校招 type=0 直招 type=null 校招直招都有
	 * @return
	 */
	@RequestMapping(value = "/findRecruits", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public List<CmRecruit> getRecruitInfoByType(@RequestParam(value = "type", required = false) Integer type) {

		List<CmRecruit> list = recruitService.findRecruitByType(type);
		return list;
	}
	
	
	/**
	 * 根据状态查询招聘信息
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "/findRecruitByState", method = RequestMethod.GET, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public List<CmRecruit> getRecruitInfoByState(@RequestParam(value = "state", required = false) Integer state) {

		List<CmRecruit> list = recruitService.findRecruitByState(state);
		return list;
	} 
	
	/**
	 * 审核招聘信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.POST, produces = {
			"application/json;charset=UTF-8" })
	@ResponseBody
	public Message verifyRecruitInfo(@ModelAttribute("recruit") CmRecruit recruit) {

		Message msg = null;
		try {
			recruitService.updateRecruit(recruit);
			msg = new Message(200, "verify recruit success");
			return msg;
		} catch (Exception e) {
			e.printStackTrace();
			msg = new Message(211, "verify recruit failure");
			return msg;
		}
	}

}
